package org.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public List<Node> nodes; // building
    public String name;
    public boolean goOutside;
    public boolean alternatePath;

    public Graph(String name, List<Node> nodes){
        this.name = name;
        //make copy
        this.nodes = nodes;
    }

    public List<Node> shortestPath(Node start, Node goal, Profile profile) {
        applyPreference(profile);
        Map<Node, List<Node>> result = dijkstra(start);
        // ALTERNATE PATH ?
        return result.get(goal);
    }

    public void applyPreference(Profile profile){
        // does that even work
        for(Node variable : nodes)
        {
            if((! profile.useStairs) && (variable.type == NodeType.STAIRS)) {
                variable.setVisited(true);
            }
            else if((! profile.useElevator) && (variable.type == NodeType.ELEVATOR)) {
                variable.setVisited(true);
            }
        }
        this.goOutside = profile.goOutside;
        this.alternatePath = profile.alternatePath;
    }

    public Map<Node, List<Node>> dijkstra(Node start){
        //initialize
        List<Node> queue = new ArrayList<>();
        Map<Node, List<Node>> path = new HashMap<>();

        for(Node variable : nodes)
        {
            if(! variable.visited) {
                path.put(variable, new ArrayList<Node>());
            }
        }

        start.setDistance(0);
        queue.add(start);

        //loop
        while(! queue.isEmpty()){
            Node currentNode = queue.remove(0);
            currentNode.setVisited(true);
            Map<Node, Integer> neighbours = currentNode.getNeighbours();

            for (Map.Entry<Node,Integer> neighbour : neighbours.entrySet()) {
                //neighbour.getKey()
                //neighbour.getValue()
                if(! goOutside && currentNode.leadsOutside && neighbour.getKey().leadsOutside){
                    // does not work for exits next to each other
                    continue;
                }
                int newDistance = currentNode.distance + neighbour.getValue();
                if(! neighbour.getKey().visited) {
                    queue.add(neighbour.getKey());
                }
                if(newDistance < neighbour.getKey().distance){
                    neighbour.getKey().setDistance(newDistance);
                    List<Node> newPath = new ArrayList<>();
                    newPath.add(currentNode);
                    newPath.addAll(path.get(neighbour.getKey()));
                    // does it update it?
                    path.put(neighbour.getKey(), newPath);
                }
            }
            // GO OUTSIDE))
        }

        return path;
    }





}
