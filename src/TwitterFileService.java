import twitter4j.Status;
import twitter4j.User;

import java.io.*;

public class TwitterFileService {
    public void writeTweet(Status tweet) throws IOException {
        File file = new File("s.txt");  // this is a file handle, s.txt may or may not exist
        boolean found=false;  // flag for target txt being present
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null)  // classic way of reading a file line-by-line
                if(line.equals(tweet.getId() + "\t"
                        + "@" + tweet.getUser().getScreenName() +"\t"
                        + tweet.getText().replaceAll("\n", " ") +"\t"
                        + tweet.getRetweetCount() +"\t"
                        + tweet.getCreatedAt())){
                    found=true;
                    break;  // if the text is present, we do not have to read the rest after all
                }
        } catch(FileNotFoundException fnfe){}

        if(!found){  // if the text is not found, it has to be written
            try(PrintWriter pw=new PrintWriter(new FileWriter(file,true))){  // it works with
                // non-existing files too
                pw.println(tweet.getId() + "\t"
                        + "@" + tweet.getUser().getScreenName() +"\t"
                        + tweet.getText().replaceAll("\n", " ") +"\t"
                        + tweet.getRetweetCount() +"\t"
                        + tweet.getCreatedAt());
            }
        }
    }

    public void writeUser(User user){
        // TODO
    }
}
