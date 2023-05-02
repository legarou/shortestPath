package org.shortestpath;
import java.util.Map;

enum NodeType {
    ROOM,
    STAIRS,
    ELEVATOR
}

public class Node {

    public final int id;
    public final String name;
    public final NodeType type;
    public final boolean leadsOutside;
    public final Map<Node, Integer> neighbours; // or id + weight?
    public int distance;
    public boolean visited;

    public Node(int id, String name, NodeType type, boolean leadsOutside, Map neighbours) {
        this.id = id;
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

    public Map<Node, Integer> getNeighbours(){
        // should return copy
        return neighbours;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
