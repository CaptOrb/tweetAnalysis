package org.tojaco;

import java.util.ArrayList;
import java.util.List;

public class Hashtag {
    private final String tag;
    private final List<String> words = new ArrayList<>();

    public Hashtag(String tag){
        this.tag = tag;
    }

    public String toString(){
        return tag.toString();
    }

}
