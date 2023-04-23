package utils;

import consoleReader.PropertiesReceivable;
import data.Coordinates;
import data.Location;
import data.Route;

import java.time.LocalDate;
import java.util.Map;

public class ClientRouteFactory implements ClientRouteCreatable, RouteCreatable {
    private final PropertiesReceivable propertiesReceiver;
    private RouteCreationContainer container;
    /**
     * crates routes by reading from given input stream
     * @param propertiesReceiver stream to read from
     */
    public ClientRouteFactory(PropertiesReceivable propertiesReceiver) {
        this.propertiesReceiver = propertiesReceiver;
    }
    public Route createRoute(int id) {
        Coordinates coordinates = propertiesReceiver.getCoordinatesFromInput("coordinates:\n");
        Location to = propertiesReceiver.getLocationFromInput("location \"to\":\n", false);
        Location from = propertiesReceiver.getLocationFromInput("location \"from\":\n", true);
        double distance = propertiesReceiver.getDoubleBiggerThanOneFromInput("distance:\t");
        String name = propertiesReceiver.getNotNullStringFromInput("name:\t\t");
        container = new RouteCreationContainer(coordinates, distance, to, from, name);
        return null;
    }

    @Override
    public RouteCreationContainer getContainer() {
        return container;
    }
}
