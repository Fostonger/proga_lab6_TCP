package concurrency;

import org.apache.logging.log4j.Logger;
import serverInterpreter.ServerCommandInterpreter;
import transportShells.ClientRequest;
import utils.serverReaderWriter.ServerReadableWritable;
import utils.serverReaderWriter.ServerReaderWriter;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConcurrentSocketListener implements Runnable {
    private final Logger logger;
    private final SelectionKey key;
    private final ServerCommandInterpreter interpreter;
    private final Executor writingPool;
    private final Executor interpretingPool;
    public ConcurrentSocketListener(Logger logger, SelectionKey key, ServerCommandInterpreter interpreter,
                                    Executor writingPool, Executor interpretingPool) {
        this.logger = logger;
        this.interpreter = interpreter;
        this.key = key;
        this.writingPool = writingPool;
        this.interpretingPool = interpretingPool;
    }
    @Override
    public void run() {
        ServerReadableWritable readerWriter = new ServerReaderWriter(key, logger);
        if (readerWriter.hasObject()) {
            ClientRequest request = (ClientRequest) readerWriter.getObject();
            if (request == null) return;
            logger.info(
                    "extracted command from client \"" + request.getSession().getName() + "\": " +
                            request.getCommandShell().getCommandName()
            );
            Responder respondWriter = new Responder(readerWriter, logger);
            Task interpretingTask = new Task(interpreter, request, writingPool, respondWriter);
            Handler interpretationHandler = new Handler(interpretingTask, interpretingPool);
            interpretationHandler.handle();
        }
    }
}
