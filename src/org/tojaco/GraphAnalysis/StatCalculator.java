package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.Graph.VertexCreator;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.util.ArrayList;
import java.util.List;

public class StatCalculator {
    DirectedGraph<TwitterUser, String> userModel;
    DirectedGraph<Hashtag, String> hashtagSummaries;

    StatCalculator(DirectedGraph<TwitterUser, String> userModel, DirectedGraph<Hashtag, String> hashtagSummaries){
        this.userModel = userModel;
        this.hashtagSummaries = hashtagSummaries;
    }

    private List<TwitterUser> getProportionList(List<TwitterUser> totalSet, String subsetCondition){
        List<TwitterUser> subset = new ArrayList<>();
        for( TwitterUser user : totalSet ){
            Vertex<TwitterUser> vertex = userModel.getAllVerticesInGraph().get(user.getUserHandle());
            for( Arc<String> arc : userModel.getGraph().get(vertex) ){
                if ( arc.getVertex().getLabel().equals(subsetCondition) ){
                    subset.add(vertex.getLabel());
                    break;
                }
            }
        }

        return subset;
    }

}
