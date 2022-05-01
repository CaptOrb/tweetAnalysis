package org.tojaco;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;

import java.util.*;

public class Lexicon<E> {

    Lexicon(){
        initOppositesHashmap();
        initStanceGivenConditionList();
    }

    private final Set<String> lexiconDictionary = new HashSet<>();

    private final ArrayList<String> lexiconTermsWithFeatures = new ArrayList<>();

    public ArrayList<String> getLexiconTermsWithFeatures() {
        return lexiconTermsWithFeatures;
    }

    public void addToLexiconWithFeatures(Collection<String> term){
        lexiconTermsWithFeatures.addAll(term);
    }

    public Set<String> getLexiconDictionary() {
        return lexiconDictionary;
    }

    public void initialiseLexiconDictionary(DirectedGraph<Hashtag, E> sumHashTagGraph) {
        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet : sumHashTagGraph.getGraph().entrySet()) {
            lexiconDictionary.add(entrySet.getKey().toString());
        }
    }

    private final HashMap<String, String> oppositeQualities = new HashMap<>();

    private void initOppositesHashmap() {
        oppositeQualities.put("problem", "solution");
        oppositeQualities.put("accepting", "rejecting");
        oppositeQualities.put("scientific", "religious");
        oppositeQualities.put("rights", "responsibilities");
        oppositeQualities.put("rightwing", "leftwing");
        oppositeQualities.put("negation", null);
        oppositeQualities.put("numeric", null);
        oppositeQualities.put("time", null);
        oppositeQualities.put("racial", null);
        oppositeQualities.put("animal", null);
        oppositeQualities.put("vulgar", null);
        oppositeQualities.put("place", null);
        oppositeQualities.put("authority", null);
        oppositeQualities.put("business", null);
        oppositeQualities.put("distrust", null);
        oppositeQualities.put("social", null);
        oppositeQualities.put("personal", null);
        oppositeQualities.put("political", null);
    }

    public HashMap<String, String> getOppositeQualities() {
        return oppositeQualities;
    }
    Map<String, List<String>> stanceGivenConditionList = new LinkedHashMap<>();
    private void addToStanceGivenConditionList(String key, String newValue) {
        List<String> currentValue = stanceGivenConditionList.computeIfAbsent(key, k -> new ArrayList<>());
        currentValue.add(newValue);
    }

    private void initStanceGivenConditionList() {

        addToStanceGivenConditionList("anti", "ref:fauci");
//addToStanceGivenConditionList("-ref:fauci", "anti");
//addToStanceGivenConditionList("-ref:vaccine", "anti");
//addToStanceGivenConditionList("ref:vaccine", "pro");
//addToStanceGivenConditionList("ref:pfizer", "pro");
        addToStanceGivenConditionList("anti", "-ref:pfizer");
        addToStanceGivenConditionList("anti", "-ref:corona");
        addToStanceGivenConditionList("pro", "ref:corona");
        addToStanceGivenConditionList("pro", "ref:tony_holohan");
        addToStanceGivenConditionList("anti", "-ref:tony_holohan");
        addToStanceGivenConditionList("anti", "ref:distrust");
        addToStanceGivenConditionList("pro", "ref:media");
        addToStanceGivenConditionList("anti", "-ref:media");
        addToStanceGivenConditionList("pro", "ref:janssen");
        addToStanceGivenConditionList("anti", "-ref:janssen");
//addToStanceGivenConditionList("ref:janssen", "pro");
//addToStanceGivenConditionList("-ref:janssen", "anti");
        addToStanceGivenConditionList("pro", "leftwing");
        addToStanceGivenConditionList("anti", "leftwing");
        addToStanceGivenConditionList("pro", "rightwing");
        addToStanceGivenConditionList("anti", "rightwing");
//addToStanceGivenConditionList("ref:vaccine", "ref:boosted");
//addToStanceGivenConditionList("-ref:vaccine", "boosted");
//addToStanceGivenConditionList("vaccinated", "ref:vaccine");
//addToStanceGivenConditionList("vaccinated", "-ref:vaccine");
//addToStanceGivenConditionList("nojab", "anti");

    }

    public Map<String, List<String>> getStanceGivenConditionList() {
        return stanceGivenConditionList;
    }
}
