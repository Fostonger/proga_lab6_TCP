package utils.serverReaderWriter;

import java.net.SocketException;

public interface ServerWritable {
    void writeResponse(String message) throws SocketException;
}
