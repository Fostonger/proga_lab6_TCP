package database;

import data.Coordinates;
import data.Location;
import data.Route;
import session.Session;
import session.SessionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class PriorityQueueJDBCConnector implements IdGenerator {
    private final JDBCWorker jdbcWorker;
    public PriorityQueueJDBCConnector(JDBCWorker jdbcWorker) throws SQLException {
        this.jdbcWorker = jdbcWorker;
        jdbcWorker.statement(statement -> {
            statement.execute(DBStatements.CREATE_IF_NOT_EXIST.getStatement());
        });
    }
    public ArrayList<Route> getPriorityQueue() throws SQLException {
        return jdbcWorker.statement(statement -> {
            ArrayList<Route> routes = new ArrayList<Route>();
            ResultSet resultSet = statement.executeQuery(DBStatements.SELECT_ALL.getStatement());
            while(resultSet.next()) {
                routes.add(createRoute(resultSet));
            }
            return routes;
        });
    }

    public void addRoute(Route route) throws SQLException {
        jdbcWorker.preparedStatement(DBStatements.ADD_ROUTE, insertRoute -> {
            setRoute(insertRoute, route.getId(), route);
            insertRoute.execute();
        });
    }

    public void removeById(int id) throws SQLException {
        jdbcWorker.preparedStatement(DBStatements.DELETE_BY_ID, removeBy -> {
            removeBy.setInt(1, id);
            removeBy.execute();
        });
    }

    public void clearAllByUser(String author) throws SQLException {
        jdbcWorker.preparedStatement(DBStatements.CLEAR_ALL_BY_USER, removeAll -> {
            removeAll.setString(1, author);
            removeAll.execute();
        });
    }

    public void updateById(Route route, int id) throws SQLException {
        jdbcWorker.preparedStatement(DBStatements.UPDATE_BY_ID, update -> {
            setRoute(update, id, route);
            update.setInt(16, id);
            update.execute();
        });
    }

    public Session getUser(String name) throws SQLException {
        return jdbcWorker.preparedStatement(DBStatements.SELECT_USER, userStmt -> {
            userStmt.setString(1, name);
            ResultSet resultSet = userStmt.executeQuery();
            if (resultSet.next()) {
                return new Session(
                        resultSet.getString("username"),
                        resultSet.getBytes("hashPassword"),
                        SessionType.LOGIN);
            }
            return null;
        });
    }
    public void registerUser(String name, byte[] hashPassword) throws SQLException {
        jdbcWorker.preparedStatement(DBStatements.ADD_USER, userStmt -> {
            userStmt.setString(1, name);
            userStmt.setBytes(2, hashPassword);
            userStmt.execute();
        });
    }

    @Override
    public int generateId() throws SQLException {
        return jdbcWorker.statement(statement -> {
            ResultSet resultSet = statement.executeQuery(DBStatements.GENERATE_ID.getStatement());
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
            return 0;
        });
    }

    private Route createRoute(ResultSet set) throws SQLException {
        return new Route(
                set.getInt("id"),
                createCoordinates(set),
                set.getDate("creationDate").toLocalDate(),
                set.getDouble("distance"),
                createLocation(set, LocationType.TO),
                createLocation(set, LocationType.FROM),
                set.getString("name"),
                set.getString("author")
        );
    }
    private void setRoute(PreparedStatement insertRoute, int id, Route route) throws SQLException {
        insertRoute.setInt(1, id);
        setCoordinates(insertRoute, route.getCoordinates());
        insertRoute.setDate(4, new Date(route.getCreationDate().toEpochDay()));
        insertRoute.setDouble(5, route.getDistance());
        setLocation(insertRoute, route.getTo(), LocationType.TO);
        setLocation(insertRoute, route.getFrom(), LocationType.FROM);
        insertRoute.setString(14, route.getName());
        insertRoute.setString(15, route.getAuthor());
    }
    private Coordinates createCoordinates(ResultSet set) throws SQLException {
        return new Coordinates(
                set.getInt("coordinateX"),
                set.getInt("coordinateY")
        );
    }
    private void setCoordinates(PreparedStatement stmt, Coordinates coord) throws SQLException {
        stmt.setInt(2, coord.getX());
        stmt.setInt(3, coord.getY());
    }
    private Location createLocation(ResultSet set, LocationType type) throws SQLException {
        String locationPrefix = switch (type) {
            case TO -> "locationTo";
            case FROM -> "locationFrom";
        };
        if (set.getString(locationPrefix + "X") == null)
            return null;
        return new Location(
                set.getLong(locationPrefix + "X"),
                set.getInt(locationPrefix + "Y"),
                set.getDouble(locationPrefix + "Z"),
                set.getString(locationPrefix + "Name")
        );
    }
    private void setLocation(PreparedStatement stmt, Location loc, LocationType type) throws SQLException {
        int offset = switch (type) {
            case TO -> 0;
            case FROM -> 4;
        };
        if (loc != null) {
            stmt.setLong(6 + offset, loc.getX());
            stmt.setInt(7 + offset, loc.getY());
            stmt.setDouble(8 + offset, loc.getZ());
            stmt.setString(9 + offset, loc.getName());
        } else {
            stmt.setNull(6 + offset, Types.NULL);
            stmt.setNull(7 + offset, Types.NULL);
            stmt.setNull(8 + offset, Types.NULL);
            stmt.setNull(9 + offset, Types.NULL);
        }
    }

    private enum LocationType {
        TO, FROM;
    }
}
