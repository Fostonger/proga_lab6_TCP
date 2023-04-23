package concurrency;

import serverInterpreter.ServerCommandInterpreter;
import transportShells.ClientRequest;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public class Task implements Runnable {
    private final ServerCommandInterpreter interpreter;
    private final ClientRequest request;
    private final Executor writingPool;
    private final Responder responder;
    public Task(ServerCommandInterpreter interpreter, ClientRequest request, Executor writingPool, Responder responder) {
        this.interpreter = interpreter;
        this.request = request;
        this.writingPool = writingPool;
        this.responder = responder;
    }

    @Override
    public void run() {
        responder.setMessage(interpreter.fetchCommand(request));
        writingPool.execute(responder);
    }
}
