package utils;

import data.Route;
import utils.sessionManager.SessionManageable;

import java.time.LocalDate;

public class ServerRouteFactory implements RouteCreatable {
    private RouteCreationContainer container;
    private SessionManageable session;
    public ServerRouteFactory() {}

    public void setContainer(RouteCreationContainer container) {
        this.container = container;
    }
    public void setSession(SessionManageable session) {
        this.session = session;
    }
    @Override
    public Route createRoute(int id) {
        LocalDate currentDate = LocalDate.now();
        return new Route(id, container.getCoordinates(), currentDate,
                container.getDistance(), container.getTo(), container.getFrom(), container.getName(), session.getName());

    }
}
