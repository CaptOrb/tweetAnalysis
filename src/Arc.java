public class Arc<E>{
    private final Vertex<E> vertex; //a user retweets this person
    private int weight; //the weight on each edge

    public Arc(Vertex<E> vertex,int weight ) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public Vertex<E> getVertex(){return vertex;}

    public int getWeight() {
        return weight;
    }

    public void incrementWeight(){ weight ++; }

    public void decrementWeight(){ weight--; }

    public String toString(){
        return "Label: " + vertex.toString() + ", arc weight: " + weight;
    }
}
