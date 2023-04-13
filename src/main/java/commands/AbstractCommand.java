package commands;

/**
 * Base class for all commands
 */
public abstract class AbstractCommand {
    private final String name;
    private final String description;
    private final String infoName;

    /**
     * @param name name of the command that will be used to define executed by user command
     * @param infoName name of the command that will be printed when 'help' function is executed; write here all parameters if any
     * @param description description of the command that will be printed when 'help' function is executed
     */
    public AbstractCommand(String name, String infoName, String description) {
        this.name = name;
        this.description = description;
        this.infoName = infoName;
    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public String getInfoName() {
        return infoName;
    }

    /**
     * method to be fired when the execution of the command is requested
     * @param arg arguments of command or empty string if none is needed
     * @return string with execution result that will be printed
     */
    public abstract String execute(String arg);
}
