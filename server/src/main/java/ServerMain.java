import database.JDBCWorker;
import database.PriorityQueueJDBCConnector;
import network.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queueManager.PriorityQueueManager;
import serverInterpreter.ServerCommandInterpreter;
import utils.ExitHandler;
import utils.serverReaderWriter.ServerReadableWritable;
import utils.serverReaderWriter.ServerReaderWriter;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Iterator;

public class ServerMain {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
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

        ServerReadableWritable readerWriter;
        Selector selector = new NetworkConnector(logger).getConnections(8080);
        if (selector == null) return;
        Runtime.getRuntime().addShutdownHook(new Thread(new ExitHandler(queueManager, logger)));
        try {
            while (!selector.keys().isEmpty()) {
                if (selector.select() != 0) {
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        readerWriter = new ServerReaderWriter(key, logger);
                        ServerCommandInterpreter interpreter = new ServerCommandInterpreter(
                                queueManager, readerWriter);
                        if (readerWriter.hasObject()) {
                            readerWriter.writeResponse(interpreter.fetchCommand());
                        }
                        it.remove();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error that stopped the server: " + e.getMessage() );
        }
    }
}
