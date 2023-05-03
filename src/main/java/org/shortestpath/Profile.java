package org.shortestpath;

enum Algorithm {
    DIJKSTRA,
    FLOYD_WARSHALL
}

public class Profile {
    public boolean useStairs;
    public boolean useElevator;
    public boolean goOutside;
    public Algorithm algorithm;
    public boolean alternatePath;

    public Profile(){
        this.useElevator=true;
        this.useStairs=true;
        this.goOutside=true;
        this.algorithm=Algorithm.DIJKSTRA;
        this.alternatePath=true;
    }
}
