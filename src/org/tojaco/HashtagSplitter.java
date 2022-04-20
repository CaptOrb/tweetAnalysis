package org.tojaco;

import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;


public class HashtagSplitter<T,E>{

    public void splitHashtagsByCamelCase(DirectedGraph<Hashtag, E> hashtagToUsers){
        for(Vertex<Hashtag> hashtag: hashtagToUsers.getGraph().keySet()){
            String hashtagWord[] = hashtag.toString().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            for(int i=0; i<hashtagWord.length;i++){
                if(!(hashtagWord[i].contains("..."))){
                    hashtag.getLabel().addWord(hashtagWord[i].replaceAll("[#.,]",""));
                }
                //System.out.print(hashtagWord[i].replaceAll("#",""));
            }
        }
    }
}
