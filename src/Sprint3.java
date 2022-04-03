import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sprint3 {

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
            RetweetGraph<String> rtGraph = findRetweets.toPutIntoHashMap(configuration);
            String k="sdknfkd";

            // test vertex1:
            // findRetweets.getVertex("@twitterHandle", rtGraph.getAllVerticesInGraph());

            // test vertex2
            // findRetweets.getVertex("@twitterHandle2", rtGraph.getAllVerticesInGraph());

            // rtGraph.getLabelBetweenVertices(vertex1, vertex2);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
