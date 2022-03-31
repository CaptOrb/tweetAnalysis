import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class RetweetFileService<E> {

    public void writeRetweetFile(HashMap<Vertex<E>, ArrayList<Arc>> retweetHashMap) throws IOException {
        File file = TwitterFileService.createFile("Testing", "testingWriter.txt");

        StringBuilder sb = new StringBuilder();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {

            for (Vertex<E> vertex : retweetHashMap.keySet()) {
                sb.append(vertex.getLabel()).append("[");
                for (int i = 0; i < retweetHashMap.get(vertex).size(); i++) {
                    // lets worry about getting it to print properly to the terminal and not to the file yet..
                    sb.append(retweetHashMap.get(vertex).get(i).vertex.getLabel());
                    sb.append("(").append(vertex.getWeight()).append(")").append(", ");
                }
                sb.replace(sb.length() - 2, sb.length() , "");
                sb.append("]");
                System.out.println(sb);
                pw.println(sb);
                sb.setLength(0);
                pw.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
