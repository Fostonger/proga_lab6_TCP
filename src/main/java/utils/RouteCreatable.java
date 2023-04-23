package utils;

import data.Route;

import java.util.Map;

public interface RouteCreatable {
    /**
     * Creates route by reading values from input stream
     * @param ids map of ids to get new unique id
     * @return new route
     */
    public Route createRoute(int id);
}
