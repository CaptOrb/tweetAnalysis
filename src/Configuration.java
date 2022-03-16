import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Configuration {

    private String API_KEY;
    private String API_SECRET_KEY;
    private String BEARER_TOKEN;
    private String DATA_DIRECTORY;
    private String DATA_FILE;
    private String TEMP_HASH_TAGS;
    private LinkedList<String> HASHTAGS = new LinkedList<>();;

    public String getAPIKey() {
        return API_KEY;
    }

    public String getAPISecretKey() {
        return API_SECRET_KEY;
    }

    public String getBearerToken() {
        return BEARER_TOKEN;
    }

    public String getDataDirectory() {
        return DATA_DIRECTORY;
    }

    public String getDataFile() {
        return DATA_FILE;
    }

    public LinkedList<String> getHashTags() {
        return HASHTAGS;
    }

    // our config file has hashtags separated by spaces
    // add each one to a list
    public void putHashTagsIntoList(String hashTags) {
        String[] listHashTags = hashTags.split(" ");

        for(String hashtag: listHashTags) {
            HASHTAGS.add(hashtag);
        }
    }


    public void getSettingsFromFile() throws IOException {

        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config_file");

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

            TEMP_HASH_TAGS = properties.getProperty("HASHTAGS");

            putHashTagsIntoList(TEMP_HASH_TAGS);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties from file:.", e);
        }

    }

    // JUST FOR TESTING

    public static void main(String[] args){

        Configuration initalConfig = new Configuration();
        try {
            initalConfig.getSettingsFromFile();

            System.out.println(initalConfig.getHashTags().get(0));

			/*ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(initalConfig.getAPIKey())
			  .setOAuthConsumerSecret(initalConfig.getAPISecretKey())
			  .setOAuthAccessToken(initalConfig.getBearerToken());
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();*/
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}