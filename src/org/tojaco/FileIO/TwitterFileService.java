package org.tojaco.FileIO;

import org.tojaco.Configuration;
import twitter4j.Status;
import twitter4j.User;

import java.io.*;
import java.util.HashSet;

//This class generates a data file with the tweets that we gathered,
//saving the tweets in this format:status_id <tab> @userhandle <tab> tweet text <tab> num_retweets <tab> timestamp <newline>
//It also stores the @userhandle, the location, the short biography, number of followers of the user. One user is stored per line, using tabs to delimit the fields of the entry.
//The code checks that the user or tweet is not in the file and so doesn't store duplicates
//The files are opened and written to in append mode
public class TwitterFileService extends FileService {

    private final HashSet<Long> foundTweetIDS = new HashSet<>();
    private final HashSet<String> foundUserHandles = new HashSet<>();

    public HashSet<String> getFoundUserHandles() {
        return foundUserHandles;
    }

    public HashSet<Long> getFoundTweetIDS() {
        return foundTweetIDS;
    }

    public void readTweetsIntoSet(File file){
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                foundTweetIDS.add(Long.parseLong(lineContents[0]));
                foundUserHandles.add(lineContents[1]);
            }
        } catch (IOException | NullPointerException | NumberFormatException fnfe) {
            // suppress and carry on
            //fnfe.printStackTrace();
        }

    }

    public void writeUser(User user) throws IOException {
        File file = createFile(Configuration.getDataDirectory(), Configuration.getUserFile());

        String userName = "@" + user.getScreenName();
        if (!foundUserHandles.contains(userName)) {
            String bio; //if a user doesn't have a bio they were not getting written to the user file
            if (user.getDescription() == null) {
                bio = "No bio";
            } else {
                bio = user.getDescription().replaceAll("\n", " ").replaceAll("\n\n"," ");
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                pw.println(userName + "\t"
                        + user.getLocation() + "\t"
                        + bio + "\t"
                        + user.getFollowersCount());
                pw.flush();

                foundUserHandles.add(userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void writeTweet(Status tweet, boolean retweet) throws IOException {
        File file = createFile(Configuration.getDataDirectory(), Configuration.getDataFile());

        if (!foundTweetIDS.contains(tweet.getId())) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                String tweetText;
                if (retweet) {
                    // tweet.getRetweetedStatus().getText() will cause RT @retweeted user not to be appended to the file
                    // but if we don't use the above, retweets get truncated due to the char limit
                    // so just prepend it manually
                    tweetText = "RT @" + tweet.getRetweetedStatus().getUser().getScreenName() + ": " +
                            tweet.getRetweetedStatus().getText();
                } else {
                    tweetText = tweet.getText();
                }

                pw.println(tweet.getId() + "\t"
                        + "@" + tweet.getUser().getScreenName() + "\t"
                        + tweetText.replaceAll("\n", " ") + "\t"
                        + tweet.getRetweetCount() + "\t"
                        + tweet.getCreatedAt());
                pw.flush();

                foundTweetIDS.add(tweet.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
