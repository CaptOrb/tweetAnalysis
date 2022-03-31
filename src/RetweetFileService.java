import twitter4j.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetweetFileService<E> {

    Map<Vertex<E>, ArrayList<Arc>> tempHashMap = new HashMap<Vertex<E>, ArrayList<Arc>>();

    public void writeRetweetFile(HashMap<Vertex<E>, ArrayList<Arc>> retweetHashMap,
                                 Configuration configuration) throws IOException {
        File file = TwitterFileService.createFile("Testing", "testingWriter.txt");

        for (Vertex<E> vertex : retweetHashMap.keySet())

            try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                pw.println(vertex.getLabel() + "\t" + retweetHashMap.get(vertex).get(0));

                pw.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
