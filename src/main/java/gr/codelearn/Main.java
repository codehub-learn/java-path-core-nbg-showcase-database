package gr.codelearn;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import static org.h2.tools.Server.startWebServer;

public class Main {

    Server server;

    public static void main(String[] args) {
        Main main = new Main();
        main.startH2Server();
        DataSource.loadJDBCDriver();
        try (Connection connection = DataSource.getConnection()){
            //main.createTable();
            main.insertData();
            main.selectData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        main.stopH2Server();
    }

    private void selectData(){
        String sql = "SELECT * FROM TEST";
        try(Statement statement = DataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);){
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(id + ", " + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertData() {
        try(Statement statement = DataSource.getConnection().createStatement()){
            String sql = "INSERT INTO TEST (NAME) VALUES ('ioannis')";
            int i = statement.executeUpdate(sql);
            System.out.println(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable(){
        try (Statement statement = DataSource.getConnection().createStatement()){
            String sql = "CREATE TABLE TEST (ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(50))";
            int i = statement.executeUpdate(sql);
            System.out.println(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void startH2Server(){
        try {
            System.out.println("Starting H2 Server.");
            this.server = Server.createTcpServer();
            server.start();
            //startWebServer(connection);
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