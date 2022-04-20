package org.tojaco.Graph;

public class CreateStringVertex implements VertexCreator<String> {
    public Vertex<String> createVertex(String label){
        return new Vertex<>(label);
    }
}
