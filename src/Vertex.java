public class Vertex<T> {
    String label;
    int numRetweets;
    Vertex(String label, int numRetweets) {
        this.label = label;
        this.numRetweets=numRetweets; //we need a weight associated with each vertex for a person retweeting themselves
    }

}


