package org.matsim.Homework;

        import org.matsim.api.core.v01.Id;
        import org.matsim.api.core.v01.events.*;

        import org.matsim.api.core.v01.events.handler.*;
        import org.matsim.api.core.v01.population.Person;
        import org.w3c.dom.Document;
        import org.w3c.dom.Element;
        import org.w3c.dom.Node;
        import org.w3c.dom.NodeList;
        import org.xml.sax.SAXException;

        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;
        import javax.xml.parsers.ParserConfigurationException;
        import java.io.*;
        import java.util.*;


public class distanceTimeLevi implements PersonDepartureEventHandler, PersonArrivalEventHandler {

    // NEW UBahn
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


    // To print agents in file //

    BufferedWriter bufferedWriter;

    public distanceTimeLevi(String outputFile) throws IOException {
        FileWriter fileWriter = new FileWriter(outputFile);
        bufferedWriter = new BufferedWriter(fileWriter);
    }


    Set<String> affectedAgents = new HashSet<>();
    public Map<String, Double> linkDistance = new HashMap<>();  // LinkId, LinkLength


    // Fill affectedAgents VARIABLE with File of affectedAgentsNewUBahn.txt

    public distanceTimeLevi(/*BufferedWriter bufferedWriter*/) throws IOException, ParserConfigurationException, SAXException {
        File file = new File("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/levi/ConvertedFile.txt");


        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            affectedAgents.add(in.nextLine());
        }

        // Pass all links with length to Map linkDistance READING XML

        //File fileNetwork = new File("scenarios/berlin-v5.2-1pct/input/be_5_network_with-pt-ride-freight.xml");
        File fileNetwork = new File("C:/Users/Thien/Documents/GitHub/Homework2/scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct-SCENAR1_reduced_lanes-freq_U9-new_UBahn/berlin-v5.5-1pct.output_network.xml");

        Scanner in2 = new Scanner(fileNetwork);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(fileNetwork);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("link");

        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                Double distance = Double.parseDouble(eElement.getAttribute("length"));
                linkDistance.put(eElement.getAttribute("id"), distance);
            }
        }

    }


    /*
                 // CALCULATE AFFECTED AGENTS new U-Bahn //
    @Override
    public void handleEvent(PersonDepartureEvent event) {
        for(int i=0; i<links.length-1; i++) {                           // don't include drivers
            if(event.getLinkId().equals(Id.createLinkId(links[i])) && !event.getPersonId().toString().startsWith("pt")) {
                affectedAgents.add(event.getPersonId().toString());
            }
        }
    }

    public void print() throws IOException {
        System.out.println("Size " + affectedAgents.size());
        Iterator it = affectedAgents.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }

       // All Affected Agents to affectedAgentsNewUBahn.txt" //

        Iterator<String> it = affectedAgents.iterator();
        while(it.hasNext()){
            bufferedWriter.write(it.next());
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
    }
    */


    // AFFECTED AGENTS' TOTAL TIME AND DISTANCE IN TRAFFIC //

    public Map<Id<Person>, Double> departureMap = new HashMap<>();
    public double totalTime = 0;
    double totalDistance = 0;

    @Override
    public void handleEvent(PersonDepartureEvent event) {
        if(affectedAgents.contains(event.getPersonId().toString())) {     // Comment for TOTAL
            departureMap.put(event.getPersonId(), event.getTime());     // TIME
            totalDistance+=linkDistance.get(event.getLinkId().toString());      // DISTANCE
        }
    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        if(departureMap.containsKey(event.getPersonId())) {
            totalTime += event.getTime() - departureMap.get(event.getPersonId());
        }
    }


    public void print() throws IOException {

        // TIME
        System.out.println("Affected Agents = " + affectedAgents.size());
        System.out.println("Number of Agents in Map = " + departureMap.keySet().size());
        System.out.println("Total Hours = " + totalTime / 3600);  // Comes in seconds

        // DISTANCE
        System.out.println("Distance in km = " + totalDistance/1000);     // Metros a Kms

    }

    /////////////////////////////////////////////////////////////////////////////////
    // AFFECTED Agents TIME IN TRAFFIC:
    // ORIGINAL --> 71h, 23 agents
    // NEW -->
    /////////////////////////////////////////////////////////////////////////////////
    // TOTAL Agents TIME IN TRAFFIC
    // ORIGINAL --> 134124h, 119363 agents
    // NEW -->
    /////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////
    // AFFECTED Agents DISTANCE IN TRAFFIC:
    // ORIGINAL --> 326km, 23 agents
    // NEW -->
    /////////////////////////////////////////////////////////////////////////////////
    // TOTAL Agents DISTANCE IN TRAFFIC:
    // ORIGINAL --> 274965km, 119363 agents
    // NEW -->
    /////////////////////////////////////////////////////////////////////////////////





    // DISTANCE TRAVELLED OLD VERSION //

    /*
    double totalDistance = 0;

    @Override
    public void handleEvent(LinkEnterEvent event) {
        if(affectedAgents.contains(event.getVehicleId().toString())){
           totalDistance+=linkDistance.get(event.getLinkId().toString());
        }
    }


    public void print2() throws IOException {

        System.out.println("Distance in km = " + totalDistance/1000);     // Metros a Kms

        System.out.println("Original Affected Vehicles = " + affectedAgents.size());

        /////////////////////////////////////////////////////////////////////////////////
        // AFFECTED Vehicles DISTANCE IN TRAFFIC:
        // ORIGINAL -->
        // 150IT -->
        /////////////////////////////////////////////////////////////////////////////////
        // TOTAL Agents DISTANCE IN TRAFFIC:
        // ORIGINAL -->
        // 150IT -->
        /////////////////////////////////////////////////////////////////////////////////

    }
    */


    // ADDITIONAL STUDY // DISTANCE TRAVELLED ONLY IN STREET BY ALL AGENTS BEFORE AND AFTER
    /*
    double totalDistance = 0;
    Set<String> newAffectedVehicles = new HashSet<>();


    @Override
    public void handleEvent(LinkEnterEvent event) {
        for(int i=0; i<links.length-1; i++) {
            String linkToString = String.valueOf(links[i]);                     // Int to String
            if (event.getLinkId().equals(Id.createLinkId(linkToString))) {      // If one of Bundesallee Links
                totalDistance+=linkDistance.get(event.getLinkId().toString());
                newAffectedVehicles.add(event.getVehicleId().toString());
            }
        }
    }


    public void print() throws IOException {

        System.out.println("Distance in km = " + totalDistance/1000);     // Metros a Kms

        System.out.println("Original Affected Vehicles = " + affectedVehicles.size());

        System.out.println("Real Affected Vehicles = " + newAffectedVehicles.size());


    }
    */

}
