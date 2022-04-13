package org.tojaco.Graph;

public interface VertexCreator<T> {
    public Vertex<T> createVertex(String label);
}
