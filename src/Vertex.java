public class Vertex<T> {
    private final String label;
    private final int weight; //the weight on each vertex, 0 if user does not retweet themselves

    Vertex(String label, int weight) {
        this.label = label;
        this.weight = weight; //we need a weight associated with each vertex for a person retweeting themselves
    }

    public String getLabel() {
        return label;
    }

    public int getWeight() {
        return weight;
    }
}


