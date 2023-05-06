package org.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<String, Node> nodes; // building
    private String name;
    private String start;
    private String end;
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

    public Graph(){
        this.name="No Name";
        this.nodes=null;
    }

    public void setNodes(Map<String, Node> nodes){
        this.nodes.clear();
        this.nodes = nodes;
    }

    public void shortestPath(String start, String goal, Profile newProfile) {
        if(nodes == null) {
            System.out.println("Cannot call algorithm with no building!");
            return;
        }

        this.end=goal;
        // if profile unchanged & already calculated
        if(null != this.start && this.start.equals(start) && ! profileWasUpdated(newProfile)){
            System.out.println("RESULT: ");
            printPredecessor();
            return;
        }

        this.start = start;
        if(! profileWasUpdated(newProfile) && profile.getAlgorithm().equals(Algorithm.FLOYD_WARSHALL)){
            this.start = start;
            System.out.println("RESULT: ");
            printPredecessor();
            return;
        }

        initializeNodes();
        applyPreference(newProfile);


        if(profile.getAlgorithm().equals(Algorithm.DIJKSTRA)){
            System.out.println("dijkstra:");
            dijkstra();
            // ALTERNATE PATH ?
            System.out.println("RESULT: ");
            printPredecessor();

        } else if (profile.getAlgorithm().equals(Algorithm.FLOYD_WARSHALL)) {
            System.out.println("floyd:");
            floyd_warshall();
            // ALTERNATE PATH ?
            System.out.println("RESULT: ");
            printPredecessor();
        }
        else {
            System.out.println("ERROR");
        }

    }

    private void initializeNodes() {
        for(Map.Entry<String,Node> variable : nodes.entrySet())
        {
            variable.getValue().setVisited(false);
            variable.getValue().setDistance(99999999);
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

    public void applyPreference(Profile newProfile){
        if(null == profile)
            return;

        this.profile.setAlgorithm(newProfile.getAlgorithm());
        this.profile.setGoOutside(newProfile.isGoOutside());
        this.profile.setUseStairs(newProfile.isUseStairs());
        this.profile.setAlternatePath(newProfile.isAlternatePath());
        this.profile.setUseElevator(newProfile.isUseElevator());

        for(Map.Entry<String,Node> variable : nodes.entrySet())
        {
            Node node = variable.getValue();
            if((! profile.isUseStairs()) && (node.getType() == NodeType.STAIRS)) {
                node.setVisited(true);
            }
            else if((! profile.isUseElevator()) && (node.getType() == NodeType.ELEVATOR)) {
                node.setVisited(true);
            }
        }
    }

    public void dijkstra(){
        //initialize
        List<Node> queue = new ArrayList<>();
        predecessor.clear();

        for(Map.Entry<String,Node> node : nodes.entrySet())
        {
            if(! node.getValue().isVisited()) {
                predecessor.put(node.getKey(), "");
            }
        }

        nodes.get(start).setDistance(0);
        queue.add(nodes.get(start));

        //loop
        while(! queue.isEmpty()){
            Node currentNode = queue.remove(0);
            if(currentNode.isVisited()) {
                continue;
            } else {
                currentNode.setVisited(true);
            }
            Map<String, Integer> neighbours = currentNode.getNeighbours();

            for (Map.Entry<String,Integer> neighbour : neighbours.entrySet()) {
                Node neighbourNode = nodes.get(neighbour.getKey());
                int neighbourDistance = neighbour.getValue();

                if(! profile.isGoOutside() && currentNode.isLeadsOutside() && neighbourNode.isLeadsOutside()){
                    // does not work for exits next to each other
                    continue;
                }
                int newDistance = currentNode.getDistance() + neighbourDistance;
                if(! neighbourNode.isVisited()) {
                    queue.add(neighbourNode);
                }
                if(newDistance < neighbourNode.getDistance()){
                    neighbourNode.setDistance(newDistance);
                    predecessor.put(neighbourNode.getName(), currentNode.getName());
                }
            }
            // GO OUTSIDE
        }
    }

    public void printPredecessor(){
        if(null == profile || null == start || null == end)
            return;

        if(profile.getAlgorithm().equals(Algorithm.DIJKSTRA)) {
            if(predecessor.containsKey(end) && predecessor.containsKey(start)) {
                printDijkstraResult(end);
            }
        }
        else {
            if(predecessor.containsKey(start+end)) {
                printFloydResult();
            }
        }
    }

    public void printDijkstraResult(String node){
        if(! node.equals(start)){
            printDijkstraResult(predecessor.get(node));
        }
        printNode(node);
    }

    public void printFloydResult(){
        String previous = "";
        String data;
        String[] splitData;
        data = predecessor.get(start + end);
        splitData = data.split(";");
        for ( String line : splitData) {
            if(! previous.equals(line)){
                printNode(line);
            }
            previous = line;
        }
    }

    public void printNode(String node) {
        System.out.println(nodes.get(node));
    }

    public void floyd_warshall(){
        //initialize
        predecessor.clear();
        Map<String, Node> shallowCopy = new HashMap(nodes);
        Map<String, Integer> distanceMap = new HashMap<>();


        for(Map.Entry<String,Node> node : shallowCopy.entrySet())
        {
            if(node.getValue().isVisited()) {
                shallowCopy.remove(node.getKey());
            }
        }

        for(Map.Entry<String,Node> node1 : shallowCopy.entrySet())
        {
            for(Map.Entry<String,Node> node2 : shallowCopy.entrySet())
            {
                predecessor.put(node1.getKey() + node2.getKey(), "");
                // initial distances and paths
                if(node1.getKey().equals(node2.getKey())){
                    distanceMap.put(node1.getKey() + node2.getKey(), 0);
                    predecessor.put(node1.getKey() + node2.getKey(), "");
                }
                else if(-1 < node1.getValue().getNeighbourDistance(node2.getValue())){
                    distanceMap.put(node1.getKey() + node2.getKey(), node1.getValue().getNeighbourDistance(node2.getValue()));
                    predecessor.put(node1.getKey() + node2.getKey(), node1.getKey() + ";" + node2.getKey());
                }
                else {
                    distanceMap.put(node1.getKey() + node2.getKey(), 99999999);
                    predecessor.put(node1.getKey() + node2.getKey(), "");
                }
            }
        }

        //
        for(int i = 0; i < shallowCopy.size(); i++) {
            for(Map.Entry<String,Node> node1 : shallowCopy.entrySet())
            {
                for(Map.Entry<String,Node> node2 : shallowCopy.entrySet())
                {
                    for(Map.Entry<String,Node> nodeMid : shallowCopy.entrySet())
                    {
                        int newDistance = distanceMap.get(node1.getKey() + nodeMid.getKey())+ distanceMap.get(nodeMid.getKey() + node2.getKey());
                        if(newDistance < distanceMap.get(node1.getKey() + node2.getKey())){
                            distanceMap.put(node1.getKey() + node2.getKey(), newDistance);
                            predecessor.put(node1.getKey() + node2.getKey(), predecessor.get(node1.getKey() + nodeMid.getKey()) + ";" + predecessor.get(nodeMid.getKey() + node2.getKey()));
                        }

                    }
                }
            }
        }
    }

}
