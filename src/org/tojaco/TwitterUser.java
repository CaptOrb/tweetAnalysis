package org.tojaco;

public class TwitterUser {
    private String userHandle;
    private int stance;

    TwitterUser(String userHandle){
        this.userHandle = userHandle;
    }

    public String getUserHandle() { return userHandle; }
    public int getStance() { return stance; }

    public String toString(){ return userHandle; }
}
