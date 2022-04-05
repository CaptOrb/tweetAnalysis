import java.util.*;

public class findEvangelists {
    private final Map<Vertex<String>, Integer> retweetsHashMap = new HashMap<>();
    private final Map<Vertex<String>, Integer> evangelistsHashMap = new HashMap<>();

    public Map<Vertex<String>, Integer> getEvangelistsHashMap() {
        return evangelistsHashMap;
    }

    public void findTotalRetweets(RetweetGraph<String> retweetedGraph) {

        for (Vertex<String> vertex : retweetedGraph.getGraph().keySet()) {
            int totalRetweets = 0;

            for (int i = 0; i < retweetedGraph.getGraph().get(vertex).size(); i++) {
                totalRetweets += retweetedGraph.getGraph().get(vertex).get(i).getWeight();
            }
          //  System.out.println(vertex);
           // System.out.println(totalRetweets);
            retweetsHashMap.put(vertex, totalRetweets);
        }

        List<Map.Entry<Vertex<String>, Integer>> topNRetweeted = sortMostRetweeted(retweetsHashMap);

        for (int i = 0; i < 100; i++) {
            evangelistsHashMap.put(topNRetweeted.get(i).getKey(), topNRetweeted.get(i).getValue());
            System.out.println(topNRetweeted.get(i).getKey());
        }
    }

    private <K, V extends Comparable<? super V>>
    List<Map.Entry<K, V>> sortMostRetweeted(Map<K, V> map) {

        List<Map.Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());

        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;
    }
}
