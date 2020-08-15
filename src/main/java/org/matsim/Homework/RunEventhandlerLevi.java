package org.matsim.Homework;

        import org.matsim.core.api.experimental.events.EventsManager;
        import org.matsim.core.events.EventsUtils;
        import org.matsim.core.events.MatsimEventsReader;
        import org.xml.sax.SAXException;

        import javax.xml.parsers.ParserConfigurationException;
        import java.io.IOException;


public class RunEventhandlerLevi  {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        // Original
        //String inputFile = "C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pctBaseScenario/berlin-v5.5-1pct.output_events.xml.gz";

        // New
        String inputFile = "C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct-SCENAR1_reduced_lanes-freq_U9-new_UBahn/berlin-v5.5-1pct.output_events.xml.gz";

        // To print in File
        String agentsNewUBahn = "scenarios/berlin-v5.2-1pct/output/affectedAgentsNewUBahn.txt";

        EventsManager eventsManager = EventsUtils.createEventsManager();

        distanceTimeLevi eventHandler = new distanceTimeLevi(/*agentsNewUBahn*/);
        eventsManager.addHandler(eventHandler);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        eventHandler.print();
        //eventHandler.print2();

    }
}