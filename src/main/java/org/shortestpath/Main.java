package org.shortestpath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = "";
        String von = "A0.06 Mensa";
        String zu = "F.4.05 Lesezone";
        String[] profileQuestions = { "use an elevator", "use the stairs", "go outside", "receive an alternate path, if your profile does not deliver a result?", "algorithm"};
        boolean[] profileAnswers = { true, true, true, true};

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ShortestPathManager shortestPathManager = new ShortestPathManager();
        if(! shortestPathManager.readProfile()){
            System.out.println("Hello! Please set up your profile: ");
            input = "profile";
        }

        do {
            if(input.equalsIgnoreCase("HELP")){
                System.out.println("READ: \t\tRead the building into the program from a prepared file.");
                System.out.println("PRINT:\t\tPrint the building plan.");
                System.out.println("PATH:\t\tCall the shortest path program. You will have to enter the start and end point, and can choose between using your own profile or setting up a new one. " +
                        "\nYour profile preferences will not be changed.");
                System.out.println("PROFILE:\tSet up your profile preferences, which guide your path results.");
                System.out.println("QUIT:\t\tQuit the program.");
            }
            else if (input.equalsIgnoreCase("PROFILE")) {
                for(int i = 0; i < profileQuestions.length;){
                    String[] options = {"yes", "y", "no", "n"};
                    while(! (input.equalsIgnoreCase("return") || input.equalsIgnoreCase("quit"))){
                        if(4 == i){
                            System.out.println("Which Algorithm would you like to use, Dijkstra or FLoyd-Warshall? \nPlease enter Dijkstra or Floyd");
                            options[0] = "dijkstra";
                            options[1] = "d";
                            options[2] = "floyd";
                            options[3] = "f";
                        }
                        else {
                            System.out.println("Would you like to be able to " + profileQuestions[i] + "? \nPlease enter yes or no");
                        }
                        input = reader.readLine();
                        if (input.equalsIgnoreCase(options[0]) || input.equalsIgnoreCase(options[1]) || input.equalsIgnoreCase(options[2]) || input.equalsIgnoreCase(options[3]) || input.equalsIgnoreCase("return") || input.equalsIgnoreCase("quit")) {
                            switch (input.charAt(0)) {
                                case 'y':
                                case 'Y':   profileAnswers[i] = true;
                                    break;
                                case 'n':
                                case 'N':   profileAnswers[i] = false;
                                    break;
                                case 'd':
                                case 'D':   profileQuestions[4] = "DIJKSTRA";
                                    break;
                                case 'f':
                                case 'F':   profileQuestions[4] = "FLOYD_WARSHALL";
                                    break;
                            }
                            break;
                        }
                        else {
                            System.out.println("Wrong answer, please try again with '" + options[0] + "', '" + options[0] + "', '" + options[0] + "', '" + options[0] + ", 'quit' or 'return' in order to get to the previous question.");
                        }
                    }
                    if(input.equalsIgnoreCase("return")){
                        if(0 != i){
                            i--;
                        }
                    }
                    else if(input.equalsIgnoreCase("quit")){
                        break;
                    }
                    else {
                        i++;
                    }
                    input="xx";
                }
                if(input.equalsIgnoreCase("quit")){
                    System.out.println("Profile update aborted.");
                }
                else {
                    shortestPathManager.setProfile(profileAnswers[0], profileAnswers[1], profileAnswers[2], profileAnswers[3], profileQuestions[4]);
                }
            }
            else if (input.equalsIgnoreCase("ALGORITHM")) {
                System.out.println("Which Algorithm would you like to use, Dijkstra or FLoyd-Warshall? \nPlease enter Dijkstra or Floyd");
                while(! input.equalsIgnoreCase("quit")){
                    input = reader.readLine();
                    if (input.equalsIgnoreCase("dijkstra") || input.equalsIgnoreCase("d") || input.equalsIgnoreCase("floyd") || input.equalsIgnoreCase("f") || input.equalsIgnoreCase("quit")) {
                        switch (input.charAt(0)) {
                            case 'd':
                            case 'D':   profileQuestions[4] = "DIJKSTRA";
                                        break;
                            case 'f':
                            case 'F':   profileQuestions[4] = "FLOYD_WARSHALL";
                                        break;
                            case 'q':
                            case 'Q':   break;
                        }
                        break;
                    }
                    else {
                        System.out.println("Wrong answer, please try again with '" + "dijsktra" + "', '" + "d" + "', '" + "floyd" + "', '" + "f" + ", 'quit' or 'return' in order to get to the previous question.");
                    }
                }
                if(input.equalsIgnoreCase("quit")){
                    System.out.println("Profile update aborted.");
                }
                else {
                    shortestPathManager.setProfileAlgorithm(profileQuestions[4]);
                }
            }
            else if (input.equalsIgnoreCase("READ")) {
                System.out.println("Please enter the filename: (example: building.txt)");
                input = reader.readLine();
                shortestPathManager.readBuilding(input);
            }
            else if (input.equalsIgnoreCase("PRINT")) {
                shortestPathManager.printBuilding();
            }
            else if (input.equalsIgnoreCase("PATH")) {
                System.out.println("Please enter what from where you would like to start:");
                String from = reader.readLine();
                System.out.println("Please enter where You would like to go:");
                String to = reader.readLine();
                System.out.println("Would you like to use your own profile, or use a new one for one time only? Please answer with 'new' or 'current':");
                while(! (input.equalsIgnoreCase("current") || input.equalsIgnoreCase("c") || input.equalsIgnoreCase("new") || input.equalsIgnoreCase("n"))){
                    input = reader.readLine();
                    if (input.equalsIgnoreCase("new") || input.equalsIgnoreCase("n")) {
                        for(int i = 0; i < profileQuestions.length;){
                            String[] options = {"yes", "y", "no", "n"};
                            while(! (input.equalsIgnoreCase("return") || input.equalsIgnoreCase("quit"))){
                                if(4 == i){
                                    System.out.println("Which Algorithm would you like to use, Dijkstra or FLoyd-Warshall? \nPlease enter Dijkstra or Floyd");
                                    options[0] = "dijkstra";
                                    options[1] = "d";
                                    options[2] = "floyd";
                                    options[3] = "f";
                                }
                                else {
                                    System.out.println("Would you like to be able to " + profileQuestions[i] + "? \nPlease enter yes or no");
                                }
                                input = reader.readLine();
                                if (input.equalsIgnoreCase(options[0]) || input.equalsIgnoreCase(options[1]) || input.equalsIgnoreCase(options[2]) || input.equalsIgnoreCase(options[3]) || input.equalsIgnoreCase("return") || input.equalsIgnoreCase("quit")) {
                                    switch (input.charAt(0)) {
                                        case 'y':
                                        case 'Y':   profileAnswers[i] = true;
                                            break;
                                        case 'n':
                                        case 'N':   profileAnswers[i] = false;
                                            break;
                                        case 'd':
                                        case 'D':   profileQuestions[4] = "DIJKSTRA";
                                            break;
                                        case 'f':
                                        case 'F':   profileQuestions[4] = "FLOYD_WARSHALL";
                                            break;
                                    }
                                    break;
                                }
                                else {
                                    System.out.println("Wrong answer, please try again with '" + options[0] + "', '" + options[0] + "', '" + options[0] + "', '" + options[0] + ", 'quit' or 'return' in order to get to the previous question.");
                                }
                            }
                            if(input.equalsIgnoreCase("return")){
                                if(0 != i){
                                    i--;
                                }
                            }
                            else if(input.equalsIgnoreCase("quit")){
                                System.out.println("Profile update aborted.");
                                break;
                            }
                            else {
                                i++;
                            }
                            input="xx";
                        }
                        if(input.equalsIgnoreCase("quit")){
                            System.out.println("Shortest path calculation needs to be aborted as well.");
                            break;
                        }
                        else{
                            shortestPathManager.shortestPathWithNewProfile(from, to, profileAnswers[0], profileAnswers[1], profileAnswers[2], profileAnswers[3], profileQuestions[4]);
                            input="new";
                        }

                    }
                    else if (input.equalsIgnoreCase("current") || input.equalsIgnoreCase("c")) {
                        shortestPathManager.shortestPathWithOwnProfile(from, to);
                    }
                    else {
                        System.out.println("Wrong answer, please try again with 'new', 'current', 'n' or 'c'");
                    }
                }


            }
            else if (input.equalsIgnoreCase("QUIT")) {
            }
            else {
                System.out.println(input + "\nInvalid input, please try again.\n");
            }
            // TEST
            /*else if (input.equalsIgnoreCase("TEST")) {
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
                shortestPathManager.shortestPathWithOwnProfile(von,zu);

                zu = "F.6.12 Labor";
                System.out.println("Setup Profile Dijkstra");
                shortestPathManager.setProfile(true, true, true, true, "DIJKSTRA");
                System.out.println("Shortest path Dijkstra");
                shortestPathManager.shortestPathWithOwnProfile(von,zu);

                von = "F.6.12 Labor";
                zu = "A4.31 Buero";
                System.out.println("Setup Profile Dijkstra");
                shortestPathManager.setProfile(true, true, true, true, "DIJKSTRA");
                System.out.println("Shortest path Dijkstra");
                shortestPathManager.shortestPathWithOwnProfile(von,zu);


                von = "A0.06 Mensa";
                zu = "F.4.05 Lesezone";
                System.out.println("Setup Profile Floyd");
                shortestPathManager.setProfile(true, true, true, true, "Floyd_warshall");
                System.out.println("Shortest path Floyd");
                shortestPathManager.shortestPathWithOwnProfile(von,zu);

                zu = "F.6.12 Labor";
                System.out.println("Setup Profile Floyd");
                shortestPathManager.setProfile(true, true, true, true, "Floyd_warshall");
                System.out.println("Shortest path Floyd");
                shortestPathManager.shortestPathWithOwnProfile(von,zu);

                von = "F.6.12 Labor";
                zu = "A4.31 Buero";
                System.out.println("Setup Profile Floyd");
                shortestPathManager.setProfile(true, true, true, true, "Floyd_warshall");
                System.out.println("Shortest path Floyd");
                shortestPathManager.shortestPathWithOwnProfile(von,zu);
            }*/

            System.out.println("Please enter what you would like to do " +
                    "(help, read, print, path, profile, quit):\n");
            input = reader.readLine();

        } while(! input.equalsIgnoreCase("QUIT"));
        System.out.println("Goodbye!");
    }

}
