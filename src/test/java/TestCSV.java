import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCSV {

    private class TestData {
        String title;
        String Name;
        Map<String, Integer> Neighbours = new HashMap<>();

        @Override
        public String toString() {
            return "Title: " + title + " Name: " + " Neighbours: " + Neighbours.toString();
        }
    }


    @Test
    public void ReadCSVTest() {
        String demoPath = "C:\\xampp\\demo.csv";
        String line = "";
        String splitChar1 = ";";
        String splitChar2 = ",";


        List<TestData> testData = new ArrayList<>();
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(demoPath));
            boolean firstRound = true;
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                if (firstRound) {
                    firstRound = false;
                    continue;
                }

                String[] lineContent = line.split(splitChar1);    // use ; as separator

                var currentTestData = new TestData();
                currentTestData.title = lineContent[0];
                currentTestData.Name = lineContent[1];

                String[] ArrayContent = lineContent[2].split(splitChar2);

                int i = 0;
                int j = 1;

                for (i = 0; j < ArrayContent.length; i = i+2) {
                    currentTestData.Neighbours.put(ArrayContent[i], Integer.parseInt(ArrayContent[j]));
                    j=j+2;
                }

                testData.add(currentTestData);
            }
        } catch (  IOException e) {
            e.printStackTrace();
        }

        for (var data :  testData) {
            System.out.println(data.toString());
        }
    }
}