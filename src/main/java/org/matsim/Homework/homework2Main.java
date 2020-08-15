package org.matsim.Homework;

/*
import jdk.internal.org.jline.utils.Log;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.*;
import org.matsim.pt.utils.TransitScheduleValidator;
import org.matsim.vehicles.Vehicle;
import org.matsim.vehicles.VehicleType;
import org.matsim.vehicles.VehiclesFactory;
*/
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.VspExperimentalConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.*;
import org.matsim.pt.utils.TransitScheduleValidator;
import org.matsim.vehicles.Vehicle;
import org.matsim.vehicles.VehicleType;
import org.matsim.vehicles.VehiclesFactory;

import java.util.ArrayList;


public class homework2Main {

    private final static Logger LOG = Logger.getLogger(homework2Main.class);

    public static void main(String[] args) {

        Config config = ConfigUtils.loadConfig("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.config.xml");
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);
        config.controler().setLastIteration(0);

        config.vspExperimental().setVspDefaultsCheckingLevel(VspExperimentalConfigGroup.VspDefaultsCheckingLevel.ignore);

        Scenario scenario = ScenarioUtils.loadScenario(config);

        TransitSchedule transitSchedule = scenario.getTransitSchedule();
        TransitLine railBundesallee = transitSchedule.getTransitLines().get(Id.create("X9---17528_700", TransitLine.class));

        TransitRoute route0 = railBundesallee.getRoutes().get(Id.create("X9---17528_700_25", TransitRoute.class));

        //TransitScheduleFactory tsf = transitSchedule.getFactory();
        //Departure departure =tsf.createDeparture(Id.create("newDep", Departure.class), (8*60. + 45)*60.);

        //route0.addDeparture(departure);


        //scenario.getTransitSchedule().getTransitLines().get(Id.create("newLink01", TransitLine.class)).removeRoute();

        //route0.addDeparture(departure);



        ArrayList<Departure> arr = new ArrayList<Departure>();
        for (Departure departureTime : route0.getDepartures().values()) {
            arr.add(departureTime);
        }

        for(int i =0; i<arr.size();i++){
            route0.removeDeparture(arr.get(i));
        }
        //route0.removeDeparture(departureTime);






        // validator
        TransitScheduleValidator.ValidationResult result = TransitScheduleValidator.validateAll(transitSchedule, scenario.getNetwork());
        for (String error : result.getErrors()) {
            LOG.warn(error);
        }
        for (String warning : result.getWarnings()) {
            LOG.warn(warning);
        }
        for (TransitScheduleValidator.ValidationResult.ValidationIssue issue : result.getIssues()) {
            LOG.warn(issue.getMessage());
        }

        // association of the departure with a vehicle
        /*
        VehiclesFactory vf = scenario.getVehicles().getFactory(); //creating veh factory
        Id<Vehicle> vehId = Id.createVehicleId("tr_3"); // creating a vehicle
        VehicleType smallTrain = scenario.getTransitVehicles().getVehicleTypes().get(Id.create("1", VehicleType.class));
        Vehicle vehicle = vf.createVehicle(vehId,smallTrain);
        scenario.getTransitVehicles().addVehicle(vehicle);
        */
        //departure.setVehicleId(vehId);

        //TransitScheduleWriter tsw = new TransitScheduleWriter(transitSchedule);
        //tsw.writeFile("scenarios/pt-tutorial/transitSchedule.xml");

        Controler controler = new Controler(scenario);

        controler.run();
    }
}