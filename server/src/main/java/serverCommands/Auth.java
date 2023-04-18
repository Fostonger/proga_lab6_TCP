package serverCommands;

import commands.AbstractCommand;

public class Auth extends AbstractCommand {
    public Auth() {
        super("auth", "auth", "authorises user");
    }

    @Override
    public String execute(String arg) {
        return null;
    }
}
