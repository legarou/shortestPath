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
}
