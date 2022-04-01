public interface DirectedGraph<T> {
    public void addConnection(Vertex<T> source, Arc<T> arc);
    public void removeVertex(Vertex<T> vertex);
    public void addEdge(Vertex<T> vertex1, Vertex<T> vertex2);
    public void removeEdge(Vertex<T> vertex1, Vertex<T> vertex2);
}