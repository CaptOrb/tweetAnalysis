import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetweetGraph<E> implements Graph {

    private Map<Vertex<E>, List<Vertex<E>>> adjVertices = new HashMap<>();

    public Map<Vertex<E>, List<Vertex<E>>> getAdjVertices() {
        return adjVertices;
    }

    List<Vertex<E>> getAdjVertices(Vertex<E> vertex) {
        return adjVertices.get(vertex);
    }

    public void setAdjVertices(Map<Vertex<E>, List<Vertex<E>>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    @Override
    public void addVertex(String label) {
        // initially weight is 0
        // need to change somehow
        adjVertices.putIfAbsent(new Vertex<>(label, 0), new ArrayList<>());
    }

    @Override
    public void removeVertex(Vertex vertex) {

    }

    @Override
    public void addEdge(Vertex vertex1, Vertex vertex2) {

    }

    @Override
    public void removeEdge(Vertex vertex1, Vertex vertex2) {

    }
}
