package org.tojaco;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.RetweetGraph;
import org.tojaco.Graph.Vertex;

import java.util.ArrayList;

public class RetweetGraphAnalyser {
    public void assignUserStances( RetweetGraph<String> rtGraph ){
        for (Vertex<String> vertex : rtGraph.getGraph().keySet()) {
            int totalOfStances = 0;
            for( Arc<String> arc : rtGraph.getGraph().get(vertex) ){
                totalOfStances += arc.getVertex().getStance();
            }
            int stance = totalOfStances/rtGraph.getGraph().get(vertex).size();
            vertex.setStance(stance);
        }
    }
}
