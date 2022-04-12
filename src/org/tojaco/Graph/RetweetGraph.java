package org.tojaco.Graph;

import java.util.ArrayList;

// Implementation of a directed graph to graph retweets
// getRetweeted method returns a list of retweeted users by a given retweeter
// addArc method is used to build the graph and link a retweeter to their retweeted
// hasArcBetween method indicates whether a user is connected to another user by retweeting/ being retweeted by them
// getLabelBetweenVertices method will indicate number of times retweeted ( the label on the arc )


public class RetweetGraph<T> extends Graph<T> {

    public ArrayList<Arc<T>> getRetweeted(Vertex<T> key) { return getGraph().get(key); }
}
