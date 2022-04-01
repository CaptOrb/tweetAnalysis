import java.lang.reflect.Array;
import java.util.*;

public class RetweetGraph<T> implements DirectedGraph<T> {

    private Map<Vertex<T>, ArrayList<Arc<T>>> graph = new HashMap<>();
    private List<Vertex<T>> allVerticesInGraph = new ArrayList<>();

    public Map<Vertex<T>, ArrayList<Arc<T>>> getGraph() { return graph; }

    public List<Vertex<T>> getAllVerticesInGraph() { return allVerticesInGraph; }

   public ArrayList<Arc<T>> getAdjVertices(Vertex<T> key) { return graph.get(key); }


    @Override
    public void addConnection(Vertex<T> source, Arc<T> arc) {
        // Check if vertex given has already been made as a key in the hashmap
        // If it has, access the values it corresponds to and add the corresponding value
        // If it hasn't, create it as a new key

        if(!graph.containsKey(source)){
            addNewKeyValuePair(source, arc);
        }

        else {
            addToExistingKey(source, arc);
        }
        controlUsers(source);
        controlUsers(arc.getVertex());
    }

    @Override
    public void removeVertex(Vertex<T> vertex) {

    }

    @Override
    public void addEdge(Vertex<T> vertex1, Vertex<T> vertex2) {

    }

    @Override
    public void removeEdge(Vertex<T> vertex1, Vertex<T> vertex2) {

    }

    private void addToExistingKey(Vertex<T> vertex, Arc<T> arc){
        // check for self retweet first. then:
        // If list of arcs already contains the given arc we simply increase the weight of the arc by 1
        // If not add the new arc to the list

        if(vertex == arc.getVertex()){
            vertex.incrementWeight();
            return;
        }

        for(Arc<T> testArc : graph.get(vertex)){
            if(testArc.getVertex() == arc.getVertex()){
                testArc.incrementWeight();
                return;
            }
        }
        graph.get(vertex).add(arc);
    }

    private void addNewKeyValuePair(Vertex<T> vertex, Arc<T> arc){
        // if self retweet make a new key with an empty list of arcs
        // increment weight of vertex
        if(vertex == arc.getVertex()){
            ArrayList<Arc<T>> arcs = new ArrayList<>();
            graph.put(vertex, arcs);
            vertex.incrementWeight();
            return;
        }

        ArrayList<Arc<T>> arcs = new ArrayList<>();
        arcs.add(arc);
        graph.put(vertex, arcs);
    }

    public boolean hasArcBetween(Vertex<T> vertex1, Vertex<T> vertex2) {
        // check both vertices are in the graph initially
        if (!allVerticesInGraph.contains(vertex1) && allVerticesInGraph.contains(vertex2)) {
            return false;
        }

        // check if either vertex is a key
        // if so check its value for an arc containing the other vertex
        if (graph.containsKey(vertex1)) {
            for (Arc<T> arc : graph.get(vertex1)) {
                if (arc.getVertex() == vertex2) {
                    return true;
                }
            }
        }
        if (graph.containsKey(vertex2)) {
            for (Arc<T> arc : graph.get(vertex2)) {
                if (arc.getVertex() == vertex1) {
                    return true;
                }
            }
        }
            return false;

    }

    public void controlUsers(Vertex<T> user){
        if(!allVerticesInGraph.contains(user)){
            allVerticesInGraph.add(user);
        }
    }
}
