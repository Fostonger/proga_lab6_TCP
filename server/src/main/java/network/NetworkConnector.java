package network;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executor;

public class NetworkConnector implements Runnable {
    public interface ClientConnectionsHandler {
        void handle(SelectionKey key);
    }
    private final Logger logger;
    private final ClientConnectionsHandler handler;
    private final Selector selector = Selector.open();
    private final int port;
    public NetworkConnector(Logger logger, int port, ClientConnectionsHandler handler) throws IOException {
        this.logger = logger;
        this.handler = handler;
        this.port = port;
    }

    private void getConnections(int port) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(port));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (!selector.keys().isEmpty()) {
                if (selector.select() != 0) {
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = ssc.accept();
                            logger.info("got connection from client: " + socketChannel.getRemoteAddress());
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            handler.handle(key);
                        }
                        it.remove();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Couldn't make connections: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        getConnections(port);
    }
}
