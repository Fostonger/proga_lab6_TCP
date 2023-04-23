package transportShells;

import utils.RouteCreationContainer;

import java.io.Serializable;

public class CommandShell implements Serializable {
    private final String commandName;
    private final String argument;
    private RouteCreationContainer container;
    public CommandShell( String commandName, String argument) {
        this.argument = argument;
        this.commandName = commandName;
    }
    public String getArgument() {
        return argument;
    }
    public String getCommandName() {
        return commandName;
    }
    public void setContainer(RouteCreationContainer container) {
        this.container = container;
    }
    public RouteCreationContainer getContainer() {
        return container;
    }
}
