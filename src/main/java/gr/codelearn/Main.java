package gr.codelearn;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    Server server;

    public static void main(String[] args) {
        Main main = new Main();
        main.startH2Server();
        main.loadJDBCDriver();
        main.getConnection();
        main.stopH2Server();

    }

    private final String CONNECTION_FILE = "jdbc:h2:~/.h2/sample";
    private final String CONNECTION_MEMORY = "jdbc:h2:mem:sample";

    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "";

    private void getConnection(){
        try (Connection connection = DriverManager.getConnection(CONNECTION_MEMORY, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()){
            String sql = "CREATE TABLE TEST (ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(50))";
            int i = statement.executeUpdate(sql);
            System.out.println(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadJDBCDriver(){
        //org.h2.Driver.load(); //2nd way to load driver
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load driver");
            System.exit(-1);
        }
    }

    private void startH2Server(){
        try {
            System.out.println("Starting H2 Server.");
            this.server = Server.createTcpServer();
            server.start();
        } catch (SQLException e) {
            System.out.println("Could not start server");
            System.exit(-1);
        }
    }

    private void stopH2Server(){
        if(server.isRunning(true)){
            System.out.println("Stopping H2 Server.");
            server.stop();
        }
    }

    private void iteratorShowcase(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);

        Iterator<Integer> iterator = integers.iterator();
        while(iterator.hasNext()){
            Integer next = iterator.next();
            System.out.println(next);
        }
    }
}