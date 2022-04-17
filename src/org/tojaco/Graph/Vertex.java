package org.tojaco.Graph;

public class Vertex<T> {
    private final T label;

    public Vertex(T label) {

        this.label = label;
    }

    public T getLabel() {
        return label;
    }

    public String toString(){
       return label.toString();
    }
}


