package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.DirectedGraph;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;
import twitter4j.User;

public class StatCalculator {
    DirectedGraph<TwitterUser, String> userModel;
    DirectedGraph<Hashtag, String> hashtagSummaries;

    StatCalculator(DirectedGraph<TwitterUser, String> userModel, DirectedGraph<Hashtag, String> hashtagSummaries){
        this.userModel = userModel;
        this.hashtagSummaries = hashtagSummaries;
    }
}
