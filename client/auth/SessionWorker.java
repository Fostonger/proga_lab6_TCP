package auth;

import commands.GreaterThanDistance;
import consoleReader.CommandReadable;
import consoleReader.OutputWritable;
import session.Session;
import session.SessionType;
import transportShells.ClientRequest;
import transportShells.CommandShell;
import utils.ClientNetWorkable;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SessionWorker {
    public static Session getSession(
            CommandReadable consoleReader, OutputWritable consoleWriter, ClientNetWorkable netWorker
    ) throws IOException, ClassNotFoundException {
        SessionType type;
        do {
            consoleWriter.writeMessage("Login or register ('l' or 'r' respectively): ");
        } while ((type = validateType(consoleReader)) == null);
        consoleWriter.writeMessage("Enter name:\t\t\t\t\t\t\t\t");
        String name = getUsername(consoleReader);
        consoleWriter.writeMessage("Enter password (press enter if none):\t");
        String password;
        try {
            password = getPassword(consoleReader);
        } catch (NoSuchAlgorithmException e) {
            consoleWriter.writeMessage("Sorry, couldn't find algorithm for password hashing!");
            return null;
        }
        Session session = new Session(name, password, type);
        CommandShell commandShell = new CommandShell("auth", "");
        ClientRequest request = new ClientRequest(session, commandShell);
        String response = netWorker.sendRequestAndGetResponse(request);
        consoleWriter.writeMessage(response + "\n");
        return response.equals("auth successful, welcome!")
                ? session
                : null;
    }

    private static SessionType validateType(CommandReadable consoleReader) {
        switch (consoleReader.getString()) {
            case "l" -> { return SessionType.LOGIN; }
            case "r" -> { return SessionType.REGISTER; }
            default -> { return null; }
        }
    }
    private static String getUsername(CommandReadable consoleReader) {
        return consoleReader.getString();
    }
    private static String getPassword(CommandReadable consoleReader) throws NoSuchAlgorithmException {
        String password = consoleReader.getString();
        MessageDigest digest = MessageDigest.getInstance("SHA-384");
        return new String(digest.digest(password.getBytes()));
    }
}
