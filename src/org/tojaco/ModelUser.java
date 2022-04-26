package org.tojaco;

import org.tojaco.Graph.*;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.util.ArrayList;
import java.util.Map;

public class ModelUser {

    //by adding list of qualities to each hashtag, and then adding the qualities of the hashtags that user uses, we create model of that user

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
            for(int i=0; i<entry.getValue().size(); i++){
                entry.getKey().getLabel().addQuality(entry.getValue().get(i).getVertex().getLabel().getQualities());
            }
        }
    }

    public DirectedGraph<TwitterUser, String> makeUserToQualityGraph(DirectedGraph<TwitterUser, Hashtag> usersToHashtags, GraphElements graphElements){
        DirectedGraph<TwitterUser, String> usersToQualities = new DirectedGraph();

        for(Vertex<TwitterUser> user: usersToHashtags.getGraph().keySet()) {
            for(int i=0; i<user.getLabel().getQualities().size();i++){
                VertexCreator<String> vertexCreator = new CreateStringVertex();
                Vertex<String> destVertex = graphElements.getVertex(user.getLabel().getQualities().get(i), vertexCreator);
                Arc<String> myArc = new Arc<>(destVertex, +1);
                usersToQualities.addArc(user, myArc);

            }
        }
        return usersToQualities;
    }
}
