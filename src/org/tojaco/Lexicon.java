package org.tojaco;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lexicon<E> {

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
        if(oppositeQualities.isEmpty()){
            initOppositesHashmap();
        }
        return oppositeQualities;
    }

    private final Map<String, String> stanceGivenConditionList = Stream.of(new String[][] {
            { "anti", "-ref:fauci" },
            { "-ref:fauci", "anti" },
            { "-ref:vaccine", "anti" },
            { "ref:vaccine", "pro" },
            { "ref:pfizer", "pro" },
            { "anti", "-ref:pfizer" },
            { "anti", "-ref:corona" },
            { "pro", "ref:corona" },
            { "pro", "ref:tony_holohan" },
            { "anti", "-ref:tony_holohan" },
            { "anti", "ref:distrust" },
            { "pro", "ref:media" },
            { "anti", "-ref:media" },
            { "pro", "ref:janssen" },
            { "anti", "-ref:janssen" },
            { "ref:janssen", "pro" },
            { "-ref:janssen", "anti" },
            { "pro", "leftwing" },
            { "anti", "leftwing" },
            { "pro", "rightwing" },
            { "anti", "rightwing" },
            { "ref:vaccine", "ref:boosted" },
            { "-ref:vaccine", "boosted" },
            { "vaccinated", "ref:vaccine" },
            { "vaccinated", "-ref:vaccine" },
            { "nojab", "anti" },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public Map<String, String> getStanceGivenConditionList() {
        return stanceGivenConditionList;
    }
}
