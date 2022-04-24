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
              //  System.out.println(IndefArticle.replaceAll("[#.,]",""));
                //System.out.println(restOfWord.replaceAll("[#.,]",""));
            } else{
                hashtag.getLabel().addWord(hashtagWord.replaceAll("[#.,]","").toLowerCase());
                //System.out.println(hashtagWord.replaceAll("[#.,]",""));
            }
        } else{
            hashtag.getLabel().addWord(hashtagWord.replaceAll("[#.,]","").toLowerCase());
            //System.out.println(hashtagWord.replaceAll("[#.,]",""));
        }
    }

    public static void splitHashtagsByLexiconHelper(String hashtag, Set<String> lexiconDictionary,
                                                    Stack<String> tagComponent, List<List<String>> splitStr) {

        hashtag = hashtag.replaceAll("[#]", "").toLowerCase();

        for (int i = 0; i < hashtag.length(); i++) {
            String substring = hashtag.substring(0, i + 1);

            if (lexiconDictionary.contains(substring)) {

                // we use a stack to maintain the order of the words
                tagComponent.push(substring);

                if (i == hashtag.length() - 1) {
                    splitStr.add(new ArrayList<>(tagComponent));
                } else {
                    // recursive call
                    splitHashtagsByLexiconHelper(hashtag.substring(i + 1),
                            lexiconDictionary, tagComponent, splitStr);
                }
                // pop matched word from the stack
                tagComponent.pop();
            }
        }
    }

/*    public boolean splitHashtagsByLexiconHelper(String hashTag, Hashtag hashtagObj, Set<String> lexiconDictionary, Stack<String> words) {

        hashTag = hashTag.replaceAll("[#]", "").toLowerCase();

        if (hashTag.length() == 0) {
            return true;
        } else {

            // keep matching each character until we find a valid word
            for (int i = 1; i <= hashTag.length(); i++) {

                String firstWord = hashTag.substring(0, i);
                String remainSubStr = hashTag.substring(i);

                if ((lexiconDictionary.contains(firstWord))
                        && (splitHashtagsByLexiconHelper(remainSubStr, hashtagObj,lexiconDictionary, words))) {

                    if(!hashtagObj.getWords().contains(firstWord)){
                        hashtagObj.addWord(firstWord);
                    }
                    return true;
                }
            }
        }
        return false;
    }*/

    public void initialiseLexiconDictionary(DirectedGraph<Hashtag, E> sumHashTagGraph) {
        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet : sumHashTagGraph.getGraph().entrySet()) {
            lexiconDictionary.add(entrySet.getKey().toString());
        }
    }

    public void splitHashtagsByLexicon(DirectedGraph<Hashtag, E> sumHashTagGraph) {

        for (Map.Entry<Vertex<Hashtag>, ArrayList<Arc<E>>> entrySet : sumHashTagGraph.getGraph().entrySet()) {

            Hashtag hashtag = entrySet.getKey().getLabel();

            List<List<String>> splitStr = new LinkedList<>();

            splitHashtagsByLexiconHelper(hashtag.toString(), lexiconDictionary, new Stack<>(), splitStr);

            // then add the word splits
            for (List<String> listResult : splitStr) {
                for (String word : listResult) {
                    if (!hashtag.getWords().contains(word)) {
                        hashtag.addWord(word);
                    }
                }
            }

            // commented this out for now because it gives some wrong results e.g. #GETVACCINATEDNOW was split into: getvaccinatednow
            // where it should have been split up further
           //  hashtag.editListOfWords();

            System.out.print(hashtag + " was split into:\t");
            for (String s : hashtag.getWords()) {
                System.out.print(s + " ");
            }
            System.out.println();

        }
    }
}
