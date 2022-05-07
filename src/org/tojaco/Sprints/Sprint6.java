package org.tojaco.Sprints;

import org.tojaco.*;
import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.Graph.CreateHashtagVertex;
import org.tojaco.Graph.CreateUserVertex;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Sprint6 {

    private static final ArrayList<String> retweets = new ArrayList<>();
    private static final ArrayList<String> hashtags = new ArrayList<>();
    public static ArrayList<String> getRetweets() {
        return retweets;
    }

    public static ArrayList<String> getHashtags() {
        return hashtags;
    }
    public void sprint6(File dataFile) throws IOException {
        // graph for using implemented methods on
        // see org.tojaco.Graph.DirectedGraph.java for description of public methods
        GraphElements graphElements = new GraphElements();
        FindGraphElements findGraphElements;

        DirectedGraph<TwitterUser, TwitterUser> rtGraph = new DirectedGraph<>();
        DirectedGraph<TwitterUser, TwitterUser> retweetedGraph;
        GraphReadWriteService rfs = new GraphReadWriteService();
        Lexicon lexicon = new Lexicon();
        DirectedGraph lexiconGraph;
        HashtagSummarizer hashtagSummarizer = new HashtagSummarizer();

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

        System.out.println("Retweet graph and retweeted graph added successfully to Graph directory!");

        FindEvangelists findEvangelists = new FindEvangelists();
        Map<Vertex<TwitterUser>, Integer> retweetHashMap = findEvangelists.findTotalRetweets(retweetedGraph);

        System.out.println("Now calculating hashtag graphs...");
        FindGraphElements fge1 = new FindGraphElements<>(new CreateUserVertex(), new CreateHashtagVertex());

        DirectedGraph<TwitterUser, Hashtag> usertoHashTag = fge1.createGraph(graphElements, getHashtags(), 0, 1);

        rfs.writeFileFromGraph(usertoHashTag,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getUSERS_TO_HASHTAGS()), true);

        FindGraphElements findGraphElements2 = new FindGraphElements<>(new CreateHashtagVertex(), new CreateUserVertex());

        DirectedGraph<Hashtag, TwitterUser> hashtagToUsers = findGraphElements2.createGraph(graphElements, getHashtags(), 1, 0);

        rfs.writeFileFromGraph(hashtagToUsers,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getHASHTAGS_TO_USERS()), true);

        HashtagSplitter hashtagSplitter = new HashtagSplitter();
        hashtagSplitter.splitHashtagsByCamelCase(hashtagToUsers);

        lexiconGraph = lexicon.getLexiconGraph();

        DirectedGraph<Hashtag, String> sumHashTagGraph = hashtagSummarizer.summarizeHashtag(hashtagToUsers, lexiconGraph, lexicon.getGraphElementsLexicon());

        hashtagSplitter.splitHashtagsByLexicon(hashtagToUsers, lexicon.getLexiconDictionary());

        rfs.writeFileFromGraph(lexicon.getLexiconGraph(), new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getLEXICON_FILE()), false);

        GraphElements graphElements2 = new GraphElements();

        DirectedGraph<Hashtag, String> hashtagToWordGraph = hashtagSummarizer.hashtagMadeUpOf(hashtagToUsers, graphElements2);

        rfs.writeFileFromGraph(hashtagToWordGraph, new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getHASHTAG_TO_WORDS()), false);

        hashtagSummarizer.assignGistOfTags(sumHashTagGraph);

        rfs.writeFileFromGraph(sumHashTagGraph, new File(Configuration.getGRAPH_DIRECTORY(),
                Configuration.getHASHTAG_SUMMARY_FILE()), true);

    }
}
