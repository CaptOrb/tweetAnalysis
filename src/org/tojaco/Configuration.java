package org.tojaco;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

// This org.tojaco.Configuration class is for loading a configuration file with the necessary named parameters for the task
//(directory names, file names, Twitter keys, numeric settings, etc.).

public class Configuration {

    private String API_KEY;
    private String API_SECRET_KEY;
    private String BEARER_TOKEN;
    private String ACCESS_TOKEN;
    private String ACCESS_TOKEN_SECRET;
    private String DATA_DIRECTORY;
    private String DATA_FILE;
    private String USER_FILE;
    private String GRAPH_DIRECTORY;
    private String RTGRAPH_OUTPUT_FILE;
    private String RTWEETEDGRAPH_OUTPUT_FILE;
    private String STANCE_FILE;
    private String LANGUAGE;
    private String BATCH_SIZE;
    private String SLEEP_TIME;
    private String[] HASHTAGS;

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

    public String getGRAPH_DIRECTORY() {
        return GRAPH_DIRECTORY;
    }

    public String getRTGRAPH_OUTPUT_FILE() {
        return RTGRAPH_OUTPUT_FILE;
    }

    public String getRTWEETEDGRAPH_OUTPUT_FILE() {
        return RTWEETEDGRAPH_OUTPUT_FILE;
    }

    public String getSTANCE_FILE(){ return STANCE_FILE; }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public String getACCESS_TOKEN_SECRET() {
        return ACCESS_TOKEN_SECRET;
    }

    public String getLanguage() {
        return LANGUAGE;
    }

    public int getSleepTime() {
        return Integer.parseInt(SLEEP_TIME);
    }

    public int getBatchSize() {
        return Integer.parseInt(BATCH_SIZE);
    }

    public String[] getHashTags() {
        return HASHTAGS;
    }

    public String[] splitHashTags(String hashTags) {

        return hashTags.split(" ");
    }

    public void getSettingsFromFile(Configuration configuration, String file, int mode) throws IOException {

        Properties properties = new Properties();
        FileInputStream customInputFile = null;
        InputStream classPathInputFile = null;

        if (mode == 1) {
            customInputFile = new FileInputStream(file);
        } else {
            classPathInputFile = configuration.getClass().getClassLoader().getResourceAsStream(file);
        }
        try {
            if (customInputFile != null) {
                properties.load(customInputFile);
            } else if (classPathInputFile != null) {
                properties.load(classPathInputFile);
            } else {
                throw new RuntimeException("NO CONFIG FILE IN THE CLASS PATH OR GIVEN AS A COMMAND LINE ARGUMENT");
            }

            configuration.API_KEY = properties.getProperty("API_KEY");
            configuration.API_SECRET_KEY = properties.getProperty("APIKEY_SECRET");
            configuration.BEARER_TOKEN = properties.getProperty("BEARER_TOKEN");
            configuration.ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
            configuration.ACCESS_TOKEN_SECRET = properties.getProperty("ACCESS_TOKEN_SECRET");
            configuration.DATA_DIRECTORY = properties.getProperty("DATA_DIRECTORY");
            configuration.DATA_FILE = properties.getProperty("DATA_FILE");
            configuration.USER_FILE = properties.getProperty("USER_FILE");
            configuration.GRAPH_DIRECTORY = properties.getProperty("GRAPH_OUTPUT_DIRECTORY");
            configuration.RTGRAPH_OUTPUT_FILE = properties.getProperty("RTGRAPH_OUTPUT_FILE");
            configuration.RTWEETEDGRAPH_OUTPUT_FILE = properties.getProperty("RETWEETGPHOUTPUTFILE");
            configuration.STANCE_FILE = properties.getProperty("STANCE_FILE");
            configuration.SLEEP_TIME = properties.getProperty("SLEEPTIMEMS");
            configuration.LANGUAGE = properties.getProperty("LANGUAGE");
            configuration.BATCH_SIZE = properties.getProperty("BATCH_SIZE");
            configuration.HASHTAGS = splitHashTags(properties.getProperty("HASHTAGS"));
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
                .setOAuthAccessTokenSecret(configuration.getACCESS_TOKEN_SECRET())
                .setTweetModeExtended(true);

        return new TwitterFactory(cb.build());
    }

    public TwitterStream getTwitterStream(Configuration configuration){

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(configuration.getAPIKey())
                .setOAuthConsumerSecret(configuration.getAPISecretKey())
                .setOAuthAccessToken(configuration.getACCESS_TOKEN())
                .setOAuthAccessTokenSecret(configuration.getACCESS_TOKEN_SECRET())
                .setTweetModeExtended(true)
                .setJSONStoreEnabled(true);
                return new TwitterStreamFactory(cb.build()).getInstance();
    }
}