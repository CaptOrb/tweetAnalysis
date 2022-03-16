import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Configuration {

    private static String API_KEY;
    private static String API_SECRET_KEY;
    private static String BEARER_TOKEN;
    private static String ACCESS_TOKEN;
    private static String ACCESS_TOKEN_SECRET;
    private static String DATA_DIRECTORY;
    private static String DATA_FILE;
    private static String TEMP_HASH_TAGS;
    private static LinkedList<String> HASHTAGS = new LinkedList<>();;

    public static String getAPIKey() {
        return API_KEY;
    }

    public static String getAPISecretKey() {
        return API_SECRET_KEY;
    }

    public static String getBearerToken() {
        return BEARER_TOKEN;
    }

    public static String getDataDirectory() {
        return DATA_DIRECTORY;
    }

    public static String getDataFile() {
        return DATA_FILE;
    }

    public static String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public static String getACCESS_TOKEN_SECRET() {
        return ACCESS_TOKEN_SECRET;
    }

    public static LinkedList<String> getHashTags() {
        return HASHTAGS;
    }

    // our config file has hashtags separated by spaces
    // add each one to a list
    public static void putHashTagsIntoList(String hashTags) {
        String[] listHashTags = hashTags.split(" ");

        for(String hashtag: listHashTags) {
            HASHTAGS.add(hashtag);
        }
    }


    public static void getSettingsFromFile() throws IOException {

        Properties properties = new Properties();

        InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("config_file");

        if (inputStream == null) {
            throw new RuntimeException("Couldn't find the config file in the classpath.");
        }
        try {
            properties.load(inputStream);

            API_KEY = properties.getProperty("API_KEY");
            API_SECRET_KEY = properties.getProperty("APIKEY_SECRET");
            BEARER_TOKEN = properties.getProperty("BEARER_TOKEN");
            DATA_DIRECTORY = properties.getProperty("DATA_DIRECTORY");
            DATA_FILE = properties.getProperty("DATA_FILE");
            ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
            ACCESS_TOKEN_SECRET = properties.getProperty("ACCESS_TOKEN_SECRET");


            TEMP_HASH_TAGS = properties.getProperty("HASHTAGS");

            putHashTagsIntoList(TEMP_HASH_TAGS);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties from file:.", e);
        }
    }

    public static void initConfig(){

        System.out.println(Configuration.getAPIKey());
        System.out.println(Configuration.getAPISecretKey());
        System.out.println(Configuration.getACCESS_TOKEN());
        System.out.println(Configuration.getACCESS_TOKEN_SECRET());


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Configuration.getAPIKey())
                .setOAuthConsumerSecret(Configuration.getAPISecretKey())
                .setOAuthAccessToken(Configuration.getACCESS_TOKEN())
                .setOAuthAccessTokenSecret(Configuration.getACCESS_TOKEN_SECRET());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            Query query = new Query("test");
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }

            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }

    // JUST FOR TESTING

    public static void main(String[] args){



        try {
            Configuration.getSettingsFromFile();
            Configuration.initConfig();

            System.out.println(Configuration.getHashTags().get(0));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}