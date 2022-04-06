package org.tojaco;

import org.tojaco.Graph.Vertex;

import java.io.File;
import java.util.Map;

public class AssignStances {
    public void determineProAntiVaxEvangelists(Map<Vertex<String>, Integer> evangelists, File file){
        for (Vertex<String> vertex : evangelists.keySet()){
            if(vertex.getLabel().equals("@michaelmalice"))
                vertex.setStance(-1000);
                //read in from stances.txt
                //Label: SPACE @username SPACE anti/pro/idk
                //so split by spaces not tabs :-)
        }
    }
}
