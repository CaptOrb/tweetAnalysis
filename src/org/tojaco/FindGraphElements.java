package org.tojaco;

import org.tojaco.FileIO.RetweetFileService;
import org.tojaco.Graph.*;
import twitter4j.Twitter;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FindGraphElements {

    private final ArrayList<String> retweets = new ArrayList<>();

    public ArrayList<String> getRetweets() {
        return retweets;
    }

    public DirectedGraph<TwitterUser, TwitterUser> toPutIntoHashMap(Configuration configuration, TwitterUsers users, int a, int b) throws IOException {
        DirectedGraph<TwitterUser, TwitterUser> rtGraph = new DirectedGraph<>();

        //Map<String, Vertex<String>> allVerticesInGraph = rtGraph.getAllVerticesInGraph();
        for (String rt : retweets) {
            String[] line = rt.split("\t"); //line[0] contains user, line[1] contains the user they are retweeting
            Vertex<TwitterUser> srcVertex = users.getVertex(line[a]);
            Vertex<TwitterUser> destVertex = users.getVertex(line[b]);
            Arc<TwitterUser> myArc = new Arc<>(destVertex, +1);

            rtGraph.addArc(srcVertex, myArc);
            //to check that getLabelBetweenVertices works
            //System.out.println(srcVertex.getLabel() + " " + destVertex.getLabel() + " " + rtGraph.getLabelBetweenVertices(srcVertex,destVertex));

        }
        RetweetFileService<TwitterUser> rs = new RetweetFileService<>();

        String outputFile;
        if (a == 0) {
            outputFile = configuration.getRTGRAPH_OUTPUT_FILE();
        } else {
            outputFile = configuration.getRTWEETEDGRAPH_OUTPUT_FILE();
        }

        rs.writeRetweetFile(rtGraph.getGraph(),
                new File(configuration.getGRAPH_DIRECTORY(),
                        outputFile));

        return rtGraph;
    }


    public ArrayList<String> initialiseRetweets(File dataFile) {
        RetweetFileService<String> rfs = new RetweetFileService<>();
        getRetweets().addAll(rfs.readRetweetsIntoSet(dataFile));

        return retweets;
    }
}
