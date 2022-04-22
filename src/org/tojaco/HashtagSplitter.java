package org.tojaco;

import org.tojaco.Graph.Arc;
import org.tojaco.Graph.DirectedGraph;
import org.tojaco.Graph.Vertex;
import org.tojaco.GraphElements.Hashtag;

import java.util.*;


public class HashtagSplitter<T, E> {

    private final Set<String> lexiconDictionary = new HashSet<>();

    public Set<String> getLexiconDictionary() {
        return lexiconDictionary;
    }

    private List<String> wordSplitList = new LinkedList<>();

    public void splitHashtagsByCamelCase(DirectedGraph<Hashtag, E> hashtagToUsers) {
        for (Vertex<Hashtag> hashtag : hashtagToUsers.getGraph().keySet()) {
            String hashtagWord[] = hashtag.toString().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            for(int i=0; i<hashtagWord.length;i++){
                if(!(hashtagWord[i].contains("…"))){
                    hashtag.getLabel().addWord(hashtagWord[i].replaceAll("[#.,]","").toLowerCase());
                    //System.out.println(hashtagWord[i].replaceAll("[#.,]",""));
                }

                //System.out.print(hashtagWord[i].replaceAll("#",""));
            }
            //System.out.println();
        }
    }

    public boolean splitHashtagsByLexiconHelper(String hashTag, Set<String> lexiconDictionary) {

        hashTag = hashTag.replaceAll("[#]", "").toLowerCase();

        if (hashTag.length() == 0) {
            return true;
        } else {

            // keep matching each character until we find a valid word
            for (int i = 1; i <= hashTag.length(); i++) {
                String firstWord = hashTag.substring(0, i);
                String remainSubStr = hashTag.substring(i);

                if ((lexiconDictionary.contains(firstWord))
                        && (splitHashtagsByLexiconHelper(remainSubStr, lexiconDictionary))) {
                    wordSplitList.add(firstWord);
                    return true;
                }
            }
        }
        return false;
    }

    public void initialiseLexiconDictionary(DirectedGraph<Hashtag, E> sumHashTagGraph) {
        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet : sumHashTagGraph.getGraph().entrySet()) {

            Vertex<Hashtag> key = entrySet.getKey();

            lexiconDictionary.add(key.toString());
        }
    }

    public void splitHashtagsByLexicon(DirectedGraph<Hashtag, E> sumHashTagGraph) {

        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet : sumHashTagGraph.getGraph().entrySet()) {

            Vertex<Hashtag> hashtag = entrySet.getKey();

            wordSplitList.clear();

            boolean splitSuccess = splitHashtagsByLexiconHelper(hashtag.toString(), lexiconDictionary);

            if (splitSuccess) {
                System.out.print(hashtag + " was split into:\t");
                for (String s : wordSplitList) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }

        }
    }
}
