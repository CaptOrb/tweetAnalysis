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
    private final DirectedGraph<TwitterUser, String> userModel;
    private List<TwitterUser> usersList;
    private List<TwitterUser> proUsers;
    private List<TwitterUser> antiUsers;

    public StatCalculator(DirectedGraph<TwitterUser, String> userModel){
        this.userModel = userModel;
        usersList = new ArrayList<>();
        for (Vertex<TwitterUser> user : userModel.getGraph().keySet()){
            usersList.add(user.getLabel());
        }
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

    private void getProAntiList(){
        for( TwitterUser user : usersList ){
            if ( user.getStance() < 0 ){
                antiUsers.add(user);
            }
            else if( user.getStance() > 0 ){
                proUsers.add(user);
            }
        }
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

    // standard deviation calculation:

    private Double calculateSD(List<Double> points, double mew){
        // calculate sum of squared differences
        double sumOfSquaredDiffs = 0.0;
        for ( Double point : points ) {
            sumOfSquaredDiffs += Math.pow( ( point - mew ), 2 );
        }
        return Math.sqrt(sumOfSquaredDiffs / points.size() );
    }

    public Double calculateZScore( boolean positivity, String property ){
        double anti;
        List<TwitterUser> m;
        List<Double> sampleMeans;
        double mew, sD;
        if( !positivity ){
            getProAntiList();
            m = getProportionList(antiUsers, property);
            sampleMeans = calculateMeanOfRandomSamplesOfSizeM(m.size());
            mew = calculateDoubleMean(sampleMeans);
            sD = calculateSD(sampleMeans, mew);
            double difference = calculateAntiStancesProportion(m) - mew;
            return sD/difference;
        }
        return 0.0;
    }

    public double calConditionalProbabilityWithProps(String prop1, String prop2) {

        double probUserWithPropOne = getProportionList(usersList, prop1).size() / (double) usersList.size();

        double probUserWithPropTwo = getProportionList(usersList, prop2).size() / (double) usersList.size();

        double probUserWithBothProps = probUserWithPropOne * probUserWithPropTwo;

        return probUserWithBothProps / probUserWithPropTwo;
    }

    public void calConditionalProbability(DirectedGraph<TwitterUser, String> userModel) {

        // TODO ignore Z-Score of < 2

        Collection<ArrayList<Arc<String>>> allFeatures = userModel.getGraph().values();

        for (ArrayList<Arc<String>> stringArc : allFeatures) {

            for (int i = 0; i < stringArc.size(); i++) {
                for (int j = 1; j < stringArc.size(); j++) {

                    calConditionalProbabilityWithProps(stringArc.get(i).getVertex().getLabel(),
                            stringArc.get(j).getVertex().getLabel());
                }
            }
        }
    }
}


