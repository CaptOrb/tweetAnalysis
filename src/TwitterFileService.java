import twitter4j.Status;
import twitter4j.User;

import java.io.*;

public class TwitterFileService {

    public void writeUser(User user, Configuration configuration) throws IOException {

        // needs to be modified to use correct directory.
        File file = new File(configuration.getUserFile());

        // only creates a new file if it doesn't exist
        file.createNewFile();
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)  // classic way of reading a file line-by-line
                if (line.equals("@" + user.getScreenName() + "\t"
                        + user.getLocation() + "\t"
                        + user.getDescription().replaceAll("\n", " ") + "\t"
                        + user.getFollowersCount())) {

                    found = true;

                    break;  // if the text is present, we do not have to read the rest after all
                }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        if (!found) {
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
    }

    private boolean isTweetInFile(Status tweet, String fileName) {

        try {

            File file = new File(fileName);
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

        // needs to be modified to use correct directory.
        File file = new File(configuration.getDataFile());

        // only creates a new file if it doesn't exist
        file.createNewFile();

        if (!isTweetInFile(tweet, file.getAbsolutePath())) {
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
    }

}
