package utils;

import data.Coordinates;
import data.Location;

import java.io.Serializable;

public class RouteCreationContainer implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Location from; //Поле может быть null
    private Location to; //Поле не может быть null
    private double distance; //Значение поля должно быть больше 1

    /**
     * Base class that is stored in database
     * @param coordinates coordinates of the route, nonnull parameter
     * @param distance distance of the route
     * @param to location "to" of the route, cannot be null
     * @param from location "from" of the route, can be null
     * @param name name of the route, nonnull parameter
     */
    public RouteCreationContainer(
            Coordinates coordinates,
            double distance,
            Location to,
            Location from,
            String name) {
        this.coordinates = coordinates;
        this.distance = distance;
        this.to = to;
        this.from = from;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public double getDistance() {
        return distance;
    }
    public Location getFrom() {
        return from;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Location getTo() {
        return to;
    }
}
