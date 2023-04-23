package concurrency;

import org.apache.logging.log4j.Logger;
import utils.serverReaderWriter.ServerWritable;

import java.net.SocketException;

public class Responder implements Runnable {
    private final ServerWritable writer;
    private String message;
    private final Logger logger;
    public Responder(ServerWritable writer, Logger logger) {
        this.writer = writer;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            writer.writeResponse(message);
            logger.info("Successfully wrote answer!");
        } catch (SocketException e) {
            logger.error("Couldn't write answer: " + e.getMessage());
        }

    }

    public void setMessage(String message) {
        this.message = message;
    }
}
