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
        RetweetFileService<E> retweetFileService = new RetweetFileService<E>();

        HashMap<Vertex<E>, ArrayList<Arc>> tempHashMap = new HashMap<>();

        ArrayList<Arc> arcsForChloe = new ArrayList<>();

        Vertex<E> testVertex = new Vertex<>("Chloe", 0);
        Vertex<E> testVertex2 = new Vertex<>("Leo", 0);
        Vertex<E> testVertex3 = new Vertex<>("Liam", 0);

        arcsForChloe.add(new Arc(testVertex, 0));
        arcsForChloe.add(new Arc(testVertex3, 0));

        tempHashMap.put(testVertex2, arcsForChloe);
        retweetFileService.writeRetweetFile(tempHashMap);
    }
}
