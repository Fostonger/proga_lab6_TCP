package commands;

import queueManager.PriorityQueueManageable;

import java.util.NoSuchElementException;

/**
 * command that removes element from the collection by its id
 */
public class RemoveById extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    /**
     * @param queueManager manager to remove element from
     */
    public RemoveById(PriorityQueueManageable queueManager) {
        super("remove_by_id","remove_by_id id", "removes element from the collection by given id");
        this.queueManager = queueManager;
    }

    @Override
    public String execute(String arg) {
        try {
            int id = Integer.parseInt(arg);
            if (queueManager.getQueue().isEmpty()) return "collection is already empty!\n";
            queueManager.removeById(id);
            return "element with id " + arg + " was removed successfully!\n";
        } catch (NoSuchElementException e) {
            return "there is no element with id " + arg + " in collection\n";
        } catch (NumberFormatException e) {
            return "\"" + arg + "\" is not valid id, it needs to be integer\n";
        }
    }
}
