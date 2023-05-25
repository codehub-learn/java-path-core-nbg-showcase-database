package gr.codelearn;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMSSQL {
    Server server;

    public static void main(String[] args) {
        MainMSSQL main = new MainMSSQL();
        DataSourceMSSQL.loadJDBCDriver();
        try (Connection connection = DataSourceMSSQL.getConnection()){
            main.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void createTable(){
        try (Statement statement = DataSourceMSSQL.getConnection().createStatement()){
            String sql = "CREATE TABLE Customers (CUSTOMER_ID int NOT NULL, LASTNAME varchar(255) NOT NULL, FIRSTNAME varchar(255), PRIMARY KEY (CUSTOMER_ID))";
            int i = statement.executeUpdate(sql);
            System.out.println(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
