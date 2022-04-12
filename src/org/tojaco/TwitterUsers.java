package org.tojaco;

import org.tojaco.Graph.Vertex;

import java.util.HashMap;
import java.util.Map;

public class TwitterUsers<T> {
    private final Map<T, Vertex<T>> allVerticesInGraph = new HashMap<>();

    public Map<T, Vertex<T>> getAllVerticesInGraph() { return allVerticesInGraph; }

    public Vertex<T> getVertex(T label) {
        // check list of existing users
        // if user exists, then return user
        // if not create a new user with given label and return
        if (allVerticesInGraph.containsKey(label)) {
            return allVerticesInGraph.get(label);
        }
        Vertex<T> vertex = new Vertex<T>(label);
        allVerticesInGraph.put(label, vertex);
        return vertex;
    }
}
