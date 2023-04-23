package commands;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * command that returns string representing all possible commands' names and descriptions
 */
public class Help extends AbstractCommand {
    private final Collection<AbstractCommand> commands;

    /**
     * @param commands all possible commands list
     */
    public Help(Collection<AbstractCommand> commands) {
        super("help", "help","shows all possible commands");
        this.commands = commands;
    }

    /**
     * Could set filteredCommands on construction time, but that would leave some commands
     * @param arg arguments of command or empty string if none is needed
     * @return the set of available commands, without auth command
     */
    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            StringBuilder helpString = new StringBuilder();
            int maxNameLength = maxNameLength();
            maxNameLength -= maxNameLength % 4;
            var filteredCommands = commands.stream()
                    .filter( command -> !command.getName().equals("auth"))
                    .collect(Collectors.toMap(AbstractCommand::getName, command -> command));
            for (AbstractCommand command : filteredCommands.values()) {
                helpString.append(command.getInfoName())
                        .append(addTab(maxNameLength-command.getInfoName().length()))
                        .append(command.getDescription()).append("\n");
            }
            return helpString.toString();
        } else {
            return "help function doesn't take any arguments\n";
        }
    }

    /**
     * adds needed amount of tabs
     * @param charCount how many characters need to be added to string
     * @return string containing needed amount of tabs
     */
    private String addTab(int charCount) {
        StringBuilder tabs = new StringBuilder();
        int loopCount = (charCount + ((4 - (charCount % 4))) % 4) / 4 + 1;
        for (int i = 0; i <= loopCount; i++) { tabs.append("\t"); }
        return tabs.toString();
    }

    /**
     * @return maximum length of command name from commands list
     */
    private int maxNameLength() {
        var maxNameCommand = commands.stream().max((o1, o2) -> {
            if (o1.getInfoName().length() < o2.getInfoName().length()) return -1;
            return (o1.getInfoName().length() > o2.getInfoName().length()) ? 0 : 1;
        });
        if (maxNameCommand.isPresent()) return maxNameCommand.get().getInfoName().length();
        return 0;
    }
}