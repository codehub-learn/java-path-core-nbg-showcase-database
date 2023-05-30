package gr.codelearn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceConnectionPooling {
    private static final String DB_CONNECTION_URL_FILE_MODE = "jdbc:h2:~/.h2/sample";
    private static final String DB_CONNECTION_URL_MEMORY_MODE = "jdbc:h2:mem:sample";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    //Hikari connection pooling
    private static HikariDataSource hikariDataSource;

    private DataSourceConnectionPooling() {
    }

    private static void initializeHikariConnectionPooling() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_CONNECTION_URL_MEMORY_MODE);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);

        // This property controls the maximum number of milliseconds that a client (that's you) will wait for a
        // connection from the pool.
        config.setConnectionTimeout(10000);

        config.addDataSourceProperty("maximumPoolSize", "10");
        config.addDataSourceProperty("minimumIdle", "2");
        config.addDataSourceProperty("idleTimeout", "300000");

        hikariDataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (hikariDataSource == null) {
            initializeHikariConnectionPooling();
        }
        return hikariDataSource.getConnection();
    }

    public static void loadDatabaseDriver() {
        org.h2.Driver.load();

        // Traditional way of loading database driver
        // H2 driver from http://www.h2database.com
		/*
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		*/
        System.out.println("H2 JDBC driver server has been successfully loaded.");
    }
}
