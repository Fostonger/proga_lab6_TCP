package serverCommands;

import commands.AbstractCommand;

/**
 * command that executes script from given filepath
 */
public class ServerExecuteScript extends AbstractCommand {
    public ServerExecuteScript() {
        super("execute_script", "execute_script filepath", "executes commands from given script");
    }

    @Override
    public String execute(String arg) { return ""; }
}
