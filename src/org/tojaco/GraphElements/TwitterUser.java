package org.tojaco.GraphElements;

import java.util.ArrayList;
import java.util.List;

public class TwitterUser implements Stanceable {
    private final String userHandle;
    private int stance;
    private boolean hasStance;
    private List<String> qualities = new ArrayList<>();


    public List<String> getQualities() {
        return qualities;
    }

    public void addQuality(List<String> qualitysFromHashtags){
        qualities.addAll(qualitysFromHashtags);
    }

    public TwitterUser(String userHandle){
        this.userHandle = userHandle;
    }

    public String getUserHandle() { return userHandle; }
    public int getStance() { return stance; }

    public void setStance(int stance){
        this.stance = stance;
        hasStance = true;
    }

    public boolean hasStance(){
        return hasStance;
    }

    public String toString(){ return userHandle; }
}
