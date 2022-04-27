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

    private Double calculateAntiStancesProportion( List<TwitterUser> usersSample ){
        double subset = 0.0;
        for ( TwitterUser user : usersSample ){
            if( user.getStance() < 0 ){
                subset++;
            }
        }
        return subset/usersSample.size();
    }

    private List<Double> calculateMeanOfRandomSamplesOfSizeM(int m ) {
        List<Double> probabilities = new ArrayList<>();

        for(int i = 0; i < 100; i ++) {
            List<TwitterUser> sampleUsers = new ArrayList<>();
            for (Vertex<TwitterUser> vertex : userModel.getGraph().keySet()) {
                sampleUsers.add(vertex.getLabel());
                probabilities.add( calculateAntiStancesProportion(sampleUsers) );
            }
        }
        return probabilities;
    }

    private Double calculateDoubleMean(List<Double> list){
        Double total = 0.0;
        for ( Double curr : list ){
            total += curr;
        }

        return total/list.size();
    }

    private Double calculateSD(List<Double> points, double mew){
        // calculate sum of squared differences
        double sumOfSquaredDiffs = 0.0;
        for ( Double point : points ) {
            sumOfSquaredDiffs += Math.pow( ( point - mew ), 2 );
        }
        return Math.sqrt(sumOfSquaredDiffs / points.size() );
    }

    public Double calculateZScore(){
        // TODO
        return 0.0;
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


