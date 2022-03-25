import java.io.File;
import java.io.IOException;

//Run the entire program here
public class Sprint2 {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        try {
            // user chooses to provide a config file as a command arg
            if (args.length == 1) {
                configuration.getSettingsFromFile(configuration, args[0], 1);
            } else {
                // config file is on class path
                configuration.getSettingsFromFile(configuration, "config_file", 0);
            }

            // read tweet id's and user handles into the hashset
            // so later on we can do an "occurs check"
            File dataFile = new File(configuration.getDataDirectory(), configuration.getDataFile());
            TwitterFileService twitterFileService = new TwitterFileService();

            if (dataFile.exists()) {
                twitterFileService.readTweetsIntoSet(dataFile);
            }

            StreamTweets st = new StreamTweets(configuration, twitterFileService);
            st.streamTweets();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
