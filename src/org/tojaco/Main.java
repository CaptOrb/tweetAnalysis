package org.tojaco;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        try {
            // user chooses to provide a config file as a command arg
            if (args.length == 1) {
                Configuration.getSettingsFromFile(args[0], 1);
            } else {
                // config file is on class path
                Configuration.getSettingsFromFile("config_file", 0);
            }

            File dataFile = new File(Configuration.getDataDirectory(), Configuration.getDataFile());
            MainUtil.showProgramOptions(dataFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

