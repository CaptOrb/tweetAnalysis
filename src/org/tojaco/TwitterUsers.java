package org.tojaco;

import org.tojaco.Graph.Vertex;

import java.util.HashMap;
import java.util.Map;

public class TwitterUsers {
    private final Map<String, Vertex<TwitterUser>> allVerticesInGraph = new HashMap<>();
    private final Map<Vertex<TwitterUser>, Integer> userStances = new HashMap<>();

    public Map<String, Vertex<TwitterUser>> getAllVerticesInGraph() { return allVerticesInGraph; }
    public Map<Vertex<TwitterUser>, Integer> getUserStances(){ return userStances; }

    public Vertex<TwitterUser> getVertex(String label) {
        // check list of existing users
        // if user exists, then return user
        // if not create a new user with given label and return
        if (allVerticesInGraph.containsKey(label)) {
            return allVerticesInGraph.get(label);
        }
        Vertex<TwitterUser> vertex = new Vertex<TwitterUser>(new TwitterUser(label));
        allVerticesInGraph.put(label, vertex);
        return vertex;
    }
}
