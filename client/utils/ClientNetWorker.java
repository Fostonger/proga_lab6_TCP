package utils;

import transportShells.ServerResponse;

import java.io.*;
import java.net.Socket;

public class ClientNetWorker implements ClientNetWorkable {
    private final OutputStream outputStream;
    private final InputStream inputStream;
    public ClientNetWorker(Socket clientSocket) throws IOException{
        this.outputStream = clientSocket.getOutputStream();
        this.inputStream = clientSocket.getInputStream();
    }
    @Override
    public void sendObject(Object o) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteArrayOutputStream);

            objOut.writeObject(o);
            objOut.flush();

            byte[] data = byteArrayOutputStream.toByteArray();
            outputStream.write(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public String getResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInput = new ObjectInputStream(inputStream);
        ServerResponse serverResponse = (ServerResponse) objectInput.readObject();
        if (serverResponse != null)
            return serverResponse.getResponse();
        return null;
    }

    @Override
    public String sendRequestAndGetResponse(Object o) throws IOException, ClassNotFoundException {
        sendObject(o);
        return getResponse();
    }
}
