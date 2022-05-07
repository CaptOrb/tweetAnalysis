package org.tojaco.Sprints;

import org.tojaco.Configuration;
import org.tojaco.FileIO.TwitterFileService;
import org.tojaco.GrabTweets;
import twitter4j.TwitterFactory;

import java.io.File;
import java.io.IOException;

public class Sprint1 {
    public void sprint1(File dataFile) throws IOException {
        TwitterFileService ts = new TwitterFileService();

        TwitterFactory tf = Configuration.getTwitterFactory();

        GrabTweets grabTweets = new GrabTweets();

        if (dataFile.exists()) {
            ts.readTweetsIntoSet(dataFile);
        }

        grabTweets.grabSomeTweets(tf, ts);
    }
}
