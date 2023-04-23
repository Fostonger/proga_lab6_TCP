package utils;

import data.Route;

import java.util.Map;

public interface ClientRouteCreatable extends RouteCreatable {
    /**
     * Returns filled RouteCreationContainer, which was filled by executed command
     * @return new container
     */
    public RouteCreationContainer getContainer();
}
