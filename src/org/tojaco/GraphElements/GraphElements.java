package org.tojaco.GraphElements;

import org.tojaco.Graph.Vertex;
import org.tojaco.Graph.VertexCreator;

import java.util.HashMap;
import java.util.Map;

public class GraphElements {
    private final Map<String, Vertex> allVerticesInGraph = new HashMap<>();

    public Map<String, Vertex> getAllVerticesInGraph() { return allVerticesInGraph; }

//    public Vertex<TwitterUser> getVertex(String label) {
//        // check list of existing users
//        // if user exists, then return user
//        // if not create a new user with given label and return
//        if (allVerticesInGraph.containsKey(label)) {
//            return allVerticesInGraph.get(label);
//        }
//        Vertex<TwitterUser> vertex = new Vertex<TwitterUser>(new TwitterUser(label));
//        allVerticesInGraph.put(label, vertex);
//        return vertex;
//    }

    public Vertex getVertex(String label, VertexCreator vertexCreator) {
        // check list of existing users
        // if user exists, then return user
        // if not create a new user with given label and return
        if (allVerticesInGraph.containsKey(label)) {
            return allVerticesInGraph.get(label);
        }
        Vertex vertex = vertexCreator.createVertex(label);
        //Vertex vertex = new Vertex<>(label);
        allVerticesInGraph.put(label.toString(), vertex);
        return vertex;
    }
}
