package org.shortestpath;

enum Algorithm {
    DIJKSTRA,
    FLOYD_WARSHALL
}


public class Profile {
    private boolean useStairs;
    private boolean useElevator;
    private boolean goOutside;
    private Algorithm algorithm;
    private boolean alternatePath;

    public Profile(){
        this.useElevator=true;
        this.useStairs=true;
        this.goOutside=true;
        this.algorithm=Algorithm.DIJKSTRA;
        this.alternatePath=true;
    }

    public Profile(boolean useElevator, boolean useStairs, boolean goOutside, boolean alternatePath, String algorithm){
        this.useElevator=useElevator;
        this.useStairs=useStairs;
        this.goOutside=goOutside;
        this.algorithm=Algorithm.valueOf(algorithm.toUpperCase());
        this.alternatePath=alternatePath;
    }

    public void setAlgorithmFromString(String algorithm){
        this.algorithm=Algorithm.valueOf(algorithm);
    }

    public Algorithm getAlgorithm(){
        return algorithm;
    }

    public boolean isUseStairs() {
        return useStairs;
    }

    public void setUseStairs(boolean useStairs) {
        this.useStairs = useStairs;
    }

    public boolean isUseElevator() {
        return useElevator;
    }

    public void setUseElevator(boolean useElevator) {
        this.useElevator = useElevator;
    }

    public boolean isGoOutside() {
        return goOutside;
    }

    public void setGoOutside(boolean goOutside) {
        this.goOutside = goOutside;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isAlternatePath() {
        return alternatePath;
    }

    public void setAlternatePath(boolean alternatePath) {
        this.alternatePath = alternatePath;
    }
}
