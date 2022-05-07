package org.tojaco.Sprints;

import org.tojaco.*;
import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.Graph.*;
import org.tojaco.GraphAnalysis.GraphAnalyser;
import org.tojaco.GraphAnalysis.StanceAnalysis;
import org.tojaco.GraphAnalysis.StatCalculator;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;
import twitter4j.Twitter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Sprint8 {

    private static final ArrayList<String> retweets = new ArrayList<>();
    private static final ArrayList<String> hashtags = new ArrayList<>();
    public static ArrayList<String> getRetweets() {
        return retweets;
    }

    public static ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void sprint8(File dataFile) throws IOException {
        GraphElements graphElements = new GraphElements();
        FindGraphElements findGraphElements;

        DirectedGraph<TwitterUser, TwitterUser> rtGraph = new DirectedGraph<>();
        DirectedGraph<TwitterUser, TwitterUser> retweetedGraph;
        GraphReadWriteService rfs = new GraphReadWriteService();
        Lexicon lexicon = new Lexicon();
        DirectedGraph lexiconGraph;
        HashtagSummarizer hashtagSummarizer = new HashtagSummarizer();
        // graph for using implemented methods on
        // see org.tojaco.Graph.DirectedGraph.java for description of public methods

        findGraphElements = new FindGraphElements(new CreateUserVertex(), new CreateUserVertex());

        if (dataFile.exists()) {
            getRetweets().addAll(rfs.loadDataFromInputFile(dataFile));
        }

        getHashtags().addAll(rfs.getHashtags());

        rtGraph = findGraphElements.createGraph(graphElements, getRetweets(), 0, 1);
        retweetedGraph = findGraphElements.createGraph(graphElements, getRetweets(), 1, 0);

        rfs.writeFileFromGraph(rtGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTGRAPH_OUTPUT_FILE()), true);

        rfs.writeFileFromGraph(retweetedGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getRTWEETEDGRAPH_OUTPUT_FILE()), true);

        System.out.println("Retweet graph / retweeted graph added successfully to Graph directory!");

        System.out.println("Now generating mentions / mentioned graphs. Standby.");
        // calling this will both generate and output the mention / mentioned graphs
        MentionGraph mentionGraph = new MentionGraph(rfs);

        System.out.println("Mentions / Mentioned Graphs added successfully to Graph directory!");

        FindEvangelists findEvangelists = new FindEvangelists();
        Map<Vertex<TwitterUser>, Integer> retweetHashMap = findEvangelists.findTotalRetweets(retweetedGraph);

        AssignStances assignStances = new AssignStances();
        File StanceFile = new File(Configuration.getSTANCE_FILE());
        assignStances.determineProAntiVaxEvangelists(graphElements, rtGraph, StanceFile);

        // initial setup for calculating stances
        GraphAnalyser graphAnalyser = new GraphAnalyser();

        for (int i = 0; i < 10; i++) {
            graphAnalyser.assignUserStances(rtGraph);
            graphAnalyser.assignUserStances(retweetedGraph);
        }

        StanceAnalysis analysis = new StanceAnalysis();
        analysis.checkStance100Users(retweetHashMap);

        //outputGraphAnalysis(graphAnalyser, rtGraph, graphElements, false, false);

        System.out.println("Now calculating hashtag graphs...");
        FindGraphElements fge1 = new FindGraphElements<>(new CreateUserVertex(), new CreateHashtagVertex());
        DirectedGraph<TwitterUser, Hashtag> usertoHashTag = fge1.createGraph(graphElements, getHashtags(), 0, 1);

        //outputGraphAnalysis(graphAnalyser, rtGraph, graphElements, true, false);

        rfs.writeFileFromGraph(usertoHashTag,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getUSERS_TO_HASHTAGS()), true);


        FindGraphElements findGraphElements2 = new FindGraphElements<>(new CreateHashtagVertex(), new CreateUserVertex());

        DirectedGraph<Hashtag, TwitterUser> hashtagToUsers = findGraphElements2.createGraph(graphElements, getHashtags(), 1, 0);

        rfs.writeFileFromGraph(hashtagToUsers,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getHASHTAGS_TO_USERS()), true);

        //3a and 3b
        for (int i = 0; i < 3; i++) { //theres no change in coverage from 3 to 4, but theres a change in coverage from 2 to 3
            graphAnalyser.assignUserStances(usertoHashTag);
            graphAnalyser.assignUserStances(hashtagToUsers);

        }
        //by running this again we get more coverage
        for (int i = 0; i < 5; i++) { //by upping this to 10 there's no change in coverage
            graphAnalyser.assignUserStances(rtGraph);
            graphAnalyser.assignUserStances(retweetedGraph);

        }
        //outputGraphAnalysis(graphAnalyser, rtGraph, graphElements, true, false);
        StanceAnalysis analyse = new StanceAnalysis();
        // users100New.checkStance(retweetHashMap);
        analyse.assignStancesByHashtags(hashtagToUsers, graphElements, rtGraph);

        // outputGraphAnalysis(graphAnalyser, rtGraph, graphElements, false, true);

        analyse.find100Hashtags(hashtagToUsers);

        analyse.find100HashtagsS5(rtGraph, hashtagToUsers);

        HashtagSplitter hashtagSplitter = new HashtagSplitter();
        hashtagSplitter.splitHashtagsByCamelCase(hashtagToUsers);

        hashtagSummarizer = new HashtagSummarizer();

        lexiconGraph = lexicon.getLexiconGraph();

        DirectedGraph sumHashTagGraph = hashtagSummarizer.summarizeHashtag(hashtagToUsers, lexiconGraph, lexicon.getGraphElementsLexicon());

        hashtagSplitter.splitHashtagsByLexicon(hashtagToUsers, lexicon.getLexiconDictionary());

        rfs.writeFileFromGraph(lexiconGraph, new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getLEXICON_FILE()), false);

        GraphElements graphElements2 = new GraphElements();

        DirectedGraph hashtagToWordGraph = hashtagSummarizer.hashtagMadeUpOf(hashtagToUsers, graphElements2);

        rfs.writeFileFromGraph(hashtagToWordGraph, new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getHASHTAG_TO_WORDS()), false);

        hashtagSummarizer.assignGistOfTags(sumHashTagGraph);

        rfs.writeFileFromGraph(sumHashTagGraph, new File(Configuration.getGRAPH_DIRECTORY(),
                Configuration.getHASHTAG_SUMMARY_FILE()), true);


        System.out.println("Now calculating User to Qualities graph...");

        ModelUser modelUser = new ModelUser();
        modelUser.addSummaryOfHashtag(sumHashTagGraph);
        modelUser.addSummaryOfHashtagToUserQualities(usertoHashTag);

        DirectedGraph usersToQualities = modelUser.makeUserToQualityGraph(usertoHashTag, graphElements);

        rfs.writeFileFromGraph(usersToQualities, new File(Configuration.getGRAPH_DIRECTORY(),
                Configuration.getUSER_QUALITIES()), true);

        System.out.println("User to Qualities graph added successfully to Graph directory!");
        StatCalculator statCalculator = new StatCalculator(usersToQualities);

        System.out.println("Now automating collection of significant conditional probabilities");
        statCalculator.automateConditionalProbCalculation(lexicon);
        // statCalculator.outputSignificantConditionalProbabilities();

        GraphReadWriteService graphReadWriteService = new GraphReadWriteService();
        graphReadWriteService.writeGephiFile(rtGraph, new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getGEPHI_FILE_1()));

        assignStances.determineProAntiVaxEvangelists(mentionGraph.getMentionGE(), mentionGraph, StanceFile);

        // is there a better way than this?
        for (int i = 0; i < 10; i++) {
            graphAnalyser.assignUserStances(mentionGraph.getMentionGraph());
            graphAnalyser.assignUserStances(mentionGraph.getMentionedGraph());
        }

        graphReadWriteService.writeGephiFile(mentionGraph.getMentionGraph(), new File(Configuration.getGRAPH_DIRECTORY(), "testMentionGraph.gdf"));
        graphReadWriteService.writeGephiFile(mentionGraph.getMentionedGraph(), new File(Configuration.getGRAPH_DIRECTORY(), "testMentionedGraph.gdf"));
        graphReadWriteService.writeGephiHashtagFile(hashtagToUsers, new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getGEPHI_HASHTAG_TO_USER_FILE()));


    }
}
