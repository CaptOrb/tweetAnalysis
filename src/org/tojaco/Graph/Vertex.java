package org.tojaco.Graph;

public class Vertex<T> {
    private final T label;
    private int stance;
    boolean stanceSet = false;

    public int getStance(){ return stance; }

    public void setStance(int stance){
        this.stance = stance;
        stanceSet = true;
    }

    public Vertex(T label) {
        this.label = label;
    }

    public T getLabel() {
        return label;
    }

    public String toString(){
        return "Label: " + label.toString();
    }
}


