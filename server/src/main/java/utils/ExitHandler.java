package utils;

import org.apache.logging.log4j.Logger;
import queueManager.PriorityQueueManageable;

import java.io.IOException;

public class ExitHandler implements Runnable {
    private final PriorityQueueManageable queueManager;
    private final Logger logger;
    public ExitHandler(PriorityQueueManageable queueManager, Logger logger) {
        this.queueManager = queueManager;
        this.logger = logger;
    }

    @Override
    public void run() {
        if (queueManager == null) {
            logger.error("Couldn't write to file: queueManager is null");
            return;
        }
        try {
            logger.info("Successfully wrote collection to file");
            queueManager.save();
        } catch (IOException e) {
            logger.error("Couldn't write to file: " + e.getMessage());
        }
    }
}
