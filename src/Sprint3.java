import java.io.File;
import java.io.IOException;

public class Sprint3 {

    // just for testing purposes
    // remove before submission
   static void createGraph() {
        RetweetGraph<String> graph = new RetweetGraph<>();
        graph.addVertex("Bob");
        graph.addVertex("Alice");
        graph.addVertex("Mark");
        graph.addVertex("Rob");
        graph.addVertex("Maria");
        //graph.addEdge("Bob", "Alice");

        System.out.println(graph.getAdjVertices("Alice"));
        System.out.println(graph.getAdjVertices("Bob"));

   }
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
                findRetweets.readRetweetsIntoSet(dataFile);
            }
            createGraph();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
