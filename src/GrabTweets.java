import twitter4j.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class GrabTweets {

    // This file is just for testing so far
    // we can delete it / modify if needs be ;)

    HashSet<Long> foundTweets = new HashSet<>();

    // CAUTION: will keep running infinitely.
    //don't run for too long or the API KEY's might get suspended ;)

    //look into filterquery

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

    public void grabSomeTweets(TwitterFactory tf, Configuration configuration){

        try {

            // is this the right way to check for different terms in queries?
            // it doesn't like having too much
            Query query = new Query("(#VaccinesSaveLives) OR (#5g) OR (#VaccinesSaveLives) OR (#5g) OR (#VaccinesSaveLives) OR (#5g) OR (#VaccinesWork) OR (#TheTruthAboutCovid) OR (#mandatoryvaccinations) OR (#vaccinationssuck) OR (#forcedvaccination) OR (#ImVaccinated) OR (#Igotmyshot) OR #VaxxedAndMasked OR (#DontWaitVaccinate) OR (#vaccineskill) OR (#vaccinesideeffects) OR (#vaccineinjuries) OR (#vaccinedeaths) OR (#novaccinemandates) OR (#GetTheFuckingVaccine) OR (#vaccinescausebraindamage) OR (#vaccinescauseautism)");
            query.setCount(configuration.getBatchSize());
            query.setResultType(Query.ResultType.recent);

            query.setLang(configuration.getLanguage());
            //query.setQuery("");

            QueryResult result;
            do {
                result = tf.getInstance().search(query);
                handleRateLimit(result.getRateLimitStatus());
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    if (!(foundTweets.contains(tweet.getId()))) {
                            System.out.println("@" + tweet.getUser().getScreenName() + "\tTWEET TEXT: " + tweet.getText().replaceAll("\n", "") + "\tTWEET ID: " + tweet.getId()
                                    + "\tNUM RETWEETS: " + tweet.getRetweetCount() + "\tTime stamp: " + tweet.getCreatedAt());

                        foundTweets.add(tweet.getId());
                    }
                }

            } while ((query = result.nextQuery()) != null);
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

            grabTweets.grabSomeTweets(tf, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
