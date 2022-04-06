package org.tojaco;

import org.tojaco.FileIO.TwitterFileService;
import org.tojaco.Graph.*;
import twitter4j.TwitterFactory;

import java.util.Scanner;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        try {
            // user chooses to provide a config file as a command arg
            if (args.length == 1) {
                configuration.getSettingsFromFile(configuration, args[0], 1);
            } else {
                // config file is on class path
                configuration.getSettingsFromFile(configuration, "config_file", 0);
            }

            File dataFile = new File(configuration.getDataDirectory(), configuration.getDataFile());

            showProgramOptions(configuration, dataFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void showProgramOptions(Configuration configuration, File dataFile) throws IOException {
        TwitterFileService ts = new TwitterFileService();

        System.out.println("Enter 1, 2, 3, or 4. " +
                "\n1. Search for Tweets using search API (Sprint 1)" +
                "\n2. Search for Tweets using the steaming API (Sprint 2)" +
                "\n3. Build a retweet Graph (Sprint 3)" +
                "\n4. Assign stances to Tweets (Sprint 4)" +
                "\nOr enter -1 to quit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        FindRetweets findRetweets;
        RetweetGraph<String> rtGraph;
        RetweetGraph<String> rtGraph1;
        switch (option){
            case 1:
                TwitterFactory tf = configuration.getTwitterFactory(configuration);

                GrabTweets grabTweets = new GrabTweets();

                grabTweets.grabSomeTweets(tf, configuration, ts);
                break;
            case 2:
                StreamTweets st = new StreamTweets(configuration, ts);
                st.streamTweets();
                break;
            case 3:
                 findRetweets = new FindRetweets();
                 rtGraph = findRetweets.toPutIntoHashMap(configuration, 0, 1);

                if (dataFile.exists()) {
                    findRetweets.initialiseRetweets(dataFile);
                }
                rtGraph1 = findRetweets.toPutIntoHashMap(configuration, 1, 0);

                System.out.println("Retweet graph added successfully to org.tojaco.Graph directory!");

                showSprint3Options(findRetweets, rtGraph);
                break;
            case 4:

                // graph for using implemented methods on
                // see org.tojaco.Graph.RetweetGraph.java for description of public methods

                findRetweets = new FindRetweets();

                if (dataFile.exists()) {
                    findRetweets.initialiseRetweets(dataFile);
                }
                rtGraph = findRetweets.toPutIntoHashMap(configuration, 0, 1);
                rtGraph1 = findRetweets.toPutIntoHashMap(configuration, 1, 0);

                System.out.println("Retweet graph added successfully to org.tojaco.Graph directory!");

                RetweetGraph<String> retweetedGraph = findRetweets.toPutIntoHashMap(configuration, 1, 0);

                FindEvangeLists findEvangelist = new FindEvangeLists();
                findEvangelist.findTotalRetweets(retweetedGraph);
                break;
        }

    }

    public static void showSprint3Options(FindRetweets findRetweets, RetweetGraph<String> rtGraph) {
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

                Vertex<String> start = findRetweets.getVertex(newArc[0], rtGraph.getAllVerticesInGraph());

                // new org.tojaco.Graph.Vertex<String>(newArc[0]);
                Vertex<String> end = findRetweets.getVertex(newArc[1], rtGraph.getAllVerticesInGraph());
                //new org.tojaco.Graph.Vertex<String>(newArc[1]);

                if (rtGraph.hasArcBetween(start, end)) {
                    System.out.println("There already exists an arc between these two vertices.");
                } else {
                    Arc<String> arc = new Arc<>(end, Integer.parseInt(newArc[2]));
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

                Vertex<String> vertex1 = findRetweets.getVertex(vertices[0], rtGraph.getAllVerticesInGraph());

                Vertex<String> vertex2 = findRetweets.getVertex(vertices[1], rtGraph.getAllVerticesInGraph());

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

