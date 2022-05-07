package org.tojaco.Sprints;

import org.tojaco.Configuration;
import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.FindGraphElements;
import org.tojaco.Graph.CreateUserVertex;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.TwitterUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sprint3 {
    private static final ArrayList<String> retweets = new ArrayList<>();
    public static ArrayList<String> getRetweets() {
        return retweets;
    }

    public void sprint3(File dataFile) throws IOException {
        DirectedGraph<TwitterUser, TwitterUser> rtGraph = new DirectedGraph<>();
        DirectedGraph<TwitterUser, TwitterUser> retweetedGraph;

        FindGraphElements findGraphElements = new FindGraphElements<>(new CreateUserVertex(), new CreateUserVertex());

        GraphElements graphElements = new GraphElements();

        GraphReadWriteService rfs = new GraphReadWriteService();

        if (dataFile.exists()) {
            getRetweets().addAll(rfs.loadDataFromInputFile(dataFile));
        }

        rtGraph = findGraphElements.createGraph(graphElements, getRetweets(), 0, 1);

        retweetedGraph = findGraphElements.createGraph(graphElements, getRetweets(), 1, 0);

        rfs.writeFileFromGraph(rtGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTGRAPH_OUTPUT_FILE()), true);

        rfs.writeFileFromGraph(retweetedGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTWEETEDGRAPH_OUTPUT_FILE()), true);
        System.out.println("Retweet graph added successfully to Graph directory!");

        SprintHelper.showSprint3Options(rtGraph, graphElements);
    }
}
