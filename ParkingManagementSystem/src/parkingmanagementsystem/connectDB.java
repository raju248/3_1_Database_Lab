/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import java.sql.*;

/**
 *
 * @author Tech Land
 */
public class connectDB {

    public void connectDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager
                    .getConnection(
                            "jdbc:sqlserver://localhost:1433;databaseName=ParkingManagementSystem;selectMethod=cursor", "sa", "22448800");
            System.out.println("DATABASE NAME IS:"
                    + connection.getMetaData().getDatabaseProductName());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                System.out.println("Customer NAME: "
                        + resultSet.getString("Name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
