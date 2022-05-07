package org.tojaco.Sprints;

import org.tojaco.FileIO.TwitterFileService;
import org.tojaco.StreamTweets;

import java.io.File;
import java.io.IOException;

public class Sprint2 {

    public void sprint2(File dataFile) throws IOException {
        TwitterFileService ts = new TwitterFileService();

        StreamTweets st = new StreamTweets(ts);

        if (dataFile.exists()) {
            ts.readTweetsIntoSet(dataFile);
        }
        st.streamTweets();
    }
}
