package commands;

import data.Route;
import queueManager.PriorityQueueManageable;

import java.sql.SQLException;

/**
 * command that returns string containing the first element's description
 */
public class Head extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    /**
     * @param queueManager manager to get head from
     */
    public Head(PriorityQueueManageable queueManager) {
        super("head", "head", "prints first element of the collection");
        this.queueManager = queueManager;
    }

    @Override
    public String execute(String arg) {
        try {
            Route head = queueManager.getHead();
            if (head != null) return head.toString();
            return "collection is empty\n";
        } catch (SQLException e) {
            return "error occurred while trying to get head of the collection: " + e.getMessage() +" \n";
        }
    }
}
