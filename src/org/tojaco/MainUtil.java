package org.tojaco;
import org.tojaco.Sprints.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainUtil {
    public static void showProgramOptions(File dataFile) throws IOException {

        System.out.println("Enter 1, 2, 3, 4, 5, 6, 7 or 8 " +
                "\n1. Search for Tweets using search API (Sprint 1)" +
                "\n2. Search for Tweets using the streaming API (Sprint 2)" +
                "\n3. Build a retweet Graph (Sprint 3)" +
                "\n4. Assign stances to Tweets (Sprint 4)" +
                "\n5. Assign stances to HashTags (Sprint 5)" +
                "\n6. Get the gist of a Hashtag (Sprint 6)" +
                "\n7. Assign a \"psychological profile\" to each user in the dataset  (Sprint 7)" +
                "\n8. Generate .gdf files for Gephi  (Sprint 8)" +
                "\nOr enter -1 to quit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();


        switch (option) {
            case 1:
                Sprint1 sprint1 = new Sprint1();
                sprint1.sprint1(dataFile);
                break;
            case 2:
                Sprint2 sprint2 = new Sprint2();
                sprint2.sprint2(dataFile);
                break;
            case 3:
                Sprint3 sprint3 = new Sprint3();
                sprint3.sprint3(dataFile);
                break;
            case 4:
                Sprint4 sprint4 = new Sprint4();
                sprint4.sprint4(dataFile);
                break;

            case 5:
                Sprint5 sprint5 = new Sprint5();
                sprint5.sprint5(dataFile);
                break;

            case 6:
                Sprint6 sprint6 = new Sprint6();
                sprint6.sprint6(dataFile);
                break;

            case 7:
                Sprint7 sprint7 = new Sprint7();
                sprint7.sprint7(dataFile);
                break;

            case 8:
                Sprint8 sprint8 = new Sprint8();
                sprint8.sprint8(dataFile);
                break;
        }
    }
}
