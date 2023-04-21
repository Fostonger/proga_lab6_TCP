package utils;

import data.Route;
import database.IdGenerator;
import org.apache.logging.log4j.Logger;
import session.Session;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class ServerRouteFactory implements RouteCreatable {
    private RouteCreationContainer container;
    private Session session;
    public ServerRouteFactory() {}

    public void setContainer(RouteCreationContainer container) {
        this.container = container;
    }
    public void setSession(Session session) {
        this.session = session;
    }
    @Override
    public Route createRoute(int id) {
        LocalDate currentDate = LocalDate.now();
        return new Route(id, container.getCoordinates(), currentDate,
                container.getDistance(), container.getTo(), container.getFrom(), container.getName(), session.getName());

    }
}
