package commands;

import data.Route;
import queueManager.PriorityQueueManageable;
import utils.RouteCreatable;

import java.util.NoSuchElementException;

/**
 * command that updates element of the collection by its id
 */
public class Update extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    private final RouteCreatable routeFactory;

    /**
     * @param queueManager manager whose queue's element will be updated
     * @param routeFactory factory to use to get new element
     */
    public Update(PriorityQueueManageable queueManager, RouteCreatable routeFactory) {
        super("update","update id {element}", "updates element with given id");
        this.queueManager = queueManager;
        this.routeFactory = routeFactory;
    }

    @Override
    public String execute(String arg) {
        try {
            int id = Integer.parseInt(arg);

            if (!queueManager.containsId(id)) throw new NoSuchElementException();

            Route newRoute = routeFactory.createRoute(queueManager.getIds());
            queueManager.updateById(id, newRoute);
            return "element with id " + arg + " was updated successfully!\n";
        } catch (NoSuchElementException e) {
            return "there is no element with id " + arg + " in collection\n";
        } catch (NumberFormatException e) {
            return arg + " is not valid id, it needs to be integer\n";
        }
    }
}
