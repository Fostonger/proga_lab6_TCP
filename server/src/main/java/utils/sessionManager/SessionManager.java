package utils.sessionManager;

import session.Session;

public class SessionManager implements SessionManageable {
    private Session session;
    @Override
    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }
    public String getName() { return session.getName(); }
    public byte[] getHashedPassword() { return session.getPasswordHash(); }
}
