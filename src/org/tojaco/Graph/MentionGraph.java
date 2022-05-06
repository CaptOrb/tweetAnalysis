package org.tojaco.Graph;

import org.tojaco.Configuration;
import org.tojaco.FileIO.GraphReadWriteService;
import org.tojaco.FindGraphElements;
import org.tojaco.GraphElements.GraphElements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MentionGraph extends DirectedGraph {

    private static final ArrayList<String> mentions = new ArrayList<>();
    DirectedGraph mentionGraph;
    DirectedGraph mentionedGraph;

    public MentionGraph(GraphReadWriteService grfs) throws IOException {
        FindGraphElements mentionFGE = new FindGraphElements<>(new CreateUserVertex(), new CreateStringVertex());

        GraphElements mentionGE = new GraphElements();
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

    public DirectedGraph getMentionGraph() {
        return mentionGraph;
    }

    public DirectedGraph getMentionedGraph() {
        return mentionedGraph;
    }
}
