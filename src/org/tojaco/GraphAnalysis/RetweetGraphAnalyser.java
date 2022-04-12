package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;

import org.tojaco.Graph.RetweetGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.TwitterUsers;

public class RetweetGraphAnalyser {
    public void assignUserStances(RetweetGraph<String> rtGraph, TwitterUsers<String> users){
        int totalOfStances = 0;
        int numArcs = 0;
        for (Vertex<String> vertex : rtGraph.getGraph().keySet()) {
            totalOfStances = 0;
            numArcs = 0;
            for (Arc<String> arc : rtGraph.getGraph().get(vertex)) {
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

    public float calculateCoverage(RetweetGraph<String> rtGraph, TwitterUsers<String> users){
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

    public float calculatePercentagePositiveStances(RetweetGraph<String> rtGraph, TwitterUsers<String> users){
        float positiveStances = 0;
        float hasStance = 0;
        for(Vertex<String> vertex : users.getAllVerticesInGraph().values()){
            if ( users.getUserStances().get(vertex) != null) {
                hasStance++;
                if ( users.getUserStances().get(vertex) > 0) {
                    positiveStances++;
                }
            }
        }
        return (positiveStances / hasStance) * 100;
    }
    public float calculatePercentageNegativeStances(RetweetGraph<String> rtGraph, TwitterUsers<String> users){
        float negativeStances = 0;
        float hasStance = 0;
        for(Vertex<String> vertex : rtGraph.getAllVerticesInGraph().values()){
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
