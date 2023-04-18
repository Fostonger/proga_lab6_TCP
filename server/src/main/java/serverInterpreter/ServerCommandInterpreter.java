package serverInterpreter;

import commands.*;
import queueManager.PriorityQueueManageable;
import serverCommands.ServerExecuteScript;
import serverCommands.ServerExit;
import transportShells.ClientRequest;
import transportShells.CommandShell;
import utils.RouteCreatable;
import utils.RouteFactory;
import utils.ServerRouteFactory;
import utils.serverReaderWriter.ServerCommandReadable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * interpreter and executor of the commands
 */
public class ServerCommandInterpreter {
    private final Map<String, AbstractCommand> commands;
    private final ServerCommandReadable clientInput;
    private ServerRouteFactory routeCreator = new ServerRouteFactory();

    /**
     * @param queueManager manager to be used in commands
     */
    public ServerCommandInterpreter(PriorityQueueManageable queueManager,
                                    ServerCommandReadable clientInput) {
        this.clientInput = clientInput;

        commands = new HashMap<>();

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

        Info info = new Info(queueManager.getContainer());
        commands.put(info.getName(), info);

        Show show = new Show(queueManager);
        commands.put(show.getName(), show);

        Update update = new Update(queueManager, routeCreator);
        commands.put(update.getName(), update);

        Clear clear = new Clear(queueManager);
        commands.put(clear.getName(), clear);

        ServerExit exit = new ServerExit();
        commands.put(exit.getName(), exit);

        Head head = new Head(queueManager);
        commands.put(head.getName(), head);

        ServerExecuteScript exec = new ServerExecuteScript();
        commands.put(exec.getName(), exec);
    }

    /**
     * fetches command from input channel and executes the command
     */
    public String fetchCommand() {
        ClientRequest request = (ClientRequest) clientInput.getObject();
        if (request == null)
            return "Client returned object, that cannot be casted to command\n";
        AbstractCommand command = commands.get(request.getCommandShell().getCommandName());
        routeCreator.setContainer(request.getCommandShell().getContainer());
        return command.execute(request.getCommandShell().getArgument());
    }
}

