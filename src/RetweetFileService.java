import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class RetweetFileService<E> {

    public void writeRetweetFile(HashMap<Vertex<E>, ArrayList<Arc>> retweetHashMap) throws IOException {
        File file = TwitterFileService.createFile("Testing", "testingWriter.txt");

        for (Vertex<E> vertex : retweetHashMap.keySet())

            try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {

                for (int i = 0; i < retweetHashMap.get(vertex).size(); i++) {
                    // lets worry about getting it to print properly to the terminal and not to the file yet..
                    pw.println(vertex.getLabel() + "\t" + retweetHashMap.get(vertex).get(i).vertex.getLabel());

                    System.out.println(vertex.getLabel() + "\t" + retweetHashMap.get(vertex).get(i).vertex.getLabel());
                    pw.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
