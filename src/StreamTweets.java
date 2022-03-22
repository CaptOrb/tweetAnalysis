import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

public class StreamTweets {


    StatusListener listener = new StatusListener() {
            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onStatus(Status status) {
                System.out.println(status.getId() + "\t" + "@" + status.getUser().getScreenName() + "\t"
                        + status.getText().replaceAll("\n", " ") + "\t" + status.getRetweetCount() + "\t" + status.getCreatedAt());

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
}


