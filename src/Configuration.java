import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Configuration {

    private String API_KEY;
    private String API_SECRET_KEY;
    private String BEARER_TOKEN;
    private String ACCESS_TOKEN;
    private String ACCESS_TOKEN_SECRET;
    private String DATA_DIRECTORY;
    private String DATA_FILE;
    private String USER_FILE;
    private String LANGUAGE;
    private String BATCH_SIZE;
    private String SLEEP_TIME;
    private final LinkedList<String> HASHTAGS = new LinkedList<>();

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

    public String getUserFile() {
        return USER_FILE;
    }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public String getACCESS_TOKEN_SECRET() {
        return ACCESS_TOKEN_SECRET;
    }

    public String getLanguage() {
        return LANGUAGE;
    }

    public String getSleepTime() {
        return SLEEP_TIME;
    }

    public String getBatchSize() {
        return BATCH_SIZE;
    }

    public LinkedList<String> getHashTags() {
        return HASHTAGS;
    }

    // our config file has hashtags separated by spaces
    // add each one to a list
    public void putHashTagsIntoList(String hashTags, Configuration configuration) {
        String[] listHashTags = hashTags.split(" ");

        configuration.HASHTAGS.addAll(Arrays.asList(listHashTags));
    }

    public void getSettingsFromFile(Configuration configuration) throws IOException {

        Properties properties = new Properties();

        InputStream inputStream = configuration.getClass().getClassLoader().getResourceAsStream("config_file");

        if (inputStream == null) {
            throw new RuntimeException("Couldn't find the config file in the classpath.");
        }
        try {
            properties.load(inputStream);

            configuration.API_KEY = properties.getProperty("API_KEY");
            configuration.API_SECRET_KEY = properties.getProperty("APIKEY_SECRET");
            configuration.BEARER_TOKEN = properties.getProperty("BEARER_TOKEN");
            configuration.ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
            configuration.ACCESS_TOKEN_SECRET = properties.getProperty("ACCESS_TOKEN_SECRET");
            configuration.DATA_DIRECTORY = properties.getProperty("DATA_DIRECTORY");
            configuration.DATA_FILE = properties.getProperty("DATA_FILE");
            configuration.USER_FILE = properties.getProperty("USER FILE");
            configuration.SLEEP_TIME = properties.getProperty("SLEEPTIMEMS");
            configuration.LANGUAGE = properties.getProperty("LANGUAGE");
            configuration.BATCH_SIZE = properties.getProperty("BATCH_SIZE");
            String TEMP_HASH_TAGS = properties.getProperty("HASHTAGS");

            putHashTagsIntoList(TEMP_HASH_TAGS, configuration);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties from file:", e);
        }
    }

    public TwitterFactory getTwitterFactory(Configuration configuration){

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(configuration.getAPIKey())
                .setOAuthConsumerSecret(configuration.getAPISecretKey())
                .setOAuthAccessToken(configuration.getACCESS_TOKEN())
                .setOAuthAccessTokenSecret(configuration.getACCESS_TOKEN_SECRET());

        return new TwitterFactory(cb.build());
    }
}