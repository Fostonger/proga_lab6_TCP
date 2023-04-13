package utils;

import consoleReader.PropertiesReceivable;
import data.Coordinates;
import data.Location;
import data.Route;

import java.time.LocalDate;
import java.util.Map;

public class RouteFactory implements RouteCreatable{
    PropertiesReceivable propertiesReceiver;

    /**
     * crates routes by reading from given input stream
     * @param propertiesReceiver stream to read from
     */
    public RouteFactory(PropertiesReceivable propertiesReceiver) {
        this.propertiesReceiver = propertiesReceiver;
    }
    public Route createRoute(Map<Integer, Route> ids) {
        int id = 1;
        while(ids.containsKey(id)) id++;
        LocalDate currentDate = LocalDate.now();
        Coordinates coordinates = propertiesReceiver.getCoordinatesFromInput("coordinates:\n");
        Location to = propertiesReceiver.getLocationFromInput("location \"to\":\n", false);
        Location from = propertiesReceiver.getLocationFromInput("location \"from\":\n", true);
        double distance = propertiesReceiver.getDoubleBiggerThanOneFromInput("distance:\t");
        String name = propertiesReceiver.getNotNullStringFromInput("name:\t\t");
        return new Route(id, coordinates, currentDate, distance, to, from, name);
    }
}
