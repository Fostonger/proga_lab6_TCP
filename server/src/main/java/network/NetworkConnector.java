package network;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NetworkConnector implements NetworkConnectable {
    private final Logger logger;
    public NetworkConnector(Logger logger) {
        this.logger = logger;
    }

    public Selector getConnections(int port) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            SocketChannel client = ssc.accept();
            logger.info("Successfully connected client! Remote: " + client.getRemoteAddress() + "; Local: " + client.getLocalAddress());
            Selector selector = Selector.open();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            return selector;
        } catch (IOException e) {
            logger.error("Couldn't make connection: " + e.getMessage());
            return null;
        }
    }
}
