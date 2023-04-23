package commands;

import data.Route;
import queueManager.PriorityQueueManageable;
import utils.RouteCreatable;

import java.sql.SQLException;

/**
 * add_if_max command; adds value if its distance is greater than the max of the collection
 */
public class AddIfMax extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    private final RouteCreatable routeFactory;
    /**
     * @param queueManager manager to add to
     * @param routeFactory factory which will be used to create new route
     */
    public AddIfMax(PriorityQueueManageable queueManager, RouteCreatable routeFactory) {
        super("add_if_max", "add_if_max {element}","adds element if it is greater than max distance of the collection");
        this.queueManager = queueManager;
        this.routeFactory = routeFactory;
    }
    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            Route newRoute = routeFactory.createRoute(queueManager.generateId());
            try {
                if (queueManager.addRouteIfMax(newRoute)) {
                    return "successfully added new route!\n";
                }
            } catch (SQLException e) {
                return "couldn't add route: " + e.getMessage() + "\n";
            }
            return "the route is not greater than the greatest of the collection\n";
        } else {
            return "invalid parameter\n";
        }
    }
}
