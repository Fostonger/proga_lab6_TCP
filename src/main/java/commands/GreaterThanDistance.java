package commands;

import queueManager.PriorityQueueManageable;

/**
 * command that returns string containing count of elements whose distance is greater than the given one
 */
public class GreaterThanDistance extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    /**
     * @param queueManager manager to get count from
     */
    public GreaterThanDistance(PriorityQueueManageable queueManager) {
        super("count_greater_than_distance",
                "count_greater_than_distance distance",
                "prints number of elements, whose distance is greater than the given one");
        this.queueManager = queueManager;
    }
    @Override
    public String execute(String arg) {
        if (arg.equals("")) return "the command takes parameters, see 'help'!\n";
        try {
            double distance = Double.parseDouble(arg);
            long count = queueManager.countGreaterThanDistance(distance);
            return "there are " + count + " elements with distance greater than " + distance + "\n";
        } catch (NumberFormatException e) {
            return arg + " is not valid id, it needs to be double\n";
        }
    }
}
