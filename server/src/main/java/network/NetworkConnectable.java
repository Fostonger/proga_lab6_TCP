package network;

import utils.serverReaderWriter.ServerReadableWritable;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.Selector;

public interface NetworkConnectable {
    public Selector getConnections(int port);
}
