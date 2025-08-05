package utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conection {
    private static Connection connection = null;

    private static Properties props = new Properties() {{
        setProperty("db.url", "jdbc:postgresql://localhost:5432/java-testes");
        setProperty("db.user", "postgres");
        setProperty("db.password", "1234");
    }};

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}