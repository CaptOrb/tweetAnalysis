package org.tojaco.Graph;

public class Vertex<T> {
    private final T label;
    private int stance;
    private boolean hasStance;

    public Vertex(T label) {

        this.label = label;
    }

    public T getLabel() {
        return label;
    }

/*    public String toString(){
            return "Label: " + label.toString();
    }*/

    public int getStance() { return stance; }

    public void setStance(int stance){
        this.stance = stance;
        hasStance = true;
    }

    public boolean hasStance(){
        return hasStance;
    }

    public String toString(){
       return label.toString();
    }
}


