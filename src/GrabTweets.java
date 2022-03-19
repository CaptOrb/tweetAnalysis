import twitter4j.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GrabTweets {

    // This file is just for testing so far
    // we can delete it / modify if needs be ;)

    HashSet<Long> foundTweets = new HashSet<>();
    ArrayList<Long> users = new ArrayList<>(); //changed to user ID instead
    // experimental
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
                Thread.sleep(Math.max(sleep * 1000, 0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // CAUTION: will keep running for a very long time.
    // don't run for too long or the API KEY's might get suspended ;)
    public void grabSomeTweets(TwitterFactory tf, Configuration configuration) throws IOException {

        String[] hashTags = configuration.getHashTags();

        //now have string array hashTags full of hash tags :-)

        for (int i = 0; i < hashTags.length; i++) {
            try {
                Query query = new Query(hashTags[i]);
                query.setCount(configuration.getBatchSize());
                query.setResultType(Query.ResultType.recent);
                query.setLang(configuration.getLanguage());
                TwitterFileService tfs = new TwitterFileService();
                QueryResult result;
                do {
                    result = tf.getInstance().search(query);
                    handleRateLimit(result.getRateLimitStatus());
                    List<Status> tweets = result.getTweets();
                    for (Status tweet : tweets) {
                        User user = tweet.getUser();
                        if (!(foundTweets.contains(tweet.getId()))) {
                            tfs.writeToFile(tweet);
                            foundTweets.add(tweet.getId());
                        }
                        if(!(users.contains(user.getId()))){
                            tfs.writeUser(user);
                            users.add(user.getId());
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

}