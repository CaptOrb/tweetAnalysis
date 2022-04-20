package org.tojaco.FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LexiconFileService {


    public ArrayList<String> readLexiconFile(File file) {
        ArrayList<String> lexicon = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineContents = line.split(" ");
                try {

                    String term = lineContents[1];
                    String test = lineContents[2];
                    String test2 = lineContents[3];

                    System.out.println("1 is" + term +  " 2 is " + test + " " + "3 is "+ test2);



                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//
                } catch (Exception ignored) {
                }
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return lexicon;
    }
}
