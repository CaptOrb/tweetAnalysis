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

    public boolean splitHashtagsByLexiconHelper(String hashTag, Hashtag hashtagObj, Set<String> lexiconDictionary) {

        hashTag = hashTag.replaceAll("[#]", "").toLowerCase();

        if (hashTag.length() == 0) {
            return true;
        } else {

            // keep matching each character until we find a valid word
            for (int i = 1; i <= hashTag.length(); i++) {
                String firstWord = hashTag.substring(0, i);
                String remainSubStr = hashTag.substring(i);

                if ((lexiconDictionary.contains(firstWord))
                        && (splitHashtagsByLexiconHelper(remainSubStr, hashtagObj,lexiconDictionary))) {

                    if(!hashtagObj.getWords().contains(firstWord)){
                        hashtagObj.addWord(firstWord);
                    }
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

            Hashtag hashtag = entrySet.getKey().getLabel();

            boolean splitSuccess = splitHashtagsByLexiconHelper(hashtag.toString(), hashtag, lexiconDictionary);

            if (splitSuccess) {
                System.out.print(hashtag + " was split into:\t");
                for (String s : hashtag.getWords()) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }

        }
    }
}
