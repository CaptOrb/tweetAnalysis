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
                findRetweets.readRetweetsIntoSet(dataFile);
            }
            findRetweets.toPutIntoHashMap();

            testHashMap();
            // createGraph();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // just for testing purposes
    // remove before submission
    public static <E> void testHashMap() throws IOException {
        RetweetFileService<String> retweetFileService = new RetweetFileService<>();

/*        HashMap<Vertex<String>, ArrayList<Arc<String>>> tempHashMap = new HashMap<>();

        ArrayList<Arc<String>> arcsForChloe = new ArrayList<>();

        Vertex<String> testVertex = new Vertex<>("@Chloe", 0);
        Vertex<String> testVertex2 = new Vertex<>("@Leo", 0);
        Vertex<String> testVertex3 = new Vertex<>("@Liam", 0);

        arcsForChloe.add(new Arc<>(testVertex2, 0));
        arcsForChloe.add(new Arc<>(testVertex3, 0));

        tempHashMap.put(testVertex, arcsForChloe);

        ArrayList<Arc<String>> arcsForChloee = new ArrayList<>();

        Vertex<String> testVertexe = new Vertex<>("@n", 0);
        Vertex<String> testVertex2e = new Vertex<>("@1234", 0);
        Vertex<String> testVertex3e = new Vertex<>("@567", 0);

        arcsForChloee.add(new Arc<>(testVertex2e, 0));
        arcsForChloee.add(new Arc<>(testVertex3e, 0));*/

       // tempHashMap.put(testVertexe, arcsForChloee);
      //  retweetFileService.writeRetweetFile(tempHashMap);
    }
}
