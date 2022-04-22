package org.tojaco;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;

import java.util.*;


public class HashtagSplitter<T,E>{

    public void splitHashtagsByCamelCase(DirectedGraph<Hashtag, E> hashtagToUsers){
        for(Vertex<Hashtag> hashtag: hashtagToUsers.getGraph().keySet()){
            String hashtagWord[] = hashtag.toString().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            for(int i=0; i<hashtagWord.length;i++){
                if(!(hashtagWord[i].contains("…"))){
                    hashtag.getLabel().addWord(hashtagWord[i].replaceAll("[#.,]","").toLowerCase());
                    //System.out.println(hashtagWord[i].replaceAll("[#.,]",""));
                }

                //System.out.print(hashtagWord[i].replaceAll("#",""));
            }
            //System.out.println();
        }
    }

    public void splitHashtagsByLexicon(DirectedGraph<Hashtag, E> sumHashTagGraph){

        for(Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet: sumHashTagGraph.getGraph().entrySet()){

            Vertex<Hashtag> hashtag = entrySet.getKey();

            //System.out.println(hashtag);



        }
    }
}
