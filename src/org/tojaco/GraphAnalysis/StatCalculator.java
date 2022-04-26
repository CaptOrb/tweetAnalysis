package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;

import java.util.ArrayList;
import java.util.Collection;
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


    public double calConditionalProbabilityWithProps(DirectedGraph<TwitterUser, String> userModel, String prop1, String prop2) {

        Set<Vertex<TwitterUser>> userList = userModel.getGraph().keySet();

        double probUserWithPropOne = getProportionList(userList, prop1).size() / (double) userList.size();

        double probUserWithPropTwo = getProportionList(userList, prop2).size() / (double) userList.size();

        double probUserWithBothProps = probUserWithPropOne * probUserWithPropTwo;

        return probUserWithBothProps / probUserWithPropTwo;
    }

    public void calConditionalProbability(DirectedGraph<TwitterUser, String> userModel) {

        // TODO ignore Z-Score of < 2

        Collection<ArrayList<Arc<String>>> allFeatures = userModel.getGraph().values();

        for (ArrayList<Arc<String>> stringArc : allFeatures) {

            for (int i = 0; i < stringArc.size(); i++) {
                for (int j = 1; j < stringArc.size(); j++) {

                    calConditionalProbabilityWithProps(userModel, stringArc.get(i).getVertex().getLabel(),
                            stringArc.get(j).getVertex().getLabel());
                }
            }
        }
    }
}


