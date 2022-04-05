import java.util.*;

public class findEvangelists {
    Map<Vertex<String>, Integer> retweetsHashMap = new HashMap<>();
    Map<Vertex<String>, Integer> evangelistsHashMap = new HashMap<>();

    public void findTotalRetweets(RetweetGraph<String> retweetedGraph) {


        int totalRetweets = 0;
        int lowestInfluence = 0;
        for (Vertex<String> vertex : retweetedGraph.getGraph().keySet()) {

            for (int i = 0; i < retweetedGraph.getGraph().get(vertex).size(); i++) {
                totalRetweets += retweetedGraph.getGraph().get(vertex).get(i).getWeight();
            }
            System.out.println(vertex);
            System.out.println(totalRetweets);
            retweetsHashMap.put(vertex, totalRetweets);

            totalRetweets = 0; //reset
        }
        System.out.println("ANSWER");
        for (int i = 0; i < 100; i++) {
            evangelistsHashMap.put(entriesSortedByValues(retweetsHashMap).get(i).getKey(), entriesSortedByValues(retweetsHashMap).get(i).getValue());
            System.out.println(entriesSortedByValues(retweetsHashMap).get(i).getKey());
        }

    }

    static <K, V extends Comparable<? super V>>
    List<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

        List<Map.Entry<K, V>> sortedEntries = new ArrayList<Map.Entry<K, V>>(map.entrySet());

        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;
    }
}
