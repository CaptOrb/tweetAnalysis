import twitter4j.Status;
import twitter4j.User;

import java.io.*;

public class TwitterFileService {
    public void writeTweet(Status tweet) throws IOException {
        File file = new File("s.txt");  // this is a file handle, s.txt may or may not exist
        boolean found = false;  // flag for target txt being present
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)  // classic way of reading a file line-by-line
                if (line.equals(tweet.getId() + "\t"
                        + "@" + tweet.getUser().getScreenName() + "\t"
                        + tweet.getText().replaceAll("\n", " ") + "\t"
                        + tweet.getRetweetCount() + "\t"
                        + tweet.getCreatedAt())

                        || tweet.getRetweetedStatus() != null && line.equals(tweet.getId() + "\t"
                        + "@" + tweet.getUser().getScreenName() + "\t"
                        + tweet.getRetweetedStatus().getText().replaceAll("\n", " ") + "\t"
                        + tweet.getRetweetCount() + "\t"
                        + tweet.getCreatedAt())) {
                    found = true;

                    break;  // if the text is present, we do not have to read the rest after all
                }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        if (!found) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {

                if (tweet.getRetweetedStatus() != null) {
                    pw.println(tweet.getId() + "\t"
                            + "@" + tweet.getUser().getScreenName() + "\t"
                            + tweet.getRetweetedStatus().getText().replaceAll("\n", " ") + "\t"
                            + tweet.getRetweetCount() + "\t"
                            + tweet.getCreatedAt());

                } else {
                    pw.println(tweet.getId() + "\t"
                            + "@" + tweet.getUser().getScreenName() + "\t"
                            + tweet.getText().replaceAll("\n", " ") + "\t"
                            + tweet.getRetweetCount() + "\t"
                            + tweet.getCreatedAt());
                }

            }
        }
    }

    public void writeUser(User user) throws IOException {
        File file = new File("users.txt");
        //try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        //} catch (FileNotFoundException fnfe) {
        //    fnfe.printStackTrace();
        //}
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                pw.println("@" + user.getScreenName() + "\t"
                        + user.getLocation() + "\t"
                        + user.getDescription().replaceAll("\n", " ") + "\t"
                        + user.getFollowersCount());
            }

        }
    }
