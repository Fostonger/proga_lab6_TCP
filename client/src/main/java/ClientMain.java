import utils.*;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost",8080);
            ClientWorker.create(clientSocket, System.in, System.out);
        }
        catch (Exception e) {
            System.out.println("Unable to connect to the server!");
        }
    }
}
