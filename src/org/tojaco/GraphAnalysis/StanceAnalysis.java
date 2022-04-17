package org.tojaco.GraphAnalysis;
import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements;
import org.tojaco.MainUtil;
import org.tojaco.TwitterUser;

import java.util.ArrayList;
import java.util.Map;


public class StanceAnalysis<T,E>{

    //take 100 users and check if stance is correct
    public void checkStance100Users(Map<Vertex<T>, Integer> retweetsHashMap){
        int i = 0;
        for(Map.Entry<Vertex<T>,Integer> entry : retweetsHashMap.entrySet()){
            if(entry.getValue()>10){
                i++;
                //System.out.println(entry.getKey()); //was used to get 100 users, results found in 100Users.txt
            }
            if(i==100)
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

    public void assignStancesByHashtags(DirectedGraph<T,E> hashtagsToUsers, GraphElements graphElements){
        for (Vertex<T> vertex : hashtagsToUsers.getGraph().keySet()) {
            for (Arc<E> arc : hashtagsToUsers.getGraph().get(vertex)) {
                if (vertex.hasStance()) {
                    vertex.setStance(0); //in Vertex.java, by setting stance to 0 we set hasStance to false
                }
            }
        }
        //now calculate stances for users just based solely on the hashtags they use

        RetweetGraphAnalyser graphAnalyser = new RetweetGraphAnalyser();

        for (int i = 0; i < 5; i++) { //by upping this to 10 there's no change in coverage
            graphAnalyser.assignUserStances(hashtagsToUsers);
        }

        System.out.println("\n4a, set stances for users using hashtags only:");

        System.out.println("Coverage in graph HashtagToUsers graph using ONLY hashtags: " + graphAnalyser.calculateCoverage(graphElements) + "%");
        System.out.println("Percentage of users without a stance using ONLY hashtags: " + (graphAnalyser.calculateCoverage(graphElements) - 100) * -1 + "%");
        System.out.println("Percentage positive stances using ONLY hashtags: " + graphAnalyser.calculatePercentagePositiveStances(hashtagsToUsers, graphElements) + "%");
        System.out.println("Percentage negative stance using ONLY hashtags: " + graphAnalyser.calculatePercentageNegativeStances(hashtagsToUsers, graphElements) + "%");

    }
}
