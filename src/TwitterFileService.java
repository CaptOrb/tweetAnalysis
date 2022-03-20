import twitter4j.Status;
import twitter4j.User;

import java.io.*;

//This class generates a data file with the tweets that we gathered,
//saving the tweets in this format:status_id <tab> @userhandle <tab> tweet text <tab> num_retweets <tab> timestamp <newline>
//It also stores the @userhandle, the location, the short biography, number of followers of the user. One user is stored per line, using tabs to delimit the fields of the entry.
//The code checks that the user or tweet is not in the file and so doesn't store duplicates
//The files are opened and written to in append mode
public class TwitterFileService {

    boolean isUserInFile(User user, File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                String id = String.valueOf(user.getId());
                if (lineContents[0].equals(id)) {
                    return true;
                }
            }
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }
        return false;
    }

    public void writeUser(User user, Configuration configuration) throws IOException {
        // needs to be modified to use correct directory.
        File file = new File(configuration.getDataDirectory(), configuration.getUserFile());

        // only creates a new file if it doesn't exist
        file.createNewFile();

        if (file.exists() || file.getParentFile().mkdirs()) {

            if (!isUserInFile(user, file)) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                    pw.println("@" + user.getScreenName() + "\t"
                            + user.getLocation() + "\t"
                            + user.getDescription().replaceAll("\n", " ") + "\t"
                            + user.getFollowersCount());
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new RuntimeException("Couldn't create / get file in the directory");
        }
    }

    private boolean isTweetInFile(Status tweet, File file) {

        try {

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split("\t");
                String id = String.valueOf(tweet.getId());
                if (lineContents[0].equals(id)) {
                    return true;
                }
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return false;
    }

    public void writeTweet(Status tweet, boolean retweet, Configuration configuration) throws IOException {

        File file = new File(configuration.getDataDirectory(), configuration.getDataFile());

        if (file.exists() || file.getParentFile().mkdirs()) {

            // only creates a new file if it doesn't exist
            file.createNewFile();

            if (!isTweetInFile(tweet, file)) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                    String tweetText;
                    if (retweet)
                        tweetText = tweet.getRetweetedStatus().getText();
                    else
                        tweetText = tweet.getText();

                    pw.println(tweet.getId() + "\t"
                            + "@" + tweet.getUser().getScreenName() + "\t"
                            + tweetText.replaceAll("\n", " ") + "\t"
                            + tweet.getRetweetCount() + "\t"
                            + tweet.getCreatedAt());
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new RuntimeException("Couldn't create / get file in the directory");
        }
    }

}
