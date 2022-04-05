import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class findEvangelists {
    Map<Vertex<String>, Integer > evangelistsHashMap = new HashMap<>();
    public void top100(RetweetGraph<String> retweetedGraph){
        int totalRetweets=0;
        for(Vertex<String> vertex : retweetedGraph.getGraph().keySet()){
            System.out.println(vertex);
            for (int i = 0; i < retweetedGraph.getGraph().get(vertex).size(); i++){
               totalRetweets+=retweetedGraph.getGraph().get(vertex).get(i).getWeight();
            }
            System.out.println(totalRetweets);
            evangelistsHashMap.put(vertex,totalRetweets);
            totalRetweets=0; //reset
        }

    }
}
