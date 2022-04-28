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
}
