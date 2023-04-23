package concurrency;

import java.util.concurrent.Executor;

public class Handler {
    private final Task commandTask;
    private final Executor executionPool;;
    public Handler(Task commandTask, Executor executionPool) {
        this.executionPool = executionPool;
        this.commandTask = commandTask;
    }
    public void handle() {
        executionPool.execute(commandTask);
    }
}
