package org.shortestpath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ShortestPathManager shortestPathManager = new ShortestPathManager();

        while(! input.equalsIgnoreCase("QUIT")){
            System.out.println("Please enter what you would like to do " +
                    "(help, read, print, path, profile, quit):\n");
            input = reader.readLine();

            if(input.equalsIgnoreCase("HELP")){
                System.out.println(input);
            }
            else if (input.equalsIgnoreCase("PROFILE")) {
                shortestPathManager.setProfile(askAbout("use an elevator"), askAbout("use the stairs"), askAbout("go outside"), askAbout("receive an alternate path, if your profile does not deliver a result?"), askAlgorithm());
            }
            else if (input.equalsIgnoreCase("READ")) {
                System.out.println(input);
                shortestPathManager.readBuilding("building.txt");
            }
            else if (input.equalsIgnoreCase("PRINT")) {
                shortestPathManager.printBuilding();
            }
            else if (input.equalsIgnoreCase("PATH")) {
                // ask about profile
                shortestPathManager.shortestPathWithProfile("bett", "fenster");
            }
            else if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("Goodbye!");
            }
            // TEST
            else if (input.equalsIgnoreCase("TEST")) {
                System.out.println("Test!");
                System.out.println("Read building");
                shortestPathManager.readBuilding("building.txt");
                System.out.println("Print building");
                shortestPathManager.printBuilding();

                System.out.println("Setup Profile Dijkstra");
                shortestPathManager.setProfile(true, true, true, true, "DIJKSTRA");
                System.out.println("Shortest path Dijkstra");
                shortestPathManager.shortestPathWithProfile("bett", "fenster");

                System.out.println("Setup Profile Floyd");
                shortestPathManager.setProfile(true, true, true, true, "Floyd_warshall");
                System.out.println("Shortest path Floyd");
                shortestPathManager.shortestPathWithProfile("bett", "fenster");
            }
            else {
                System.out.println(input + "\nInvalid input, please try again.\n");
            }

        }
    }

    public static boolean askAbout(String question){
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Would you like to be able to " + question + "? \nPlease enter yes or no");

        while(! input.equalsIgnoreCase("QUIT")){
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

    public static String askAlgorithm(){
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Which Algorithm would you like to use, Dijkstra or FLoyd-Warshall? \nPlease enter Dijkstra or Floyd");

        while(! input.equalsIgnoreCase("QUIT")){
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
