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

    public void sprint6(File dataFile) throws IOException {

        final ArrayList<String> getRetweets = new ArrayList<>();

        // graph for using implemented methods on
        // see org.tojaco.Graph.DirectedGraph.java for description of public methods
        GraphElements graphElements = new GraphElements();
        FindGraphElements<TwitterUser,TwitterUser> findGraphElements;

        GraphReadWriteService rfs = new GraphReadWriteService();
        Lexicon<String> lexicon = new Lexicon<>();

        HashtagSummarizer hashtagSummarizer = new HashtagSummarizer<>();

        findGraphElements = new FindGraphElements<>(new CreateUserVertex(), new CreateUserVertex());

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

        System.out.println("Now calculating hashtag graphs...");
        FindGraphElements<TwitterUser, Hashtag> userHashTagFGE = new FindGraphElements<>(new CreateUserVertex(), new CreateHashtagVertex());

        DirectedGraph<TwitterUser, Hashtag> userToHashTag = userHashTagFGE.createGraph(graphElements, getHashtags, 0, 1);

        rfs.writeFileFromGraph(userToHashTag,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getUSERS_TO_HASHTAGS()), true);

        FindGraphElements<Hashtag,TwitterUser> HashTagUserFGE = new FindGraphElements<>(new CreateHashtagVertex(), new CreateUserVertex());

        DirectedGraph<Hashtag, TwitterUser> hashtagToUsers = HashTagUserFGE.createGraph(graphElements, getHashtags, 1, 0);

        rfs.writeFileFromGraph(hashtagToUsers,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getHASHTAGS_TO_USERS()), true);

        HashtagSplitter hashtagSplitter = new HashtagSplitter<>();
        hashtagSplitter.splitHashtagsByCamelCase(hashtagToUsers);

        DirectedGraph<String,String> lexiconGraph = lexicon.getLexiconGraph();

        DirectedGraph<Hashtag, String> sumHashTagGraph = hashtagSummarizer.summarizeHashtag(hashtagToUsers, lexiconGraph, lexicon.getGraphElementsLexicon());

        hashtagSplitter.splitHashtagsByLexicon(hashtagToUsers, lexicon.getLexiconDictionary());

        rfs.writeFileFromGraph(lexicon.getLexiconGraph(), new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getLEXICON_FILE()), false);

        GraphElements hashTagWordsGE = new GraphElements();

        DirectedGraph<Hashtag, String> hashtagToWordGraph = hashtagSummarizer.hashtagMadeUpOf(hashtagToUsers, hashTagWordsGE);

        rfs.writeFileFromGraph(hashtagToWordGraph, new File(Configuration.getGRAPH_DIRECTORY(), Configuration.getHASHTAG_TO_WORDS()), false);

        hashtagSummarizer.assignGistOfTags(sumHashTagGraph);

        rfs.writeFileFromGraph(sumHashTagGraph, new File(Configuration.getGRAPH_DIRECTORY(),
                Configuration.getHASHTAG_SUMMARY_FILE()), true);

    }
}
