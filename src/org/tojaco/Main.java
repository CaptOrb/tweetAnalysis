package org.tojaco;
import org.tojaco.FileIO.ReadHashtags;

import java.io.*;
import java.util.ArrayList;

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

           // MainUtil.showProgramOptions(configuration, dataFile);
            ReadHashtags readHashtags = new ReadHashtags();
            readHashtags.readRetweetsIntoSet(dataFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

