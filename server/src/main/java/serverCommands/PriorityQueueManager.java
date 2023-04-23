package serverCommands;

import data.CollectionContainer;
import data.Route;
import database.Authorizer;
import database.JDBCWorker;
import database.LoginAnswer;
import database.PriorityQueueJDBCConnector;
import org.apache.logging.log4j.Logger;
import queueManager.PriorityQueueManageable;
import session.Session;
import utils.RouteFilter;
import utils.sessionManager.SessionManageable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PriorityQueueManager implements PriorityQueueManageable, Authorizer {
    private CollectionContainer container;
    private Map<Integer, Route> ids;
    private final PriorityQueueJDBCConnector dbConnector;
    private final Logger logger;
    private SessionManageable session;

    /**
     * Class that manages collection
     * @param dbConnector worker whose work is to connect and interact with database
     */
    private PriorityQueueManager(PriorityQueueJDBCConnector dbConnector, Logger logger) {
        this.dbConnector = dbConnector;
        this.logger = logger;
        try {
            container = new CollectionContainer(dbConnector.getPriorityQueue());
            ids = container.updateQueue(queue -> {
                return Collections.synchronizedMap(queue.stream().collect(Collectors.toMap(Route::getId, route -> route)));
            });
        } catch (SQLException e) {
            container = new CollectionContainer(Collections.synchronizedList(new ArrayList<Route>()));
            ids = Collections.synchronizedMap(new HashMap<>());
        }
    }
    public static PriorityQueueManager InitWithDB(String url, String username, String password, Logger logger) throws SQLException {
        JDBCWorker jdbcWorker = new JDBCWorker(url, username, password);
        PriorityQueueJDBCConnector jdbcConnector = new PriorityQueueJDBCConnector(jdbcWorker);
        return new PriorityQueueManager(jdbcConnector, logger);
    }

    public void add(Route route) throws SQLException {
        if (route.getId() < 1)
            throw new SQLException("Couldn't generate id for that route!");
        dbConnector.addRoute(route);
        container.updateQueue( queue -> {
            queue.add(route);
        });
        ids.put(route.getId(), route);
    }
    public boolean addRouteIfMin(Route route) throws SQLException {
        return container.updateQueue(queue -> {
            if (queue.isEmpty()) {
                this.add(route);
                return true;
            }
            if (queue.stream().min(RouteFilter.DISTANCE_LESS).get().getDistance() > route.getDistance()) {
                add(route);
                return true;
            }
            return false;
        });
    }
    public boolean addRouteIfMax(Route route) throws SQLException  {
        return container.updateQueue(queue -> {
            if (queue.isEmpty()) {
                add(route);
                return true;
            }
            if (queue.stream().max(RouteFilter.DISTANCE_GREATER).get().getDistance() < route.getDistance()) {
                add(route);
                return true;
            }
            return false;
        });
    }
    public long countLessThanDistance(double distance) throws SQLException {
        return container.updateQueue(queue -> {
            return queue.stream()
                    .filter(route -> route.getDistance() < distance)
                    .count();
        });
    }
    public long countGreaterThanDistance(double distance) throws SQLException {
        return container.updateQueue(queue -> {
            return queue.stream()
                    .filter(route -> route.getDistance() > distance)
                    .count();
        });
    }
    public List<Route> filterLessThanDistance(double distance) throws SQLException {
        return container.updateQueue(queue -> {
            return queue.stream()
                    .filter(route -> route.getDistance() < distance)
                    .sorted(RouteFilter.BY_NAME)
                    .toList();
        });
    }
    public void updateById(int id, Route newRoute) throws NoSuchElementException, SQLException  {
        container.updateQueue(queue -> {
            if (!ids.containsKey(id)) throw new NoSuchElementException();
            if (!isAuthor(id)) throw new SQLException("you are not the owner of the route");
            dbConnector.updateById(newRoute, id);
            queue.remove(ids.replace(id, newRoute));
            queue.add(newRoute);
        });
    }
    public boolean containsId(int id)  {
        return ids.containsKey(id);
    }
    public void save() throws IOException {}
    public Route getHead() throws SQLException {
        return container.updateQueue(queue -> { return queue.peek(); } );
    }
    public void clear() throws SQLException  {
        container.updateQueue(queue -> {
            dbConnector.clearAllByUser(session.getName());
            ids = ids.values().stream()
                    .filter(value -> !value.getAuthor().equals(session.getName()))
                    .collect(Collectors.toMap(Route::getId, value -> value));
            queue.removeIf(route -> !ids.containsKey(route.getId()));
        });
    }
    public List<Route> getQueue() throws SQLException {
        return container.updateQueue(queue -> {
            return queue.stream().toList();
        });
    }
    public CollectionContainer getContainer() {
        return container;
    }
    public void removeById(int id) throws NoSuchElementException, SQLException {
        container.updateQueue(queue -> {
            if (!ids.containsKey(id)) throw new NoSuchElementException();
            if (!isAuthor(id)) throw new SQLException("you are not author of the route!");
            dbConnector.removeById(id);
            queue.remove(ids.remove(id));
        });
    }
    public int generateId() {
        try {
            return dbConnector.generateId();
        } catch (SQLException e) {
            logger.error("Couldn't generate id, error: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public LoginAnswer register(String name, byte[] hashedPassword) {
        try {
            Session curSession = dbConnector.getUser(name);
            if (curSession != null) return LoginAnswer.DUPLICATE;
            dbConnector.registerUser(name, hashedPassword);
            return LoginAnswer.SUCCESS;
        } catch (SQLException e) {
            return LoginAnswer.DB_ERROR;
        }
    }

    @Override
    public LoginAnswer login(String name, byte[] hashedPassword) {
        try {
            Session curSession = dbConnector.getUser(name);
            if (curSession == null) return LoginAnswer.USER_NOT_FOUND;
            if (!Arrays.equals(curSession.getPasswordHash(), hashedPassword))
                return LoginAnswer.WRONG_PASSWORD;
            return LoginAnswer.SUCCESS;
        } catch (SQLException e) {
            return LoginAnswer.DB_ERROR;
        }
    }

    public void setSessionManager(SessionManageable session) {
        this.session = session;
    }
    private boolean isAuthor(int id) {
        return session.getName().equals(ids.get(id).getAuthor());
    }
}
