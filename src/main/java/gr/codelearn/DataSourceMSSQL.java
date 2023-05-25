package gr.codelearn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceMSSQL {

    private static final String MSSQL_CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=sampleDatabase;integratedSecurity=false;encrypt=false;trustServerCertificate=false";

    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "p@ssw0rd123";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(MSSQL_CONNECTION, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public static void loadJDBCDriver(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load driver");
            System.exit(-1);
        }
    }
}
