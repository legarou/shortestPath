package org.shortestpath;
import java.util.Map;

enum NodeType {
    ROOM,
    STAIRS,
    ELEVATOR
}

public class Node {

    public final String name;
    public final NodeType type;
    public final boolean leadsOutside;
    public Map<String, Integer> neighbours; // or id + weight?
    public int distance;
    public boolean visited;

    public Node(String name, NodeType type, boolean leadsOutside, Map neighbours) {
        this.name = name;
        this.type = type;
        this.leadsOutside = leadsOutside;
        this.neighbours = neighbours;
        this.distance = 1000000000;
        this.visited = false;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Map<String, Integer> getNeighbours(){
        // should return copy
        return neighbours;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Type: " + type.toString() + " Neighbours: " + neighbours.toString() + " visited: " + visited + " leadsOutside: " + leadsOutside + " distance: " + distance;
    }
}
