package org.matsim.Homework;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

import java.io.IOException;

public class RunEventHandler {

    public static void main(String[] args) throws IOException {

        //Base scenario
        String inputFile = "C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pctBaseScenario/berlin-v5.5-1pct.output_events.xml.gz";


        //Changed scenario 1 with both u9 and new U-Bahn Line
        //String inputFile = "C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct-SCENAR1_reduced_lanes-freq_U9-new_UBahn/berlin-v5.5-1pct.output_events.xml.gz";


        EventsManager eventsManager = EventsUtils.createEventsManager();

        eventhandlerBundesalleeStreet eventHandler = new eventhandlerBundesalleeStreet();
        eventsManager.addHandler(eventHandler);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        eventHandler.printVehicle();

        eventHandler.writeVehicleID();

    }
}
