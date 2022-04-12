/*package org.tojaco.Graph;

import java.util.ArrayList;

// Implementation of a directed graph to graph retweets
// getRetweeted method returns a list of retweeted users by a given retweeter
// addArc method is used to build the graph and link a retweeter to their retweeted
// hasArcBetween method indicates whether a user is connected to another user by retweeting/ being retweeted by them
// getLabelBetweenVertices method will indicate number of times retweeted ( the label on the arc )


public class RetweetGraph<T, E> extends Graph<T, E> {

    public ArrayList<Arc<E>> getRetweeted(Vertex<T> key) { return getGraph().get(key); }

    @Override
    public void removeArc(Vertex<T> vertex, Arc<E> arc) {
        if ( !getGraph().containsKey(vertex) ){
            System.out.println(vertex.toString() + " does not retweet anyone");
            return;
        }

        if ( !getGraph().get(vertex).contains(arc) ){
            System.out.println(vertex.toString() + "does not retweet " + arc.toString());
            return;
        }

        if(arc.getWeight() > 1){
            arc.decrementWeight();
            return;
        }
        getGraph().get(vertex).remove(arc);
        if (getGraph().get(vertex).isEmpty()){
            getGraph().remove(vertex);
        }
    }
}*/
