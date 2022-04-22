package org.tojaco.FileIO;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;

import java.io.*;
import java.util.*;

// HANDLES reading and writing to/from the graph output file
public class GraphReadWriteService extends FileService {

    // private final ArrayList<String> retweets = new ArrayList<>();

    private final ArrayList<String> hashtags = new ArrayList<>();
    //private HashMap<String, List<String>> hashtags = new HashMap<>();

    /*public void setRetweets(ArrayList<String> retweets) {
        this.retweets = retweets;
    }*/

   /* public ArrayList<String> getRetweets() {
        return retweets;
    }*/

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    /*public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }*/


    public <T, E> void writeFileFromGraph(DirectedGraph<T, E> graph, File file, boolean weight) throws IOException {

        createFile(file.getParent(), file.getName());

        Map<Vertex<T>, ArrayList<Arc<E>>> graphHashMap = graph.getGraph();

        StringBuilder sb = new StringBuilder();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {

            for (Vertex<T> vertex : graphHashMap.keySet()) {

                sb.append(vertex.getLabel().toString()).append(" [");

                for (int i = 0; i < graphHashMap.get(vertex).size(); i++) {
                    if (i > 0) {
                        sb.append(", ").append(graphHashMap.get(vertex).get(i).getVertex().getLabel());
                    } else {
                        sb.append(graphHashMap.get(vertex).get(i).getVertex().getLabel());
                    }
                    if(weight==true) {
                        sb.append("(").append(graphHashMap.get(vertex).get(i).getWeight()).append(")");
                    }

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

    public ArrayList<String> loadDataFromInputFile(File file) {

        final ArrayList<String> retweets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                try {
                    Long.parseLong(lineContents[0]);

                    String retweetedUserWithText = lineContents[2];
                    String[] tweetText = retweetedUserWithText.split(" "); //lineContents[2] is "RT @RetweetedUser tweetText" if it's a retweet
                    String retweetedUser = tweetText[1];

                    if (isTextRetweet(tweetText)) {
                        String username = retweetedUser.replaceAll(":", ""); //remove : after the retweeted user
                        retweets.add(lineContents[1] + "\t" + username); //adds @User + "\t" + @RetweetedUser and whatever they tweeted
                    }
                    String temp = retweetedUserWithText.trim().replaceAll(" +", " ");
                    //String temp = lineContents[2].replaceAll("  ", " ");
                    tweetText = temp.split("[^a-zA-Z#]+"); //split the tweet text
                    //tweetText= tweetText.split(" ");
                    for (String tweetComponent : tweetText) {
                        String hashtagInLine = null;
                        if (tweetComponent.startsWith("#")) {
                            hashtagInLine = tweetComponent;
                        }
                        if (hashtagInLine != null) {
                            hashtags.add(lineContents[1] + "\t" + hashtagInLine);
                        }
                    }

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//                    String[] lineContents1 = line.split("\t");
//                    System.out.println(lineContents[0]);
                } catch (Exception ignored) {
                }
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return retweets;
    }

    private boolean isTextRetweet(String[] lineContents) {
        return lineContents[0].contains("RT") && lineContents[1].startsWith("@");

    }
}
