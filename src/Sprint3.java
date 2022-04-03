import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Sprint3 {

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

            FindRetweets findRetweets = new FindRetweets();

            if (dataFile.exists()) {
                findRetweets.initialiseRetweets(dataFile);
            }
            // graph for using implemented methods on
            // see RetweetGraph.java for description of public methods
            RetweetGraph<String> rtGraph = findRetweets.toPutIntoHashMap(configuration);

            int option = 0;

            while(option!=-1) {

                System.out.println("Enter 1, 2, or 3. " +
                        "\n1. Add new arc to graph" +
                        "\n2. Query whether there is an arc between two labels" +
                        "\n3. Request list of all vertices that a given vertex points to" +
                        "\nOr enter -1 to quit");
                Scanner scanner = new Scanner(System.in);
                option = scanner.nextInt();
                if (option == 1) {
                    System.out.print("Enter start vertex, end vertex and numeric label, separated by spaces: ");
                    String[] newArc = new String[3];
                    for (int i = 0; i < 3; i++) {
                        newArc[i] = scanner.next();
                    }

                    Vertex<String> start = new Vertex<String>(newArc[0]);
                    Vertex<String> end = new Vertex<String>(newArc[1]);
                    Arc<String> arc = new Arc<String>(end, Integer.valueOf(newArc[2]));
                    rtGraph.addArc(start, arc);

                    System.out.print("Vertex with " + start.toString() + " and arc with " + end.toString() + " was added to the graph.\n");
                }
                if (option == 2) {

                    System.out.print("Enter first and second vertex (remember to include @ symbol): ");
                    String[] vertices = new String[2];

                    for (int i = 0; i < 2; i++) {
                        vertices[i] = scanner.next();
                    }

                    Vertex<String> vertex1 = findRetweets.getVertex(vertices[0], rtGraph.getAllVerticesInGraph());

                    Vertex<String> vertex2 = findRetweets.getVertex(vertices[1], rtGraph.getAllVerticesInGraph());

                    boolean hasArc = rtGraph.hasArcBetween(vertex1, vertex2);

                    System.out.println("There is an arc between " + vertices[0] + " and "
                            + vertices[1] + ": " + hasArc + ", and the label between them is " + rtGraph.getLabelBetweenVertices(vertex1, vertex2));
                }
                if (option == 3) {
                    System.out.print("Enter a vertex to find its outgoing arcs:  (not yet implemented)\n");

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
