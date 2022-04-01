import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetweetFileService<E> {

    public void writeRetweetFile(Map<Vertex<E>, ArrayList<Arc<E>>> retweetHashMap) throws IOException {
        File file = TwitterFileService.createFile("Testing", "testingWriter.txt");

        StringBuilder sb = new StringBuilder();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {

            for (Vertex<E> vertex : retweetHashMap.keySet()) {
                if(vertex.getWeight()!=0){
                    sb.append(vertex.getLabel()).append("(").append(vertex.getWeight()).append(")").append("[");
                } else {
                    sb.append(vertex.getLabel()).append("[");
                }

                for (int i = 0; i < retweetHashMap.get(vertex).size(); i++) {
                    // lets worry about getting it to print properly to the terminal and not to the file yet..
                    sb.append(retweetHashMap.get(vertex).get(i).getVertex().getLabel());
                    sb.append("(").append(retweetHashMap.get(vertex).get(i).getWeight()).append(")").append(", ");
                }
               // sb.replace(sb.length() - 2, sb.length() , "");
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
