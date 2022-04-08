package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.RetweetGraph;
import org.tojaco.Graph.Vertex;

public class RetweetGraphAnalyser {
    public void assignUserStances( RetweetGraph<String> rtGraph ){
        for (Vertex<String> vertex : rtGraph.getGraph().keySet()) {
            int totalOfStances = 0;
            for( Arc<String> arc : rtGraph.getGraph().get(vertex) ){
                totalOfStances += arc.getVertex().getStance();
            }
            int stance = totalOfStances/rtGraph.getGraph().get(vertex).size();
            vertex.setStance(stance);
        }
    }

    public float calculateCoverage(RetweetGraph<String> rtGraph){
        // iterate all users in graph
        // if they have been given a stance then increment the stance counter
        // divide by number of users to return coverage as a decimal
        float stances = 0;
        for(String user : rtGraph.getAllVerticesInGraph().keySet()){
            if(rtGraph.getAllVerticesInGraph().get(user).hasStance()) {
                stances ++;
            }
        }
        return stances / rtGraph.getAllVerticesInGraph().size();
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
        return positiveStances / hasStance;
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
        return negativeStances / hasStance;
    }

}
