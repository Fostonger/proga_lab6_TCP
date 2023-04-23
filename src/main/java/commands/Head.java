package commands;

import data.Route;
import queueManager.PriorityQueueManageable;

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
        Route head = queueManager.getHead();
        if (head != null) return head.toString();
        return "collection is empty\n";
    }
}
