package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.GraphElements.Stanceable;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StanceAnalysis<T, E> {

    //take 100 users and check if stance is correct
    public void checkStance100Users(Map<Vertex<T>, Integer> retweetsHashMap) {
        int i = 0;
        for (Map.Entry<Vertex<T>, Integer> entry : retweetsHashMap.entrySet()) {
            if (entry.getValue() > 10) {
                i++;
                //System.out.println(entry.getKey()); //was used to get 100 users, results found in 100Users.txt
            }
            if (i == 100)
                break;
        }
    }

/*    //This is all the users with no stances :-)
    public double UsersWithNoStance(Map<Vertex<T>, Integer> retweetsHashMap){
        ArrayList<Vertex<T>> usersNoStance = new ArrayList<Vertex<T>>();
        for(Map.Entry<Vertex<T>,Integer> entry : retweetsHashMap.entrySet()){
            if(!(entry.getKey().hasStance())){
                usersNoStance.add(entry.getKey());
                //System.out.println(entry.getKey()); //was used to find users with no stance, probably better to write to a file :-) instead just copied and pasted into UsesNoStances
            }
        }
        return usersNoStance.size();
    }*/

    public void assignStancesByHashtags(DirectedGraph<Stanceable, Stanceable> hashtagsToUsers, GraphElements graphElements, DirectedGraph graph) {
        for (Vertex<Stanceable> vertex : hashtagsToUsers.getGraph().keySet()) {
            if (vertex.getLabel().hasStance()) {
                vertex.getLabel().setStance(0); //in Vertex.java, by setting stance to 0 we set hasStance to false
            }
    /*        for (Arc<Stanceable> arc : hashtagsToUsers.getGraph().get(vertex)) {
                if (arc.getVertex().getLabel().hasStance()) {
                    arc.getVertex().getLabel().setStance(0); //in Vertex.java, by setting stance to 0 we set hasStance to false

                }*/


            //}
        }
        //now calculate stances for users just based solely on the hashtags they use

        GraphAnalyser graphAnalyser = new GraphAnalyser();

        for (int i = 0; i < 5; i++) { //by upping this to 10 there's no change in coverage
            graphAnalyser.assignUserStances(hashtagsToUsers);
        }

        System.out.println("\n4a, set stances for users using hashtags only:");

        System.out.println("Coverage in graph HashtagToUsers graph using ONLY hashtags: " + graphAnalyser.calculateCoverage(graph, graphElements) + "%");
        System.out.println("Percentage of users without a stance using ONLY hashtags: " + (graphAnalyser.calculateCoverage(graph, graphElements) - 100) * -1 + "%");
        System.out.println("Percentage positive stances using ONLY hashtags: " + graphAnalyser.calculatePercentagePositiveStances(hashtagsToUsers, graphElements) + "%");
        System.out.println("Percentage negative stance using ONLY hashtags: " + graphAnalyser.calculatePercentageNegativeStances(hashtagsToUsers, graphElements) + "%");
    }

    public HashMap<Stanceable, Integer> find100HashtagsS5(DirectedGraph<TwitterUser, TwitterUser> retweetGraph, DirectedGraph<Hashtag, TwitterUser> userToHashTags) {

        HashMap<Stanceable, Integer> randomUsersWithHashTag = new HashMap<>();

        // focus only on users who retweet 10 or more other users (for the retweet-based stances)
        for (Map.Entry<Vertex<TwitterUser>, ArrayList<Arc<TwitterUser>>> vertex : retweetGraph.getGraph().entrySet()) {

            if (vertex.getValue().size() >= 10) {
                randomUsersWithHashTag.putIfAbsent(vertex.getKey().getLabel(), vertex.getKey().getLabel().getStance());
            }
        }

        //who use 10 or more different hashtags (for the hashtag-based stances)
        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<TwitterUser>>> vertex2 : userToHashTags.getGraph().entrySet()) {
            if (vertex2.getValue().size() >= 10) {

                randomUsersWithHashTag.putIfAbsent(vertex2.getKey().getLabel(), vertex2.getKey().getLabel().getStance());
            }
        }

        int i = 0;

        // test output results
        for (Map.Entry<Vertex<TwitterUser>, ArrayList<Arc<TwitterUser>>> vertex : retweetGraph.getGraph().entrySet()) {
            if (i < 100) {

                System.out.println(vertex.getKey().getLabel() + " " + vertex.getKey().getLabel().getStance());
                i++;
            }
        }
        return randomUsersWithHashTag;
    }

    public void find100Hashtags(DirectedGraph<T, E> hashtagsToUsers) {
        int i = 0;
        for (Vertex<T> vertex : hashtagsToUsers.getGraph().keySet()) {
            i++;
            //System.out.println(vertex.getLabel() + ", Stance: " + vertex.getStance());
            if (i == 100)
                break;

        }
    }
}
