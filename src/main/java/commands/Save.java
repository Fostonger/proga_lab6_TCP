package commands;

import queueManager.PriorityQueueManageable;

import java.io.IOException;

/**
 * command that saves current collection to disk
 */
public class Save extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    /**
     * @param queueManager manager whose collection to save
     */
    public Save(PriorityQueueManageable queueManager) {
        super("save", "save", "saves all changes to file");
        this.queueManager = queueManager;
    }
    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            try {
                queueManager.save();
            } catch (IOException e) {
                return "sorry! couldn't save to file: " + e.getMessage() + "\n";
            }
            return "successfully saved to file!\n";
        } else {
            return "command was ran with incorrect argument, see 'info' for information\n";
        }
    }
}
