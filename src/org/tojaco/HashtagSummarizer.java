package org.tojaco;

import org.tojaco.Graph.*;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;

import java.util.ArrayList;

public class HashtagSummarizer<T,E> {
    public void summarizeHashtag(DirectedGraph<Hashtag, E> hashtagToUsers, DirectedGraph<String, String> lexicon, GraphElements graphElements){
        for(Vertex<Hashtag> hashtag: hashtagToUsers.getGraph().keySet()) {
            for(int i=0; i<hashtag.getLabel().getWords().size();i++){
                VertexCreator<String> vertexCreator = new CreateStringVertex();
                Vertex<String> s1 = graphElements.getVertex(hashtag.getLabel().getWords().get(i), vertexCreator);
                ArrayList<Arc<String>> s2 = lexicon.getGraph().get(s1);
                String s = "";
            }
           // System.out.println();
        }
    }
}
