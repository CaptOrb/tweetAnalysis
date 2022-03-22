import twitter4j.*;

import java.io.IOException;

public class StreamTweets {

    Configuration internalConfig;

    public StreamTweets(Configuration configuration){
        internalConfig = configuration;
    }

    StatusListener listener = new StatusListener() {
        @Override
        public void onException(Exception e) {

        }

        @Override
        public void onStatus(Status status) {

            TwitterFileService ts = new TwitterFileService();


            //User user = status.getUser();
            try {

                if(status.getRetweetedStatus() !=null){
                    ts.writeUser(status.getRetweetedStatus().getUser(), internalConfig);
                    System.out.println(status.getRetweetedStatus().getUser().getScreenName() + status.getText());
                } else {
                    System.out.println(status.getUser().getScreenName() + status.getText());
                    ts.writeUser(status.getUser(), internalConfig);
                }
                ts.writeTweet(status, status.getRetweetedStatus() != null, internalConfig);
                //ts.writeUser(user,internalConfig);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        }

        @Override
        public void onTrackLimitationNotice(int i) {

        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {

            }
        };

    public void streamTweets() {
        // set up streaming api and add the class field StatusListener
        TwitterStream tf = internalConfig.getTwitterStreamFactory(internalConfig);
        tf.addListener(listener);

        // create query using hashtags from config file and pass them to the streaming api
        FilterQuery query = new FilterQuery();
        query.track(internalConfig.getHashTags());
        tf.filter(internalConfig.getHashTags());
    }

}
