package org.tojaco.GraphAnalysis;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;
import org.tojaco.GraphElements.TwitterUser;
import org.tojaco.Lexicon;
import twitter4j.Twitter;

import java.util.*;

public class StatCalculator {
    private final DirectedGraph<TwitterUser, String> userModel;
    private List<TwitterUser> usersList;
    private List<TwitterUser> proUsers;
    private List<TwitterUser> antiUsers;

    private final Map<String, Double> conditionalPropZSScore = new HashMap<>();

    private final Map<String, Double> significantConditionalProbabilities = new HashMap<>();

    public StatCalculator(DirectedGraph<TwitterUser, String> userModel){
        this.userModel = userModel;
        usersList = new ArrayList<>();
        antiUsers = new ArrayList<>();
        proUsers = new ArrayList<>();
        for (Vertex<TwitterUser> user : userModel.getGraph().keySet()){
            usersList.add(user.getLabel());
        }
    }


    private List<TwitterUser> getProportionList(List<TwitterUser> totalSet, String subsetCondition){
        List<TwitterUser> subset = new ArrayList<>();
        for( TwitterUser user : totalSet ){
            Vertex<TwitterUser> vertex = userModel.getAllVerticesInGraph().get(user.getUserHandle());
            for(String quality : vertex.getLabel().getQualities()){
                if (quality.equals((subsetCondition))){
                    subset.add(vertex.getLabel());
                    break;
                }
            }
//            for( Arc<String> arc : userModel.getGraph().get(vertex) ){
//                if ( arc.getVertex().getLabel().equals(subsetCondition) ){
//                    subset.add(vertex.getLabel());
//                    break;
//                }
//            }
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

    private Double calculateProStancesProportion( List<TwitterUser> usersSample ){
        double subset = 0.0;
        for ( TwitterUser user : usersSample ){
            if( user.getStance() > 0 ){
                subset++;
            }
        }
        return subset/usersSample.size();
    }

    private List<Double> calculateMeanOfRandomSamplesOfSizeM(boolean positivity, int m ) {
        List<Double> probabilities = new ArrayList<>();

//        for(int i = 0; i < 100; i ++) {
//            List<TwitterUser> sampleUsers = new ArrayList<>();
//            for (Vertex<TwitterUser> vertex : userModel.getGraph().keySet()) {
//                sampleUsers.add(vertex.getLabel());
//            }
//            probabilities.add( calculateAntiStancesProportion(sampleUsers) );
//        }
//        int i = 0;

        Random generator = new Random();
        Object[] values = usersList.toArray();
        for( int i = 0; i < 100; i++ ) {
            List<TwitterUser> sampleUsers = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                Object randomValue = values[generator.nextInt(values.length)];
                sampleUsers.add((TwitterUser)randomValue);
            }
            if(!positivity){
                probabilities.add( calculateAntiStancesProportion(sampleUsers) );
            }
            else{
                probabilities.add( calculateProStancesProportion(sampleUsers) );
            }

        }
//        for ( int j = 0; j < 100; j++) {
//            List<TwitterUser> sampleUsers = new ArrayList<>();
//            for (Vertex<TwitterUser> vertex : userModel.getGraph().keySet()) {
//                sampleUsers.add((vertex.getLabel()));
//                if (sampleUsers.size() == 100) {
//                    break;
//                }
//            }
//            probabilities.add( calculateAntiStancesProportion(sampleUsers) );
//        }
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

    private Double calculateSD(List<Double> points, double μ){
        // calculate sum of squared differences
        double sumOfSquaredDiffs = 0.0;
        for ( Double point : points ) {
            double diff = point - μ ;
            double squaredDiff = diff * diff;
            sumOfSquaredDiffs += squaredDiff;
        }
        return Math.sqrt(sumOfSquaredDiffs / points.size() );
    }

    public Double calculateZScore( boolean positivity, String property ){
        double anti;
        List<TwitterUser> m;
        List<Double> sampleMeans;
        double mew, sD;
        getProAntiList();
        m = getProportionList(usersList, property);
        sampleMeans = calculateMeanOfRandomSamplesOfSizeM(positivity, m.size());
        mew = calculateDoubleMean(sampleMeans);
        sD = calculateSD(sampleMeans, mew);
        double difference;
        if( !positivity ){
            difference = calculateAntiStancesProportion(m) - mew;
        }
        else{
            difference = calculateProStancesProportion(m) - mew;
        }
        return difference/sD;
    }

    public double calConditionalProbabilityWithProps(String prop1, String prop2) {

        double probUserWithPropOne = getProportionList(usersList, prop1).size() / (double) usersList.size();

        double probUserWithPropTwo = getProportionList(usersList, prop2).size() / (double) usersList.size();
        double intersection = getProportionList(getProportionList(usersList, prop1), prop2).size() / (double) usersList.size();

        double probUserWithBothProps = probUserWithPropOne * probUserWithPropTwo;

        return intersection / probUserWithPropTwo;
    }

    public void automateConditionalProbCalculation(Lexicon<String> lexicon) {

        for (Map.Entry<String, List<String>> featureMapping : lexicon.getStanceGivenConditionList().entrySet()) {

            for(String condition: featureMapping.getValue()) {

                double zScore = 0.0;
                if(featureMapping.getKey().equals("anti")) {
                    zScore = calculateZScore(false, condition);
                }
                else if( featureMapping.getKey().equals("pro") ){
                    zScore = calculateZScore(true, condition);
                }

                double conditionalProbability = calConditionalProbabilityWithProps(featureMapping.getKey(),
                        condition);

                if (zScore >= 2) {

                    String propertyBeingChecked = featureMapping.getKey() + " : " + condition;

                    conditionalPropZSScore.put(propertyBeingChecked, zScore);

                    significantConditionalProbabilities.put(propertyBeingChecked,
                            conditionalProbability);

                }
            }
        }
    }

    public void outputSignificantConditionalProbabilities(){
        System.out.println("\nMost significant conditional probabilities found");
        System.out.format("%20s%20s%20s", "Property", "Probability", "Z-Score");
        System.out.println();

        for(Map.Entry<String, Double> list : significantConditionalProbabilities.entrySet()) {
            System.out.format("%20s%20f%20f", list.getKey(), list.getValue(), conditionalPropZSScore.get(list.getKey()));
            System.out.println();
        }
    }
}