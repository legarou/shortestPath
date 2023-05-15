package org.shortestpath;

import java.io.IOException;

public class ShortestPathManager {
    FileManager fileManager;
    Graph graph;
    Profile profile;

    public ShortestPathManager(){
        this.fileManager = new FileManager();
        this.graph = null;
        this.profile = null;
    }

    public void setProfile(boolean useElevator, boolean useStairs, boolean goOutside, boolean alternatePath, String algorithm){
        if(null == profile){
            profile = new Profile();
        }
        profile.setAlgorithmFromString(algorithm);
        profile.setAlternatePath(alternatePath);
        profile.setGoOutside(goOutside);
        profile.setUseElevator(useElevator);
        profile.setUseStairs(useStairs);
    }

    public void readBuilding(String file){
        fileManager.readBuilding(file);
    }

    public boolean readProfile() throws IOException {
        profile = fileManager.readProfile("profile.txt");
        return ! (null == profile);
    }

    public void printBuilding(){
        fileManager.printBuilding();
    }

    public void shortestPathWithProfile(String start, String end){
        if(null != profile)
        {
            if(null == graph){
                graph = new Graph("My Graph", fileManager.getNodes());
            }
            graph.shortestPath(start, end,profile);
        }
    }

}
