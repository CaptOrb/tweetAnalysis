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
            String hashtagWord[] = hashtag.toString().split("(?<=[a-z])(?=[A-Z])");
            for(int i=0; i<hashtagWord.length;i++){
                checkIfA(hashtagWord[i], hashtag);
            }
            System.out.println();
        }
    }

    public void checkIfA(String hashtagWord, Vertex<Hashtag> hashtag){
        if(hashtagWord.startsWith("A") && (hashtagWord.length())!=1) {

            char secondLetter = hashtagWord.charAt(1);
            char thirdLetter = 'a';
            if(hashtagWord.length()>2) {
                thirdLetter = hashtagWord.charAt(2);
            }
            if(secondLetter>= 'A' && secondLetter <= 'Z' && thirdLetter >= 'a' && thirdLetter <='z' ){
                String IndefArticle = hashtagWord.substring(0,1); //start index is inclusive, end index is exclusive
                String restOfWord = hashtagWord.substring(1);
                hashtag.getLabel().addWord(IndefArticle.replaceAll("[#.,]","").toLowerCase());
                hashtag.getLabel().addWord(restOfWord.replaceAll("[#.,]","").toLowerCase());
                System.out.println(IndefArticle.replaceAll("[#.,]",""));
                System.out.println(restOfWord.replaceAll("[#.,]",""));
            } else{
                hashtag.getLabel().addWord(hashtagWord.replaceAll("[#.,]","").toLowerCase());
                System.out.println(hashtagWord.replaceAll("[#.,]",""));
            }
        } else{
            hashtag.getLabel().addWord(hashtagWord.replaceAll("[#.,]","").toLowerCase());
            System.out.println(hashtagWord.replaceAll("[#.,]",""));
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

            // System.out.println(hashtag.toString());

            lexiconDictionary.add(key.toString());
        }
    }

    public void splitHashtagsByLexicon(DirectedGraph<Hashtag, E> sumHashTagGraph) {

        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet : sumHashTagGraph.getGraph().entrySet()) {

            Hashtag hashtag = entrySet.getKey().getLabel();

            boolean splitSuccess = splitHashtagsByLexiconHelper(hashtag.toString(), hashtag, lexiconDictionary);

            hashtag.editListOfWords();

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
