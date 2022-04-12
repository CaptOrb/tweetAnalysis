package org.tojaco.GraphAnalysis;
import org.tojaco.Graph.Vertex;
import org.tojaco.TwitterUser;

import java.util.Map;


//take 100 users and check if stance is correct
public class Users100 {
    public void checkStance(Map<Vertex<TwitterUser>, Integer> retweetsHashMap){
        int i = 0;
        for(Map.Entry<Vertex<TwitterUser>,Integer> entry : retweetsHashMap.entrySet()){
            if(entry.getValue()>10){
                i++;
                //System.out.println(entry.getKey()); //was used to get 100 users, results found in 100Users.txt
            }
            if(i==100)
                break;
        }
    }
}
