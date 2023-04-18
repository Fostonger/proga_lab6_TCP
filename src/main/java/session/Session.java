package session;

public class Session {
    private final String name;
    private final String passwordHash;
    private final SessionType type;
    public Session(String name, String passwordHash, SessionType type) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
}
