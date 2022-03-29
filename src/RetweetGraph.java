import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetweetGraph<E> implements Graph {

    private Map<Vertex<E>, List<Vertex<E>>> adjVertices = new HashMap<>();

    public Map<Vertex<E>, List<Vertex<E>>> getAdjVertices() {
        return adjVertices;
    }

    List<Vertex<E>> getAdjVertices(String label) {
        return adjVertices.get(new Vertex<E>(label));
    }

    public void setAdjVertices(Map<Vertex<E>, List<Vertex<E>>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    @Override
    public void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex<>(label), new ArrayList<>());
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
