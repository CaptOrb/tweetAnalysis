import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class RetweetFileService<E> {

    public void writeRetweetFile(Map<Vertex<E>, ArrayList<Arc<E>>> retweetHashMap,
                                 Configuration configuration) throws IOException {

        File file = TwitterFileService.createFile(configuration.getGRAPH_DIRECTORY(),
                configuration.getRTGRAPH_OUTPUT_FILE());

        StringBuilder sb = new StringBuilder();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {

            for (Vertex<E> vertex : retweetHashMap.keySet()) {

                    sb.append(vertex.getLabel()).append(" [");

                for (int i = 0; i < retweetHashMap.get(vertex).size(); i++) {
                    if (i > 0) {
                        sb.append(", ").append(retweetHashMap.get(vertex).get(i).getVertex().getLabel());
                    } else {
                        sb.append(retweetHashMap.get(vertex).get(i).getVertex().getLabel());
                    }
                    sb.append("(").append(retweetHashMap.get(vertex).get(i).getWeight()).append(")");

                }

                sb.append("]");
              //  System.out.println(sb);
                pw.println(sb);
                sb.setLength(0);
                pw.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
