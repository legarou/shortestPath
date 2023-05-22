package org.shortestpath.model;


public class Profile {
    private boolean useStairs;
    private boolean useElevator;
    private boolean goOutside;
    private AlgorithmType algorithm;
    private boolean alternatePath;

    public Profile(){
        this.useElevator=true;
        this.useStairs=true;
        this.goOutside=true;
        this.algorithm= AlgorithmType.DIJKSTRA;
        this.alternatePath=true;
    }

    public Profile(boolean useElevator, boolean useStairs, boolean goOutside, boolean alternatePath, String algorithm){
        this.useElevator=useElevator;
        this.useStairs=useStairs;
        this.goOutside=goOutside;
        this.algorithm= AlgorithmType.valueOf(algorithm.toUpperCase());
        this.alternatePath=alternatePath;
    }

    public void setAlgorithmFromString(String algorithm){
        this.algorithm= AlgorithmType.valueOf(algorithm.toUpperCase());
    }

    public AlgorithmType getAlgorithm(){
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

    public void setAlgorithm(AlgorithmType algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isAlternatePath() {
        return alternatePath;
    }

    public void setAlternatePath(boolean alternatePath) {
        this.alternatePath = alternatePath;
    }
}
