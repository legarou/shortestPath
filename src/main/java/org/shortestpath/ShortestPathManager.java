package org.shortestpath;

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
        profile = new Profile( useElevator,  useStairs,  goOutside,  alternatePath,  algorithm);
    }

    public void alterProfile(boolean useElevator, boolean useStairs, boolean goOutside, boolean alternatePath, String algorithm){
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
