import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FindRetweets {

    private final ArrayList<String> retweets = new ArrayList<>();
    public ArrayList<String> getRetweets() {
        return retweets;
    }

    //Any retweets are now contained in arraylist retweets:
    public void readRetweetsIntoSet(File file) {
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
                }
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }

    }

    public void toPutIntoHashMap(Configuration configuration) throws IOException {
        RetweetGraph<String> rtGraph = new RetweetGraph<>();

        List<Vertex<String>> allVerticesInGraph = rtGraph.getAllVerticesInGraph();
        for (String rt : retweets) {
            String[] line = rt.split("\t"); //line[0] contains user, line[1] contains the user they are retweeting
            Vertex<String> srcVertex = getVertex(line[0], allVerticesInGraph);
            Vertex<String> destVertex = getVertex(line[1], allVerticesInGraph);
            Arc<String> myArc = new Arc<>(destVertex, +1);
            // Maintain list of users in graph
            rtGraph.controlUsers(srcVertex);
            rtGraph.controlUsers(destVertex);

            rtGraph.addArc(srcVertex, myArc);
            //to check that getLabelBetweenVertices works
            //System.out.println(srcVertex.getLabel() + " " + destVertex.getLabel() + " " + rtGraph.getLabelBetweenVertices(srcVertex,destVertex));

        }
        RetweetFileService<String> rs = new RetweetFileService<>();

        rs.writeRetweetFile(rtGraph.getGraph(), configuration);
    }

    private Vertex<String> getVertex(String label, List<Vertex<String>> usersInGraph) {
        // check list of existing users
        // if user exists, then return user
        // if not create a new user with given label and return
        for (Vertex<String> user : usersInGraph) {
            if (user.getLabel().equals(label)) {
                return user;
            }
        }
        return new Vertex<String>(label);
    }
}
