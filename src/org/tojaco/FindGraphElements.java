package org.tojaco;

import org.tojaco.Graph.*;

import java.io.IOException;
import java.util.*;

public class FindGraphElements {

    public <T, E> DirectedGraph<T, E> createGraph(ArrayList<String> list, int a, int b) throws IOException {
        DirectedGraph<T, E> rtGraph = new DirectedGraph<>();

        //Map<String, Vertex<String>> allVerticesInGraph = rtGraph.getAllVerticesInGraph();
        for (String rt : list) {
            String[] line = rt.split("\t"); //line[0] contains user, line[1] contains the user they are retweeting
            Vertex<T> srcVertex = rtGraph.getVertex(line[a]);
            Vertex<E> destVertex = rtGraph.getVertex(line[b]);
            Arc<E> myArc = new Arc<>(destVertex, +1);

            rtGraph.addArc(srcVertex, myArc);
            //to check that getLabelBetweenVertices works
            //System.out.println(srcVertex.getLabel() + " " + destVertex.getLabel() + " " + rtGraph.getLabelBetweenVertices(srcVertex,destVertex));

        }
        return rtGraph;
    }

}
