package org.tojaco.Sprints;

import org.tojaco.AssignStances;
import org.tojaco.Configuration;
import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.FindEvangelists;
import org.tojaco.FindGraphElements;
import org.tojaco.Graph.CreateUserVertex;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphAnalysis.GraphAnalyser;
import org.tojaco.GraphAnalysis.StanceAnalysis;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.TwitterUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Sprint4 {

    public void sprint4(File dataFile) throws IOException {
        final ArrayList<String> getRetweets = new ArrayList<>();

        FindGraphElements<TwitterUser,TwitterUser> findGraphElements = new FindGraphElements<>(new CreateUserVertex(), new CreateUserVertex());

        GraphElements graphElements = new GraphElements();

        GraphReadWriteService rfs = new GraphReadWriteService();

        if (dataFile.exists()) {
            getRetweets.addAll(rfs.loadDataFromInputFile(dataFile));
        }

        DirectedGraph<TwitterUser, TwitterUser> rtGraph = findGraphElements.createGraph(graphElements, getRetweets, 0, 1);
        DirectedGraph<TwitterUser, TwitterUser> retweetedGraph = findGraphElements.createGraph(graphElements, getRetweets, 1, 0);

        rfs.writeFileFromGraph(rtGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTGRAPH_OUTPUT_FILE()), true);

        rfs.writeFileFromGraph(retweetedGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTWEETEDGRAPH_OUTPUT_FILE()), true);
        System.out.println("Retweet graph added successfully to Graph directory!");

        FindEvangelists findEvangelist = new FindEvangelists();
        Map<Vertex<TwitterUser>, Integer> retweetsHashMap = findEvangelist.findTotalRetweets(retweetedGraph);

        AssignStances assignStances = new AssignStances();
        File StanceFile = new File(Configuration.getSTANCE_FILE());
        assignStances.determineProAntiVaxEvangelists(graphElements, rtGraph, StanceFile);

        // initial setup for calculating stances
        GraphAnalyser graphAnalyser = new GraphAnalyser<>();

        for (int i = 0; i < 20; i++) {
            graphAnalyser.assignUserStances(rtGraph);
            graphAnalyser.assignUserStances(retweetedGraph);
        }

        graphAnalyser.outputGraphAnalysis(rtGraph, graphElements, false, false);
        StanceAnalysis users100 = new StanceAnalysis<>();
        users100.checkStance100Users(retweetsHashMap);
    }
}
