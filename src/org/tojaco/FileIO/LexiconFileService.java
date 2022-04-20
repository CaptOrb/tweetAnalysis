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

                    // tag will be the source vertex
                    String tag = lineContents[1];
                    String destVertex;

                    for (int i = 2; i < lineContents.length; i++) {
                        destVertex = lineContents[i];
                        destVertex = destVertex.replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", "").replaceAll(" ", "\t");

                        //test will be the dest vertex

                        // then add the combined tag and test to the arraylist.

                        System.out.print(" " + i + " is " + destVertex);

                        lexicon.add(tag + destVertex);
                    }
                    System.out.println("\n");

                } catch (Exception e) {}
            }
        } catch (IOException | NullPointerException fnfe) {
            fnfe.printStackTrace();
        }
        return lexicon;
    }
}
