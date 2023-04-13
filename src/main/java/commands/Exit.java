package commands;

import consoleReader.OutputWritable;

/**
 * command that exits the program
 */
public class Exit extends AbstractCommand {
    private final OutputWritable outputWriter;
    /**
     * @param outputWriter the command exits the program before returning the value, so it needs the output stream to write the message itself
     */
    public Exit(OutputWritable outputWriter) {
        super("exit", "exit", "exits application without writing to file");
        this.outputWriter = outputWriter;
    }

    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            outputWriter.writeMessage("terminating program!\n");
            System.exit(0);
            return null;
        } else {
            return "command doesn't get any arguments\n";
        }
    }
}
