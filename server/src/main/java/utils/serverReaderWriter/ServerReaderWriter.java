package utils.serverReaderWriter;
import org.apache.logging.log4j.Logger;

import transportShells.CommandShell;
import transportShells.ServerResponse;

import java.io.*;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ServerReaderWriter implements ServerReadableWritable {
    private final SelectionKey clientKey;
    private final ByteBuffer buffer;
    private final Logger logger;
    public ServerReaderWriter(SelectionKey clientKey, Logger logger) {
        this.clientKey = clientKey;
        this.logger = logger;
        buffer = ByteBuffer.allocate(8000);
    }
    public Object getObject() {
        try {
            SocketChannel clientChannel = (SocketChannel) clientKey.channel();
            buffer.clear();
            clientChannel.read(buffer);
            buffer.flip();
            logger.info("Have got command from client " + clientChannel.getRemoteAddress() + " (remote)");

            ByteArrayInputStream baos = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(baos);
            Object receivedObj = ois.readObject();
            if (((CommandShell) receivedObj) != null)
                logger.info("Command: " + ((CommandShell) receivedObj).getCommandName());
            else
                logger.info("Couldn't parse command from received object");
            return receivedObj;
        }  catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean hasObject() {
        return clientKey.isReadable();
    }

    @Override
    public void writeResponse(String message) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject( new ServerResponse(message) );
            oos.close();
            oos.flush();
            buffer.clear();
            buffer.put(baos.toByteArray());
            buffer.flip();
        } catch (IOException e) {
            logger.error("Couldn't write response to buffer, error: " + e.getMessage());
        }
        try {
            SocketChannel clientChannel = (SocketChannel) clientKey.channel();
            logger.info("Writing response to client " + clientChannel.getRemoteAddress());
            clientChannel.write(buffer);
        } catch (IOException e) {
            logger.error("Client disconnected!");
            try {
                clientKey.channel().close();
            } catch (IOException ex) {
                logger.error("Couldn't close the channel: " + e.getMessage());
            }
        }
    }
}
