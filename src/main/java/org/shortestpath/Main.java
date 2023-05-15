package org.shortestpath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = "";
        String von = "A0.06 Mensa";
        String zu = "F.4.05 Lesezone";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ShortestPathManager shortestPathManager = new ShortestPathManager();
        if(! shortestPathManager.readProfile()){
            System.out.println("Hello! Please set up your profile: ");
            shortestPathManager.setProfile(askAbout("use an elevator"), askAbout("use the stairs"), askAbout("go outside"), askAbout("receive an alternate path, if your profile does not deliver a result?"), askAlgorithm());

        }

        while(! input.equalsIgnoreCase("QUIT")){
            System.out.println("Please enter what you would like to do " +
                    "(help, read, print, path, profile, quit):\n");
            input = reader.readLine();

            if(input.equalsIgnoreCase("HELP")){
                System.out.println("READ: \t\tRead the building into the program from a prepared file.");
                System.out.println("PRINT:\t\tPrint the building plan.");
                System.out.println("PATH:\t\tCall the shortest path program. You will have to enter the start and end point, and can choose between using your own profile or setting up a new one. " +
                        "\nYour profile preferences will not be changed.");
                System.out.println("PROFILE:\tSet up your profile preferences, which guide your path results.");
                System.out.println("QUIT:\t\tQuit the program.");
            }
            else if (input.equalsIgnoreCase("PROFILE")) {
                shortestPathManager.setProfile(askAbout("use an elevator"), askAbout("use the stairs"), askAbout("go outside"), askAbout("receive an alternate path, if your profile does not deliver a result?"), askAlgorithm());
            }
            else if (input.equalsIgnoreCase("READ")) {
                shortestPathManager.readBuilding("building.txt");
            }
            else if (input.equalsIgnoreCase("PRINT")) {
                shortestPathManager.printBuilding();
            }
            else if (input.equalsIgnoreCase("PATH")) {
                System.out.println("Please enter what from where you would like to start:");
                String from = reader.readLine();
                System.out.println("Please enter where You would like to go:");
                String to = reader.readLine();
                shortestPathManager.shortestPathWithProfile(from, to);
            }
            else if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("Goodbye!");
            }
            // TEST
            else if (input.equalsIgnoreCase("TEST")) {
                System.out.println("Test!");
                System.out.println("Read building");
                shortestPathManager.readBuilding("building2.txt");
                //System.out.println("Print building");
                //shortestPathManager.printBuilding();

                von = "A0.06 Mensa";
                zu = "F.4.05 Lesezone";
                System.out.println("Setup Profile Dijkstra");
                shortestPathManager.setProfile(true, true, true, true, "DIJKSTRA");
                System.out.println("Shortest path Dijkstra");
                shortestPathManager.shortestPathWithProfile(von,zu);

                zu = "F.6.12 Labor";
                System.out.println("Setup Profile Dijkstra");
                shortestPathManager.setProfile(true, true, true, true, "DIJKSTRA");
                System.out.println("Shortest path Dijkstra");
                shortestPathManager.shortestPathWithProfile(von,zu);

                von = "F.6.12 Labor";
                zu = "A4.31 Buero";
                System.out.println("Setup Profile Dijkstra");
                shortestPathManager.setProfile(true, true, true, true, "DIJKSTRA");
                System.out.println("Shortest path Dijkstra");
                shortestPathManager.shortestPathWithProfile(von,zu);


                von = "A0.06 Mensa";
                zu = "F.4.05 Lesezone";
                System.out.println("Setup Profile Floyd");
                shortestPathManager.setProfile(true, true, true, true, "Floyd_warshall");
                System.out.println("Shortest path Floyd");
                shortestPathManager.shortestPathWithProfile(von,zu);

                zu = "F.6.12 Labor";
                System.out.println("Setup Profile Floyd");
                shortestPathManager.setProfile(true, true, true, true, "Floyd_warshall");
                System.out.println("Shortest path Floyd");
                shortestPathManager.shortestPathWithProfile(von,zu);

                von = "F.6.12 Labor";
                zu = "A4.31 Buero";
                System.out.println("Setup Profile Floyd");
                shortestPathManager.setProfile(true, true, true, true, "Floyd_warshall");
                System.out.println("Shortest path Floyd");
                shortestPathManager.shortestPathWithProfile(von,zu);
            }
            else {
                System.out.println(input + "\nInvalid input, please try again.\n");
            }

        }
    }

    public static boolean askAbout(String question) throws IOException {
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Would you like to be able to " + question + "? \nPlease enter yes or no");

        while(! input.equalsIgnoreCase("QUIT")){
            input = reader.readLine();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                return true;
            }
            else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                return false;
            }
            else {
                System.out.println("Wrong answer, please try again with 'yes', 'no', 'y' or 'n'");
            }
        }
        return true;
    }

    public static String askAlgorithm() throws IOException {
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Which Algorithm would you like to use, Dijkstra or FLoyd-Warshall? \nPlease enter Dijkstra or Floyd");

        while(! input.equalsIgnoreCase("QUIT")){
            input = reader.readLine();
            if (input.equalsIgnoreCase("d") || input.equalsIgnoreCase("dijkstra")) {
                return "DIJKSTRA";
            }
            else if (input.equalsIgnoreCase("f") || input.equalsIgnoreCase("floyd")) {
                return "FLOYD_WARSHALL";
            }
            else {
                System.out.println("Wrong answer, please try again with 'Dijkstra', 'Floyd', 'd' or 'f'");
            }
        }
        return "DIJKSTRA";
    }

}
