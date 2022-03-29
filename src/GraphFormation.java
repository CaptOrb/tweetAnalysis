import twitter4j.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphFormation {
    //this works for how the file is made but like technically it is reusable if a diff file has the same structure?
    //but otherwise, if we did this WHILE collecting tweets we just add @user and the retweeted user IFF it is a retweet?
    //like so yeah it could be reusable :)
    //if doing it at time of writing to file, don't forget to add @ to start of username, and this is based on fact that "RT" is in the tweet text!!


    //NOTE - hashset doesn't allow duplicates so I used an arraylist instead
    //for example, if me jane tweeted myself 5 times it would only appear once in a hashset but we gotta know about
    //them 4 other times :)
    private final ArrayList<String> retweets = new ArrayList<String>();

    public ArrayList<String> getRetweets() {
        return retweets;
    }

    //Any retweets are now contained in arraylist retweets:
    public void readRetweetsIntoSet(File file){
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                String[] findRetweet = lineContents[2].split(" "); //lineContents[2] is "RT @RetweetedUser tweetText" if it's a retweet
                if(findRetweet[0].contains("RT")){
                    String username = findRetweet[1].replaceAll(":",""); //remove : after the retweeted user
                    retweets.add(lineContents[1] + "\t" + username); //adds @User + "\t" + @RetweetedUser and whatever they tweeted
                }
            }
//            for( String rt : retweets ){ //for testing purposes, uncomment if you wanna see that it works
//                System.out.println(rt);
//            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }

    }


}
