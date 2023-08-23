package utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/android_class_design?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String user = "root";
    private static final String password = "root";
    private static Connection connection = null;
    private static Statement statement = null;

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public Statement getStatement() throws SQLException {
        if (statement == null) {
            statement = getConnection().createStatement();
        }
        return statement;
    }

    public static void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}

