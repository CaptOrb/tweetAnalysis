public class Vertex<T> {
    private final String label;

    Vertex(String label) {
        this.label = label;
        this.numRetweets=numRetweets; //we need a weight associated with each vertex for a person retweeting themselves
    }

    public String getLabel() {
        return label;
    }
}


