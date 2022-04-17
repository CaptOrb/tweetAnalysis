package org.tojaco.Graph;

import org.tojaco.GraphElements.TwitterUser;

public class CreateUserVertex implements VertexCreator<TwitterUser>{
    public Vertex<TwitterUser> createVertex(String label){
        Vertex<TwitterUser> vertex = new Vertex<>(new TwitterUser(label));
        return vertex;
    }
}
