package org.tojaco.FileIO;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.Vertex;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// HANDLES reading and writing to/from the graph output file
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
        final HashMap<String, List<String>> hashtags = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                try {
                    Long.parseLong(lineContents[0]);
                    String[] tweetText = lineContents[2].split(" "); //lineContents[2] is "RT @RetweetedUser tweetText" if it's a retweet
                    if (tweetText[0].contains("RT") && tweetText[1].contains("@")) {
                        String username = tweetText[1].replaceAll(":", ""); //remove : after the retweeted user
                        retweets.add(lineContents[1] + "\t" + username); //adds @User + "\t" + @RetweetedUser and whatever they tweeted
                    }
                    //String[] tweetText = lineContents[2].split(" "); //split the tweet text

                    List<String> hashtagsInLine = new ArrayList<>();
                    for(int i = 0; i<tweetText.length; i++){
                        if(tweetText[i].startsWith("#"))
                            hashtagsInLine.add(tweetText[i]); //appends \t and the hashtag
                    }
                    if(!(hashtagsInLine.size()<1)){
                        hashtags.put(lineContents[1], hashtagsInLine);
                        System.out.println(lineContents[1] +" "+ hashtagsInLine);
                    }

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

                } catch (Exception exception) {

                }
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return retweets;

    }


/*    public org.tojaco.Graph.RetweetGraph<String> readGraphFile(File file) {

        org.tojaco.Graph.RetweetGraph<String> rt = new org.tojaco.Graph.RetweetGraph<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split(" ");

                org.tojaco.Graph.Vertex<String> sourceVertex = org.tojaco.FindRetweets.getVertex(lineContents[0], rt.getAllVerticesInGraph());

                // need to set vertex weights
                // need to separate userhandles from weights somehow
                for (int i = 1; i < lineContents.length; i++) {

                    org.tojaco.Graph.Vertex<String> destVertex = org.tojaco.FindRetweets.getVertex(lineContents[i], rt.getAllVerticesInGraph());

                    org.tojaco.Graph.Arc<String> myArc = new org.tojaco.Graph.Arc<>(destVertex, +1);

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
