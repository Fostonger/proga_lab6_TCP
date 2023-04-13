package utils;

import consoleReader.CommandReaderable;
import data.Route;
import utils.serverReaderWriter.ServerCommandReadable;

import java.time.LocalDate;
import java.util.Map;

public class ServerRouteFactory implements RouteCreatable {
    private RouteCreationContainer container;
    public ServerRouteFactory() {}

    public void setContainer(RouteCreationContainer container) {
        this.container = container;
    }
    @Override
    public Route createRoute(Map<Integer, Route> ids) {
        int id = 1;
        while(ids.containsKey(id)) id++;
        LocalDate currentDate = LocalDate.now();
        return new Route(id, container.getCoordinates(), currentDate,
                container.getDistance(), container.getTo(), container.getFrom(), container.getName());

    }
}
