import twitter4j.*;

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
            try {
                ts.writeTweet(status, false, internalConfig);
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
        TwitterStream tf = configuration.getTwitterStreamFactory(configuration);
        tf.addListener(listener);

        String trackParam[] = configuration.getHashTags();
        FilterQuery query = new FilterQuery();
        query.track(trackParam);
        tf.filter(query);

        //return tf;
    }

    /*public TwitterStream setUp(Configuration configuration) {

        TwitterStream tf = configuration.getTwitterStreamFactory(configuration);
        tf.addListener(new StreamTweets(configuration).listener);
        return tf;
    }

}
