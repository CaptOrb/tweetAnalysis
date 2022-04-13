package org.tojaco;

import org.tojaco.FileIO.RetweetFileService;
import org.tojaco.Graph.*;
import twitter4j.Twitter;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FindGraphElements {

    private final ArrayList<String> retweets = new ArrayList<>();
    private final ArrayList<String> hashtags = new ArrayList<>();

    public ArrayList<String> getHashtags() { return hashtags;}

    public ArrayList<String> getRetweets() {
        return retweets;
    }

    public <T, E> DirectedGraph<T, E> toPutIntoHashMap(ArrayList<String> list, int a, int b) throws IOException {
        DirectedGraph<T, E> rtGraph = new DirectedGraph<>();

        //Map<String, Vertex<String>> allVerticesInGraph = rtGraph.getAllVerticesInGraph();
        for (String rt : list) {
            String[] line = rt.split("\t"); //line[0] contains user, line[1] contains the user they are retweeting
            Vertex<T> srcVertex = rtGraph.getVertex(line[a]);
            Vertex<E> destVertex = rtGraph.getVertex(line[b]);
            Arc<E> myArc = new Arc<>(destVertex, +1);

            rtGraph.addArc(srcVertex, myArc);
            //to check that getLabelBetweenVertices works
            //System.out.println(srcVertex.getLabel() + " " + destVertex.getLabel() + " " + rtGraph.getLabelBetweenVertices(srcVertex,destVertex));

        }
        return rtGraph;
    }

    public ArrayList<String> initialiseRetweets(File dataFile) {
        RetweetFileService<String> rfs = new RetweetFileService<>();
        getRetweets().addAll(rfs.readRetweetsIntoSet(dataFile));

        return retweets;
    }
}
