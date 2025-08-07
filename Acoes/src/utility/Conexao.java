package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    private static final Properties props = new Properties();

    static {
        props.setProperty("db.url", "jdbc:postgresql://localhost:5432/java-testes");
        props.setProperty("db.user", "postgres");
        props.setProperty("db.password", "1234");
    }

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password")
        );
    }
}