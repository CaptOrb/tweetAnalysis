package org.tojaco.Graph;

import org.tojaco.Hashtag;
import org.tojaco.TwitterUser;

import java.util.HashMap;

public class CreateHashtagVertex implements VertexCreator<Hashtag> {
    public Vertex<Hashtag> createVertex(String label){
        Vertex<Hashtag> vertex = new Vertex<>(new Hashtag(label));
        return vertex;
    }
}
