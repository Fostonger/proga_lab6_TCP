package commands;

import transportShells.CommandShell;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleCommandFetcher {
    public static CommandShell fetchCommand(String commandString, Map<String, AbstractCommand> commands) {
        String commandName;
        String argName;
        Pattern commandNamePat = Pattern.compile("^\\w+\\s*");
        Pattern argNamePat = Pattern.compile("^.+");
        Matcher matcher = commandNamePat.matcher(commandString);

        if (matcher.find()) {
            commandName = matcher.group().trim();
        } else {
            return null;
        }
        AbstractCommand command = commands.get(commandName);
        if (command == null) {
            return null;
        }

        commandString = commandString.substring(commandName.length());

        matcher = argNamePat.matcher(commandString);
        argName = matcher.find() ? matcher.group().trim() : "";
        return new CommandShell(commandName, argName);
    }
}
