package org.tojaco;

import org.tojaco.Graph.*;
import org.tojaco.GraphElements.GraphElements;

import java.io.IOException;
import java.util.*;

public class FindGraphElements<T, E> {
    private final VertexCreator<T> srcVertexCreator;
    private final VertexCreator<E> destVertexCreator;

    FindGraphElements(VertexCreator<T> srcVertexCreator, VertexCreator<E> destVertexCreator){
        this.srcVertexCreator = srcVertexCreator;
        this.destVertexCreator = destVertexCreator;
    }

    public DirectedGraph<T, E> createGraph(GraphElements graphElements, ArrayList<String> list, int a, int b) throws IOException {
        DirectedGraph<T, E> rtGraph = new DirectedGraph<>();

        for (String rt : list) {
            String[] line = rt.split("\t"); //line[0] contains user, line[1] contains the user they are retweeting
            Vertex<T> srcVertex = graphElements.getVertex(line[a], srcVertexCreator);
            Vertex<E> destVertex = graphElements.getVertex(line[b], destVertexCreator);
            Arc<E> myArc = new Arc<>(destVertex, +1);

            rtGraph.addArc(srcVertex, myArc);
            //to check that getLabelBetweenVertices works
            //System.out.println(srcVertex.getLabel() + " " + destVertex.getLabel() + " " + rtGraph.getLabelBetweenVertices(srcVertex,destVertex));

        }
        return rtGraph;
    }

}
