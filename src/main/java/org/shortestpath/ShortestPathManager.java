package org.shortestpath;

import java.io.IOException;

public class ShortestPathManager {
    FileManager fileManager;
    Graph graph;
    Profile profile;

    public ShortestPathManager(){
        this.fileManager = new FileManager();
        this.graph = new Graph();
        this.profile = null;
    }

    public void setProfile(boolean useElevator, boolean useStairs, boolean goOutside, boolean alternatePath, String algorithm) throws IOException {
        if(null == profile){
            profile = new Profile();
        }
        profile.setAlgorithmFromString(algorithm);
        profile.setAlternatePath(alternatePath);
        profile.setGoOutside(goOutside);
        profile.setUseElevator(useElevator);
        profile.setUseStairs(useStairs);
        fileManager.setProfile(profile);
        System.out.println("Profile has been set.");
    }

    public void setProfileAlgorithm(String algorithm) throws IOException {
        if(null == profile){
            profile = new Profile();
        }
        profile.setAlgorithmFromString(algorithm);
        fileManager.setProfile(profile);
        System.out.println("Algorithm has been set.");
    }

    public void readBuilding(String filepath){
        if(! fileManager.readBuilding(filepath)){
            System.out.println("File " + filepath + " could not be found or the provided file does not fulfill the required format.");
        }
        System.out.println("Building has been successfully scanned.");
    }

    public boolean readProfile() throws IOException {
        profile = fileManager.readProfile("profile.txt");
        return ! (null == profile);
    }

    public void printBuilding(){
        if(! fileManager.printBuilding()){
            System.out.println("There is no building to print! A building has to be read first.");
        }
    }

    public void shortestPathWithOwnProfile(String start, String end){
        int pathCase = 0;
        if(null != profile)
        {
            pathCase = graph.shortestPath(start, end,profile, fileManager.getBuilding());
        }
        else {
            System.out.println("In order to start the shortest path algorithm with a pre-existing profile, a profile must be set up");
        }

        switch (pathCase) {
            case 1:
                graph.printPredecessor();
                //n success
                break;
            case 0:
                // else
                break;
            case -1:
                System.out.println("Cannot call algorithm with no building!");
                break;
            case -2:
                System.out.println("Could not calculate the current path, at least one of the nodes does not exist in the current building.");
                break;
            case -3:
                // error
                break;
            case 2:
                System.out.println("No path could be found for these two nodes!");
                break;
            case 3:
                System.out.println("No path could be found for these two nodes with your profile preferences, but the search for an alternate path has been successful.");
                graph.printPredecessor();
                break;
            case 4:
                System.out.println("No path could be found for these two nodes with your profile preferences and the search for an alternate path has been unsuccessful.");
                break;
            case 5:
                // error
                break;
            default:
                System.out.println("Something went wrong!");
                // code block
        }
    }

    public void shortestPathWithNewProfile(String start, String end, boolean useElevator, boolean useStairs, boolean goOutside, boolean alternatePath, String algorithm){
        Profile newProfile = new Profile(useElevator, useStairs, goOutside, alternatePath, algorithm);

        Graph newGraph = new Graph();
        int pathCase = newGraph.shortestPath(start, end, newProfile, fileManager.getBuilding());

        switch (pathCase) {
            case 1:
                newGraph.printPredecessor();
                //n success
                break;
            case 0:
                // else
                break;
            case -1:
                System.out.println("Cannot call algorithm with no building!");
                break;
            case -2:
                System.out.println("Could not calculate the current path, at least one of the nodes does not exist in the current building.");
                break;
            case -3:
                // error
                break;
            case 2:
                System.out.println("No path could be found for these two nodes!");
                break;
            case 3:
                System.out.println("No path could be found for these two nodes with your profile preferences, but the search for an alternate path has been successful.");
                newGraph.printPredecessor();
                break;
            case 4:
                System.out.println("No path could be found for these two nodes with your profile preferences and the search for an alternate path has been unsuccessful.");
                break;
            case 5:
                // error
                break;
            default:
                System.out.println("Something went wrong!");
                // code block
        }

    }

}
