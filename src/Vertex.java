public class Vertex<T> {
    private final T label;
    private int influence;

    public int getInfluence(){ return influence; }

    public void addToInfluence(int retweets){ influence += retweets; }

    Vertex(T label) {
        this.label = label;
    }

    public T getLabel() {
        return label;
    }

    public String toString(){
        return "Label: " + label.toString();
    }
}


