package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.*;

import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Stanceable;
import org.tojaco.GraphElements.TwitterUser;

public class GraphAnalyser<T, E> {
    public void assignUserStances(DirectedGraph<Stanceable, Stanceable> Graph){

        int totalOfStances = 0;
        int numArcs = 0;
        for (Vertex<Stanceable> vertex : Graph.getGraph().keySet()) {

            totalOfStances = 0;
            numArcs = 0;
            for (Arc<Stanceable> arc : Graph.getGraph().get(vertex)) {
                 if(arc.getVertex().getLabel().hasStance()){
                     totalOfStances += arc.getVertex().getLabel().getStance();
                     numArcs++;
                }
            }
            if (numArcs > 0) {
                int stance = totalOfStances / numArcs;
                vertex.getLabel().setStance(stance);
            }
        }
    }

    public float calculateCoverage(DirectedGraph<TwitterUser, TwitterUser> graph, GraphElements graphElements){
        // iterate all users in graph
        // if they have been given a stance then increment the stance counter
        float stances = 0;
        for (String user : graph.getAllVerticesInGraph().keySet()){
            Vertex<Stanceable> vertex = graph.getAllVerticesInGraph().get(user);
            if ( vertex.getLabel().hasStance() ){
                stances ++;
            }
        }
        return (stances / graph.getAllVerticesInGraph().size()) * 100;
    }

    public float calculatePercentagePositiveStances(DirectedGraph<TwitterUser, TwitterUser> rtGraph, GraphElements graphElements){
        float positiveStances = 0;
        float hasStance = 0;
        for(Vertex<Stanceable> vertex : rtGraph.getAllVerticesInGraph().values()){
            if ( vertex.getLabel().hasStance()){
                hasStance++;
                if ( vertex.getLabel().getStance() > 0 ){
                    positiveStances++;
                }
            }
        }
        return (positiveStances / hasStance) * 100;
    }
    public float calculatePercentageNegativeStances(DirectedGraph<TwitterUser, TwitterUser> rtGraph, GraphElements graphElements){
        float negativeStances = 0;
        float hasStance = 0;
        for(Vertex<Stanceable> vertex : rtGraph.getAllVerticesInGraph().values()){
            if (vertex.getLabel().hasStance()){
                hasStance++;
                if ( vertex.getLabel().getStance() < 0 ){
                    negativeStances++;
                }
            }
        }
        return (negativeStances / hasStance) * 100;
    }
}
