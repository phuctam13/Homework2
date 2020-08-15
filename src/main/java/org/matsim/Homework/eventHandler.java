package org.matsim.Homework;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.core.api.experimental.events.AgentWaitingForPtEvent;
import org.matsim.core.api.experimental.events.handler.AgentWaitingForPtEventHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// LinkEnterEventHandler
public class eventHandler implements  PersonArrivalEventHandler  {
    int[] links = {43439,
            43460,
            43474,
            43479,
            40425,
            40439,
            43438,
            43461,
            43473,
            43480,
            40426,
            40438,
            43437,
            43462,
            43472,
            43481,
            43436,
            43463,
            40427,
            40437,
            40428,
            40436,
            40429,
            40435,
            43435,
            43464,
            3835,
            40430,
            40434,
            43434,
            43465,

    };





    Set<String> affectedAgents = new HashSet<>();
/*
    @Override
    public void handleEvent(LinkEnterEvent linkEnterEvent) {

        for(int i =0; i<links.length-1;i++){
            String linkToString = String.valueOf(links[i]);
            String pt_ = "pt_";
            String ptLinkToString = pt_ + linkToString;
            if(linkEnterEvent.getLinkId().equals(Id.createLinkId(ptLinkToString))){
                System.out.println("link ID " + linkToString);
                System.out.println("car ID " +linkEnterEvent.getVehicleId().toString());
                affectedAgents.add(linkEnterEvent.getVehicleId().toString());


            }
        }
    }

 */


    public void printVehicle(){
        System.out.println(affectedAgents.size());
    }

    @Override
    public void handleEvent(PersonArrivalEvent personArrivalEvent) {

        for(int i =0; i<links.length-1;i++){
            String linkToString = String.valueOf(links[i]);
            String pt_ = "pt_";
            String ptLinkToString = pt_ + linkToString;
            if(personArrivalEvent.getLinkId().equals(Id.createLinkId(ptLinkToString))&& !personArrivalEvent.getPersonId().toString().startsWith("pt")){
                System.out.println("link ID " + linkToString);
                System.out.println("Person ID " +personArrivalEvent.getPersonId().toString());
                affectedAgents.add(personArrivalEvent.getPersonId().toString());

            }
        }
    }



    public  void writeVehicleID () throws IOException {
        FileWriter fw = new FileWriter("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput//PersonIDScenario1.txt");

        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(affectedAgents.toString());
        bw.newLine();

        bw.close();
    }


}
