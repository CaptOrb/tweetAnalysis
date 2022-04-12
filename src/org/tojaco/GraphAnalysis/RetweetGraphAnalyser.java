package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;

import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Graph;
import org.tojaco.Graph.Vertex;
import org.tojaco.TwitterUser;
import org.tojaco.TwitterUsers;
import twitter4j.Twitter;

public class RetweetGraphAnalyser {
    public void assignUserStances(DirectedGraph<TwitterUser, TwitterUser> rtGraph, TwitterUsers users){
        int totalOfStances = 0;
        int numArcs = 0;
        for (Vertex<TwitterUser> vertex : rtGraph.getGraph().keySet()) {
            totalOfStances = 0;
            numArcs = 0;
            for (Arc<TwitterUser> arc : rtGraph.getGraph().get(vertex)) {
                if( users.getUserStances().get(arc.getVertex()) != null ){
                // if (arc.getVertex().hasStance()) {
                //    totalOfStances += arc.getVertex().getStance();
                    totalOfStances += users.getUserStances().get(arc.getVertex());
                    numArcs++;
                }
            }
            if (numArcs > 0) {
                int stance = totalOfStances / numArcs;
                //vertex.setStance(stance);
                users.getUserStances().putIfAbsent(vertex, stance);
            }
        }
    }

    public float calculateCoverage(Graph<TwitterUser, TwitterUser> rtGraph, TwitterUsers users){
        // iterate all users in graph
        // if they have been given a stance then increment the stance counter
        float stances = 0;
        for (String user : users.getAllVerticesInGraph().keySet()){
            if ( users.getUserStances().get(users.getAllVerticesInGraph().get(user)) != null){
                stances ++;
            }
        }
        return (stances / users.getAllVerticesInGraph().size()) * 100;
    }

    public float calculatePercentagePositiveStances(Graph<TwitterUser, TwitterUser> rtGraph, TwitterUsers users){
        float positiveStances = 0;
        float hasStance = 0;
        for(Vertex<TwitterUser> vertex : users.getAllVerticesInGraph().values()){
            if ( users.getUserStances().get(vertex) != null) {
                hasStance++;
                if ( users.getUserStances().get(vertex) > 0) {
                    positiveStances++;
                }
            }
        }
        return (positiveStances / hasStance) * 100;
    }
    public float calculatePercentageNegativeStances(DirectedGraph<TwitterUser, TwitterUser> rtGraph, TwitterUsers users){
        float negativeStances = 0;
        float hasStance = 0;
        for(Vertex<TwitterUser> vertex : rtGraph.getAllVerticesInGraph().values()){
            if (users.getUserStances().get(vertex) != null){
                hasStance++;
                if ( users.getUserStances().get(vertex) < 0 ){
                    negativeStances++;
                }
            }
        }
        return (negativeStances / hasStance) * 100;
    }

}
