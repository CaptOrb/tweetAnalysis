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
                if(hashtagWord[i].startsWith("A") && (hashtagWord[i].length())!=1) {

                    char secondLetter = hashtagWord[i].charAt(1);
                    char thirdLetter = 'a';
                    if(hashtagWord[i].length()>2) {
                        thirdLetter = hashtagWord[i].charAt(2);
                    }
                    if(secondLetter>= 'A' && secondLetter <= 'Z' && thirdLetter >= 'a' && thirdLetter <='z' ){
                        String IndefArticle = hashtagWord[i].substring(0,1); //start index is inclusive, end index is exclusive
                        String restOfWord = hashtagWord[i].substring(1);
                        hashtag.getLabel().addWord(IndefArticle.replaceAll("[#.,]","").toLowerCase());
                        hashtag.getLabel().addWord(restOfWord.replaceAll("[#.,]","").toLowerCase());
                        System.out.println(IndefArticle.replaceAll("[#.,]",""));
                        System.out.println(restOfWord.replaceAll("[#.,]",""));
                    } else{
                        hashtag.getLabel().addWord(hashtagWord[i].replaceAll("[#.,]","").toLowerCase());
                        System.out.println(hashtagWord[i].replaceAll("[#.,]",""));
                    }
                } else{
                    hashtag.getLabel().addWord(hashtagWord[i].replaceAll("[#.,]","").toLowerCase());
                    System.out.println(hashtagWord[i].replaceAll("[#.,]",""));
                }

            }
            System.out.println();
        }
    }


    //split by A for FauciIsAHero, so after an A is found and its followed by one capital letter then split it (must be followed by other letters)
    // but PNAS shouldn't be split, regex for if A is followed by only ONE capital letter then don't split it

    public void splitByA(Vertex<Hashtag> hashtag, String str){
        //if(str.contain)
    }

    public boolean breakHashTagByLexicon(String hashTag, Set<String> lexiconDictionary) {

        hashTag = hashTag.replaceAll("[#]", "").toLowerCase();

        if (hashTag.length() == 0) {
            return true;
        } else {

            // keep matching each character until we find a valid word
            for (int i = 1; i <= hashTag.length(); i++) {
                String firstWord = hashTag.substring(0, i);
                String remainSubStr = hashTag.substring(i);

                if ((lexiconDictionary.contains(firstWord)) && (breakHashTagByLexicon(remainSubStr, lexiconDictionary))) {
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

            // System.out.println(hashtag.toString());

            lexiconDictionary.add(key.toString());
        }
    }

    public void splitHashtagsByLexicon(DirectedGraph<Hashtag, E> sumHashTagGraph) {

        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet : sumHashTagGraph.getGraph().entrySet()) {

            Vertex<Hashtag> hashtag = entrySet.getKey();

            wordSplitList.clear();

            breakHashTagByLexicon(hashtag.toString(), lexiconDictionary);

        }
    }
}
