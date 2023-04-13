package commands;

import data.Route;
import queueManager.PriorityQueueManageable;
import utils.RouteCreatable;
/**
 * add_if_min command; adds value if its distance is less than the min of the collection
 */
public class AddIfMin extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    private final RouteCreatable routeFactory;
    /**
     * @param queueManager manager to add to
     * @param routeFactory factory which will be used to create new route
     */
    public AddIfMin(PriorityQueueManageable queueManager, RouteCreatable routeFactory) {
        super("add_if_min", "add_if_min {element}","adds element if it is less than min distance of the collection");
        this.queueManager = queueManager;
        this.routeFactory = routeFactory;
    }
    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            Route newRoute = routeFactory.createRoute(queueManager.getIds());
            if (queueManager.addRouteIfMin(newRoute)) {
                return "successfully added new route!\n";
            }
            return "the route is not less than the minimum of the collection\n";
        } else {
            return "invalid parameter\n";
        }
    }
}
