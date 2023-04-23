package serverCommands;

import commands.AbstractCommand;
import database.Authorizer;
import session.SessionType;
import utils.sessionManager.SessionManageable;

public class Auth extends AbstractCommand {
    private final SessionManageable sessionManager;
    private final Authorizer authorizer;
    public Auth(SessionManageable sessionManager, Authorizer authorizer) {
        super("auth", "auth", "authorises user");
        this.sessionManager = sessionManager;
        this.authorizer = authorizer;
    }

    @Override
    public String execute(String arg) {
        if (sessionManager.getSession().getType() == SessionType.LOGIN) {
            return authorizer.login(
                    sessionManager.getName(),
                    sessionManager.getHashedPassword()
            ).getMessage();
        } else {
            return authorizer.register(
                    sessionManager.getName(),
                    sessionManager.getHashedPassword()
            ).getMessage();
        }
    }
}
