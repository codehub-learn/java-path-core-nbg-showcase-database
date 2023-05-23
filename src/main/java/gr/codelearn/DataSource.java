package gr.codelearn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static final String CONNECTION_FILE = "jdbc:h2:~/.h2/sample";
    private static final String CONNECTION_MEMORY = "jdbc:h2:mem:sample";

    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
       if(connection == null){
           connection = DriverManager.getConnection(CONNECTION_MEMORY, DB_USER, DB_PASSWORD);
       }
       return connection;
    }

    public static void loadJDBCDriver(){
        //org.h2.Driver.load(); //2nd way to load driver
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load driver");
            System.exit(-1);
        }
    }
}
