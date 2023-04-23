package commands;

import data.Route;
import queueManager.PriorityQueueManageable;

import java.util.List;
import java.util.PriorityQueue;

/**
 * command that returns string with all elements' description
 */
public class Show extends AbstractCommand {
    private final PriorityQueueManageable queueManager;

    /**
     * @param queueManager manager whose elements' descriptions will be returned
     */
    public Show(PriorityQueueManageable queueManager) {
        super("show","show", "prints all elements of the collection to standard output");
        this.queueManager = queueManager;
    }

    @Override
    public String execute(String arg) {
        List<Route> queue = queueManager.getQueue();
        if (queue == null) return "program was given incorrect queueManager!\n";
        if (arg.equals("")) {
            StringBuilder showString = new StringBuilder();
            for (int i = 0; i < queue.size(); i++) {
                showString.append("--------- Element ").append(i + 1).append(" ---------\n");
                showString.append(queue.get(i).toString());
            }
            return (queue.size() > 0) ? showString.toString() : "collection is empty!\n";
        } else {
            return "command was ran with incorrect argument, see 'help' for information\n";
        }
    }
}
