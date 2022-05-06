package org.tojaco;

import org.tojaco.FileIO.TwitterFileService;
import twitter4j.*;

import java.io.*;
import java.util.HashSet;
import java.util.List;

//This class is a tweet gatherer using the twitter search API, it asks Twitter to give tweets that contain hashtags from our configuration file
//Newline characters are removed from tweets and replaced with a space
public class GrabTweets {

    // TEST CODE FROM STACK OVERFLOW
    // TO TRY AND AVOID EXCEEDING RATE LIMITS
    private void handleRateLimit(RateLimitStatus rateLimitStatus) {
        if (rateLimitStatus != null) {
            int remaining = rateLimitStatus.getRemaining();
            int resetTime = rateLimitStatus.getSecondsUntilReset();
            int sleep;
            if (remaining == 0) {
                sleep = resetTime + 1; //adding 1 more seconds
            } else {
                sleep = (resetTime / remaining) + 1; //adding 1 more seconds
            }

            try {
                Thread.sleep(Math.max(sleep * Configuration.getSleepTime(), 0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // CAUTION: will keep running for a very long time.
    // don't run for too long or the API KEY's might get suspended ;)
    public void grabSomeTweets(TwitterFactory tf, TwitterFileService tfs) throws IOException {

        String[] hashTags = Configuration.getHashTags();
        final HashSet<Long> foundTweetIDS = tfs.getFoundTweetIDS();
        final HashSet<String> foundUserHandles = tfs.getFoundUserHandles();

        for (String hashTag : hashTags) {
            try {
                Query query = new Query(hashTag);
                query.setCount(Configuration.getBatchSize());
                query.setResultType(Query.ResultType.recent);
                query.setLang(Configuration.getLanguage());
                QueryResult result;
                do {
                    result = tf.getInstance().search(query);
                    handleRateLimit(result.getRateLimitStatus());
                    List<Status> tweets = result.getTweets();
                    for (Status tweet : tweets) {
                        User user = tweet.getUser();
                        if (!(foundTweetIDS.contains(tweet.getId()))) {
                            tfs.writeTweet(tweet, tweet.getRetweetedStatus() != null);
                            foundTweetIDS.add(tweet.getId());
                        }
                        String userName = "@" + user.getScreenName();
                        if (!(foundUserHandles.contains(userName))) {
                            tfs.writeUser(user);
                            foundUserHandles.add(userName);
                        }
                    }

                } while ((query = result.nextQuery()) != null);

            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to search tweets: " + te.getMessage());
            }
        }
        System.exit(0);
    }

}