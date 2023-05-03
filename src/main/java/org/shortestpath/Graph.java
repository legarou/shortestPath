package org.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<String, Node> nodes; // building
    public String name;
    public boolean goOutside;
    public boolean alternatePath;

    public Graph(String name, Map<String, Node> nodes){
        this.name = name;
        //make copy
        this.nodes = nodes;
        this.goOutside=true;
        this.alternatePath=true;
    }

    public void shortestPath(String start, String goal, Profile profile) {
        applyPreference(profile);
        System.out.println("dijkstra:");
        Map<String, List<Node>> result = dijkstra(nodes.get(start));
        // ALTERNATE PATH ?
        //System.out.println(result);
        System.out.println("RESULT: ");
        System.out.println(result.get(goal));
    }

    public void applyPreference(Profile profile){
        if(null == profile)
            return;

        // does that even work
        for(Map.Entry<String,Node> variable : nodes.entrySet())
        {
            Node node = variable.getValue();
            if((! profile.useStairs) && (node.type == NodeType.STAIRS)) {
                node.setVisited(true);
            }
            else if((! profile.useElevator) && (node.type == NodeType.ELEVATOR)) {
                node.setVisited(true);
            }
        }
        this.goOutside = profile.goOutside;
        this.alternatePath = profile.alternatePath;
    }

    public Map<String, List<Node>> dijkstra(Node start){
        //initialize
        List<Node> queue = new ArrayList<>();
        Map<String, List<Node>> path = new HashMap<>();

        for(Map.Entry<String,Node> node : nodes.entrySet())
        {
            if(! node.getValue().visited) {
                path.put(node.getValue().getName(), new ArrayList<Node>());
            }
        }

        start.setDistance(0);
        queue.add(start);


        //loop
        while(! queue.isEmpty()){
            Node currentNode = queue.remove(0);
            if(currentNode.visited) {
                continue;
            } else {
                currentNode.setVisited(true);
            }
            Map<String, Integer> neighbours = currentNode.getNeighbours();

            for (Map.Entry<String,Integer> neighbour : neighbours.entrySet()) {
                Node neighbourNode = nodes.get(neighbour.getKey());
                int neighbourDistance = neighbour.getValue();

                if(! goOutside && currentNode.leadsOutside && neighbourNode.leadsOutside){
                    // does not work for exits next to each other
                    continue;
                }
                int newDistance = currentNode.distance + neighbourDistance;
                if(! neighbourNode.visited) {
                    queue.add(neighbourNode);
                }
                if(newDistance < neighbourNode.distance){
                    neighbourNode.setDistance(newDistance);
                    List<Node> newPath = new ArrayList<>();
                    newPath.add(currentNode);
                    newPath.addAll(path.get(neighbourNode.getName()));
                    // does it update it?
                    // fix path!!! is not right. remove last, but how
                    path.put(neighbourNode.getName(), newPath);
                }
            }
            // GO OUTSIDE))
        }

        return path;
    }





}
