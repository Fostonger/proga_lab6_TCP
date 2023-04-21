package serverInterpreter;

import commands.*;
import queueManager.PriorityQueueManageable;
import queueManager.PriorityQueueManager;
import serverCommands.Auth;
import serverCommands.ServerExecuteScript;
import serverCommands.ServerExit;
import transportShells.ClientRequest;
import utils.ServerRouteFactory;
import utils.serverReaderWriter.ServerCommandReadable;
import utils.sessionManager.SessionManageable;
import utils.sessionManager.SessionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * interpreter and executor of the commands
 */
public class ServerCommandInterpreter {
    private final Map<String, AbstractCommand> commands;
    private final ServerCommandReadable clientInput;
    private final SessionManageable sessionManager = new SessionManager();
    private final ServerRouteFactory routeCreator = new ServerRouteFactory();

    /**
     * @param queueManager manager to be used in commands
     */
    public ServerCommandInterpreter(PriorityQueueManager queueManager,
                                    ServerCommandReadable clientInput) {
        this.clientInput = clientInput;
        queueManager.setSessionManager(sessionManager);

        commands = new HashMap<>();

        Add add = new Add(queueManager, routeCreator);
        commands.put(add.getName(), add);

        RemoveById removeById = new RemoveById(queueManager);
        commands.put(removeById.getName(), removeById);

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

        Auth auth = new Auth(sessionManager, queueManager);
        commands.put(auth.getName(), auth);

        Help help = new Help(commands.values());
        commands.put(help.getName(), help);
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
        routeCreator.setSession(request.getSession());
        sessionManager.setSession(request.getSession());

        return command.execute(request.getCommandShell().getArgument());
    }
}

