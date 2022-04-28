package org.tojaco;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;

import java.util.*;

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

    public HashMap<String, String> oppositesHashmap() {
        HashMap<String, String> oppositeQualities = new HashMap<>();
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

        return oppositeQualities;
    }
}
