package org.matsim.Homework;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.network.io.NetworkWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class networkmodifier {


    public static void main(String[] args) {

        int[] links = {150439, 36874,
                77388, 81239,
                77387, 81240,
                77386, 153659,
                77382, 153660,
                77391, 78198,
                13016, 133604,
                13015, 133605,
                13014, 133606,
                99280, 49245,
                78199, 99226,
                99312, 99257, 144206, 144221,
                48013, 144197, 99236, 99266,
                111755, 144198, 144196, 48015,
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

        Path inputNetwork = Paths.get(args[0]);
        Path outputNetwork = Paths.get(args[1]);

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        for (int i = 0; i < links.length - 1; i++) {
            String linkToString = String.valueOf(links[i]);

            network.getLinks().get(Id.createLinkId(linkToString)).setNumberOfLanes(1);
            //network.getLinks().get(Id.createLinkId(linkToString)).setAllowedModes(Collections.singleton("nothing"));
        }


        new NetworkWriter(network).write(outputNetwork.toString());

    }
}
