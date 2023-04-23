package clientCommands;

import clientInterpreter.ClientCommandInterpreter;
import commands.AbstractCommand;
import consoleReader.CommandReaderable;
import consoleReader.OutputWritable;
import consoleReader.PropertiesReceiver;
import queueManager.PriorityQueueManageable;
import transportShells.ServerResponse;
import utils.ClientRouteFactory;
import utils.InterpreterSwitchable;
import utils.RequestSendable;
import utils.RouteFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * command that executes script from given filepath
 */
public class ClientExecuteScript extends AbstractCommand {
    private final OutputWritable output;
    private final InterpreterSwitchable interpreterSwitch;
    /**
     * @param output output stream to write messages to
     */
    public ClientExecuteScript(OutputWritable output, InterpreterSwitchable interpreterSwitch) {
        super("execute_script", "execute_script filepath", "executes commands from given script");
        this.output = output;
        this.interpreterSwitch = interpreterSwitch;
    }

    @Override
    public String execute(String arg) {
        try {
            CommandReaderable reader = new CommandReaderable() {
                private final Scanner commandScanner = new Scanner(new FileInputStream(arg));
                public String getString() { return commandScanner.nextLine(); }
                public int getInt() { return commandScanner.nextInt(); }
                public double getDouble() { return commandScanner.nextDouble(); }
                public long getLong() { return commandScanner.nextLong(); }
                public boolean hasNext() { return commandScanner.hasNext(); }
            };

            if (output == null) return "the command was given incorrect output!";
            PropertiesReceiver receiver = new PropertiesReceiver(reader, null);
            ClientRouteFactory routeFactory = new ClientRouteFactory(receiver);
            ClientCommandInterpreter interpreter = new ClientCommandInterpreter(reader, output, routeFactory, interpreterSwitch);
            interpreterSwitch.switchInterpreter(interpreter);
        } catch (IOException e) {
            output.writeMessage("Error! Couldn't find script with filename \"" + arg + "\"\n");
        }
        return "the end of script\n";
    }
}
