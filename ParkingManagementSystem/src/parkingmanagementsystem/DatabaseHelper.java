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
public class DatabaseHelper {

    public static String USERNAME = "sa";
    public static String PASSWORD = "22448800";
    public static String PATH = "jdbc:sqlserver://localhost:1433;databaseName=ParkingManagementSystem;selectMethod=cursor";

    Connection connection = null;

    public void connectDB() {
        /*coonection must be closed  by object after any operation*/
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager
                    .getConnection(PATH, USERNAME, PASSWORD);

            if (connection != null) {
                System.out.println("Connected");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            connectDB();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                System.out.println("Customer NAME: "
                        + resultSet.getString("Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void AddUser(String Name, String MobileNo, String Password) {
        try {
            connectDB();
            String sql = "INSERT INTO Users (Name, MobileNo, Password) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Name);
            statement.setString(2, MobileNo);
            statement.setString(3, Password);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            } else {
                System.out.println("User already exists");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean CheckUser(PreparedStatement ps) {
        ResultSet resultSet = null;
        boolean valid = false;
        
        try {
            //connectDB();
            resultSet = ps.executeQuery();
             
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        try
        {
            valid = resultSet.next();
        }
        catch(SQLException e)
        {
            
        }
        
        return valid;
    }    
}
