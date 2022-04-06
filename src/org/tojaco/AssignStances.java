package org.tojaco;

import org.tojaco.Graph.Vertex;

import java.util.Map;

public class AssignStances {
    public void antiVaxEvangelists(Map<Vertex<String>, Integer> evangelists){
        for (Vertex<String> vertex : evangelists.keySet()){
            if(vertex.getLabel().equals("@michaelmalice") ||
                    vertex.getLabel().equals("@SebGorka") ||
                    vertex.getLabel().equals("@PapiTrumpo") ||
                    vertex.getLabel().equals("@TruthMav")||
                    vertex.getLabel().equals("@EricMMatheny")||
                    vertex.getLabel().equals("@castterry73")||
                    vertex.getLabel().equals("@Arwenstar")||
                    vertex.getLabel().equals("@MattHoyOfficial")||
                    vertex.getLabel().equals("@NeilClark66")||
                    vertex.getLabel().equals("@NeverSleever")||
                    vertex.getLabel().equals("@Zieleds") ||
                    vertex.getLabel().equals("@ChrisLoesch")||
                    vertex.getLabel().equals("@Lenabellalou")||
                    vertex.getLabel().equals("@GeorginaLishma1")||
                    vertex.getLabel().equals("@ProfessorFergu1")||
                    vertex.getLabel().equals("@Gerard39delaney")||
                    vertex.getLabel().equals("@BethanyCherisse")||
                    vertex.getLabel().equals("@rosellacottage")||
                    vertex.getLabel().equals("@LeeroyPress")||
                    vertex.getLabel().equals("@DrMadej")||
                    vertex.getLabel().equals("@zoeharcombe")
            )
                vertex.setStance(-1000);

        }
    }

    public void proVaxEvangelists(Map<Vertex<String>, Integer> evangelists){
        for (Vertex<String> vertex : evangelists.keySet()) {
        
        }
    }
}
