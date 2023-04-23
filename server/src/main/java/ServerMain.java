import network.*;
import fileIO.JSONDecoder;
import fileIO.JSONEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queueManager.PriorityQueueManager;
import serverInterpreter.ServerCommandInterpreter;
import utils.ExitHandler;
import utils.serverReaderWriter.ServerReadableWritable;
import utils.serverReaderWriter.ServerReaderWriter;

import java.io.*;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class ServerMain {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        logger.info("Starting server");
        ServerReadableWritable readerWriter;
        PriorityQueueManager queueManager = null;
        Selector selector = new NetworkConnector(logger).getConnections(8080);
        try {
            queueManager = new PriorityQueueManager(new JSONDecoder(), new JSONEncoder(), "routes.json");
        } catch (IOException e) {
            logger.error("Couldn't get saved routes: " + e.getMessage());
        }
        if (selector == null || queueManager == null) return;
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
            logger.error("Error that stopped the server: " + e.getMessage() + ". Stack trace" );
        }
    }
}
