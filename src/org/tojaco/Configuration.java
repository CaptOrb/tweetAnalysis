package org.tojaco;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

// This Configuration class is for loading a configuration file with the necessary named parameters for the task
//(directory names, file names, Twitter keys, numeric settings, etc.).

public class Configuration {

    private static String API_KEY;
    private static String API_SECRET_KEY;
    private static String BEARER_TOKEN;
    private static String ACCESS_TOKEN;
    private static String ACCESS_TOKEN_SECRET;
    private static String DATA_DIRECTORY;
    private static String DATA_FILE;
    private static String USER_FILE;
    private static String GRAPH_DIRECTORY;
    private static String RTGRAPH_OUTPUT_FILE;
    private static String RTWEETEDGRAPH_OUTPUT_FILE;
    private static String USERS_TO_HASHTAGS;
    private static String HASHTAGS_TO_USERS;

    private static String LEXICON_FOLDER;

    private static String LEXICON_DATA_FILE;
    private static String LEXICON_FILE;
    private static String HASHTAG_TO_WORDS;
    private static String USER_QUALITIES;
    private static String STANCE_FILE;
    private static String HASHTAG_SUMMARY_FILE;
    private static String LANGUAGE;
    private static String BATCH_SIZE;
    private static String SLEEP_TIME;
    private static String[] HASHTAGS;
    private static String GEPHI_FILE_1;
    private static String GEPHI_HASHTAG_TO_USER_FILE;

    private static String MENTIONS_FILE;

    private static String MENTIONED_FILE;

    public static String getGEPHI_FILE_1() { return GEPHI_FILE_1; }

    public static String getGEPHI_HASHTAG_TO_USER_FILE() { return GEPHI_HASHTAG_TO_USER_FILE; }

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

    public static String getUserFile() {
        return USER_FILE;
    }

    public static String getGRAPH_DIRECTORY() {
        return GRAPH_DIRECTORY;
    }

    public static String getRTGRAPH_OUTPUT_FILE() {
        return RTGRAPH_OUTPUT_FILE;
    }

    public static String getRTWEETEDGRAPH_OUTPUT_FILE() {
        return RTWEETEDGRAPH_OUTPUT_FILE;
    }

    public static String getUSERS_TO_HASHTAGS() {
        return USERS_TO_HASHTAGS;
    }

    public static String getHASHTAGS_TO_USERS() { return HASHTAGS_TO_USERS; }

    public static String getLEXICON_FOLDER() {
        return LEXICON_FOLDER;
    }

    public static String getLEXICON_DATA_FILE() {
        return LEXICON_DATA_FILE;
    }

    public static String getLEXICON_FILE() { return LEXICON_FILE; }

    public static String getHASHTAG_TO_WORDS() { return HASHTAG_TO_WORDS; }

    public static String getUSER_QUALITIES() { return USER_QUALITIES; }

    public static String getSTANCE_FILE(){ return STANCE_FILE; }

    public static String getHASHTAG_SUMMARY_FILE() { return HASHTAG_SUMMARY_FILE; }

    public static String getMentionsFile() {
        return MENTIONS_FILE;
    }

    public static String getMentionedFile() {
        return MENTIONED_FILE;
    }

    public static String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public static String getACCESS_TOKEN_SECRET() {
        return ACCESS_TOKEN_SECRET;
    }

    public static String getLanguage() {
        return LANGUAGE;
    }

    public static int getSleepTime() {
        return Integer.parseInt(SLEEP_TIME);
    }

    public static int getBatchSize() {
        return Integer.parseInt(BATCH_SIZE);
    }

    public static String[] getHashTags() {
        return HASHTAGS;
    }

    public static String[] splitHashTags(String hashTags) {

        return hashTags.split(" ");
    }

    public static void getSettingsFromFile(String file, int mode) throws IOException {

        Properties properties = new Properties();
        FileInputStream customInputFile = null;
        InputStream classPathInputFile = null;

        if (mode == 1) {
            customInputFile = new FileInputStream(file);
        } else {
            classPathInputFile = Configuration.class.getClassLoader().getResourceAsStream(file);
        }
        try {
            if (customInputFile != null) {
                properties.load(customInputFile);
            } else if (classPathInputFile != null) {
                properties.load(classPathInputFile);
            } else {
                throw new RuntimeException("NO CONFIG FILE IN THE CLASS PATH OR GIVEN AS A COMMAND LINE ARGUMENT");
            }

            Configuration.API_KEY = properties.getProperty("API_KEY");
            Configuration.API_SECRET_KEY = properties.getProperty("APIKEY_SECRET");
            Configuration.BEARER_TOKEN = properties.getProperty("BEARER_TOKEN");
            Configuration.ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
            Configuration.ACCESS_TOKEN_SECRET = properties.getProperty("ACCESS_TOKEN_SECRET");
            Configuration.DATA_DIRECTORY = properties.getProperty("DATA_DIRECTORY");
            Configuration.DATA_FILE = properties.getProperty("DATA_FILE");
            Configuration.USER_FILE = properties.getProperty("USER_FILE");
            Configuration.GRAPH_DIRECTORY = properties.getProperty("GRAPH_OUTPUT_DIRECTORY");
            Configuration.RTGRAPH_OUTPUT_FILE = properties.getProperty("RTGRAPH_OUTPUT_FILE");
            Configuration.RTWEETEDGRAPH_OUTPUT_FILE = properties.getProperty("RETWEETGPHOUTPUTFILE");
            Configuration.USERS_TO_HASHTAGS = properties.getProperty("USERS_TO_HASHTAGS");
            Configuration.HASHTAGS_TO_USERS = properties.getProperty("HASHTAGS_TO_USERS");
            Configuration.LEXICON_FOLDER = properties.getProperty("LEXICON_FOLDER");
            Configuration.LEXICON_FILE = properties.getProperty("LEXICON_FILE");
            Configuration.LEXICON_DATA_FILE = properties.getProperty("LEXICON_DATA_FILE");
            Configuration.HASHTAG_SUMMARY_FILE = properties.getProperty("HASHTAG_SUMMARY_FILE");
            Configuration.HASHTAG_TO_WORDS = properties.getProperty("HASHTAG_TO_WORDS");
            Configuration.USER_QUALITIES = properties.getProperty("USER_QUALITIES");
            Configuration.STANCE_FILE = properties.getProperty("STANCE_FILE");
            Configuration.SLEEP_TIME = properties.getProperty("SLEEPTIMEMS");
            Configuration.LANGUAGE = properties.getProperty("LANGUAGE");
            Configuration.BATCH_SIZE = properties.getProperty("BATCH_SIZE");
            Configuration.HASHTAGS = splitHashTags(properties.getProperty("HASHTAGS"));
            Configuration.GEPHI_FILE_1 = properties.getProperty("GEPHI_FILE_1");
            Configuration.GEPHI_HASHTAG_TO_USER_FILE = properties.getProperty("GEPHI_HASHTAG_TO_USER_FILE");
            Configuration.MENTIONS_FILE = properties.getProperty("MENTIONS_FILE");
            Configuration.MENTIONED_FILE= properties.getProperty("MENTIONED_FILE");

        } catch (IOException e) {
            throw new RuntimeException("Could not read properties from file:", e);
        }
    }

    public static TwitterFactory getTwitterFactory(){

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Configuration.getAPIKey())
                .setOAuthConsumerSecret(Configuration.getAPISecretKey())
                .setOAuthAccessToken(Configuration.getACCESS_TOKEN())
                .setOAuthAccessTokenSecret(Configuration.getACCESS_TOKEN_SECRET())
                .setTweetModeExtended(true);

        return new TwitterFactory(cb.build());
    }

    public static TwitterStream getTwitterStream(){

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Configuration.getAPIKey())
                .setOAuthConsumerSecret(Configuration.getAPISecretKey())
                .setOAuthAccessToken(Configuration.getACCESS_TOKEN())
                .setOAuthAccessTokenSecret(Configuration.getACCESS_TOKEN_SECRET())
                .setTweetModeExtended(true)
                .setJSONStoreEnabled(true);
                return new TwitterStreamFactory(cb.build()).getInstance();
    }
}