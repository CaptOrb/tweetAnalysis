package org.tojaco;

import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;

public class HashtagSummarizer<T,E> {
    public void summarizeHashtag(DirectedGraph<Hashtag, E> hashtagToUsers){
        for(Vertex<Hashtag> hashtag: hashtagToUsers.getGraph().keySet()) {
            for(int i=0; i<hashtag.getLabel().getWords().size();i++){
                System.out.println(hashtag.getLabel().getWords().get(i));
            }
        }
    }
}
