package org.matsim.Homework;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class eventhandlerBundesalleeStreet implements LinkEnterEventHandler {
    int[] links = {150439, 36874,
            77388, 81239,
            77387, 81240,
            77386 ,153659,
            77382 ,153660,
            77391, 78198,
            13016, 133604,
            13015, 133605,
            13014, 133606,
            99280, 49245,
            78199, 99226,
            99312, 99257 ,144206, 144221,
            48013, 144197 ,99236, 99266,
            111755, 144198 ,144196, 48015,
            111754,
            144199, 144198, 144196, 48016,
            144193, 77323, 144178, 144190,
            144192,
            72173, 77389,
            77390,
            144179, 102671,
            62494, 77379,
            91121, 62499,
            152708, 152713,
            62634, 62495, 98340, 143455,
            62633, 98343, 62509, 143455,
            62632, 98343, 62509, 143456,
            98316, 62502,
            46610, 149298,
            46609, 147020,
            147142, 147052,
            28917, 28916,
            157336, 8008,
            157335, 8009,
            157334, 8010,
            151527, 15733,
            8007, 835
    };


    Set<String> affectedAgents = new HashSet<>();

    @Override
    public void handleEvent(LinkEnterEvent linkEnterEvent) {

        for(int i =0; i<links.length-1;i++){
            String linkToString = String.valueOf(links[i]);
            if(linkEnterEvent.getLinkId().equals(Id.createLinkId(linkToString))){
                System.out.println("link ID" + linkToString);
                System.out.println("car ID" +linkEnterEvent.getVehicleId().toString());
                System.out.println(linkEnterEvent.getVehicleId().toString());
                affectedAgents.add(linkEnterEvent.getVehicleId().toString());

            }
        }
    }

    public void printVehicle(){
        System.out.println(affectedAgents.size());
    }

    public  void writeVehicleID () throws IOException {
        FileWriter fw = new FileWriter("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput//PersonIDCarsonbundesalleeBaseTest.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(affectedAgents.toString());
        bw.newLine();

        bw.close();
    }
}
