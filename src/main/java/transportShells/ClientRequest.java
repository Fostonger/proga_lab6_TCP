package transportShells;

import session.Session;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private final Session session;
    private final CommandShell commandShell;
    public ClientRequest(Session session, CommandShell commandShell) {
        this.commandShell = commandShell;
        this.session = session;
    }
    public CommandShell getCommandShell() {
        return commandShell;
    }
    public Session getSession() {
        return session;
    }
}
