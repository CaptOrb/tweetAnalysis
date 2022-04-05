import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class findEvangelists {
    Map<Vertex<String>, Integer > retweetsHashMap = new HashMap<>();
    Map<Vertex<String>, Integer > evangelistsHashMap = new HashMap<>();

    public void findTotalRetweets(RetweetGraph<String> retweetedGraph){
        int totalRetweets=0;
        int lowestInfluence=0;
        for(Vertex<String> vertex : retweetedGraph.getGraph().keySet()){

            for (int i = 0; i < retweetedGraph.getGraph().get(vertex).size(); i++){
               totalRetweets+=retweetedGraph.getGraph().get(vertex).get(i).getWeight();
            }
            System.out.println(vertex);
            System.out.println(totalRetweets);
            retweetsHashMap.put(vertex, totalRetweets);

            totalRetweets=0; //reset
        }
    }
}
