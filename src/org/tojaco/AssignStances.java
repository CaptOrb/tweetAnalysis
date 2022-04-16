package org.tojaco;

import org.tojaco.Graph.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AssignStances {
    VertexCreator<TwitterUser> vertexCreator = new CreateUserVertex();

    public void determineProAntiVaxEvangelists(GraphElements graphElements, DirectedGraph<TwitterUser, TwitterUser> graph, File file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Vertex<TwitterUser> vertexError = null;
            try {
                while ((line = br.readLine()) != null) {

                    String[] lineContents = line.split(" ");

                    Vertex<TwitterUser> vertex = graphElements.getVertex(lineContents[0], vertexCreator);

                    vertexError = vertex;

                    if (lineContents[1].equals("anti")){
                        if (vertex.getLabel().getUserHandle().equals(lineContents[0])) {
                            vertex.setStance(-1000);
                            //twitterUsers.getUserStances().putIfAbsent(vertex, -1000);
                        }
                    } else if (lineContents[1].equals("pro") ){
                        if (vertex.getLabel().getUserHandle().equals(lineContents[0])) {
                            vertex.setStance(1000);
                            //twitterUsers.getUserStances().putIfAbsent(vertex, 1000);
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
