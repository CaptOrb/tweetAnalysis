package org.tojaco;

import org.tojaco.Graph.Vertex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class AssignStances {

    public void determineProAntiVaxEvangelists(Map<Vertex<String>, Integer> evangelists, File file){

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            System.out.println("before loop");
            int i = 0;
            int k = 1;
            try {
                for (Vertex<String> vertex : evangelists.keySet()) {
                    i++;
                    if (i == 1 || i == 50) {
                        System.out.println("in middle of loop " + i);
                    }
                    while ((line = br.readLine()) != null) {
                        String[] lineContents = line.split(" ");
                        if (lineContents[1].equals("anti")) {
                            if (vertex.getLabel().equals(lineContents[0])) {
                                vertex.setStance(-1000);
                                System.out.println(vertex + " " + vertex.getStance());
                            }
                        } else if (lineContents[1].equals("pro")) {
                            if (vertex.getLabel().equals(lineContents[0])) {
                                vertex.setStance(1000);
                                System.out.println(vertex + " " + vertex.getStance());
                            }
                        } //if it equals idk then skip
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Skipped a user" + k++ + " time(s)");
            }
            System.out.println("outside loop");
        } catch (IOException | NullPointerException | NumberFormatException fnfe) {
            fnfe.printStackTrace();
        }
    }
}
