package serverCommands;

import commands.AbstractCommand;
import session.Session;

public class ServerUserLogin extends AbstractCommand {
    private Session session;
    public ServerUserLogin(Session session) {
        super("login", "login", "logins user so it can use program");
        this.session = session;
    }

    @Override
    public String execute(String arg) {
        return null;
    }
}
