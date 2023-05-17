package org.shortestpath;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.prefs.PreferencesFactory;


public class FileManager {

    private Map<String, Node> nodes;
    private String filepath;
    public FileManager(){
        this.filepath = null;
        nodes = new HashMap<>();
    }

    public Profile readProfile(String filename) throws IOException {
        Profile newProfile = null;
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            boolean firstline = true;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(firstline){
                    firstline=false;
                    continue;
                }
                //System.out.println(data);
                String[] splitData = data.split(";");
                if(5 != splitData.length){
                    return null;
                }
                else {
                    newProfile = new Profile(Boolean.parseBoolean(splitData[0]), Boolean.parseBoolean(splitData[1]), Boolean.parseBoolean(splitData[2]), Boolean.parseBoolean(splitData[4]), splitData[3]);
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            //System.out.println("File " + filename + " could not be found, profile is not yet set up.");
            File f = new File("profile.txt");
            f.createNewFile();
            return null;
        }
        return newProfile;
    }

    public void setProfile(Profile profile) throws IOException {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("profile.txt"));
            writer.write("");
            writer.flush();
            writer.write("useElevator;useStairs;goOutside;algorithm;alternatePath\n" + profile.isUseElevator() + ";"+ profile.isUseStairs() + ";" + profile.isGoOutside() + ";" + profile.getAlgorithm() + ";" + profile.isAlternatePath());
            writer.close();
        } catch (FileNotFoundException e) {
            //System.out.println("File " + filename + " could not be found, profile is not yet set up.");
            File f = new File("profile.txt");
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean readBuilding(String filename) {
        nodes = new HashMap<>();
        this.filepath = filename;

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            boolean firstline = true;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(firstline){
                    firstline=false;
                    continue;
                }
                //System.out.println(data);
                String[] splitData = data.split(";");
                if(4 != splitData.length){
                    nodes.clear();
                    return false;
                }
                Node newnode = buildNode(splitData);
                if(null == newnode){
                    nodes.clear();
                    return false;
                }
                nodes.put(splitData[0], newnode);
            }
            myReader.close();
            return true;
        } catch (FileNotFoundException e) {
            //System.out.println("File " + filename + " could not be found.");
            return false;
        }
    }

    public Map<String, Node> getBuilding() {
        if(null == filepath){
            return null;
        }
        Map<String, Node> buildingNodes = new HashMap<>();
        try {
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            boolean firstline = true;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(firstline){
                    firstline=false;
                    continue;
                }
                //System.out.println(data);
                String[] splitData = data.split(";");
                if(4 != splitData.length){
                    buildingNodes.clear();
                    return null;
                }
                Node newnode = buildNode(splitData);
                if(null == newnode){
                    buildingNodes.clear();
                    return null;
                }
                buildingNodes.put(splitData[0], newnode);
            }
            myReader.close();
            return buildingNodes;
        } catch (FileNotFoundException e) {
            //System.out.println("File " + filename + " could not be found.");
            return null;
        }
    }


    public Node buildNode(String[] splitData){
        try {
            Map<String, Integer> neighbours = new HashMap<>();
            String[] splitNeighbours = splitData[3].split(",");
            if(0 != (splitNeighbours.length % 2)){
                return null;
            }
            for (int i = 0; i < splitNeighbours.length; i++) {
                neighbours.put(splitNeighbours[i], Integer.parseInt(splitNeighbours[++i]));
                //System.out.println(splitNeighbours[i-1] + " , " + splitNeighbours[i]);
            }

            Node newNode = new Node(splitData[0], NodeType.valueOf(splitData[1]), Boolean.parseBoolean(splitData[2]), neighbours);
            return newNode;
        } catch (final NumberFormatException e) {
            return null;
        } catch (final IllegalArgumentException e) {
            return null;
        }



    }

    public boolean printBuilding() {
        if(! nodes.isEmpty()){
            System.out.println("Printing all building nodes:");
            for (Map.Entry<String,Node> node : nodes.entrySet()) {
                System.out.println(node.getValue().getName());
            }
            return true;
        }
        else {
            return false;
        }
    }

    public Map<String, Node> getNodes(){
        if(nodes.isEmpty()){
            return null;
        }
        return getBuilding();
    }
}
