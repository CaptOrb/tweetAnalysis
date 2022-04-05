import java.io.*;
import java.util.ArrayList;
import java.util.Map;

// HANDLES reading and writing to/from the graph output fileadd
public class RetweetFileService<E> extends FileService {

    public void writeRetweetFile(Map<Vertex<E>, ArrayList<Arc<E>>> retweetHashMap, File file) throws IOException {

        createFile(file.getParent(), file.getName());

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
                pw.println(sb);
                sb.setLength(0);
                pw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> readRetweetsIntoSet(File file) {
        final ArrayList<String> retweets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                try {
                    Long.parseLong(lineContents[0]);
                    String[] findRetweet = lineContents[2].split(" "); //lineContents[2] is "RT @RetweetedUser tweetText" if it's a retweet
                    if (findRetweet[0].contains("RT") && findRetweet[1].contains("@")) {
                        String username = findRetweet[1].replaceAll(":", ""); //remove : after the retweeted user
                        retweets.add(lineContents[1] + "\t" + username); //adds @User + "\t" + @RetweetedUser and whatever they tweeted
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("invalid line format - skipped");
                } catch (Exception exception) {
                    String k = "asd";
                }
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return retweets;

    }


/*    public RetweetGraph<String> readGraphFile(File file) {

        RetweetGraph<String> rt = new RetweetGraph<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split(" ");

                Vertex<String> sourceVertex = FindRetweets.getVertex(lineContents[0], rt.getAllVerticesInGraph());

                // need to set vertex weights
                // need to separate userhandles from weights somehow
                for (int i = 1; i < lineContents.length; i++) {

                    Vertex<String> destVertex = FindRetweets.getVertex(lineContents[i], rt.getAllVerticesInGraph());

                    Arc<String> myArc = new Arc<>(destVertex, +1);

                    rt.addArc(sourceVertex, myArc);
                    rt.controlUsers(destVertex);
                }
                rt.controlUsers(sourceVertex);
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return rt;

    }*/

}
