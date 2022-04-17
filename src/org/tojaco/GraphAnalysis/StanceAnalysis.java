package org.tojaco.GraphAnalysis;
import org.tojaco.Graph.Vertex;
import org.tojaco.TwitterUser;

import java.util.ArrayList;
import java.util.Map;


//take 100 users and check if stance is correct
public class  StanceAnalysis<T>{

    public void checkStance100Users(Map<Vertex<T>, Integer> retweetsHashMap){
        int i = 0;
        for(Map.Entry<Vertex<T>,Integer> entry : retweetsHashMap.entrySet()){
            if(entry.getValue()>10){
                i++;
                //System.out.println(entry.getKey()); //was used to get 100 users, results found in 100Users.txt
            }
            if(i==100)
                break;
        }
    }

    //3b?
    public ArrayList<Vertex<T>> UsersWithNoStance(Map<Vertex<T>, Integer> retweetsHashMap){
        ArrayList<Vertex<T>> usersNoStance = new ArrayList<Vertex<T>>();
        for(Map.Entry<Vertex<T>,Integer> entry : retweetsHashMap.entrySet()){
            if(!(entry.getKey().hasStance())){
                usersNoStance.add(entry.getKey());
                System.out.println(entry.getKey()); //was used to find users with no stance, probably better to write to a file :-) instead just copied and pasted into UsesNoStances
            }
        }
        return usersNoStance;
    }
}
