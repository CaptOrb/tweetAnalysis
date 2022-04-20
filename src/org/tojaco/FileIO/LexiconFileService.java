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

                // tag will be the source vertex
                String word = lineContents[1];
                String feature;

                for (int i = 2; i < lineContents.length; i++) {

                    feature = lineContents[i].replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", "");

                    lexicon.add(word + "\t" + feature);
                }
            }

        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return lexicon;
    }
}
