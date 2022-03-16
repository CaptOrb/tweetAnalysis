import twitter4j.*;

import java.io.IOException;
import java.util.List;

public class GrabTweets {

    // This file is just for testing so far
    // we can delete it / modify if needs be ;)

    public void grabSomeTweets(TwitterFactory tf){

        try {
            Query query = new Query("test");
            QueryResult result;
            result = tf.getInstance().search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }

            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }

    public static void main(String[] args){

        Configuration configuration = new Configuration();

        try {
            configuration.getSettingsFromFile(configuration);
            TwitterFactory tf = configuration.getTwitterFactory(configuration);

            GrabTweets grabTweets = new GrabTweets();

            grabTweets.grabSomeTweets(tf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
