package org.tojaco;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        try {
            // user chooses to provide a config file as a command arg
            if (args.length == 1) {
                configuration.getSettingsFromFile(configuration, args[0], 1);
            } else {
                // config file is on class path
                configuration.getSettingsFromFile(configuration, "config_file", 0);
            }

            File dataFile = new File(configuration.getDataDirectory(), configuration.getDataFile());

           MainUtil.showProgramOptions(configuration, dataFile);

            //ReadHashtags readHashtags = new ReadHashtags();
            //readHashtags.readHashTagsFromFile(dataFile);

           // RetweetFileService<String> rtf = new RetweetFileService();
            // rtf.loadDataFromInputFile(dataFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

