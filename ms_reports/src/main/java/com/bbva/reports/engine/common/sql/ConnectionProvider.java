package com.bbva.reports.engine.common.sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {

    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    public static void closeConnection() {

        try {
            Connection connection = connection();

            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Cannot close connection because: "
                            + e.getMessage(),
                    e);
        } finally {
            connectionHolder.set(null);
        }
    }

    public static Connection connection() {
        return connectionHolder.get();
    }

    public static Connection connection(DataSource aDataSource) {

        Connection connection = connection();

        try {
            if (connection == null) {
                connection = aDataSource.getConnection();
                connectionHolder.set(connection);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Connection not provided because: "
                            + e.getMessage(),
                    e);
        }

        return connection;
    }
}