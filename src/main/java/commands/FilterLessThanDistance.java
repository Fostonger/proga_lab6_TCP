package commands;

import data.Route;
import queueManager.PriorityQueueManageable;

import java.util.List;

/**
 * command that prints all routes of the collection whose distance is less than the given one
 */
public class FilterLessThanDistance extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    /**
     * @param queueManager manager to get filtered result from
     */
    public FilterLessThanDistance(PriorityQueueManageable queueManager) {
        super("filter_less_than_distance",
                "filter_less_than_distance distance",
                "prints all elements, whose distance is less than the given one");
        this.queueManager = queueManager;
    }
    @Override
    public String execute(String arg) {
        if (arg.equals("")) return "the command takes parameters, see 'help'!\n";
        try {
            StringBuilder filterString = new StringBuilder();
            double distance = Double.parseDouble(arg);
            List<Route> routes = queueManager.filterLessThanDistance(distance);

            if (routes == null) return "program was given incorrect queueManager!\n";

            for (int i = 0; i < routes.size(); i++) {
                filterString.append("\n--------- element ").append(i+1).append(" ---------\n\n");
                filterString.append(routes.get(i).toString());
            }
            return (filterString.length() == 0)
                    ? "there are no elements whose distance is less than " + distance + "\n"
                    : filterString.toString();
        } catch (NumberFormatException e) {
            return arg + " is not valid id, it needs to be double\n";
        }
    }
}
