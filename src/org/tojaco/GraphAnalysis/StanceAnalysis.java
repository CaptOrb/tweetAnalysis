package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.GraphElements;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.Stanceable;
import org.tojaco.GraphElements.TwitterUser;

import java.util.HashMap;
import java.util.Map;


public class StanceAnalysis<T, E> {

    //take 100 users and check if stance is correct
    public void checkStance100Users(Map<Vertex<T>, Integer> retweetsHashMap) {
        int i = 0;
        for (Map.Entry<Vertex<T>, Integer> entry : retweetsHashMap.entrySet()) {
            if (entry.getValue() > 10) {
                i++;
                //System.out.println(entry.getKey()); //was used to get 100 users, results found in 100Users.txt
            }
            if (i == 100)
                break;
        }
    }

    public void assignStancesByHashtags(DirectedGraph<Stanceable, Stanceable> usersToHashtags, GraphElements graphElements, DirectedGraph graph) {
        for (Vertex<Stanceable> vertex : usersToHashtags.getGraph().keySet()) {
            if (!(vertex.getLabel().toString().startsWith("#"))) { //reset user stances, keep hashtag stances the same
                vertex.getLabel().setStance(0); //in Vertex.java, by setting stance to 0 we set hasStance to false
            }

        }
        //now calculate stances for users just based solely on the hashtags they use

        GraphAnalyser graphAnalyser = new GraphAnalyser<>();

        for (int i = 0; i < 5; i++) {
            graphAnalyser.assignUserStances(usersToHashtags);
        }
    }

    //4c
    public Map<Vertex<TwitterUser>, Integer>  findUsers10orMoreRetweets(Map<Vertex<TwitterUser>, Integer> retweetsHashMap) {
        Map<Vertex<TwitterUser>, Integer> allUsers10retweets = new HashMap<>();
        // focus only on users who retweet 10 or more other users (for the retweet-based stances)
        for (Map.Entry<Vertex<TwitterUser>, Integer> entry : retweetsHashMap.entrySet()) {
            if (entry.getValue() >= 10) {
               allUsers10retweets.put(entry.getKey(),entry.getKey().getLabel().getStance());
            }
        }
        return allUsers10retweets;
    }
    public Map<Vertex<TwitterUser>, Integer> findUsers10orMoreHashtags(Map<Vertex<TwitterUser>, Integer> hashtagHashmap){
        Map <Vertex<TwitterUser>, Integer> allUsersWit10hHashTag = new HashMap<>();
        // focus only on users who use 10 or more other hashtags (for the hashtag-based stances)
        for (Map.Entry<Vertex<TwitterUser>, Integer> vertex : hashtagHashmap.entrySet()) {
            if (vertex.getValue() >= 10) {
                allUsersWit10hHashTag.put(vertex.getKey(),vertex.getKey().getLabel().getStance());
            }
        }

        return allUsersWit10hHashTag;
    }

    public Map<Vertex<TwitterUser>, Integer> findTotalHashtags(DirectedGraph<TwitterUser, Hashtag> graph) {
        Map<Vertex<TwitterUser>, Integer> hashtagTopUsers = new HashMap<>();

        for (Vertex<TwitterUser> vertex : graph.getGraph().keySet()) {
            int totalHashtags = 0;

            for (int i = 0; i < graph.getGraph().get(vertex).size(); i++) {
                totalHashtags ++;
            }

            hashtagTopUsers.put(vertex, totalHashtags);
        }
        return hashtagTopUsers;
    }
    public Map<Vertex<TwitterUser>, Integer> findTotalRetweets(DirectedGraph<TwitterUser, TwitterUser> graph) {
        Map<Vertex<TwitterUser>, Integer> retweetTopUsers = new HashMap<>();

        for (Vertex<TwitterUser> vertex : graph.getGraph().keySet()) {
            int totalRetweets = 0;

            for (int i = 0; i < graph.getGraph().get(vertex).size(); i++) {
                totalRetweets ++;
            }

            retweetTopUsers.put(vertex, totalRetweets);
        }
        return retweetTopUsers;
    }


        public void find100Hashtags(DirectedGraph<T, E> hashtagsToUsers) {
        int i = 0;
        for (Vertex<T> vertex : hashtagsToUsers.getGraph().keySet()) {
            i++;
            //System.out.println(vertex.getLabel() + ", Stance: " + vertex.getStance());
            if (i == 100)
                break;

        }
    }
}
