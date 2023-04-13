package serverCommands;

import commands.AbstractCommand;
import queueManager.PriorityQueueManageable;

import java.io.IOException;

public class ServerExit extends AbstractCommand {
    public ServerExit() {
        super("exit", "exit", "exits application without writing to file");
    }

    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            return "closing connection\n";
        } else {
            return "command doesn't get any arguments\n";
        }
    }
}
