package org.tojaco.Sprints;

import org.tojaco.*;
import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.Graph.CreateHashtagVertex;
import org.tojaco.Graph.CreateUserVertex;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphAnalysis.GraphAnalyser;
import org.tojaco.GraphAnalysis.StanceAnalysis;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Sprint5 {

    public void sprint5(File dataFile) throws IOException {

        final ArrayList<String> getRetweets = new ArrayList<>();

        GraphReadWriteService rfs = new GraphReadWriteService();

        GraphElements graphElements = new GraphElements();

        FindGraphElements<TwitterUser,TwitterUser> findGraphElements = new FindGraphElements<>(new CreateUserVertex(), new CreateUserVertex());

        if (dataFile.exists()) {
            getRetweets.addAll(rfs.loadDataFromInputFile(dataFile));
        }

        final ArrayList<String> getHashtags = new ArrayList<>(rfs.getHashtags());

        DirectedGraph<TwitterUser, TwitterUser> rtGraph = findGraphElements.createGraph(graphElements, getRetweets, 0, 1);
        DirectedGraph<TwitterUser, TwitterUser> retweetedGraph = findGraphElements.createGraph(graphElements, getRetweets, 1, 0);

        rfs.writeFileFromGraph(rtGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTGRAPH_OUTPUT_FILE()), true);

        rfs.writeFileFromGraph(retweetedGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTWEETEDGRAPH_OUTPUT_FILE()), true);

        System.out.println("Retweet graph and retweeted graph added successfully to Graph directory!");

        FindEvangelists findEvangelists = new FindEvangelists();
        Map<Vertex<TwitterUser>, Integer> retweetHashMap = findEvangelists.findTotalRetweets(retweetedGraph);

        AssignStances assignStances = new AssignStances();
        File StanceFile = new File(Configuration.getSTANCE_FILE());
        assignStances.determineProAntiVaxEvangelists(graphElements, rtGraph, StanceFile);

        // initial setup for calculating stances
        GraphAnalyser graphAnalyser = new GraphAnalyser<>();

        for (int i = 0; i < 10; i++) {
            graphAnalyser.assignUserStances(rtGraph);
            graphAnalyser.assignUserStances(retweetedGraph);

        }

        StanceAnalysis analysis = new StanceAnalysis<>();
        analysis.checkStance100Users(retweetHashMap);

        graphAnalyser.outputGraphAnalysis(rtGraph, graphElements, false, false);

        System.out.println("Now calculating hashtag graphs...");
        FindGraphElements<TwitterUser, Hashtag> userHashTagFGE = new FindGraphElements<>(new CreateUserVertex(), new CreateHashtagVertex());
        DirectedGraph<TwitterUser, Hashtag> userToHashTag;
        userToHashTag = userHashTagFGE.createGraph(graphElements, getHashtags, 0, 1);
        rfs.writeFileFromGraph(userToHashTag,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getUSERS_TO_HASHTAGS()), true);


        FindGraphElements<Hashtag, TwitterUser> hashTagUserFGE = new FindGraphElements<>(new CreateHashtagVertex(), new CreateUserVertex());
        DirectedGraph<Hashtag, TwitterUser> hashtagToUsers;
        hashtagToUsers = hashTagUserFGE.createGraph(graphElements, getHashtags, 1, 0);

        rfs.writeFileFromGraph(hashtagToUsers,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getHASHTAGS_TO_USERS()), true);

        //3a and 3b
        for (int i = 0; i < 3; i++) { //theres no change in coverage from 3 to 4, but theres a change in coverage from 2 to 3
            graphAnalyser.assignUserStances(userToHashTag);
            graphAnalyser.assignUserStances(hashtagToUsers);

        }
        graphAnalyser.outputGraphAnalysis(rtGraph, graphElements, false, true);

        //by running this again we get more coverage
        for (int i = 0; i < 5; i++) { //by upping this to 10 there's no change in coverage
            graphAnalyser.assignUserStances(rtGraph);
            graphAnalyser.assignUserStances(retweetedGraph);

        }
        StanceAnalysis analyse = new StanceAnalysis<>();
        analyse.assignStancesByHashtags(hashtagToUsers, graphElements, rtGraph);

        graphAnalyser.outputGraphAnalysis(rtGraph, graphElements, true, false);

        analyse.find100Hashtags(hashtagToUsers);

        analyse.find100HashtagsS5(rtGraph, hashtagToUsers);
    }
}
