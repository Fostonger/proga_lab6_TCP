package consoleReader;

import java.io.*;
import java.util.Scanner;

public class CommandReader implements CommandReadable, OutputWritable {
    private final Scanner commandScanner;
    private final BufferedWriter outputWriter;

    /**
     * @param commandSource source from where commands should be read
     * @param output stream which will be used to write messages
     */
    public CommandReader(InputStream commandSource, OutputStream output) {
        commandScanner = new Scanner(commandSource);
        outputWriter = new BufferedWriter(new OutputStreamWriter(output));
    }
    public String getString() { return commandScanner.nextLine(); }
    public int getInt() { return commandScanner.nextInt(); }
    public double getDouble() { return commandScanner.nextDouble(); }
    public long getLong() { return commandScanner.nextLong(); }
    public boolean hasNext() { return commandScanner.hasNext(); }

    public void writeMessage(String message) {
        try {
            outputWriter.write(message);
            outputWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.fillInStackTrace();
        }
    }
}
