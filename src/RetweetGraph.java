import java.util.*;

public class RetweetGraph<E> implements Graph {

    private Map<Vertex<E>, ArrayList<Arc>> adjVertices = new HashMap<>();
    private List allVerticesInGraph = new ArrayList();

    public Map<Vertex<E>, ArrayList<Arc>> getAdjVertices() {
        return adjVertices;
    }

    public List getAllVerticesInGraph() { return allVerticesInGraph; }

    ArrayList<Arc> getAdjVertices(Vertex<E> key) {
        return adjVertices.get(key);
    }

    //public void setAdjVertices(Map<Vertex<E>, List<Vertex<E>>> adjVertices) {
    //    this.adjVertices = adjVertices;
    //}


    @Override
    public void addVertex(Vertex vertex, Arc arc) {
        // Check if vertex given has already been made as a key in the hashmap
        // If it has, access the values it corresponds to and add the corresponding value
        // If it hasn't, create it as a new key

        if(!adjVertices.containsKey(vertex)){
            addNewKeyValuePair(vertex, arc);
        }

        else {
            addToExistingKey(vertex, arc);
        }
        controlUsers(vertex);
        controlUsers(arc.getVertex());
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

    private void addToExistingKey(Vertex vertex, Arc arc){
        // check for self retweet first. then:
        // If list of arcs already contains the given arc we simply increase the weight of the arc by 1
        // If not add the new arc to the list

        if(vertex == arc.getVertex()){
            vertex.incrementWeight();
            return;
        }

        for(Arc testArc : adjVertices.get(vertex)){
            if(testArc.getVertex() == arc.getVertex()){
                testArc.incrementWeight();
                return;
            }
        }
        adjVertices.get(vertex).add(arc);
    }

    private void addNewKeyValuePair(Vertex vertex, Arc arc){
        ArrayList<Arc> arcs = new ArrayList<>();
        arcs.add(arc);
        adjVertices.put(vertex, arcs);
    }

    public boolean hasArcBetween(Vertex vertex1, Vertex vertex2){
        // TODO
        return false;
    }

    private void controlUsers(Vertex user){
        if(!allVerticesInGraph.contains(user)){
            allVerticesInGraph.add(user);
        }
    }

}
