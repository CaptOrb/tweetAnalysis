public interface Graph<T extends Comparable<T> > {
    public void addVertex(Vertex<T> vertex, Arc arc);
    public void removeVertex(Vertex<T> vertex);
    public void addEdge(Vertex<T> vertex1, Vertex<T> vertex2);
    public void removeEdge(Vertex<T> vertex1, Vertex<T> vertex2);
}