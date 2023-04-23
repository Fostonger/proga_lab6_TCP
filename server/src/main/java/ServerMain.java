import concurrency.ConcurrentSocketListener;
import network.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serverCommands.PriorityQueueManager;
import serverInterpreter.ServerCommandInterpreter;
import utils.ExitHandler;

import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(ServerMain.class);
        logger.info("Starting server");
        PriorityQueueManager queueManager;
        try {
            queueManager = PriorityQueueManager.InitWithDB(
                    "jdbc:postgresql://localhost:5432/studs",
                    args.length > 0 ? args[0] : "", args.length > 1 ? args[1] : "", logger);
        } catch (SQLException e) {
            logger.error("Error occurred while connecting to database: " + e.getMessage());
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new ExitHandler(queueManager, logger)));
        ServerCommandInterpreter interpreter = new ServerCommandInterpreter(queueManager);
        Executor listeningPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 3);
        Executor writingPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 3);
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            NetworkConnector connector = new NetworkConnector(logger, 8080, key -> {
                var worker = new ConcurrentSocketListener(logger, key, interpreter, writingPool, executor);
                worker.run();
            });
            listeningPool.execute(connector);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
