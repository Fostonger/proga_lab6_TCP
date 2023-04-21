package session;

import java.io.Serializable;

public class Session implements Serializable {
    private final String name;
    private final byte[] passwordHash;
    private final SessionType type;
    public Session(String name, byte[] passwordHash, SessionType type) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public byte[] getPasswordHash() {
        return passwordHash;
    }
    public SessionType getType() { return type; }
}
