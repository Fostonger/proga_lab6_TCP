package transportShells;

import java.io.Serializable;

public final class ServerResponse implements Serializable {
    private final String response;

    public ServerResponse(String response) {
        this.response = response;
    }
    public String getResponse() {
        return response;
    }
}
