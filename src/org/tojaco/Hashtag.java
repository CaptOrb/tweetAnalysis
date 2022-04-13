package org.tojaco;

import java.util.List;

public class Hashtag {
    private String tag;
    private List<String> wordsInTag;

    Hashtag(String tag){
        this.tag = tag;
    }

    public String getTag() { return tag; }
    public List<String> getWordsInTag() { return wordsInTag; }

    private void splitHashTagIntoWords(){
        // TODO
    }
}
