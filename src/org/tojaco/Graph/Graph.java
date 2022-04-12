package org.tojaco.Graph;

import java.util.*;

public class Graph<T> implements DirectedGraph<T> {

    private final Map<Vertex<T>, ArrayList<Arc<T>>> graph = new HashMap<>();
    private final Map<T, Vertex<T>> allVerticesInGraph = new HashMap<>();

    public Map<Vertex<T>, ArrayList<Arc<T>>> getGraph() { return graph; }

    public Map<T, Vertex<T>> getAllVerticesInGraph() { return allVerticesInGraph; }

    @Override
    public void addArc(Vertex<T> source, Arc<T> arc) {
        // Check if vertex given has already been made as a key in the hashmap
        // If it has, access the values it corresponds to and add the corresponding value
        // If it hasn't, create it as a new key

        if(!graph.containsKey(source)){
            addNewKeyValuePair(source, arc);
        }

        else {
            addToExistingKey(source, arc);
        }
        controlUsers(source);
        controlUsers(arc.getVertex());
    }

    @Override
    public void removeArc(Vertex<T> vertex, Arc<T> arc) {
        if ( !graph.containsKey(vertex) ){
            System.out.println(vertex.toString() + " is not in the graph");
            return;
        }

        if ( !graph.get(vertex).contains(arc) ){
            System.out.println(vertex.toString() + "does not have a link to " + arc.toString());
            return;
        }

        if(arc.getWeight() > 1){
            arc.decrementWeight();
            return;
        }
        graph.get(vertex).remove(arc);
        if (graph.get(vertex).isEmpty()){
            graph.remove(vertex);
        }
    }

    private void addToExistingKey(Vertex<T> vertex, Arc<T> arc){
        // If list of arcs already contains the given arc we simply increase the weight of the arc by 1
        // If not add the new arc to the list
        for(Arc<T> testArc : graph.get(vertex)){
            if(testArc.getVertex() == arc.getVertex()){
                testArc.incrementWeight();
                return;
            }
        }
        graph.get(vertex).add(arc);
    }

    private void addNewKeyValuePair(Vertex<T> vertex, Arc<T> arc){
        ArrayList<Arc<T>> arcs = new ArrayList<>();
        arcs.add(arc);
        graph.put(vertex, arcs);
    }

    @Override
    public boolean hasArcBetween(Vertex<T> vertex1, Vertex<T> vertex2) {
        // check both vertices are in the graph initially
        if (!allVerticesInGraph.containsKey(vertex1.getLabel()) && allVerticesInGraph.containsKey(vertex2.getLabel())) {
            return false;
        }

        // check if either vertex is a key
        // if so check its value for an arc containing the other vertex
        if (graph.containsKey(vertex1)) {
            for (Arc<T> arc : graph.get(vertex1)) {
                if (arc.getVertex() == vertex2) {
                    return true;
                }
            }
        }
        if (graph.containsKey(vertex2)) {
            for (Arc<T> arc : graph.get(vertex2)) {
                if (arc.getVertex() == vertex1) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public int getLabelBetweenVertices(Vertex<T> vertex1, Vertex<T> vertex2){

        if (!allVerticesInGraph.containsKey(vertex1.getLabel()) && allVerticesInGraph.containsKey(vertex2.getLabel())) {
            return -1;
        }

        // check if either vertex is a key
        // if so check its value for an arc containing the other vertex
        if (graph.containsKey(vertex1)) {
            for (Arc<T> arc : graph.get(vertex1)) {
                if (arc.getVertex() == vertex2) {
                    return arc.getWeight();
                }
            }
        }
        if (graph.containsKey(vertex2)) {
            for (Arc<T> arc : graph.get(vertex2)) {
                if (arc.getVertex() == vertex1) {
                    return arc.getWeight();
                }
            }
        }

        return -1;
    }



    public void controlUsers(Vertex<T> user){
        if(!allVerticesInGraph.containsKey(user.getLabel())){
            allVerticesInGraph.put(user.getLabel(), user);
        }
    }
}
