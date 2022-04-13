package org.tojaco;

public class TwitterUser {
    private final String userHandle;
    private int stance;
    private boolean hasStance;

    TwitterUser(String userHandle){
        this.userHandle = userHandle;
    }

    public String getUserHandle() { return userHandle; }
    public int getStance() { return stance; }

    public void setStance(int stance){
        this.stance = stance;
    }

    public boolean hasStance(){
        return hasStance;
    }

    public String toString(){ return userHandle; }
}
