package org.tojaco.Graph;

public class Vertex<T> {
    private final T label;
    private int stance;
    boolean hasStance = false;

    public int getStance(){ return stance; }

    public boolean hasStance(){ return hasStance; }

    public void setStance(int stance){
        this.stance = stance;
        hasStance = true;
    }

    public Vertex(T label) {
        this.label = label;
    }

    public T getLabel() {
        return label;
    }

    public String toString(){
        if(hasStance()==true){
            return label.toString() + ", Stance = " +  stance;
        } else{
            return "Label: " + label.toString();
        }
    }
}


