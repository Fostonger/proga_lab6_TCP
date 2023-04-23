package clientInterpreter;

import clientCommands.ClientExecuteScript;
import commands.*;
import consoleReader.CommandReaderable;
import consoleReader.OutputWritable;
import transportShells.CommandShell;
import utils.*;

import java.io.EOFException;
import java.util.HashMap;
import java.util.Map;

/**
 * interpreter and executor of the commands
 */
public class ClientCommandInterpreter {
    private final Map<String, AbstractCommand> commands;
    private final CommandReaderable commandReader;
    private final OutputWritable outputWriter;
    private final ClientRouteCreatable routeCreator;

    /**
     * @param commandReader reader of the commands
     * @param outputWriter stream to write commands result
     */
    public ClientCommandInterpreter(CommandReaderable commandReader, OutputWritable outputWriter,
                                    ClientRouteCreatable routeCreator,
                                    InterpreterSwitchable interpreterSwitch) {
        this.commandReader = commandReader;
        this.outputWriter = outputWriter;
        this.routeCreator = routeCreator;

        commands = new HashMap<>();
        ClientQueueManager queueManager = new ClientQueueManager();

        Add add = new Add(queueManager, routeCreator);
        commands.put(add.getName(), add);

        RemoveById removeById = new RemoveById(queueManager);
        commands.put(removeById.getName(), removeById);

        Help help = new Help(commands.values());
        commands.put(help.getName(), help);

        GreaterThanDistance greaterThanDistance = new GreaterThanDistance(queueManager);
        commands.put(greaterThanDistance.getName(), greaterThanDistance);

        LessThanDistance lessThanDistance = new LessThanDistance(queueManager);
        commands.put(lessThanDistance.getName(), lessThanDistance);

        FilterLessThanDistance filterLessThanDistance = new FilterLessThanDistance(queueManager);
        commands.put(filterLessThanDistance.getName(), filterLessThanDistance);

        AddIfMax addIfMax = new AddIfMax(queueManager, routeCreator);
        commands.put(addIfMax.getName(), addIfMax);

        AddIfMin addIfMin = new AddIfMin(queueManager, routeCreator);
        commands.put(addIfMin.getName(), addIfMin);

        Info info = new Info(null);
        commands.put(info.getName(), info);

        Show show = new Show(queueManager);
        commands.put(show.getName(), show);

        Update update = new Update(queueManager, routeCreator);
        commands.put(update.getName(), update);

        Clear clear = new Clear(queueManager);
        commands.put(clear.getName(), clear);

        Exit exit = new Exit(outputWriter);
        commands.put(exit.getName(), exit);

        Head head = new Head(queueManager);
        commands.put(head.getName(), head);

        ClientExecuteScript exec = new ClientExecuteScript(outputWriter, interpreterSwitch);
        commands.put(exec.getName(), exec);
    }

    /**
     * fetches command from input stream and executes the command if it was called correctly
     */
    public CommandShell fetchCommand() throws EOFException {
        if (!commandReader.hasNext())
            throw new EOFException();
        String commandString = commandReader.getString();
        CommandShell commandShell = ConsoleCommandFetcher.fetchCommand(commandString, commands);
        if (commandShell != null) {
            AbstractCommand command = commands.get(commandShell.getCommandName());
            command.execute(commandShell.getArgument());
            commandShell.setContainer(routeCreator.getContainer());
            return commandShell;
        } else {
            outputWriter.writeMessage("incorrect command! try writing 'help' command to see all available variants\n");
            return null;
        }
    }
}
