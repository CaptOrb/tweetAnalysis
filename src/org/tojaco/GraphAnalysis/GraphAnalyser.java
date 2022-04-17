package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;

import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Graph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements;
import org.tojaco.TwitterUser;
import twitter4j.Twitter;

public class GraphAnalyser<T, E> {
    public void assignUserStances(DirectedGraph<T, E> Graph){
        int totalOfStances = 0;
        int numArcs = 0;
        for (Vertex<T> vertex : Graph.getGraph().keySet()) {
            totalOfStances = 0;
            numArcs = 0;
            for (Arc<E> arc : Graph.getGraph().get(vertex)) {
                //if( users.getUserStances().get(arc.getVertex()) != null ){
               // if (arc.getVertex().getLabel().hasStance()) {
                 if(arc.getVertex().hasStance()){
                    //totalOfStances += arc.getVertex().getLabel().getStance();
                     totalOfStances += arc.getVertex().getStance();
                //    totalOfStances += users.getUserStances().get(arc.getVertex());
                    numArcs++;
                }
            }
            if (numArcs > 0) {
                int stance = totalOfStances / numArcs;
                //vertex.getLabel().setStance(stance);
                vertex.setStance(stance);
                //users.getUserStances().putIfAbsent(vertex, stance);
            }
        }
    }

    public float calculateCoverage(GraphElements graphElements){
        // iterate all users in graph
        // if they have been given a stance then increment the stance counter
        float stances = 0;
        for(Vertex<T> vertex : graphElements.getAllVerticesInGraph().values()){
        //for (String user : rtGraph.getAllVerticesInGraph().keySet()){
            if ( vertex.hasStance() ){
            //if ( users.getUserStances().get(users.getAllVerticesInGraph().get(user)) != null){
                stances ++;
            }
        }
        return (stances / graphElements.getAllVerticesInGraph().size()) * 100;
    }

    public float calculatePercentagePositiveStances(DirectedGraph<T, E> rtGraph, GraphElements graphElements){
        float positiveStances = 0;
        float hasStance = 0;
        for(Vertex<T> vertex : graphElements.getAllVerticesInGraph().values()){
            if ( vertex.hasStance()){
            //if ( users.getUserStances().get(vertex) != null) {
                hasStance++;
                if ( vertex.getStance() > 0 ){
                //if ( users.getUserStances().get(vertex) > 0) {
                    positiveStances++;
                }
            }
        }
        return (positiveStances / hasStance) * 100;
    }
    public float calculatePercentageNegativeStances(DirectedGraph<T, E> rtGraph, GraphElements graphElements){
        float negativeStances = 0;
        float hasStance = 0;
        for(Vertex<T> vertex : graphElements.getAllVerticesInGraph().values()){
            if (vertex.hasStance()){
            //if (users.getUserStances().get(vertex) != null){
                hasStance++;
                if ( vertex.getStance() < 0 ){
                //if ( users.getUserStances().get(vertex) < 0 ){
                    negativeStances++;
                }
            }
        }
        return (negativeStances / hasStance) * 100;
    }

}
