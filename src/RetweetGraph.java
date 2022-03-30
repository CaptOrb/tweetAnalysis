import twitter4j.User;

import java.util.List;
import java.util.Map;

public class RetweetGraph implements Graph {

    private Map<Vertex, List<Vertex>> adjVertices;

    public Map<Vertex, List<Vertex>> getAdjVertices() {
        return adjVertices;
    }

    public void setAdjVertices(Map<Vertex, List<Vertex>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    @Override
    public void addVertex(Vertex vertex) {

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
