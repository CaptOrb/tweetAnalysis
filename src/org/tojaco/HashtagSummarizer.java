package org.tojaco;

import org.tojaco.Graph.*;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;

public class HashtagSummarizer<T,E> {
    public DirectedGraph<Hashtag, String> summarizeHashtag(DirectedGraph<Hashtag, E> hashtagToUsers, DirectedGraph<String, String> lexicon, GraphElements lexGraphElements){
        DirectedGraph<Hashtag, String> hashtagAnalyses = new DirectedGraph<>();

        for(Vertex<Hashtag> hashtag: hashtagToUsers.getGraph().keySet()) {
            for(int i=0; i<hashtag.getLabel().getWords().size();i++){
                VertexCreator<String> vertexCreator = new CreateStringVertex();
                Vertex<String> s1 = lexGraphElements.getVertex(hashtag.getLabel().getWords().get(i), vertexCreator);
                if(lexicon.getGraph().containsKey(s1)) {
                    for (Arc<String> arc : lexicon.getGraph().get(s1)) {
                        hashtagAnalyses.addArc(hashtag, arc);
                    }
                }
            }
        }

        return hashtagAnalyses;
    }

    public DirectedGraph<Hashtag, String> hashtagMadeUpOf(DirectedGraph<Hashtag, E> hashtagToUsers, GraphElements graphElements) {
        DirectedGraph<Hashtag, String> hashtagToWords = new DirectedGraph<>();

        for (Vertex<Hashtag> hashtag : hashtagToUsers.getGraph().keySet()) {
            for (int i = 0; i < hashtag.getLabel().getWords().size(); i++) {
                VertexCreator<String> vertexCreator = new CreateStringVertex();
                Vertex<String> destVertex = graphElements.getVertex(hashtag.getLabel().getWords().get(i), vertexCreator);
                Arc<String> myArc = new Arc<>(destVertex, +1);
                hashtagToWords.addArc(hashtag, myArc);
            }

        }

        return hashtagToWords;
    }

    public void assignGistOfTags(DirectedGraph<Hashtag, String> summarizedTags){
        for (Vertex<Hashtag> hashtagVertex : summarizedTags.getGraph().keySet()){
            int accepting = 0, rejecting = 0;
            for (Arc<String> arc : summarizedTags.getGraph().get(hashtagVertex)) {
                if ( arc.getVertex().getLabel().equals("accepting") ){
                    accepting = arc.getWeight();
                }
                else if( arc.getVertex().getLabel().equals("rejecting")){
                    rejecting = arc.getWeight();
                }
                else if( arc.getVertex().getLabel().contains("ref:") ){
                    hashtagVertex.getLabel().addRef(arc.getVertex().getLabel());
                }
            }
            if( accepting != 0 || rejecting != 0) {
                if (accepting > rejecting) {
                    hashtagVertex.getLabel().setAcceptance("accepting");
                } else {
                    hashtagVertex.getLabel().setAcceptance("rejecting");
                }
            }
        }
    }

    // returns 1 if a given hashtag focuses more on property1
    // returns 2 if a given hashtag focuses more on property2
    // returns 0 if a given hashtag focuses more on neither properties
    public int isFocusedOn(Hashtag hashtag, DirectedGraph<Hashtag, String> hashtagAnalyses, String property1, String property2, GraphElements graphElements){
        Vertex<Hashtag> vertex = graphElements.getVertex(hashtag.toString(), new CreateHashtagVertex());
        int property1Count = 0, property2Count = 0;
        for( Arc<String> arc : hashtagAnalyses.getGraph().get(vertex)) {
            if ( arc.getVertex().getLabel().equals(property1)){
                property1Count = arc.getWeight();
            }
            else if ( arc.getVertex().getLabel().equals(property2) ){
                property2Count = arc.getWeight();
            }
        }
        if( property1Count > property2Count ){
            return 1;
        }
        else if ( property2Count > property1Count ){
            return 2;
        }
        return 0;
    }
}
