import twitter4j.*;

import java.io.IOException;

public class StreamTweets {

    public void streamTweets(Configuration configuration) {
        StatusListener listener = new StatusListener() {
            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onStatus(Status status) {
                System.out.println(status.getId() + "\t" + "@" + status.getUser().getScreenName() + "\t"
                        + status.getText().replaceAll("\n", " ") + "\t" + status.getRetweetCount() + "\t" + status.getCreatedAt());

                TwitterFileService tfs = new TwitterFileService();
                try {
                    tfs.writeTweet(status, false, configuration);
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
        tf.addListener(new StreamTweets().listener);
        return tf;
    }


    StreamTweets st = new StreamTweets();
    TwitterStream ts = st.setUp(configuration);


    String trackParam[] = configuration.getHashTags();


    FilterQuery query = new FilterQuery();

            query.track(trackParam);

            ts.filter(query);
*/
}
