package org.shortestpath;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;


public class FileManager {

    private Map<String, Node> nodes;
    public FileManager(){
        nodes = new HashMap<>();
    }

    public void readBuilding(String filename) {
        nodes.clear();

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // ignore first line
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                String[] splitData = data.split(";");
                nodes.put(splitData[0], buildNode(splitData));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Node buildNode(String[] splitData){
        Map<String, Integer> neighbours = new HashMap<>();
        String[] splitNeighbours = splitData[3].split(",");
        for (int i = 0; i < splitNeighbours.length; i++) {
            neighbours.put(splitNeighbours[i], Integer.parseInt(splitNeighbours[++i]));
            //System.out.println(splitNeighbours[i-1] + " , " + splitNeighbours[i]);
        }

        Node newNode = new Node(splitData[0], NodeType.valueOf(splitData[1]), Boolean.parseBoolean(splitData[2]), neighbours);
        return newNode;
    }

    public void printBuilding() {
        if(! nodes.isEmpty()){
            for (Map.Entry<String,Node> node : nodes.entrySet()) {
                System.out.println(node.getValue().toString());
            }
        }
        else {
            System.out.println("There is no building to print!");
        }

    }

    public Map<String, Node> getNodes(){
        return nodes;
    }
}
