package org.matsim.Homework;

import java.io.*;
import java.util.ArrayList;

public class CopareIDs {
    public static void main(String[] args ) throws IOException {

        ArrayList<String> test = new ArrayList<String>();
        ArrayList<String> test2 = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput/PersonIDCarsonbundesalleeBaseScenario.txt"));
        String line = null;


        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            /*for (String str : values) {
                System.out.println(str);
            }*/
            for (int i=0; i < values.length; i++) {
                test.add(values[i]);
            }
        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput/PersonIDCarsonbundesalleeScenario1.txt"));
        String line2 = null;

        while ((line2 = br2.readLine()) != null) {
            String[] values2 = line2.split(",");
            /*for (String str : values2) {
                System.out.println(str);
            }*/
            for (int i=0; i < values2.length; i++) {
                test2.add(values2[i]);
            }
        }

        br2.close();



        ArrayList<String> ar = new ArrayList<String>();


        for(int i = 0; i < test2.size(); i++) {
            if(!test.contains(test2.get(i)))
                ar.add(test2.get(i));
        }
        System.out.println(ar.size());
        System.out.println(ar);

        /*
        for(int i = 0; i < test.size(); i++) {
            if(!test2.contains(test.get(i)))
                ar.add(test.get(i));
        }
        System.out.println(ar.size());
        System.out.println(ar);
        */

        FileWriter fw = new FileWriter("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput//BundesAlleeCarComparebeforeAfter.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(ar.toString());
        bw.newLine();

        bw.close();
    }
}
