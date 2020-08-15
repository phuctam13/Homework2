package org.matsim.Homework;


import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class eventHandlerNewUbahn implements PersonArrivalEventHandler {

    String[] links = {
            "linkNewLineEast1",
            "linkNewLineEast2",
            "linkNewLineEast3",
            "linkNewLineEast4",
            "linkNewLineEast5",
            "linkNewLineWest1",
            "linkNewLineWest2",
            "linkNewLineWest3",
            "linkNewLineWest4",
            "linkNewLineWest5"
    };

    Set<String> affectedAgents = new HashSet<>();

    public void printVehicle(){
        System.out.println(affectedAgents.size());
    }

    @Override
    public void handleEvent(PersonArrivalEvent personArrivalEvent) {

        for(int i =0; i<links.length-1;i++){

            if(personArrivalEvent.getLinkId().equals(Id.createLinkId(links[i]))&& !personArrivalEvent.getPersonId().toString().startsWith("pt")){
                System.out.println("link ID " + links[i]);
                System.out.println("Person ID " +personArrivalEvent.getPersonId().toString());
                affectedAgents.add(personArrivalEvent.getPersonId().toString());

            }
        }
    }

    public  void writeVehicleID () throws IOException {
        FileWriter fw = new FileWriter("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/compareoutput//NewUbahnUserID.txt");

        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(affectedAgents.toString());
        bw.newLine();

        bw.close();
    }

}