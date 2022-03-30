import java.util.*;

public class RetweetGraph<E> implements Graph {

    private Map<Vertex<E>, ArrayList<Arc>> adjVertices = new HashMap<>();

    public Map<Vertex<E>, ArrayList<Arc>> getAdjVertices() {
        return adjVertices;
    }

    ArrayList<Arc> getAdjVertices(Vertex<E> key) {
        return adjVertices.get(key);
    }

    //public void setAdjVertices(Map<Vertex<E>, List<Vertex<E>>> adjVertices) {
    //    this.adjVertices = adjVertices;
    //}


    @Override
    public void addVertex(Vertex vertex, Arc arc) {
        // Check if vertex given has already been made
        // If it has, access the values it corresponds to and add the corresponding value
        // If it hasn't, create it as a new key

        if(!adjVertices.containsKey(vertex)){
            ArrayList<Arc> arcs = new ArrayList<>();
            arcs.add(arc);
            adjVertices.put(vertex, arcs);
        }

        else {
            adjVertices.get(vertex).add(arc);
        }
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
