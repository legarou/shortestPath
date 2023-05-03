package org.shortestpath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        FileManager fileManager = new FileManager();
        Graph graph = null;
        Profile profile = new Profile();

        while(! input.equalsIgnoreCase("QUIT")){
            System.out.println("Please enter what you would like to do " +
                    "(help, read, print, path, profile, quit):\n");
            input = reader.readLine();

            if(input.equalsIgnoreCase("HELP")){
                System.out.println(input);
            }
            else if (input.equalsIgnoreCase("PROFILE")) {
                System.out.println(input);
            }
            else if (input.equalsIgnoreCase("READ")) {
                System.out.println(input);
                fileManager.readBuilding("building.txt");
            }
            else if (input.equalsIgnoreCase("PRINT")) {
                System.out.println(input);
                fileManager.printBuilding();
            }
            else if (input.equalsIgnoreCase("PATH")) {
                System.out.println(input);
                graph = new Graph("My Graph", fileManager.getNodes());
                System.out.println("call");
                graph.shortestPath("bett", "fenster",profile);

            }
            else if (input.equalsIgnoreCase("QUIT")) {
                System.out.println(input);
            }
            else {
                System.out.println(input + "\nInvalid input, please try again.\n");
            }

        }
    }

}
