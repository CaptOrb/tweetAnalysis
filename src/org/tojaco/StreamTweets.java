package org.tojaco;

import org.tojaco.FileIO.TwitterFileService;
import twitter4j.*;

import java.io.IOException;

// This class is responsible for the use of Twitter 4J's streaming API
// The onStatus method describes what should happen when a new Tweet comes in
// The streamTweets method is responsible for setting up a Twitter Stream, and setting up a filterquery
// to search for occurrences of the hashtags listed in the config file being posted on Twitter
public class StreamTweets {

    private final TwitterFileService tfileService;

    public StreamTweets(TwitterFileService tfs) {
        this.tfileService = tfs;
    }

    StatusListener listener = new StatusListener() {
        @Override
        public void onException(Exception e) {

        }

        @Override
        public void onStatus(Status status) {

            try {

                tfileService.writeTweet(status, status.getRetweetedStatus() != null);
                tfileService.writeUser(status.getUser());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            System.out.println("Got a status deletion notice id:"
                    + statusDeletionNotice.getStatusId());
        }

        @Override
        public void onTrackLimitationNotice(int i) {
            System.out.println("Got track limitation notice:" + i);
        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {
            System.out.println("Got a stall warning: " +stallWarning);
        }
    };

    public void streamTweets() {
        // set up streaming api and add the class field StatusListener
        TwitterStream tf = Configuration.getTwitterStream();
        tf.addListener(listener);

        // create query using hashtags from config file and pass them to the streaming api
        FilterQuery query = new FilterQuery();
        query.track(Configuration.getHashTags());
        query.language(Configuration.getLanguage());
        tf.filter(query);
    }

}
