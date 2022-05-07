package org.tojaco.Graph;

import org.tojaco.Configuration;
import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.FindGraphElements;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.TwitterUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MentionGraph extends DirectedGraph<TwitterUser,TwitterUser> {

    private static final ArrayList<String> mentions = new ArrayList<>();
    private final DirectedGraph<TwitterUser,TwitterUser> mentionGraph;
    private final DirectedGraph<TwitterUser,TwitterUser> mentionedGraph;
    private final FindGraphElements<TwitterUser, TwitterUser> mentionFGE;
    private final GraphElements mentionGE = new GraphElements();

    public MentionGraph(GraphReadWriteService grfs) throws IOException {
        mentionFGE = new FindGraphElements<>(new CreateUserVertex(), new CreateUserVertex());

        mentions.addAll(grfs.getMentions());

        mentionGraph = mentionFGE.createGraph(mentionGE, mentions, 0,1);
        mentionedGraph = mentionFGE.createGraph(mentionGE, mentions, 1,0);

        grfs.writeFileFromGraph(mentionGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getMentionsFile()), true);

        grfs.writeFileFromGraph(mentionedGraph,
                new File(Configuration.getGRAPH_DIRECTORY(),
                        Configuration.getMentionedFile()), true);

    }

    public DirectedGraph<TwitterUser, TwitterUser> getMentionGraph() {
        return mentionGraph;
    }

    public DirectedGraph<TwitterUser,TwitterUser> getMentionedGraph() {
        return mentionedGraph;
    }

    public GraphElements getMentionGE() {
        return mentionGE;
    }

    public FindGraphElements<TwitterUser, TwitterUser> getMentionFGE() {
        return mentionFGE;
    }
}
