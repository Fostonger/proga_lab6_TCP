package utils;

import clientInterpreter.ClientCommandInterpreter;
import consoleReader.CommandReader;
import consoleReader.OutputWritable;
import consoleReader.PropertiesReceiver;
import transportShells.CommandShell;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientWorker implements InterpreterSwitchable {
    private ClientCommandInterpreter interpreter;
    private final OutputWritable output;
    private final ClientNetWorker netWorker;
    private ClientWorker(OutputWritable output, ClientNetWorker netWorker) {
        this.interpreter = null;
        this.output = output;
        this.netWorker = netWorker;
    }

    public static ClientWorker create(Socket clientSocket, InputStream input, OutputStream output) {
        CommandReader readerWriter = new CommandReader(input, output);
        PropertiesReceiver propertiesReceiver = new PropertiesReceiver(readerWriter, readerWriter);
        ClientNetWorker netWorker = null;
        try {
             netWorker = new ClientNetWorker(clientSocket);
        } catch (IOException e) {
            readerWriter.writeMessage("Couldn't get input and output streams from socket, sorry!");
            System.exit(0);
        }
        ClientRouteCreatable routeCreator = new ClientRouteFactory(propertiesReceiver);
        ClientWorker worker = new ClientWorker(readerWriter, netWorker);
        ClientCommandInterpreter interpreter = new ClientCommandInterpreter(
                readerWriter, readerWriter, routeCreator, worker);
        worker.switchInterpreter(interpreter);
        return worker;
    }

    public void execute() {
        while (true) {
            CommandShell commandShell;
            try {
                commandShell = interpreter.fetchCommand();
            } catch (EOFException e) {
                output.writeMessage("execution completed\n");
                return;
            }
            try {
                if (commandShell != null) {
                    String answer = netWorker.sendRequestAndGetResponse(commandShell);
                    output.writeMessage(answer);
                    if (answer.equals("closing connection\n")) return;
                }
            } catch (IOException e) {
                output.writeMessage("couldn't send request and get response from server. Disconnecting\n");
                return;
            } catch (ClassNotFoundException e) {
                output.writeMessage("the class received from server is in inappropriate format\n");
            }
        }
    }

    @Override
    public void switchInterpreter(ClientCommandInterpreter interpreter) {
        ClientCommandInterpreter prev = this.interpreter;
        this.interpreter = interpreter;
        execute();
        this.interpreter = prev;
    }
}
