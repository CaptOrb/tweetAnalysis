public class Edge {
    String User;
    String retweetedUser; //a user retweets this person
    int numRetweets; //the weight on each edge

    public Edge(String User, String retweetedUser, int numRetweets) {
        this.User = User;
        this.retweetedUser = retweetedUser;
        this.numRetweets = numRetweets;
    }
}
