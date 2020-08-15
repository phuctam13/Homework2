package org.matsim.Homework;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonEntersVehicleEvent;
import org.matsim.api.core.v01.events.handler.PersonEntersVehicleEventHandler;
import org.matsim.core.api.experimental.events.AgentWaitingForPtEvent;
import org.matsim.core.api.experimental.events.handler.AgentWaitingForPtEventHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class waitingForPTEventHandler implements AgentWaitingForPtEventHandler, PersonEntersVehicleEventHandler {

    String[] links = {
            "070201093802",
            "070201093704",
            "070201093602",
            "070201093502",
            "070201093402",
            "070201093302",
            "070201093202",
            "070201093102",
            "070201093002",
            "070201092902",
            "070201092802",
            "070201092702",
            "070201092602",
            "070201092502",
            "070201092402",
            "070201092302",
            "070201092202",
            "070201092102"

    };

    Set<String> affectedAgents = new HashSet<>();
    public double totalWaitingTime = 0;


    public void printVehicle(){

        System.out.println(affectedAgents.size());
    }


    @Override
    public void handleEvent(AgentWaitingForPtEvent agentWaitingForPtEvent) {
        for(int i =0; i<links.length-1;i++){
            if(agentWaitingForPtEvent.waitingAtStopId.equals(Id.createLinkId(links[i]))){
                System.out.println("link ID " + links[i]);
                System.out.println("Person ID " +agentWaitingForPtEvent.getPersonId().toString());
                affectedAgents.add(agentWaitingForPtEvent.getPersonId().toString());

            }
        }
    }

    @Override
    public void handleEvent(PersonEntersVehicleEvent personEntersVehicleEvent) {

    }
}
