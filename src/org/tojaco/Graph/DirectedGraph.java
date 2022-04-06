package org.tojaco.Graph;

public interface DirectedGraph<T> {
    public void addArc(Vertex<T> source, Arc<T> arc);
    void removeArc(Vertex<T> vertex, Arc<T> arc);
    public int getLabelBetweenVertices(Vertex<T> vertex1, Vertex<T> vertex2);
    public boolean hasArcBetween(Vertex<T> vertex1, Vertex<T> vertex2);
}