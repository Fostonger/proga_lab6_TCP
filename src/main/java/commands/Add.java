package commands;

import data.Route;
import queueManager.PriorityQueueManageable;
import utils.RouteCreatable;

/**
 * Add command
 */
public class Add extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    private final RouteCreatable routeFactory;

    /**
     * @param queueManager manager to add to
     * @param routeFactory factory which will be used to create new route
     */
    public Add(PriorityQueueManageable queueManager, RouteCreatable routeFactory) {
        super("add", "add {element}","adds an element to collection");
        this.queueManager = queueManager;
        this.routeFactory = routeFactory;
    }
    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            Route newRoute = routeFactory.createRoute(queueManager.getIds());
            queueManager.add(newRoute);
            return "successfully added element!\n";
        } else {
            return "command was ran with incorrect argument, see 'help' for information\n";
        }
    }
}
