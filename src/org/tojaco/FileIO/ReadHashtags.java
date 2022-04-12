package org.tojaco.FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadHashtags {

    public ArrayList<String> readRetweetsIntoSet(File file) {
        final ArrayList<String> hashtags = new ArrayList<>();
        String j=null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                try {
                    Long.parseLong(lineContents[0]);
                    String[] findRetweet = lineContents[2].split(" "); //lineContents[2] is "RT @RetweetedUser tweetText" if it's a retweet
                    //if (findRetweet[2].contains("#")) { //if tweet has hashtags
                        String[] tweetText = findRetweet[2].split(" "); //split the tweet text
                        StringBuilder hashtagsInLine = new StringBuilder("");
                        for(int i = 0; i<tweetText.length; i++){
                            if(tweetText[i].startsWith("#"));
                            hashtagsInLine.append("\t").append(tweetText[i]); //adds username plus hashtags
                        }
                        hashtags.add(lineContents[1] + hashtagsInLine );
                        System.out.println(lineContents[1] + hashtagsInLine);
                        //retweets.add(lineContents[1] + "\t" + username); //adds @User + "\t" + @RetweetedUser and whatever they tweeted
                    //}
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    j = "doNothing";
                } catch (Exception exception) {
                    String k = "asd";
                }
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return hashtags;

    }
}
