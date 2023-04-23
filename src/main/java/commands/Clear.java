package commands;

import queueManager.PriorityQueueManageable;

/**
 * command that clears the collection
 */
public class Clear extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    /**
     * @param queueManager manager to clear
     */
    public Clear(PriorityQueueManageable queueManager) {
        super("clear", "clear", "clears current collection");
        this.queueManager = queueManager;
    }

    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            queueManager.clear();
            return "successfully cleared collection!\n";
        } else {
            return "this command doesn't need any arguments, see 'help'\n";
        }
    }
}
