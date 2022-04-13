package org.tojaco;

import org.tojaco.Graph.*;

import java.util.*;

public class FindEvangelists {
    private final Map<Vertex<TwitterUser>, Integer> retweetsHashMap = new HashMap<>();
    private final Map<Vertex<TwitterUser>, Integer> evangelistsHashMap = new HashMap<>();

    public Map<Vertex<TwitterUser>, Integer> getEvangelistsHashMap() {
        return evangelistsHashMap;
    }

    public Map<Vertex<TwitterUser>, Integer> findTotalRetweets(DirectedGraph<TwitterUser, TwitterUser> retweetedGraph, TwitterUsers users) {


        for (Vertex<TwitterUser> vertex : retweetedGraph.getGraph().keySet()) {
            int totalRetweets = 0;

            for (int i = 0; i < retweetedGraph.getGraph().get(vertex).size(); i++) {
                totalRetweets += retweetedGraph.getGraph().get(vertex).get(i).getWeight();
            }

            retweetsHashMap.put(vertex, totalRetweets);
        }

        List<Map.Entry<Vertex<TwitterUser>, Integer>> topNRetweeted = sortMostRetweeted(retweetsHashMap);

        for (int i = 0; i < 200; i++) {
            evangelistsHashMap.put(topNRetweeted.get(i).getKey(), topNRetweeted.get(i).getValue());
        }

        return retweetsHashMap;
    }

    private <K, V extends Comparable<? super V>>
    List<Map.Entry<K, V>> sortMostRetweeted(Map<K, V> map) {

        List<Map.Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());

        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;
    }

}
