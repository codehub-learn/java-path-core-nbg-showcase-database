package gr.codelearn;

import gr.codelearn.model.Test;
import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    Server server;

    public static void main(String[] args) {
        Main main = new Main();
        main.startH2Server();
        DataSource.loadJDBCDriver();
        try (Connection connection = DataSource.getConnection()) {
            //main.createTable();
            //main.insertData();
            //main.insertMultipleData(List.of("ioa", "nikos", "manolis"));
            Test builtTest = Test.builder().firstname("Nikoleta").lastname("Nikol").build();
            main.insertDataV2(builtTest);
            System.out.println(builtTest);
            //main.selectData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        main.stopH2Server();
    }

    private void selectData() {
        String sql = "SELECT * FROM TEST";
        try (Statement statement = DataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                System.out.println(id + ", " + firstname + ", " + lastname);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertData() {
        try (Statement statement = DataSource.getConnection().createStatement()) {
            String sql = "INSERT INTO TEST (FIRSTNAME, LASTNAME) VALUES ('ioannis', 'dan')";
            int i = statement.executeUpdate(sql);
            System.out.println(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertMultipleData(List<String> names) {
        String sql = "INSERT INTO TEST (FIRSTNAME, LASTNAME) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(sql)) {
            for (String name : names) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, "lastname");
                preparedStatement.addBatch();
                preparedStatement.clearParameters();
            }
            int[] ints = preparedStatement.executeBatch();
            System.out.println(Arrays.toString(ints));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertDataV2(Test test) {
        String sql = "INSERT INTO TEST (FIRSTNAME, LASTNAME) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, test.getFirstname());
            preparedStatement.setString(2, test.getLastname());
            int i = preparedStatement.executeUpdate();
            System.out.println("Result of adding test object:" + i);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            System.out.println(generatedKeys.getLong(1));
            test.setId(generatedKeys.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable() {
        try (Statement statement = DataSource.getConnection().createStatement()) {
            String sql = "CREATE TABLE TEST (ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, FIRSTNAME VARCHAR(50), LASTNAME VARCHAR(50))";
            int i = statement.executeUpdate(sql);
            System.out.println(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void startH2Server() {
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

    private void stopH2Server() {
        if (server.isRunning(true)) {
            System.out.println("Stopping H2 Server.");
            server.stop();
        }
    }

    private void iteratorShowcase() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);

        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }
    }
}