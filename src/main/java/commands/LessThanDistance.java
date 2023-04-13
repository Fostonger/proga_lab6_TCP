package commands;

import queueManager.PriorityQueueManageable;

/**
 * command that returns string containing all routes whose distance is less than the given one
 */
public class LessThanDistance extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    /**
     * @param queueManager manager to get filtered result from
     */
    public LessThanDistance(PriorityQueueManageable queueManager) {
        super("count_less_than_distance",
                "count_less_than_distance distance",
                "prints number of elements, whose distance is less than the given one");
        this.queueManager = queueManager;
    }
    @Override
    public String execute(String arg) {
        if (arg.equals("")) return "the command takes parameters, see 'help'!\n";
        try {
            double distance = Double.parseDouble(arg);
            long count = queueManager.countLessThanDistance(distance);
            return "there are " + count + " elements with distance less than " + distance + "\n";
        } catch (NumberFormatException e) {
            return arg + " is not valid id, it needs to be double\n";
        }
    }
}
