package org.tojaco;

import org.tojaco.Graph.*;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelUser {

    //by adding list of qualities to each hashtag, and then adding the qualities of the hashtags that user uses, we create model of that user

    public void addSummaryOfHashtag(DirectedGraph<Hashtag, String> hashtagToSummary){
        for(Map.Entry<Vertex<Hashtag>, ArrayList<Arc<String>>> entry: hashtagToSummary.getGraph().entrySet()){
            for(int i=0; i<entry.getValue().size(); i++){
                entry.getKey().getLabel().addQuality(entry.getValue().get(i).getVertex().toString());
            }
            entry.getKey().getLabel().editQualityList();
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
        //to display e.g. rejecting>accepting, I want to show just the one vertex 'rejecting>accepting'
        //rather than the separate vertices 'rejecting(3)' 'accepting(2)', but we can't edit the list directly
        //as we don't want to lose the words on their own and have to split them up if we want to work with them in future

        for(Vertex<TwitterUser> user: usersToHashtags.getGraph().keySet()) {
            List<String> userQualities = new ArrayList<>();

            for(int i=0; i<user.getLabel().getQualities().size();i++){
                userQualities.add(user.getLabel().getQualities().get(i));

            }
            List<String> editedListQualities = editList(userQualities);

            for(int i=0; i<editedListQualities.size();i++){
                VertexCreator<String> vertexCreator = new CreateStringVertex();
                Vertex<String> destVertex = graphElements.getVertex(editedListQualities.get(i), vertexCreator);
                Arc<String> myArc = new Arc<>(destVertex, +1);
                usersToQualities.addArc(user, myArc);
            }
        }
        return usersToQualities;
    }

    public List<String> editList(List<String> listOfQualities){

        HashMap<String, Integer> qualities = new HashMap();
        qualities.put("accepting", 0);
        qualities.put("rejecting", 0);
        qualities.put("rightwing", 0);
        qualities.put("leftwing", 0);
        qualities.put("problem", 0);
        qualities.put("solution", 0);
        qualities.put("rights", 0);
        qualities.put("responsibilities", 0);
        int accepting = 0;
        int rejecting = 0;
        int leftwing = 0;
        int rightwing = 0;
        int problem = 0;
        int solution = 0;
        int rights = 0;
        int responsibilities = 0;
        if(listOfQualities.size()>2) {

           // List<String> toRemove = new ArrayList<>();
            for (int i = 0; i < listOfQualities.size(); i++) {
                if (listOfQualities.get(i).equals("accepting")) {
                    accepting++;

                } else if (listOfQualities.get(i).equals("rejecting")) {
                    rejecting++;
                }

            }
            if(accepting > 0 && rejecting > 0){ //don't want to add accepting or rejecting if they don't use those words
                boolean toRemove = false;
                if (accepting > rejecting) {
                    listOfQualities.add("accepting > rejecting");
                    toRemove = true;
                } else if (rejecting > accepting) {
                    listOfQualities.add("rejecting > accepting");
                    toRemove = true;
                } else if(accepting == rejecting) {
                    toRemove = true;
                    listOfQualities.add("rejecting & accepting");
                }
                if(toRemove){
                    listOfQualities.removeIf(str -> str.equals("accepting") || str.equals("rejecting"));
                }
            }
        }

        return listOfQualities;
    }
}
