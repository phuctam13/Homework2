package org.matsim.Homework;

        import org.apache.log4j.Logger;
        import org.matsim.core.network.algorithms.NetworkCleaner;
        import org.matsim.api.core.v01.Id;
        import org.matsim.api.core.v01.Scenario;
        import org.matsim.api.core.v01.network.Link;
        import org.matsim.api.core.v01.network.Network;
        import org.matsim.api.core.v01.network.NetworkWriter;
        import org.matsim.api.core.v01.network.Node;
        import org.matsim.core.config.Config;
        import org.matsim.core.config.ConfigUtils;
        import org.matsim.core.config.groups.VspExperimentalConfigGroup;
        import org.matsim.core.controler.Controler;
        import org.matsim.core.controler.OutputDirectoryHierarchy;
        import org.matsim.core.population.routes.NetworkRoute;
        import org.matsim.core.population.routes.RouteUtils;
        import org.matsim.core.utils.collections.CollectionUtils;
        import org.matsim.pt.transitSchedule.api.*;
        import org.matsim.pt.utils.TransitScheduleValidator;
        import org.matsim.vehicles.Vehicle;
        import org.matsim.vehicles.VehicleType;
        import org.matsim.vehicles.VehiclesFactory;

        import java.util.ArrayList;
        import java.util.Collections;

        import static org.apache.commons.collections.CollectionUtils.size;

public class homework2_benoit {
    /*
    //Logger will write info in the console
    private final static Logger LOG = Logger.getLogger(homework2_benoit.class);

    public static void main(String[] args) {
        for (String arg : args) {
            LOG.info( arg );
        }

        if ( args.length==0 ) {
            args = new String[] {"input/berlin-v5.5-1pct.config.xml"}  ; //config file scenario
        }
        Config config = RunBerlinScenario.prepareConfig( args ) ;
        // setting config parameters
        //config.network().setInputFile("berlin-v5.5-network.xml.gz");
        config.controler().setOutputDirectory("output-berlin-v5.5-1pct");
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        config.controler().setLastIteration(50);
        //config.plansCalcRoute().setInsertingAccessEgressWalk( true );

        // trying to bypass out of memory & consistency errors
        config.vspExperimental().setVspDefaultsCheckingLevel(VspExperimentalConfigGroup.VspDefaultsCheckingLevel.ignore);
        ConfigUtils.addOrGetModule(config, BerlinExperimentalConfigGroup.class).setPopulationDownsampleFactor(0.1);

        // loading scenario
        Scenario scenario = RunBerlinScenario.prepareScenario( config ) ;
        RunHomework2.networkModifier(scenario.getNetwork());

        //=== HW2 STARTING FROM THERE
        // Define

        TransitScheduleFactory tsf = scenario.getTransitSchedule().getFactory();
        VehiclesFactory vf = scenario.getVehicles().getFactory();

        TransitSchedule transitSchedule = scenario.getTransitSchedule();
        TransitLine transitLine = transitSchedule.getTransitLines().get(Id.create("U9---17526_400", TransitLine.class));

        TransitRoute route = transitLine.getRoutes().get(Id.create("U9---17526_400_0", TransitRoute.class));

        // REMOVES ORIGINAL DEPARTURES ON 17526_400_0 U-BAHN


        ArrayList<Departure> ald = new ArrayList<>(route.getDepartures().values());
        for (Departure value : ald) {
            route.removeDeparture(value);
        }

        // ADDS NEW DEPARTURES TO ROUTE U-BAHN Bundesallee
        int frecuency = 4;
        int numberDepartures = (24*60)/frecuency;     // 24h * 60min / 4min --> departures every 4 min
        //double time = (10 * 60. + 45) * 60.;    // 10:45:00
        double time = 0;
        int newDepID = 83339381;    // invented
        int newVehID = 80000;       // invented

        for(int i=0; i<numberDepartures; i++){
            String newDepartureID = Integer.toString(newDepID);
            Departure departure = tsf.createDeparture(Id.create(newDepartureID, Departure.class), time);

            //Association of the departure with a vehicle [vehicleRefId]
            Id<Vehicle> vehId = Id.createVehicleId("tr_" + newVehID); // creating a vehicle             // CHANGE DEPENDING BUS OR U-BAHN
            VehicleType vehicleType = scenario.getTransitVehicles().getVehicleTypes().get(Id.create("U-Bahn_veh_type", VehicleType.class));
            Vehicle vehicle = vf.createVehicle(vehId, vehicleType);
            scenario.getTransitVehicles().addVehicle(vehicle);      // adds new vehicles yo transitVehicles file

            newDepID++;
            time+=frecuency*60; // every 4 min
            newVehID++;

            departure.setVehicleId(vehId);
            route.addDeparture(departure);}

        // NETWORK CREATION
        //creating a list of nodes to connect with new links in the direction East
        ArrayList<Node> nodesNewLineEast = new ArrayList<>();
        nodesNewLineEast.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101000010", Node.class))); //Breitenbachplatz (U-Bahn)
        nodesNewLineEast.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101000093", Node.class))); //Walter Schreiber Platz (U-Bahn)
        nodesNewLineEast.add(scenario.getNetwork().getNodes().get(Id.create("pt_060063101841", Node.class))); //Feuerbachstrasse (S-Bahn)
        nodesNewLineEast.add(scenario.getNetwork().getNodes().get(Id.create("pt_060058102521", Node.class))); //Priesterweg (S-Bahn)
        nodesNewLineEast.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101002392", Node.class))); //Ullsteinstrasse (U-Bahn)
        nodesNewLineEast.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101003046", Node.class))); //Blaschkoallee (U-Bahn)
        //creating a list of nodes to connect with new links in the direction West
        ArrayList<Node> nodesNewLineWest = new ArrayList<>();
        nodesNewLineWest.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101003046", Node.class))); //Blaschkoallee (U-Bahn)
        nodesNewLineWest.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101002392", Node.class))); //Ullsteinstrasse (U-Bahn)
        nodesNewLineWest.add(scenario.getNetwork().getNodes().get(Id.create("pt_060058102521", Node.class))); //Priesterweg (S-Bahn)
        nodesNewLineWest.add(scenario.getNetwork().getNodes().get(Id.create("pt_060063101841", Node.class))); //Feuerbachstrasse (S-Bahn)
        nodesNewLineWest.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101000093", Node.class))); //Walter Schreiber Platz (U-Bahn)
        nodesNewLineWest.add(scenario.getNetwork().getNodes().get(Id.create("pt_070101000010", Node.class))); //Breitenbachplatz (U-Bahn)

        // creating a list of links to the East & setting links parameters
        ArrayList<Id<Link>> linkIdsNewLineEast = new ArrayList<>();
        for (int ii=0; ii<=size(nodesNewLineEast)-2; ii++){
            Node node1 = nodesNewLineEast.get(ii);
            Node node2 = nodesNewLineEast.get(ii+1);
            scenario.getNetwork().addLink(scenario.getNetwork().getFactory().createLink(Id.createLinkId("linkNewLineEast"+(ii+1)), node1, node2));
            scenario.getNetwork().getLinks().get(Id.get("linkNewLineEast"+(ii+1), Link.class)).setAllowedModes(Collections.singleton("pt"));
            scenario.getNetwork().getLinks().get(Id.get("linkNewLineEast"+(ii+1), Link.class)).setFreespeed(16.67);
            scenario.getNetwork().getLinks().get(Id.get("linkNewLineEast"+(ii+1), Link.class)).setCapacity(100000.0);
            linkIdsNewLineEast.add(scenario.getNetwork().getLinks().get(Id.createLinkId("linkNewLineEast"+(ii+1))).getId());        }
        // creating a list of links to the West & setting links parameters
        ArrayList<Id<Link>> linkIdsNewLineWest = new ArrayList<>();
        for (int ii=0; ii<=size(nodesNewLineWest)-2; ii++){
            Node node1 = nodesNewLineWest.get(ii);
            Node node2 = nodesNewLineWest.get(ii+1);
            scenario.getNetwork().addLink(scenario.getNetwork().getFactory().createLink(Id.createLinkId("linkNewLineWest"+(ii+1)), node1, node2));
            scenario.getNetwork().getLinks().get(Id.get("linkNewLineWest"+(ii+1), Link.class)).setAllowedModes(Collections.singleton("pt"));
            scenario.getNetwork().getLinks().get(Id.get("linkNewLineWest"+(ii+1), Link.class)).setFreespeed(16.67);
            scenario.getNetwork().getLinks().get(Id.get("linkNewLineWest"+(ii+1), Link.class)).setCapacity(100000.0);
            linkIdsNewLineWest.add(scenario.getNetwork().getLinks().get(Id.createLinkId("linkNewLineWest"+(ii+1))).getId());}

        //creating a list of transitStopFacilities for Eastline
        ArrayList<TransitStopFacility> transitStopFacilitiesToCopyEast = new ArrayList<>();
        transitStopFacilitiesToCopyEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101000010", TransitStopFacility.class)));
        transitStopFacilitiesToCopyEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101001100", TransitStopFacility.class)));
        transitStopFacilitiesToCopyEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101001392", TransitStopFacility.class)));
        transitStopFacilitiesToCopyEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101002392", TransitStopFacility.class)));
        transitStopFacilitiesToCopyEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101000010", TransitStopFacility.class)));
        transitStopFacilitiesToCopyEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070201075301", TransitStopFacility.class)));
        //creating a list of transitStopFacilities for Eastline
        ArrayList<TransitStopFacility> transitStopFacilitiesToCopyWest = new ArrayList<>();
        transitStopFacilitiesToCopyWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070201075301", TransitStopFacility.class)));
        transitStopFacilitiesToCopyWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101000010", TransitStopFacility.class)));
        transitStopFacilitiesToCopyWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101002392", TransitStopFacility.class)));
        transitStopFacilitiesToCopyWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101001392", TransitStopFacility.class)));
        transitStopFacilitiesToCopyWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101001100", TransitStopFacility.class)));
        transitStopFacilitiesToCopyWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("070101000010", TransitStopFacility.class)));

        //Adding each transit Stop to the TransitSchedule and to a transitStopFacilities list for East
        ArrayList<TransitStopFacility> newTransitStopFacilitiesEast = new ArrayList<>();
        // ading the 5 first stops
        for (int ii=0; ii<transitStopFacilitiesToCopyEast.size()-2;ii++) {
            scenario.getTransitSchedule().addStopFacility(scenario.getTransitSchedule().getFactory().createTransitStopFacility(Id.create("newStopFacilityEast"+(ii+1),
                    TransitStopFacility.class),
                    transitStopFacilitiesToCopyEast.get(ii).getCoord(), transitStopFacilitiesToCopyEast.get(ii).getIsBlockingLane()));
            scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(ii+1), TransitStopFacility.class)).
                    setLinkId(linkIdsNewLineEast.get(ii));
            scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(ii+1), TransitStopFacility.class)).
                    setName(transitStopFacilitiesToCopyEast.get(ii).getName());
            scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(ii+1), TransitStopFacility.class)).
                    setStopAreaId(transitStopFacilitiesToCopyEast.get(ii).getStopAreaId());
            newTransitStopFacilitiesEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(ii+1), TransitStopFacility.class)));}
        // adding the last stop (on the SAME LAST LINK)
        scenario.getTransitSchedule().addStopFacility(scenario.getTransitSchedule().getFactory().createTransitStopFacility(Id.create("newStopFacilityEast"+(5+1),
                TransitStopFacility.class),
                transitStopFacilitiesToCopyEast.get(5).getCoord(), transitStopFacilitiesToCopyEast.get(5).getIsBlockingLane()));
        scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(5+1), TransitStopFacility.class)).
                setLinkId(linkIdsNewLineEast.get(4));
        scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(5+1), TransitStopFacility.class)).
                setName(transitStopFacilitiesToCopyEast.get(5).getName());
        scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(5+1), TransitStopFacility.class)).
                setStopAreaId(transitStopFacilitiesToCopyEast.get(5).getStopAreaId());
        newTransitStopFacilitiesEast.add(scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityEast"+(5+1), TransitStopFacility.class)));
        //Adding each transit Stop to the TransitSchedule and to a transitStopFacilities list for West
        ArrayList<TransitStopFacility> newTransitStopFacilitiesWest = new ArrayList<>();
        //adding 1st stop
        scenario.getTransitSchedule().addStopFacility(scenario.getTransitSchedule().getFactory().createTransitStopFacility(Id.create("newStopFacilityWest"+(1),
                TransitStopFacility.class),
                transitStopFacilitiesToCopyWest.get(0).getCoord(), transitStopFacilitiesToCopyWest.get(0).getIsBlockingLane()));
        scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(1), TransitStopFacility.class)).
                setLinkId(linkIdsNewLineWest.get(0));
        scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(1), TransitStopFacility.class)).
                setName(transitStopFacilitiesToCopyWest.get(0).getName());
        scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(1), TransitStopFacility.class)).
                setStopAreaId(transitStopFacilitiesToCopyWest.get(0).getStopAreaId());
        newTransitStopFacilitiesWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(1), TransitStopFacility.class)));
        // adding the 5 others
        for (int ii=1; ii<transitStopFacilitiesToCopyWest.size()-2;ii++) {
            scenario.getTransitSchedule().addStopFacility(scenario.getTransitSchedule().getFactory().createTransitStopFacility(Id.create("newStopFacilityWest"+(ii+1),
                    TransitStopFacility.class),
                    transitStopFacilitiesToCopyWest.get(ii).getCoord(), transitStopFacilitiesToCopyWest.get(ii).getIsBlockingLane()));
            scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(ii+1), TransitStopFacility.class)).
                    setLinkId(linkIdsNewLineWest.get(ii));
            scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(ii+1), TransitStopFacility.class)).
                    setName(transitStopFacilitiesToCopyWest.get(ii).getName());
            scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(ii+1), TransitStopFacility.class)).
                    setStopAreaId(transitStopFacilitiesToCopyWest.get(ii).getStopAreaId());
            newTransitStopFacilitiesWest.add(scenario.getTransitSchedule().getFacilities().get(Id.get("newStopFacilityWest"+(ii+1), TransitStopFacility.class)));}

        //creating list of transit route stops East
        ArrayList<TransitRouteStop> listTransitRouteStopEast = new ArrayList<>();
        for (int ii=0; ii<=size(newTransitStopFacilitiesEast)-1; ii++){
            TransitRouteStop newTransitRouteStop = scenario.getTransitSchedule().getFactory().createTransitRouteStop(newTransitStopFacilitiesEast.get(ii), 0.,0.);
            listTransitRouteStopEast.add(newTransitRouteStop);        }
        //creating list of transit route stops West
        ArrayList<TransitRouteStop> listTransitRouteStopWest = new ArrayList<>();
        for (int ii=0; ii<=size(newTransitStopFacilitiesWest)-1; ii++){
            TransitRouteStop newTransitRouteStop = scenario.getTransitSchedule().getFactory().createTransitRouteStop(newTransitStopFacilitiesWest.get(ii), 0.,0.);
            listTransitRouteStopWest.add(newTransitRouteStop);        }

        // creating network routes
        NetworkRoute networkRouteEast = RouteUtils.createNetworkRoute(linkIdsNewLineEast, scenario.getNetwork());
        NetworkRoute networkRouteWest = RouteUtils.createNetworkRoute(linkIdsNewLineWest, scenario.getNetwork());

        //creating new East transit route and setting params
        TransitRoute newTransitRouteUBahnEast = scenario.getTransitSchedule().getFactory().createTransitRoute(Id.create("newTransitRouteUBahnEast",
                TransitRoute.class), networkRouteEast, listTransitRouteStopEast,"pt");
        newTransitRouteUBahnEast.setTransportMode("rail");
        //creating new West transit route and setting params
        TransitRoute newTransitRouteUBahnWest = scenario.getTransitSchedule().getFactory().createTransitRoute(Id.create("newTransitRouteUBahnWest",
                TransitRoute.class), networkRouteWest, listTransitRouteStopWest,"pt");
        newTransitRouteUBahnWest.setTransportMode("rail");

        //creating new transit line and adding route
        TransitLine newTransitLineUBahn = scenario.getTransitSchedule().getFactory().createTransitLine(Id.create("newTransitLineUBahn", TransitLine.class));
        newTransitLineUBahn.addRoute(newTransitRouteUBahnEast);
        newTransitLineUBahn.addRoute(newTransitRouteUBahnWest);
        scenario.getTransitSchedule().addTransitLine(newTransitLineUBahn);

        // creating schedule legs for East line
        for (double ii = (4*60.+30)*60.; ii<(23*60.+59)*60.; ii = ii+(4*60.)) {
            Departure departure = tsf.createDeparture(Id.create("newDep"+ii, Departure.class), ii);
            Vehicle vehicle = vf.createVehicle(Id.createVehicleId("newVeh"+ii), scenario.getTransitVehicles().getVehicleTypes().get(Id.create("U-Bahn_veh_type", VehicleType.class)));
            scenario.getTransitVehicles().addVehicle(vehicle);
            departure.setVehicleId(vehicle.getId());
            newTransitRouteUBahnEast.addDeparture(departure);
        }
        // creating schedule legs for West line
        for (double ii = (5*60.)*60.; ii<(23*60.+59)*60.; ii = ii+(4*60.)) {
            Departure departure = tsf.createDeparture(Id.create("newDepWest"+ii, Departure.class), ii);
            Vehicle vehicle = vf.createVehicle(Id.createVehicleId("newVeh"+ii), scenario.getTransitVehicles().getVehicleTypes().get(Id.create("U-Bahn_veh_type", VehicleType.class)));
            scenario.getTransitVehicles().addVehicle(vehicle);
            departure.setVehicleId(vehicle.getId());
            newTransitRouteUBahnWest.addDeparture(departure);
        }

        // Testing TranqitSchedule & network
        TransitScheduleWriter tsw = new TransitScheduleWriter(scenario.getTransitSchedule());
        tsw.writeFile("transitScheduleTEST.xml");
        NetworkWriter nww = new NetworkWriter(scenario.getNetwork());
        nww.write("newNetwork.xml");
        System.out.println("PRINTING");

        // validator
        TransitScheduleValidator.ValidationResult result = TransitScheduleValidator.validateAll(scenario.getTransitSchedule(), scenario.getNetwork());
        for (String error : result.getErrors()) {
            LOG.warn(error);
        }
        for (String warning : result.getWarnings()) {
            LOG.warn(warning);
        }
        for (TransitScheduleValidator.ValidationResult.ValidationIssue issue : result.getIssues()) {
            LOG.warn(issue.getMessage());
        }


        //new NetworkCleaner().run(scenario.getNetwork());
        //===
        Controler controler = RunBerlinScenario.prepareControler( scenario ) ;

        controler.run();
    }

    public static void networkModifier(Network network){
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

        for(int i =0; i<links.length-1;i++){
            String linkToString = String.valueOf(links[i]);
            network.getLinks().get(Id.get(linkToString, Link.class)).setAllowedModes(CollectionUtils.stringToSet("freight"));
        }
    }


     */
}
