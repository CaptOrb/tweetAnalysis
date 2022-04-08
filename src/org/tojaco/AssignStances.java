package org.tojaco;

import org.tojaco.Graph.Vertex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AssignStances {

    public void determineProAntiVaxEvangelists(TwitterUsers<String> twitterUsers, File file) {

        int posFound = 0;
        int negFound = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Vertex<String> vertexError = null;
            try {
                while ((line = br.readLine()) != null) {

                    String[] lineContents = line.split(" ");

                    Vertex<String> vertex = twitterUsers.getVertex(lineContents[0]);

                    vertexError = vertex;

                    if (lineContents[1].equals("anti")){
                            //&& negFound < 56) {
                        if (vertex.getLabel().equals(lineContents[0])) {
                            vertex.setStance(-1000);
                            negFound++;
                        }
                    } else if (lineContents[1].equals("pro") ){
                            //&& posFound < 56) {
                        if (vertex.getLabel().equals(lineContents[0])) {
                            vertex.setStance(1000);
                            posFound++;
                        }
                    } //if it equals idk then skip
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Skipped a user " + vertexError);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | NullPointerException | NumberFormatException fnfe) {
            fnfe.printStackTrace();
        }
    }
}
