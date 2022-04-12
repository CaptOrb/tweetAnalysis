package org.tojaco.Graph;

public interface Graph<T, E> {
    public void addArc(Vertex<T> source, Arc<E> arc);
    void removeArc(Vertex<T> vertex, Arc<E> arc);
    public int getLabelBetweenVertices(Vertex<T> vertex1, Vertex<T> vertex2);
    public boolean hasArcBetween(Vertex<T> vertex1, Vertex<T> vertex2);
}