package org.shortestpath;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<String, Node> nodes; // building
    private String start;
    private String end;
    private Profile profile;
    private Map<String, String> predecessor;

    public Graph(){
        this.nodes= new HashMap<>();
        this.start=null;
        this.predecessor = new HashMap<>();
        this.profile = new Profile();
    }

    public int shortestPath(String startNode, String goal, Profile newProfile, Map<String, Node> nodes) {
        if(nodes == null) {
            return -1;
        }
        this.nodes.clear();
        this.nodes = nodes;

        if(! (nodes.containsKey(startNode) && nodes.containsKey(goal)) ){
            return -2;
        }

        this.end=goal;
        // if profile unchanged & already calculated
        if(null != this.start && this.start.equals(startNode) && ! profileWasUpdated(newProfile)){
            if(hasPath()){
                //printPredecessor();
                return 1;
            }
            return 2;
        }

        this.start = startNode;
        if(! profileWasUpdated(newProfile) && profile.getAlgorithm().equals(Algorithm.FLOYD_WARSHALL)){
            // ALTERNATE PATH ?
            if(hasPath()){
                //printPredecessor();
                return 1;
            }
            return 2;
        }

        initializeNodes();
        applyProfile(newProfile);
        applyPreference();


        if(profile.getAlgorithm().equals(Algorithm.DIJKSTRA)){
            dijkstra();
            // ALTERNATE PATH ?
            if(hasPath()){
                //printPredecessor();
                return 1;
            }
            // all true but still no path possible
            if( this.profile.isUseElevator() && this.profile.isGoOutside() && this.profile.isGoOutside()) {
                return 2;
            }
            else if (this.profile.isAlternatePath()){
                this.profile.setUseElevator(true);
                this.profile.setGoOutside(true);
                this.profile.setUseStairs(true);
                initializeNodes();
                applyPreference();
                dijkstra();
                // ALTERNATE PATH ?
                if(hasPath()){
                    //printPredecessor();
                    return 3;
                }
                else {
                    return 4;
                }

            }
            else {
                // could search for alternate path but will not
                return 5;
            }

        } else if (profile.getAlgorithm().equals(Algorithm.FLOYD_WARSHALL)) {
            floyd_warshall();
            // ALTERNATE PATH ?
            if(hasPath()){
                //printPredecessor();
                return 1;
            }
            // all true but still no path possible
            if( this.profile.isUseElevator() && this.profile.isGoOutside() && this.profile.isGoOutside()) {
                return 2;
            }
            else if (this.profile.isAlternatePath()){
                this.profile.setUseElevator(true);
                this.profile.setGoOutside(true);
                this.profile.setUseStairs(true);
                initializeNodes();;
                applyPreference();
                floyd_warshall();
                // ALTERNATE PATH ?
                if(hasPath()){
                    //printPredecessor();
                    return 3;
                }
                else {
                    return 4;
                }

            }
            else {
                // could search for alternate path but will not
                return 5;
            }

        }
        else {
            System.out.println("ERROR");
            return -3;
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

    public void applyPreference(){
        if(null == profile)
            return;

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

    public void applyProfile(Profile newProfile){
        if(null == profile)
            return;

        this.profile.setAlgorithm(newProfile.getAlgorithm());
        this.profile.setGoOutside(newProfile.isGoOutside());
        this.profile.setUseStairs(newProfile.isUseStairs());
        this.profile.setAlternatePath(newProfile.isAlternatePath());
        this.profile.setUseElevator(newProfile.isUseElevator());
    }

    public void dijkstra(){
        Instant starts = Instant.now();
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
            // get smallest distance
            Node currentNode = queue.get(0);
            for (Node node : queue) {
                if(node.getDistance() < currentNode.getDistance()){
                    currentNode=node;
                }
            }
            queue.remove(currentNode);

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
        Instant ends = Instant.now();
        System.out.println("Dijkstra: " + Duration.between(starts, ends));
    }

    public void floyd_warshall(){
        Instant starts = Instant.now();

        //initialize
        predecessor.clear();
        Map<String, Node> shallowCopy = new HashMap(nodes);
        Map<String, Integer> distanceMap = new HashMap<>();


        for(Map.Entry<String,Node> node : nodes.entrySet())
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
        var test1 = shallowCopy.keySet().toArray();
        for(int i = 0; i < shallowCopy.size(); i++) {
            for(Map.Entry<String,Node> node1 : shallowCopy.entrySet())
            {
                for(Map.Entry<String,Node> node2 : shallowCopy.entrySet()) {
                    var nodeMid = test1[i];
                    int newDistance = distanceMap.get(node1.getKey() + nodeMid)+ distanceMap.get(nodeMid + node2.getKey());
                    if(newDistance < distanceMap.get(node1.getKey() + node2.getKey())){
                        distanceMap.put(node1.getKey() + node2.getKey(), newDistance);
                        predecessor.put(node1.getKey() + node2.getKey(), predecessor.get(node1.getKey() + nodeMid) + ";" + predecessor.get(nodeMid + node2.getKey()));
                    }
                }
            }
        }
        Instant ends = Instant.now();
        System.out.println("Floyd: " + Duration.between(starts, ends));
    }

    public String getPath(){
        if(null == profile || null == start || null == end)
            return null;

        if(profile.getAlgorithm().equals(Algorithm.DIJKSTRA)) {
            if(predecessor.containsKey(end) && predecessor.containsKey(start)) {
                return getDijkstraResult(end);
            }
        } else {
            if(predecessor.containsKey(start+end)) {
                return predecessor.get(start + end);
            }
        }

        return null;
    }

    public String getDijkstraResult(String node){
        if(node.equals(start)){
            return node;
        }
        else if(predecessor.get(node).equals("")){
            return "";
        }
        else if(! node.equals(start)){
            return getDijkstraResult(predecessor.get(node)) + ";" + node;
        }
        else {
            return "";
        }
    }



    public boolean printPredecessor(){
        if(null == profile || null == start || null == end)
            return false;

        if(profile.getAlgorithm().equals(Algorithm.DIJKSTRA)) {
            if(predecessor.containsKey(end) && predecessor.containsKey(start)) {
                return printDijkstraResult(end);
            }
        }
        else {
            if(predecessor.containsKey(start+end)) {
                return printFloydResult();
            }
        }
        return false;
    }
    public boolean printFloydResult(){
        String previous = "";
        String data;
        String[] splitData;
        data = predecessor.get(start + end);
        if(data.equals("")){
            return false;
        }
        splitData = data.split(";");
        for ( String line : splitData) {
            if(! previous.equals(line)){
                printNode(line);
            }
            previous = line;
        }
        return true;
    }

    public boolean printDijkstraResult(String node){
        if(node.equals(start)){
            printNode(node);
            return true;
        }
        else if(predecessor.get(node).equals("")){
            return false;
        }

        if(! node.equals(start)){
            printDijkstraResult(predecessor.get(node));
        }
        printNode(node);
        return true;
    }

    public void printNode(String node) {
        //System.out.println(nodes.get(node));
        if(node.equals(start)){
            System.out.println("Start: " +  node + " (" + nodes.get(node).getType() + ")");
        }
        else{
            System.out.println("-> " +  node + " (" + nodes.get(node).getType() + ")");
        }

    }

    public boolean hasPath(){
        if(null == profile || null == start || null == end)
            return false;

        if(profile.getAlgorithm().equals(Algorithm.DIJKSTRA)) {
            if(predecessor.containsKey(end) && predecessor.containsKey(start)) {
                return ! (null == getDijkstraResult(end) || getDijkstraResult(end).equals(""));
            }
        }
        else {
            return predecessor.containsKey(start+end);
        }
        return false;
    }

}
