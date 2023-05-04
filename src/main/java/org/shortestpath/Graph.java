package org.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<String, Node> nodes; // building
    private String name;
    private String start;
    private Profile profile;
    private Map<String, String> predecessor;

    public Graph(String name, Map<String, Node> nodes){
        this.name = name;
        //make copy
        this.nodes = nodes;
        this.start=null;
        this.predecessor = new HashMap<>();
        this.profile = new Profile();
    }

    public void shortestPath(String start, String goal, Profile newProfile) {
        if(nodes == null) {
            System.out.println("Cannot call algorithm with no building!");
            return;
        }

        // if profile unchanged & already calculated
        if(this.start.equals(start) && ! profileWasUpdated(newProfile)){
            System.out.println("RESULT: ");
            printPredecessor(predecessor, start, goal);
            return;
        }

        applyPreference(newProfile);
        if(profile.getAlgorithm().equals(Algorithm.DIJKSTRA)){
            System.out.println("dijkstra:");
            dijkstra(nodes.get(start));
            // ALTERNATE PATH ?
            System.out.println("RESULT: ");
            printPredecessor(predecessor, start, goal);

        } else if (profile.getAlgorithm().equals(Algorithm.FLOYD_WARSHALL)) {
            System.out.println("NOT SET UP");
        }
        else {
            System.out.println("ERROR");
        }

    }

    public boolean profileWasUpdated(Profile newProfile){
        if(null == profile)
            return true;

        return ! ( (this.profile.isUseElevator() == newProfile.isUseElevator())
                && (this.profile.isUseStairs() == newProfile.isUseStairs())
                && (this.profile.isAlternatePath() == newProfile.isAlternatePath())
                && (this.profile.isGoOutside() == newProfile.isGoOutside())
                && (this.profile.getAlgorithm().equals(newProfile.getAlgorithm())) );
    }

    public void applyPreference(Profile profile){
        if(null == profile)
            return;

        for(Map.Entry<String,Node> variable : nodes.entrySet())
        {
            Node node = variable.getValue();
            if((! profile.isUseStairs()) && (node.type == NodeType.STAIRS)) {
                node.setVisited(true);
            }
            else if((! profile.isUseElevator()) && (node.type == NodeType.ELEVATOR)) {
                node.setVisited(true);
            }
        }
    }

    public void dijkstra(Node start){
        //initialize
        List<Node> queue = new ArrayList<>();
        predecessor.clear();

        for(Map.Entry<String,Node> node : nodes.entrySet())
        {
            if(! node.getValue().visited) {
                predecessor.put(node.getKey(), "");
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

                if(! profile.isGoOutside() && currentNode.leadsOutside && neighbourNode.leadsOutside){
                    // does not work for exits next to each other
                    continue;
                }
                int newDistance = currentNode.distance + neighbourDistance;
                if(! neighbourNode.visited) {
                    queue.add(neighbourNode);
                }
                if(newDistance < neighbourNode.distance){
                    neighbourNode.setDistance(newDistance);
                    predecessor.put(neighbourNode.getName(), currentNode.getName());
                }
            }
            // GO OUTSIDE
        }
    }

    public void printPredecessor(Map<String, String> predecessor, String start, String end){
        String currentNode = end;
        if(predecessor.containsKey(end) && predecessor.containsKey(start)){
            while(! currentNode.equals(start)) {
                printNode(currentNode);
                currentNode = predecessor.get(currentNode);
            }
            printNode(currentNode);
        }
        else {
            System.out.println("faulty call");
        }
    }

    public void printNode(String node) {
        System.out.println(nodes.get(node));
    }



}
