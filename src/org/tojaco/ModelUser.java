package org.tojaco;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.util.ArrayList;
import java.util.Map;

public class ModelUser {


    public void addSummaryOfHashtag(DirectedGraph<Hashtag, String> hashtagToSummary){
        for(Map.Entry<Vertex<Hashtag>, ArrayList<Arc<String>>> entry: hashtagToSummary.getGraph().entrySet()){
            //System.out.println(entry.getKey().toString());
            for(int i=0; i<entry.getValue().size(); i++){
                entry.getKey().getLabel().addQuality(entry.getValue().get(i).getVertex().toString());
                //System.out.print(" " + entry.getValue().get(i).getVertex().toString());
            }
            //System.out.println();
        }
    }

    public void addSummaryOfHashtagToUserQualities(DirectedGraph<TwitterUser, Hashtag> userToHashtag){

        for(Map.Entry<Vertex<TwitterUser>, ArrayList<Arc<Hashtag>>> entry: userToHashtag.getGraph().entrySet()) {
           System.out.println(entry.getKey());
            for(int i=0; i<entry.getValue().size(); i++){
                //for(int j=0; j<entry.getValue().get(j).getVertex().getLabel().getQualities().size(); j++){
                    entry.getKey().getLabel().addQuality(entry.getValue().get(i).getVertex().getLabel().getQualities());
                //}
            }
            for(int i = 0; i<entry.getKey().getLabel().getQualities().size(); i++){
                System.out.println(entry.getKey().getLabel().getQualities().get(i));
            }
        }
    }
}
