package org.tojaco;

import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;


public class HashtagSplitter<T,E>{

    public void splitHashtags(DirectedGraph<Hashtag, E> hashtagToUsers){
        for(Vertex<Hashtag> hashtag: hashtagToUsers.getGraph().keySet()){
            String hashtagWords[] = hashtag.toString().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            for(int i=0; i<hashtagWords.length;i++){
                hashtag.getLabel().addWord(hashtagWords[i].replaceAll("#","").replaceAll("\n",""));
                System.out.println(hashtagWords[i].replaceAll("#","").replaceAll("\n",""));
            }
        }
    }
}
