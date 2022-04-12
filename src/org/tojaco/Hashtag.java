package org.tojaco;

import java.util.ArrayList;
import java.util.List;

public class Hashtag {
    private String tag;
    private List<String> words = new ArrayList<>();

    Hashtag(String tag){
        this.tag = tag;
    }
}
