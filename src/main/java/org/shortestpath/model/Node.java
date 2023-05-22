package org.shortestpath.model;

import java.util.Map;

public class Node {
    private final String name;
    private final NodeType type;
    private final boolean leadsOutside;
    private Map<String, Integer> neighbours; // or id + weight?
    private int distance;
    private boolean visited;

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

    public int getNeighbourDistance(Node node){
        if(neighbours.containsKey(node.getName())){
            return neighbours.get(node.getName());
        }
        return -1;
    }

    public NodeType getType() {
        return type;
    }

    public boolean isLeadsOutside() {
        return leadsOutside;
    }

    public void setNeighbours(Map<String, Integer> neighbours) {
        this.neighbours = neighbours;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isVisited() {
        return visited;
    }
}
