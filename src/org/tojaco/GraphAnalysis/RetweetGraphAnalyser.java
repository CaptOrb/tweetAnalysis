package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.RetweetGraph;
import org.tojaco.Graph.Vertex;

public class RetweetGraphAnalyser {
    public void assignUserStances( RetweetGraph<String> rtGraph ){
        int totalOfStances = 0;
        int numArcs = 0;
        for (Vertex<String> vertex : rtGraph.getGraph().keySet()) {
            totalOfStances = 0;
            numArcs = 0;
            for (Arc<String> arc : rtGraph.getGraph().get(vertex)) {
                if (arc.getVertex().hasStance()) {
                    totalOfStances += arc.getVertex().getStance();
                    numArcs++;
                }
            }
            if (numArcs > 0) {
                int stance = totalOfStances / numArcs;
                vertex.setStance(stance);
            }
        }
    }

    public float calculateCoverage(RetweetGraph<String> rtGraph){
        // iterate all users in graph
        // if they have been given a stance then increment the stance counter
        float stances = 0;
        for(String user : rtGraph.getAllVerticesInGraph().keySet()){
            if(rtGraph.getAllVerticesInGraph().get(user).hasStance()) {
                stances ++;
            }
        }
        return (stances / rtGraph.getAllVerticesInGraph().size()) * 100;
    }

    public float calculatePercentagePositiveStances(RetweetGraph<String> rtGraph){
        float positiveStances = 0;
        float hasStance = 0;
        for(Vertex<String> vertex : rtGraph.getAllVerticesInGraph().values()){
            if(vertex.hasStance()) {
                hasStance++;
                if (vertex.getStance() > 0) {
                    positiveStances++;
                }
            }
        }
        return (positiveStances / hasStance) * 100;
    }
    public float calculatePercentageNegativeStances(RetweetGraph<String> rtGraph){
        float negativeStances = 0;
        float hasStance = 0;
        for(Vertex<String> vertex : rtGraph.getAllVerticesInGraph().values()){
            if(vertex.hasStance()) {
                hasStance++;
                if (vertex.getStance() < 0) {
                    negativeStances++;
                }
            }
        }
        return (negativeStances / hasStance) * 100;
    }

}
