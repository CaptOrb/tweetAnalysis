import twitter4j.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

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

    public void grabSomeTweets(TwitterFactory tf, Configuration configuration) throws IOException {

        configuration.getSettingsFromFile(configuration);
        Properties properties = new Properties();

        InputStream inputStream = configuration.getClass().getClassLoader().getResourceAsStream("config_file");

        String[] hashTags;
        //like im sure there is an easier way to do this so feel free to mess around with it,
        // maybe we have a separate function for this that returns the String[] of hash tags
        if (inputStream == null) {
            throw new RuntimeException("Couldn't find the config file in the classpath.");
        }
        try {
            properties.load(inputStream);
            String convertToList = properties.getProperty("HASHTAGS");
            hashTags = configuration.putHashTagsIntoList(convertToList, configuration);
            //System.out.println(hashTags[1] + " " + hashTags[2]);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties from file:", e);
        }
        //now have string array hashTags full of hash tags :-)
    for(int i=0; i<hashTags.length;i++) {
        try {

            // is this the right way to check for different terms in queries?
            // it doesn't like having too much

            Query query = new Query(hashTags[i]);
                    //new Query("(#VaccinesSaveLives) OR (#5g) OR (#VaccinesSaveLives) OR (#5g) OR (#VaccinesSaveLives) OR (#5g) OR (#VaccinesWork) OR (#TheTruthAboutCovid) OR (#mandatoryvaccinations) OR (#vaccinationssuck) OR (#forcedvaccination) OR (#ImVaccinated) OR (#Igotmyshot) OR #VaxxedAndMasked OR (#DontWaitVaccinate) OR (#vaccineskill) OR (#vaccinesideeffects) OR (#vaccineinjuries) OR (#vaccinedeaths) OR (#novaccinemandates) OR (#GetTheFuckingVaccine) OR (#vaccinescausebraindamage) OR (#vaccinescauseautism)");
            query.setCount(100);

           // Query query = new Query("(#VaccinesSaveLives) OR (#5g) OR (#VaccinesSaveLives) OR (#5g) OR (#VaccinesSaveLives) OR (#5g) OR (#VaccinesWork) OR (#TheTruthAboutCovid) OR (#mandatoryvaccinations) OR (#vaccinationssuck) OR (#forcedvaccination) OR (#ImVaccinated) OR (#Igotmyshot) OR #VaxxedAndMasked OR (#DontWaitVaccinate) OR (#vaccineskill) OR (#vaccinesideeffects) OR (#vaccineinjuries) OR (#vaccinedeaths) OR (#novaccinemandates) OR (#GetTheFuckingVaccine) OR (#vaccinescausebraindamage) OR (#vaccinescauseautism)");
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
