import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shortestpath.Node;
import org.shortestpath.ShortestPathManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class TestAlgorithms {
    ShortestPathManager shortestPathManager;
    String[] building;

    @BeforeEach
    public void Initialize() throws IOException {
        shortestPathManager = new ShortestPathManager();
        shortestPathManager.setProfile(true, true, true, true, "DIJKSTRA");
        shortestPathManager.readBuilding("building.txt");
        Map<String, Node> buildingMap = shortestPathManager.getBuilding();
        var objectArray = buildingMap.keySet().toArray();
        building = Arrays.copyOf(objectArray, objectArray.length, String[].class);

    }

    @Test
    public void TestDijkstra() throws IOException {
        shortestPathManager.setProfileAlgorithm("DIJKSTRA");
        var generator = new Random(1);
        var output = new StringBuilder();
        output.append("DurationInNanoseconds;MemoryUsage\n");

        for(int i = 0; i < 10000  ; i++){
            var starts = System.nanoTime();
            shortestPathManager.shortestPathWithOwnProfile(building[generator.nextInt(building.length)], building[generator.nextInt(building.length)]);
            var ends = System.nanoTime();
            output.append(ends-starts + ";" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) + "\n");
        }
        var fwr = new FileWriter("dijsktraTestNew.txt");
        var pwr = new PrintWriter(fwr);
        pwr.write(output.toString());
        pwr.close();
        fwr.close();
    }

    @Test
    public void TestDijkstraNewProfileEvery100Calls() throws IOException {
        shortestPathManager.setProfileAlgorithm("DIJKSTRA");
        var generator = new Random(1);
        var output = new StringBuilder();
        output.append("DurationInNanoseconds;MemoryUsage\n");

        for(int i = 0; i < 10000; i++){
            var starts = System.nanoTime();
            if(i % 100 == 0){
                shortestPathManager.shortestPathWithNewProfile(building[generator.nextInt(building.length)], building[generator.nextInt(building.length)], true, true, true, true, "DIJKSTRA");
            }
            else {
                shortestPathManager.shortestPathWithOwnProfile(building[generator.nextInt(building.length)], building[generator.nextInt(building.length)]);
            }
            var ends = System.nanoTime();
            output.append(ends-starts + ";" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) + "\n");
        }
        var fwr = new FileWriter("dijsktraTestNewProfileEvery100CallsNew.txt");
        var pwr = new PrintWriter(fwr);
        pwr.write(output.toString());
        pwr.close();
        fwr.close();

    }

    @Test
    public void TestFloydOneProfile() throws IOException {
        shortestPathManager.setProfileAlgorithm("FLOYD_WARSHALL");
        var generator = new Random(1);
        var output = new StringBuilder();
        output.append("DurationInNanoseconds;MemoryUsage\n");

        for(int i = 0; i < 10000; i++){
            var starts = System.nanoTime();
            shortestPathManager.shortestPathWithOwnProfile(building[generator.nextInt(building.length)], building[generator.nextInt(building.length)]);
            var ends = System.nanoTime();
            output.append(ends-starts + ";" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) + "\n");
        }
        var fwr = new FileWriter("floydwarshallTestOneProfileNew.txt");
        var pwr = new PrintWriter(fwr);
        pwr.write(output.toString());
        pwr.close();
        fwr.close();

    }

    @Test
    public void TestFloydNewProfileEvery100Calls() throws IOException {
        shortestPathManager.setProfileAlgorithm("FLOYD_WARSHALL");
        var generator = new Random(1);
        var output = new StringBuilder();
        output.append("DurationInNanoseconds;MemoryUsage\n");

        for(int i = 0; i < 10000; i++){
            var starts = System.nanoTime();
            if(i % 100 == 0){
                shortestPathManager.shortestPathWithNewProfile(building[generator.nextInt(building.length)], building[generator.nextInt(building.length)], true, true, true, true, "FLOYD_WARSHALL");
            }
            else {
                shortestPathManager.shortestPathWithOwnProfile(building[generator.nextInt(building.length)], building[generator.nextInt(building.length)]);
            }
            var ends = System.nanoTime();
            output.append(ends-starts + ";" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) + "\n");
        }
        var fwr = new FileWriter("floydwarshallTestNewProfileEvery100CallsNew.txt");
        var pwr = new PrintWriter(fwr);
        pwr.write(output.toString());
        pwr.close();
        fwr.close();

    }

    @Test
    public void TestFloydNewProfileEveryCall() throws IOException {
        shortestPathManager.setProfileAlgorithm("FLOYD_WARSHALL");
        var generator = new Random(1);
        var output = new StringBuilder();
        output.append("DurationInNanoseconds;MemoryUsage\n");

        for(int i = 0; i < 100; i++){
            var starts = System.nanoTime();
            shortestPathManager.shortestPathWithNewProfile(building[generator.nextInt(building.length)], building[generator.nextInt(building.length)], true, true, true, true, "FLOYD_WARSHALL");
            var ends = System.nanoTime();
            output.append(ends-starts + ";" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) + "\n");
        }
        var fwr = new FileWriter("floydwarshallTestNewProfileEveryCall.txt");
        var pwr = new PrintWriter(fwr);
        pwr.write(output.toString());
        pwr.close();
        fwr.close();

    }

    @Test
    public void TestFloydSameStart() throws IOException {
        shortestPathManager.setProfileAlgorithm("FLOYD_WARSHALL");
        var generator = new Random(1);
        String start = building[generator.nextInt(building.length)];
        var output = new StringBuilder();
        output.append("DurationInNanoseconds;MemoryUsage\n");

        for(int i = 0; i < 100000; i++){
            var starts = System.nanoTime();
            shortestPathManager.shortestPathWithOwnProfile(start, building[generator.nextInt(building.length)]);
            var ends = System.nanoTime();
            output.append(ends-starts + ";" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) + "\n");
        }
        var fwr = new FileWriter("floydwarshallSameStart.txt");
        var pwr = new PrintWriter(fwr);
        pwr.write(output.toString());
        pwr.close();
        fwr.close();

    }

    @Test
    public void TestDijkstraSameStart() throws IOException {
        shortestPathManager.setProfileAlgorithm("DIJKSTRA");
        var generator = new Random(1);
        String start = building[generator.nextInt(building.length)];
        var output = new StringBuilder();
        output.append("DurationInNanoseconds;MemoryUsage\n");

        for(int i = 0; i < 100000; i++){
            var starts = System.nanoTime();
            shortestPathManager.shortestPathWithOwnProfile(start, building[generator.nextInt(building.length)]);
            var ends = System.nanoTime();
            output.append(ends-starts + ";" + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) + "\n");
        }
        var fwr = new FileWriter("dijkstraTestSameStart.txt");
        var pwr = new PrintWriter(fwr);
        pwr.write(output.toString());
        pwr.close();
        fwr.close();

    }
}