import twitter4j.Status;
import twitter4j.User;

import java.io.*;

public class TwitterFileService {
    public void writeUser(User user) throws IOException {

        // needs to be modified to use correct directory.
        File file = new File("users.txt");

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
            }
        }
    }

    public void writeToFile(Status tweet) throws IOException {
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

}
