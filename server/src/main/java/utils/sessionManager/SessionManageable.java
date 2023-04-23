package utils.sessionManager;

import session.Session;

public interface SessionManageable {
    Session getSession();
    void setSession(Session session);
    String getName();
    byte[] getHashedPassword();
}
