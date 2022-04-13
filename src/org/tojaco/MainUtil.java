package org.tojaco;

import org.tojaco.FileIO.RetweetFileService;
import org.tojaco.FileIO.TwitterFileService;
import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Graph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphAnalysis.RetweetGraphAnalyser;
import twitter4j.TwitterFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainUtil {

    private static final ArrayList<String> retweets = new ArrayList<>();
    private static final ArrayList<String> hashtags = new ArrayList<>();

    public static ArrayList<String> getRetweets() {
        return retweets;
    }

    public static ArrayList<String> getHashtags() {
        return hashtags;
    }

    public static void showProgramOptions(Configuration configuration, File dataFile) throws IOException {
        TwitterFileService ts = new TwitterFileService();

        System.out.println("Enter 1, 2, 3, or 4. " +
                "\n1. Search for Tweets using search API (Sprint 1)" +
                "\n2. Search for Tweets using the steaming API (Sprint 2)" +
                "\n3. Build a retweet Graph (Sprint 3)" +
                "\n4. Assign stances to Tweets (Sprint 4)" +
                "\n5. Assign stances to HashTags (Sprint 5)" +
                "\nOr enter -1 to quit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        FindGraphElements findGraphElements;
        DirectedGraph<TwitterUser, TwitterUser> rtGraph;
        DirectedGraph<TwitterUser, TwitterUser> retweetedGraph;

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
                findGraphElements = new FindGraphElements();
                TwitterUsers usersSprint3 = new TwitterUsers();

//                if (dataFile.exists()) {
//                    findGraphElements.initialiseRetweets(dataFile);
//                }
//
//                rtGraph = findGraphElements.toPutIntoHashMap(usersSprint3, 0, 1);
                System.out.println("Retweet graph added successfully to org.tojaco.Graph directory!");

                //showSprint3Options(rtGraph, usersSprint3);
                break;
            case 4:

                // graph for using implemented methods on
                // see org.tojaco.Graph.DirectedGraph.java for description of public methods

                findGraphElements = new FindGraphElements();

                RetweetFileService<String> rfs = new RetweetFileService<>();

                if (dataFile.exists()) {
                    getRetweets().addAll(rfs.readRetweetsIntoSet(dataFile));
                }
                TwitterUsers usersSprint5 = new TwitterUsers();

                getHashtags().addAll(rfs.getHashtags());

                rtGraph = findGraphElements.toPutIntoHashMap(getRetweets(), 0, 1);
                retweetedGraph = findGraphElements.toPutIntoHashMap(getRetweets(), 1, 0);
                System.out.println("Retweet graph added successfully to org.tojaco.Graph directory!");

//                FindEvangelists findEvangelist = new FindEvangelists();
//                Map<Vertex<TwitterUser>, Integer> retweetsHashMap = findEvangelist.findTotalRetweets(retweetedGraph, usersSprint4);
//
//                AssignStances assignStances = new AssignStances();
//                File StanceFile = new File(configuration.getSTANCE_FILE());
//                assignStances.determineProAntiVaxEvangelists(usersSprint4, StanceFile);

                // initial setup for calculating stances
                RetweetGraphAnalyser graphAnalyser = new RetweetGraphAnalyser();

//                for (int i = 0; i < 20; i++) {
//                    graphAnalyser.assignUserStances(rtGraph, usersSprint4);
//                    graphAnalyser.assignUserStances(retweetedGraph, usersSprint4);
//
//                }

                // get coverage of stances
                //System.out.println("Coverage in graph: " + graphAnalyser.calculateCoverage(rtGraph, usersSprint4) + "%");
                // System.out.println("Coverage in retweeted graph: " + graphAnalyser.calculateCoverage(retweetedGraph));

//                System.out.println("Percentage of users without a stance: " + (graphAnalyser.calculateCoverage(rtGraph, usersSprint4) - 100) * -1 + "%");
//
//                System.out.println("Percentage positive stances: " + graphAnalyser.calculatePercentagePositiveStances(rtGraph, usersSprint4) + "%");
//                System.out.println("Percentage negative stance: " + graphAnalyser.calculatePercentageNegativeStances(rtGraph,usersSprint4) + "%");
//
//                Users100 users100 = new Users100();
//                users100.checkStance(retweetsHashMap);


                break;

            case 5:

                // graph for using implemented methods on
                // see org.tojaco.Graph.DirectedGraph.java for description of public methods


                findGraphElements = new FindGraphElements();

                RetweetFileService<String> rfs1 = new RetweetFileService<>();
                if (dataFile.exists()) {
                    getRetweets().addAll(rfs1.readRetweetsIntoSet(dataFile));
                }
                //TwitterUsers usersSprint5 = new TwitterUsers();

                getHashtags().addAll(rfs1.getHashtags());

                rtGraph = findGraphElements.toPutIntoHashMap(getRetweets(), 0, 1);
                retweetedGraph = findGraphElements.toPutIntoHashMap(getRetweets(), 1, 0);
                System.out.println("Retweet graph added successfully to org.tojaco.Graph directory!");
//
//                findEvangelist = new FindEvangelists();
//
//
//                assignStances = new AssignStances();
//                StanceFile = new File(configuration.getSTANCE_FILE());
//                assignStances.determineProAntiVaxEvangelists(usersSprint5, StanceFile);
//
//                // initial setup for calculating stances
//                graphAnalyser = new RetweetGraphAnalyser();
//
//                for (int i = 0; i < 20; i++) {
//                    graphAnalyser.assignUserStances(rtGraph, usersSprint5);
//                    graphAnalyser.assignUserStances(retweetedGraph, usersSprint5);
//
//                }

                break;
        }
    }

    public static void showSprint3Options(Graph<TwitterUser, TwitterUser> rtGraph, TwitterUsers users) {
        int option = 0;

        System.out.println("Retweet graph added successfully to org.tojaco.Graph directory!");

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

                Vertex<TwitterUser> start = users.getVertex(newArc[0]);
                Vertex<TwitterUser> end = users.getVertex(newArc[1]);

                if (rtGraph.hasArcBetween(start, end)) {
                    System.out.println("There already exists an arc between these two vertices.");
                } else {
                    Arc<TwitterUser> arc = new Arc<>(end, Integer.parseInt(newArc[2]));
                    rtGraph.addArc(start, arc);

                    System.out.print("org.tojaco.Graph.Vertex with " + start + " and arc with " + end.toString() + " was added to the graph.\n");

                }
            }
            if (option == 2) {

                System.out.print("Enter first and second vertex (remember to include @ symbol): ");
                String[] vertices = new String[2];

                for (int i = 0; i < 2; i++) {
                    vertices[i] = scanner.next();
                }

                Vertex<TwitterUser> vertex1 = users.getVertex(vertices[0]);

                Vertex<TwitterUser> vertex2 = users.getVertex(vertices[1]);

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
