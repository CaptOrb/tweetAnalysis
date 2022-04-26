package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StatCalculator {
    DirectedGraph<TwitterUser, String> userModel;
    DirectedGraph<Hashtag, String> hashtagSummaries;

    StatCalculator(DirectedGraph<TwitterUser, String> userModel, DirectedGraph<Hashtag, String> hashtagSummaries){
        this.userModel = userModel;
        this.hashtagSummaries = hashtagSummaries;
    }

    private List<TwitterUser> getProportionList(Set<Vertex<TwitterUser>> totalSet, String subsetCondition){
        List<TwitterUser> subset = new ArrayList<>();
        for( Vertex<TwitterUser> user : totalSet ){
            Vertex<TwitterUser> vertex = userModel.getAllVerticesInGraph().get(user.getLabel().getUserHandle());
            for( Arc<String> arc : userModel.getGraph().get(vertex) ){
                if ( arc.getVertex().getLabel().equals(subsetCondition) ){
                    subset.add(vertex.getLabel());
                    break;
                }
            }
        }

        return subset;
    }


    public double calculateConditionalProbability(DirectedGraph<TwitterUser, String> userModel, String prop1, String prop2) {

        Set<Vertex<TwitterUser>> userKeySet = userModel.getGraph().keySet();

        double probUserWithPropOne = getProportionList(userKeySet, prop1).size() / (double) userKeySet.size();

        double probUserWithPropTwo = getProportionList(userKeySet, prop2).size() / (double) userKeySet.size();

        double probUserWithBothProps = probUserWithPropOne * probUserWithPropTwo;

        return probUserWithBothProps / probUserWithPropTwo;
    }

}
