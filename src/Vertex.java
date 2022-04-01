public class Vertex<T> {
    private final T label;
    private int weight = 0; //the weight on each vertex, 0 if user does not retweet themselves

    Vertex(T label, int weight) {
        this.label = label;
        this.weight = weight; //we need a weight associated with each vertex for a person retweeting themselves
    }

    public T getLabel() {
        return label;
    }

    public int getWeight() {
        return weight;
    }

    public void incrementWeight(){ weight++; }
}


