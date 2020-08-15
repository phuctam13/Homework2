package org.matsim.Homework;

import java.io.*;
import java.util.ArrayList;

public class convertFile {
    public static void main(String[] args ) throws IOException {

        ArrayList<String> test = new ArrayList<String>();


        BufferedReader br = new BufferedReader(new FileReader("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput/PersonIDCarsonbundesalleeBaseScenario.txt"));
        String line = null;

        FileWriter fw = new FileWriter("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput//ConvertedFile.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            /*for (String str : values) {
                System.out.println(str);
            }*/
            for (int i=0; i < values.length; i++) {
                test.add(values[i]);
                bw.write(values[i]);
                bw.newLine();
            }
        }
        br.close();












        bw.close();
    }
}
