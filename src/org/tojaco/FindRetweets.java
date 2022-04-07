package org.tojaco;

import org.tojaco.FileIO.RetweetFileService;
import org.tojaco.Graph.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FindRetweets {

    private final ArrayList<String> retweets = new ArrayList<>();

    public ArrayList<String> getRetweets() {
        return retweets;
    }

    public RetweetGraph<String> toPutIntoHashMap(Configuration configuration, int a, int b) throws IOException {
        RetweetGraph<String> rtGraph = new RetweetGraph<>();

        Map<String, Vertex<String>> allVerticesInGraph = rtGraph.getAllVerticesInGraph();
        for (String rt : retweets) {
            String[] line = rt.split("\t"); //line[0] contains user, line[1] contains the user they are retweeting
            Vertex<String> srcVertex = rtGraph.getVertex(line[a]);
            Vertex<String> destVertex = rtGraph.getVertex(line[b]);
            Arc<String> myArc = new Arc<>(destVertex, +1);

            rtGraph.addArc(srcVertex, myArc);
            //to check that getLabelBetweenVertices works
            //System.out.println(srcVertex.getLabel() + " " + destVertex.getLabel() + " " + rtGraph.getLabelBetweenVertices(srcVertex,destVertex));

        }
        RetweetFileService<String> rs = new RetweetFileService<>();

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
