package commands;

import consoleReader.CommandReadable;
import consoleReader.OutputWritable;
import consoleReader.PropertiesReceiver;
import queueManager.PriorityQueueManageable;
import utils.RouteFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * command that executes script from given filepath
 */
public class ExecuteScript extends AbstractCommand {
    private final PriorityQueueManageable queueManager;
    private final OutputWritable output;
    /**
     * @param queueManager manager to use in new interpreter
     * @param output output stream to write messages to
     */
    public ExecuteScript(PriorityQueueManageable queueManager, OutputWritable output) {
        super("execute_script", "execute_script filepath", "executes commands from given script");
        this.queueManager = queueManager;
        this.output = output;
    }

    @Override
    public String execute(String arg) {
        try {
            CommandReadable reader = new CommandReadable() {
                private final Scanner commandScanner = new Scanner(new FileInputStream(arg));
                public String getString() { return commandScanner.nextLine(); }
                public int getInt() { return commandScanner.nextInt(); }
                public double getDouble() { return commandScanner.nextDouble(); }
                public long getLong() { return commandScanner.nextLong(); }
                public boolean hasNext() { return commandScanner.hasNext(); }
            };

            if (output == null) return "the command was given incorrect output!";
            PropertiesReceiver receiver = new PropertiesReceiver(reader, output);
            RouteFactory routeFactory = new RouteFactory(receiver);
            CommandInterpreter interpreter = new CommandInterpreter(queueManager, routeFactory, reader, output);
            int commandsCount = 1;
            while (reader.hasNext()) {
                output.writeMessage("\n--------- command " + commandsCount++ + " ---------\n\n");
                interpreter.fetchCommand();
            }
        } catch (FileNotFoundException e) {
            return "file with given name/path is not found\n";
        }
        return "the end of script\n";
    }
}
