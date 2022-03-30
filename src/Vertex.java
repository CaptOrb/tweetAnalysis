import java.util.Objects;

public class Vertex<T> {
    T contents;
    String label;
    Vertex(T contents){
        this.contents = contents;
    }
    //Vertex(String label) {
    //    this.label = label;
    //}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(contents, vertex.contents) && Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents, label);
    }
}


