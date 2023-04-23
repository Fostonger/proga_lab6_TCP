package database;

import java.sql.SQLException;

public interface IdGenerator {
    int generateId() throws SQLException;
}
