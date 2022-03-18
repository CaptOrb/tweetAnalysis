import twitter4j.*;

import java.io.*;
import java.util.HashSet;
import java.util.List;

public class GrabTweets {

    // This file is just for testing so far
    // we can delete it / modify if needs be ;)

    HashSet<Long> foundTweets = new HashSet<>();

    // experimental
    // TEST CODE FROM STACK OVERFLOW
    // TO TRY AND AVOID EXCEEDING RATE LIMITS
    private void handleRateLimit(RateLimitStatus rateLimitStatus) {
          if (rateLimitStatus != null) {
            int remaining = rateLimitStatus.getRemaining();
            int resetTime = rateLimitStatus.getSecondsUntilReset();
            int sleep;
            if (remaining == 0) {
                sleep = resetTime + 1; //adding 1 more seconds
            } else {
                sleep = (resetTime / remaining) + 1; //adding 1 more seconds
            }

            try {
                Thread.sleep(Math.max(sleep * 1000, 0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // CAUTION: will keep running for a very long time.
    // don't run for too long or the API KEY's might get suspended ;)
    public void grabSomeTweets(TwitterFactory tf, Configuration configuration) throws IOException {

        String[] hashTags = configuration.getHashTags();

        //now have string array hashTags full of hash tags :-)

        for (int i = 0; i < hashTags.length; i++) {
            try {
                Query query = new Query(hashTags[i]);
                query.setCount(configuration.getBatchSize());
                query.setResultType(Query.ResultType.recent);
                query.setLang(configuration.getLanguage());

                QueryResult result;
                do {
                    result = tf.getInstance().search(query);
                    handleRateLimit(result.getRateLimitStatus());
                    List<Status> tweets = result.getTweets();
                    for (Status tweet : tweets) {
                        if (!(foundTweets.contains(tweet.getId()))) {
//                            System.out.println("@"
//                                    + tweet.getUser().getScreenName()
//                                    + "\tTWEET TEXT: "
//                                    + tweet.getText().replaceAll("\n", "")
//                                    + "\tTWEET ID: " + tweet.getId()
//                                    + "\tNUM RETWEETS: " + tweet.getRetweetCount()
//                                    + "\tTime stamp: " + tweet.getCreatedAt());

                            writeToFile(tweet);
                            foundTweets.add(tweet.getId());
                        }
                    }

                } while ((query = result.nextQuery()) != null);

            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to search tweets: " + te.getMessage());
                System.exit(-1);
            }
        }
        System.exit(0);
    }

    public void writeToFile(Status tweet) throws IOException {
        File file = new File("s.txt");  // this is a file handle, s.txt may or may not exist
        boolean found=false;  // flag for target txt being present
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null)  // classic way of reading a file line-by-line
                if(line.equals("@"
                        + tweet.getUser().getScreenName()
                        + "\tTWEET TEXT: "
                        + tweet.getText().replaceAll("\n", " ")
                        + "\tTWEET ID: " + tweet.getId()
                        + "\tNUM RETWEETS: " + tweet.getRetweetCount()
                        + "\tTime stamp: " + tweet.getCreatedAt())){
                    found=true;
                    break;  // if the text is present, we do not have to read the rest after all
                }
        } catch(FileNotFoundException fnfe){}

        if(!found){  // if the text is not found, it has to be written
            try(PrintWriter pw=new PrintWriter(new FileWriter(file,true))){  // it works with
                // non-existing files too
                pw.println("@"
                        + tweet.getUser().getScreenName()
                        + "\tTWEET TEXT: "
                        + tweet.getText().replaceAll("\n", " ")
                        + "\tTWEET ID: " + tweet.getId()
                        + "\tNUM RETWEETS: " + tweet.getRetweetCount()
                        + "\tTime stamp: " + tweet.getCreatedAt());
            }
        }
    }

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        try {
            // user chooses to provide a config file as a command arg
            if(args.length == 1) {
                configuration.getSettingsFromFile(configuration, args[0], 1);
            } else {
                // config file is on class path
                configuration.getSettingsFromFile(configuration, "config_file", 0);
            }

            TwitterFactory tf = configuration.getTwitterFactory(configuration);

            GrabTweets grabTweets = new GrabTweets();

            grabTweets.grabSomeTweets(tf, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

