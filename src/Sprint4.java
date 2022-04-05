import java.io.File;
import java.io.IOException;

public class Sprint4 {

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

            File dataFile = new File(configuration.getDataDirectory(), configuration.getDataFile());

            FindRetweets findRetweets = new FindRetweets();

            if (dataFile.exists()) {
                findRetweets.initialiseRetweets(dataFile);
            }
            // graph for using implemented methods on
            // see RetweetGraph.java for description of public methods
            RetweetGraph<String> rtGraph = findRetweets.toPutIntoHashMap(configuration, 0, 1);
            RetweetGraph<String> retweetedGraph = findRetweets.toPutIntoHashMap(configuration, 1, 0);

            findEvangelists findEvangelist = new findEvangelists();
            findEvangelist.findTotalRetweets(retweetedGraph);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}