import twitter4j.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GrabTweets {
    HashSet<Long> foundTweets = new HashSet<>();
    ArrayList<User> users = new ArrayList<>();

    // inspired by: https://stackoverflow.com/questions/44611659/rate-limit-exceeded
    // TO TRY AND AVOID EXCEEDING RATE LIMITS
    private void handleRateLimit(RateLimitStatus rateLimitStatus, Configuration configuration) {
        if (rateLimitStatus != null) {
            int remaining = rateLimitStatus.getRemaining();
            int resetTime = rateLimitStatus.getSecondsUntilReset();
            int sleep;
            if (remaining == 0) {
                sleep = resetTime + 1;
            } else {
                sleep = (resetTime / remaining) + 1;
            }

            try {
                Thread.sleep(Math.max(sleep * configuration.getSleepTime(), 0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void grabSomeTweets(TwitterFactory tf, Configuration configuration) throws IOException {

        String[] hashTags = configuration.getHashTags();

        for (int i = 0; i < hashTags.length; i++) {
            try {
                Query query = new Query(hashTags[i]);
                query.setCount(configuration.getBatchSize());
                query.setResultType(Query.ResultType.recent);
                query.setLang(configuration.getLanguage());

                QueryResult result;
                boolean retweet = false;
                do {
                    result = tf.getInstance().search(query);
                    handleRateLimit(result.getRateLimitStatus(),configuration);
                    List<Status> tweets = result.getTweets();
                    for (Status tweet : tweets) {
                        // create User out of each tweet
                        User user = tweet.getUser();
                        if (!(foundTweets.contains(tweet.getId()))) {
                            TwitterFileService tfs = new TwitterFileService();
                             retweet = checkRetweet(tweet); //returns true if the tweet is a retweet
                            tfs.writeTweet(tweet, retweet);
                            foundTweets.add(tweet.getId());
                        }
                        // write new users to file and add to arraylist
                        if(!(users.contains(user))){
                            TwitterFileService tfs = new TwitterFileService();
                            tfs.writeUser(user);
                            users.add(user);
                        }
                    }

                } while ((query = result.nextQuery()) != null);

            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to search tweets: " + te.getMessage());
                System.exit(-1);
            }
        }
        System.exit(0);
    }
    public boolean checkRetweet(Status tweet){
        return tweet.getRetweetedStatus() != null; //returns true if retweet status exists, false otherwise
    }
}