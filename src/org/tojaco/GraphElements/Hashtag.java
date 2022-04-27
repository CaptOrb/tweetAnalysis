package org.tojaco.GraphElements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Hashtag implements Stanceable {
    private final String tag;
    private final List<String> words = new ArrayList<String>();
    private int stance;
    private boolean hasStance;
    private String acceptance;
    private List<String> refs = new ArrayList<>();
    private List<String> qualities = new ArrayList<>();

    public List<String> getQualities() {
        return qualities;
    }

    public void addQuality(String qualityOfUser){
        qualities.add(qualityOfUser);
    }

    public void editQualityList(){
        if(qualities.size()>=2){
            for(int i=0; i<qualities.size();i++){
                if(qualities.get(i).startsWith("ref:")){
                    findAcceptingRejecting(i);
                }
            }
        }
    }

    public void findAcceptingRejecting(int index){
        for(int i=0; i<qualities.size();i++){
            if(qualities.get(i).equals("accepting")){
                String ref = "+" + qualities.get(index);
                qualities.remove(i);
                qualities.add(ref);
            }
            if(qualities.get(i).equals("rejecting")){
                String ref = "-" + qualities.get(index);
                qualities.remove(i);
                qualities.add(ref);
            }
        }
    }


    public List<String> getWords() {
        return words;
    }
    public void addWord(String wordInHashtag){
        words.add(wordInHashtag);
    }

    public void editListOfWords(){

        if(words.size()>1){
            for(int i=0; i<words.size();i++){
                String word = words.get(i).toLowerCase();
                String newTag = tag.toLowerCase().replaceAll("#", "");
                if (word.equals(newTag)) {
                    words.remove(i); //eg #freeamerica is split into freeamerica, free, america, so don't add freeamerica
                    //but if the hashtag is just one word long it WILL equal the hashtag eg #project will just equal project
                }
            }
        }
        //for example the word country should not be split into co un and try, we need to remove the words that make up that word
        for(int i = 0; i<words.size();i++){
            String word = words.get(i).toLowerCase();
            String hashtag = null;
            if(word.length()!=1) //don't want to do it for just the letter A or I etc
                hashtag = tag.toLowerCase().substring(tag.length()-word.length());
            if(word.equals(hashtag) && i<words.size()-1){
                //remove all other words from the list
                for(int j = words.size()-1; j>i; j--){
                    words.remove(j);
                }
            }
        }
    }

    public int getStance() { return stance; }
    public void setStance(int stance){
        this.stance = stance;
        hasStance = true;

        if(stance==0){
            hasStance = false;
        }
    }
    public boolean hasStance(){
        return hasStance;
    }

    public Hashtag(String tag){
        this.tag = tag;
        acceptance = "";
    }

    public String toString(){
        return tag.toString();
    }

    // combination of acceptance and refs
    public String getGist(){ return acceptance + refs; }

    public void setAcceptance( String acceptance ){
        this.acceptance = acceptance;
    }

    public List<String> getRefs(){ return refs; }

    public void addRef( String ref ){
        refs.add(ref);
    }

}
