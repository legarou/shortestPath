package org.shortestpath;

import lombok.Getter;
import lombok.Setter;

enum Algorithm {
    DIJKSTRA,
    FLOYD_WARSHALL
}

@Setter
@Getter
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
        this.algorithm=Algorithm.valueOf(algorithm);
        this.alternatePath=alternatePath;
    }

    public void setAlgorithmFromString(String algorithm){
        this.algorithm=Algorithm.valueOf(algorithm);
    }
}
