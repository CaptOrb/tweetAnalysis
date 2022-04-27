package org.tojaco;

import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.FileIO.LexiconFileService;
import org.tojaco.FileIO.TwitterFileService;
import org.tojaco.Graph.*;
import org.tojaco.GraphAnalysis.GraphAnalyser;
import org.tojaco.GraphAnalysis.StanceAnalysis;
import org.tojaco.GraphAnalysis.StatCalculator;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;
import twitter4j.TwitterFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class MainUtil {

    private static final ArrayList<String> retweets = new ArrayList<>();
    private static final ArrayList<String> hashtags = new ArrayList<>();
    private static final ArrayList<String> lexicon = new ArrayList<>();

    public static ArrayList<String> getRetweets() {
        return retweets;
    }

    public static ArrayList<String> getHashtags() {
        return hashtags;
    }

    public static ArrayList<String> getLexicon() {
        return lexicon;
    }

    public static void showProgramOptions(Configuration configuration, File dataFile) throws IOException {
        TwitterFileService ts = new TwitterFileService();

        System.out.println("Enter 1, 2, 3, 4, 5 or 7 " +
                "\n1. Search for Tweets using search API (Sprint 1)" +
                "\n2. Search for Tweets using the streaming API (Sprint 2)" +
                "\n3. Build a retweet Graph (Sprint 3)" +
                "\n4. Assign stances to Tweets (Sprint 4)" +
                "\n5. Assign stances to HashTags (Sprint 5)" +
                "\n6. Get the gist of a Hashtag (Sprint 6)" +
                "\n7. Assign a “psychological profile” to each user in the dataset  (Sprint 7)" +
                "\nOr enter -1 to quit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        GraphElements graphElements = new GraphElements();
        FindGraphElements findGraphElements;
        DirectedGraph<TwitterUser, TwitterUser> rtGraph;
        DirectedGraph<TwitterUser, TwitterUser> retweetedGraph;
        GraphReadWriteService rfs = new GraphReadWriteService();
        File lexiconFile;

        switch (option) {
            case 1:
                TwitterFactory tf = configuration.getTwitterFactory(configuration);

                GrabTweets grabTweets = new GrabTweets();

                if (dataFile.exists()) {
                    ts.readTweetsIntoSet(dataFile);
                }

                grabTweets.grabSomeTweets(tf, configuration, ts);
                break;
            case 2:
                StreamTweets st = new StreamTweets(configuration, ts);

                if (dataFile.exists()) {
                    ts.readTweetsIntoSet(dataFile);
                }
                st.streamTweets();
                break;
            case 3:
                findGraphElements = new FindGraphElements<>(new CreateUserVertex(), new CreateUserVertex());

                if (dataFile.exists()) {
                    getRetweets().addAll(rfs.loadDataFromInputFile(dataFile));
                }

                rtGraph = findGraphElements.createGraph(graphElements, getRetweets(), 0, 1);

                retweetedGraph = findGraphElements.createGraph(graphElements, getRetweets(), 1, 0);

                rfs.writeFileFromGraph(rtGraph,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTGRAPH_OUTPUT_FILE()),true);

                rfs.writeFileFromGraph(retweetedGraph,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTWEETEDGRAPH_OUTPUT_FILE()),true);
                System.out.println("Retweet graph added successfully to Graph directory!");


                showSprint3Options(rtGraph, graphElements);
                break;
            case 4:

                // graph for using implemented methods on
                // see org.tojaco.Graph.DirectedGraph.java for description of public methods

                findGraphElements = new FindGraphElements<>(new CreateUserVertex(), new CreateUserVertex());

                rfs = new GraphReadWriteService();

                if (dataFile.exists()) {
                    getRetweets().addAll(rfs.loadDataFromInputFile(dataFile));
                }

                getHashtags().addAll(rfs.getHashtags());


                rtGraph = findGraphElements.createGraph(graphElements, getRetweets(), 0, 1);
                retweetedGraph = findGraphElements.createGraph(graphElements, getRetweets(), 1, 0);

                rfs.writeFileFromGraph(rtGraph,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTGRAPH_OUTPUT_FILE()),true);

                rfs.writeFileFromGraph(retweetedGraph,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTWEETEDGRAPH_OUTPUT_FILE()),true);
                System.out.println("Retweet graph added successfully to Graph directory!");

                FindEvangelists findEvangelist = new FindEvangelists();
                Map<Vertex<TwitterUser>, Integer> retweetsHashMap = findEvangelist.findTotalRetweets(retweetedGraph);

                AssignStances assignStances = new AssignStances();
                File StanceFile = new File(configuration.getSTANCE_FILE());
                assignStances.determineProAntiVaxEvangelists(graphElements, rtGraph, StanceFile);

                // initial setup for calculating stances
                GraphAnalyser graphAnalyser = new GraphAnalyser();

                for (int i = 0; i < 20; i++) {
                    graphAnalyser.assignUserStances(rtGraph);
                    graphAnalyser.assignUserStances(retweetedGraph);

                }

                outputGraphAnalysis(graphAnalyser, rtGraph, graphElements, false);
                StanceAnalysis users100 = new StanceAnalysis();
                users100.checkStance100Users(retweetsHashMap);

                break;

            case 5:

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
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTGRAPH_OUTPUT_FILE()),true);

                rfs.writeFileFromGraph(retweetedGraph,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTWEETEDGRAPH_OUTPUT_FILE()),true);

                System.out.println("Retweet graph and retweeted graph added successfully to Graph directory!");

                FindEvangelists findEvangelists = new FindEvangelists();
                Map<Vertex<TwitterUser>, Integer> retweetHashMap = findEvangelists.findTotalRetweets(retweetedGraph);

                assignStances = new AssignStances();
                StanceFile = new File(configuration.getSTANCE_FILE());
                assignStances.determineProAntiVaxEvangelists(graphElements, rtGraph, StanceFile);

                // initial setup for calculating stances
                graphAnalyser = new GraphAnalyser();

                for (int i = 0; i < 10; i++) {
                    graphAnalyser.assignUserStances(rtGraph);
                    graphAnalyser.assignUserStances(retweetedGraph);

                }

                StanceAnalysis analysis = new StanceAnalysis();
                analysis.checkStance100Users(retweetHashMap);
 /*               double num = analysis.UsersWithNoStance(retweetHashMap);
                System.out.println(num + " divided by " + retweetHashMap.size() +"(except idk why it says only 42,905 users, theres like more than 300,000) = "+ (num/retweetHashMap.size())*100 + "%");
*/
                outputGraphAnalysis(graphAnalyser, rtGraph, graphElements, false);

                System.out.println("Now calculating hashtag graphs...");
                FindGraphElements<TwitterUser, Hashtag> fge1 = new FindGraphElements<>(new CreateUserVertex(), new CreateHashtagVertex());
                DirectedGraph<TwitterUser, Hashtag> usertoHashTag;
                usertoHashTag = fge1.createGraph(graphElements, getHashtags(), 0, 1);

                rfs.writeFileFromGraph(usertoHashTag,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getUSERS_TO_HASHTAGS()),true);


                FindGraphElements<Hashtag, TwitterUser> findGraphElements2 = new FindGraphElements<>(new CreateHashtagVertex(), new CreateUserVertex());
                DirectedGraph<Hashtag, TwitterUser> hashtagToUsers;
                hashtagToUsers = findGraphElements2.createGraph(graphElements, getHashtags(), 1, 0);

                rfs.writeFileFromGraph(hashtagToUsers,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getHASHTAGS_TO_USERS()),true);


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
                outputGraphAnalysis(graphAnalyser, rtGraph, graphElements, true);
                // StanceAnalysis analyse = new StanceAnalysis();
                // users100New.checkStance(retweetHashMap);
                // analyse.assignStancesByHashtags( hashtagToUsers,graphElements, rtGraph);

                //analyse.find100Hashtags(hashtagToUsers);

                // analyse.find100HashtagsS5(rtGraph, hashtagToUsers);


                break;

            case 6:
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
                        new File(configuration.getGRAPH_DIRECTORY(),
                               configuration.getRTGRAPH_OUTPUT_FILE()),true);

                rfs.writeFileFromGraph(retweetedGraph,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTWEETEDGRAPH_OUTPUT_FILE()),true);

                System.out.println("Retweet graph and retweeted graph added successfully to Graph directory!");

                findEvangelists = new FindEvangelists();
                retweetHashMap = findEvangelists.findTotalRetweets(retweetedGraph);

                System.out.println("Now calculating hashtag graphs...");
                fge1 = new FindGraphElements<>(new CreateUserVertex(), new CreateHashtagVertex());

                usertoHashTag = fge1.createGraph(graphElements, getHashtags(), 0, 1);

                rfs.writeFileFromGraph(usertoHashTag,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getUSERS_TO_HASHTAGS()),true);

                findGraphElements2 = new FindGraphElements<>(new CreateHashtagVertex(), new CreateUserVertex());

                hashtagToUsers = findGraphElements2.createGraph(graphElements, getHashtags(), 1, 0);

                rfs.writeFileFromGraph(hashtagToUsers,
                       new File(configuration.getGRAPH_DIRECTORY(),
                               configuration.getHASHTAGS_TO_USERS()),true);

                HashtagSplitter hashtagSplitter = new HashtagSplitter();
                hashtagSplitter.splitHashtagsByCamelCase(hashtagToUsers);

                GraphElements graphElementsLexicon = new GraphElements();

                lexiconFile = new File(configuration.getLEXICON_FOLDER(), configuration.getLEXICON_DATA_FILE());

                LexiconFileService lfs = new LexiconFileService();

                if (lexiconFile.exists()) {
                    getLexicon().addAll(lfs.readLexiconFile(lexiconFile));
                }

                FindGraphElements<String, String> findGraphElementsLex = new FindGraphElements<>(new CreateStringVertex(), new CreateStringVertex());
                DirectedGraph<String,String> lexiconGraph = findGraphElementsLex.createGraph(graphElementsLexicon, getLexicon(), 0, 1);

                HashtagSummarizer hashtagSummarizer = new HashtagSummarizer();

                DirectedGraph<Hashtag,String> sumHashTagGraph = hashtagSummarizer.summarizeHashtag(hashtagToUsers, lexiconGraph, graphElementsLexicon);

                hashtagSplitter.initialiseLexiconDictionary(lexiconGraph);

                hashtagSplitter.splitHashtagsByLexicon(hashtagToUsers);

                rfs.writeFileFromGraph(lexiconGraph, new File(configuration.getGRAPH_DIRECTORY(), configuration.getLEXICON_FILE()),false);

                GraphElements graphElements2 = new GraphElements();

                DirectedGraph<Hashtag,String> hashtagToWordGraph = hashtagSummarizer.hashtagMadeUpOf(hashtagToUsers,graphElements2);

                rfs.writeFileFromGraph( hashtagToWordGraph, new File(configuration.getGRAPH_DIRECTORY(), configuration.getHASHTAG_TO_WORDS()), false);

                hashtagSummarizer.assignGistOfTags(sumHashTagGraph);

                rfs.writeFileFromGraph(sumHashTagGraph, new File(configuration.getGRAPH_DIRECTORY(),
                        configuration.getHASHTAG_SUMMARY_FILE()) , true);

                break;

            case 7:

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
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTGRAPH_OUTPUT_FILE()),true);

                rfs.writeFileFromGraph(retweetedGraph,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getRTWEETEDGRAPH_OUTPUT_FILE()),true);

                System.out.println("Retweet graph and retweeted graph added successfully to Graph directory!");

                findEvangelists = new FindEvangelists();
                retweetHashMap = findEvangelists.findTotalRetweets(retweetedGraph);

                System.out.println("Now calculating hashtag graphs...");
                fge1 = new FindGraphElements<>(new CreateUserVertex(), new CreateHashtagVertex());

                usertoHashTag = fge1.createGraph(graphElements, getHashtags(), 0, 1);

                rfs.writeFileFromGraph(usertoHashTag,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getUSERS_TO_HASHTAGS()),true);

                findGraphElements2 = new FindGraphElements<>(new CreateHashtagVertex(), new CreateUserVertex());

                hashtagToUsers = findGraphElements2.createGraph(graphElements, getHashtags(), 1, 0);

                rfs.writeFileFromGraph(hashtagToUsers,
                        new File(configuration.getGRAPH_DIRECTORY(),
                                configuration.getHASHTAGS_TO_USERS()),true);

                graphAnalyser = new GraphAnalyser();

                for (int i = 0; i < 3; i++) { //theres no change in coverage from 3 to 4, but theres a change in coverage from 2 to 3
                    graphAnalyser.assignUserStances(usertoHashTag);
                    graphAnalyser.assignUserStances(hashtagToUsers);

                }
                //by running this again we get more coverage
                for (int i = 0; i < 5; i++) { //by upping this to 10 there's no change in coverage
                    graphAnalyser.assignUserStances(rtGraph);
                    graphAnalyser.assignUserStances(retweetedGraph);

                }

                hashtagSplitter = new HashtagSplitter();
                hashtagSplitter.splitHashtagsByCamelCase(hashtagToUsers);

                 graphElementsLexicon = new GraphElements();

                 lexiconFile = new File(configuration.getLEXICON_FOLDER(), configuration.getLEXICON_DATA_FILE());

                 lfs = new LexiconFileService();

                if (lexiconFile.exists()) {
                    getLexicon().addAll(lfs.readLexiconFile(lexiconFile));
                }

                findGraphElementsLex = new FindGraphElements<>(new CreateStringVertex(), new CreateStringVertex());
                lexiconGraph = findGraphElementsLex.createGraph(graphElementsLexicon, getLexicon(), 0, 1);

                hashtagSummarizer = new HashtagSummarizer();

                sumHashTagGraph = hashtagSummarizer.summarizeHashtag(hashtagToUsers, lexiconGraph, graphElementsLexicon);

                hashtagSplitter.initialiseLexiconDictionary(lexiconGraph);

                hashtagSplitter.splitHashtagsByLexicon(hashtagToUsers);

                rfs.writeFileFromGraph(lexiconGraph, new File(configuration.getGRAPH_DIRECTORY(), configuration.getLEXICON_FILE()),false);

                graphElements2 = new GraphElements();

                hashtagToWordGraph = hashtagSummarizer.hashtagMadeUpOf(hashtagToUsers,graphElements2);

                rfs.writeFileFromGraph( hashtagToWordGraph, new File(configuration.getGRAPH_DIRECTORY(), configuration.getHASHTAG_TO_WORDS()), false);

                hashtagSummarizer.assignGistOfTags(sumHashTagGraph);

                rfs.writeFileFromGraph(sumHashTagGraph, new File(configuration.getGRAPH_DIRECTORY(),
                        configuration.getHASHTAG_SUMMARY_FILE()) , true);

                ModelUser modelUser = new ModelUser();
                //modelUser.findHashtagsForEachUser(usertoHashTag, sumHashTagGraph);
                modelUser.addSummaryOfHashtag(sumHashTagGraph);
                modelUser.addSummaryOfHashtagToUserQualities(usertoHashTag);

                DirectedGraph<TwitterUser, String> usersToQualities = new DirectedGraph<>();

                usersToQualities = modelUser.makeUserToQualityGraph(usertoHashTag, graphElements);

                rfs.writeFileFromGraph(usersToQualities, new File(configuration.getGRAPH_DIRECTORY(),
                        configuration.getUSER_QUALITIES()) , true);

                StatCalculator statCalculator = new StatCalculator(usersToQualities);

                // not 100% done yet
                //statCalculator.calConditionalProbability(usersToQualities);

                statCalculator.calculateZScore(false, "-ref:fauci");

                break;
        }
    }

    private static void outputGraphAnalysis(GraphAnalyser graphAnalyser, DirectedGraph graph, GraphElements graphElements
            , boolean hashtagsUsed) {

        if (hashtagsUsed) {
            System.out.println("AFTER USING HASHTAGS:");
        }
        System.out.println("Coverage in graph: " + graphAnalyser.calculateCoverage(graph, graphElements) + "%");
        System.out.println("Percentage of users without a stance: " + (graphAnalyser.calculateCoverage(graph, graphElements) - 100) * -1 + "%");
        System.out.println("Percentage positive stances: " + graphAnalyser.calculatePercentagePositiveStances(graph, graphElements) + "%");
        System.out.println("Percentage negative stance: " + graphAnalyser.calculatePercentageNegativeStances(graph, graphElements) + "%");

    }


    public static void showSprint3Options(Graph<TwitterUser, TwitterUser> rtGraph, GraphElements graphElements) {
        int option = 0;

        System.out.println("Retweet graph added successfully to Graph directory!");

        while (option != -1) {

            System.out.println("Enter 1, 2, or 3. " +
                    "\n1. Add new arc to graph" +
                    "\n2. Query whether there is an arc between two vertices" +
                    "\n3. Request list of all vertices that a given vertex points to" +
                    "\nOr enter -1 to quit");
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();

            if (option == 1) {
                System.out.print("Enter start vertex, end vertex and numeric label, separated by spaces: ");
                String[] newArc = new String[3];
                for (int i = 0; i < 3; i++) {
                    newArc[i] = scanner.next();
                }

                Vertex<TwitterUser> start = graphElements.getVertex(newArc[0], new CreateUserVertex());
                Vertex<TwitterUser> end = graphElements.getVertex(newArc[1], new CreateUserVertex());

                if (rtGraph.hasArcBetween(start, end)) {
                    System.out.println("There already exists an arc between these two vertices.");
                } else {
                    Arc<TwitterUser> arc = new Arc<>(end, Integer.parseInt(newArc[2]));
                    rtGraph.addArc(start, arc);

                    System.out.print("Vertex with " + start + " and arc with " + end.toString() + " was added to the graph.\n");

                }
            }
            if (option == 2) {

                System.out.print("Enter first and second vertex (remember to include @ symbol): ");
                String[] vertices = new String[2];

                for (int i = 0; i < 2; i++) {
                    vertices[i] = scanner.next();
                }

                Vertex<TwitterUser> vertex1 = graphElements.getVertex(vertices[0], new CreateUserVertex());

                Vertex<TwitterUser> vertex2 = graphElements.getVertex(vertices[1], new CreateUserVertex());

                boolean hasArc = rtGraph.hasArcBetween(vertex1, vertex2);

                System.out.println("There is an arc between " + vertices[0] + " and "
                        + vertices[1] + ": " + hasArc + ", and the label between them is " + rtGraph.getLabelBetweenVertices(vertex1, vertex2));
            }
            if (option == 3) {
                System.out.print("Enter a vertex to find its outgoing arcs:  (not yet implemented)\n");

            }
        }
    }
}
