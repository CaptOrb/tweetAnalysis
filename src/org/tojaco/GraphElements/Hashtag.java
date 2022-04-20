package org.tojaco.GraphElements;

import java.util.ArrayList;
import java.util.List;

public class Hashtag implements Stanceable {
    private final String tag;
    private final List<String> words = new ArrayList<String>();
    private int stance;
    private boolean hasStance;

    public List<String> getWords() {
        return words;
    }
    public void addWord(String wordInHashtag){
        words.add(wordInHashtag);
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
    }

    public String toString(){
        return tag.toString();
    }

}
