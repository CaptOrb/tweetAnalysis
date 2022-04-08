package org.tojaco.GraphAnalysis;
import org.tojaco.Graph.RetweetGraph;
import org.tojaco.Graph.Vertex;


//take 100 users and check if stance is correct
public class Users100 {

    public void checkStance(RetweetGraph<String> retweetedGraph){
        int i = 0;
        for (Vertex<String> vertex : retweetedGraph.getGraph().keySet()) {
            System.out.println(vertex);
            i++;
            if(i==100)
                break;
            }
    }
}
