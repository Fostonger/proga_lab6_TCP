package database;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCWorker {
    public interface SQLFunction<T, R> {
        R apply(T object) throws SQLException;
    }

    public interface SQLConsumer<T> {
        void accept(T object) throws SQLException;
    }

    private final DataSource connectionPool;

    public JDBCWorker(String url, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        this.connectionPool = dataSource;
    }

    private void connection(SQLConsumer<? super Connection> consumer) throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            consumer.accept(conn);
        }
    }

    private <R> R connection(SQLFunction<? super Connection, ? extends R> function) throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            return function.apply(conn);
        }
    }

    public <R> R statement(SQLFunction<? super Statement, ? extends R> function) throws SQLException {
        return connection(conn -> {
            try (Statement stmt = conn.createStatement()) {
                return function.apply(stmt);
            }
        });
    }

    public void statement(SQLConsumer<? super Statement> consumer) throws SQLException {
        connection(conn -> {
            try (Statement stmt = conn.createStatement()) {
                consumer.accept(stmt);
            }
        });
    }

    public <R> R preparedStatement(
            StatementHoldable sql, SQLFunction<? super PreparedStatement, ? extends R> function
    ) throws SQLException {
        return connection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql.getStatement())) {
                return function.apply(stmt);
            }
        });
    }

    public void preparedStatement(StatementHoldable sql, SQLConsumer<? super PreparedStatement> consumer) throws SQLException {
        connection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql.getStatement())) {
                consumer.accept(stmt);
            }
        });
    }
}

