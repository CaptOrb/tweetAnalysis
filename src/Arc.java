public class Arc {
    Vertex vertex; //a user retweets this person
    int weight; //the weight on each edge

    public Arc(Vertex vertex,int weight ) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public Vertex getVertex(){return vertex;}

    public int getWeight() {
        return weight;
    }

    public void incrementWeight(){ weight++; }
}
