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
    ResultSet resultSet = null;

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
            resultSet = statement
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

    public int AddUser(Users user) {

        try {
            connectDB();
            String sql = "INSERT INTO Users (Name, PhoneNo, Password, Type) VALUES (?, ?, ?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPhoneNo());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getType());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!   ");

                resultSet = statement.getGeneratedKeys();

                resultSet.next();

                System.out.println(resultSet.getInt(1));

                return resultSet.getInt(1);
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

        return 0;
    }

    public int AddVehicleOwner(VehicleOwner owner) {

        try {
            connectDB();
            String sql = "INSERT INTO VehicleOwner (UserId, Status) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, owner.getUserId());
            statement.setInt(2, owner.getStatus());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully in vehicle owner table");

                resultSet = statement.getGeneratedKeys();

                resultSet.next();

                System.out.println(resultSet.getInt(1));

                return resultSet.getInt(1);
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

        return 0;
    }

    public boolean CheckUser(PreparedStatement ps) {
        boolean exists = false;

        try {
            //connectDB();
            resultSet = ps.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            exists = resultSet.next();
        } catch (SQLException e) {

        }

        return exists;
    }

    public void addVehicle(Vehicle vehicle) {

        try {
            connectDB();
            String sql = "INSERT INTO Vehicle (VehicleLicenseNo, Status, VehicleModel, VehicleOwnerId) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, vehicle.getVehicleLicenseNo());
            statement.setInt(2, vehicle.getVehicleStatus());
            statement.setString(3, vehicle.getVehicleModel());
            statement.setInt(4, vehicle.getVehicleOwnerId());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully in vehicle table");

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

    boolean checkVehicleExist(String license) {
        boolean exists = false;
        try {
            connectDB();
            String sql = "SELECT * FROM Vehicle WHERE VehicleLicenseNO = '" + license + "'";

            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            exists = resultSet.next();

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public int AddParkingSpotOwner(ParkingSpotOwner owner) {
        try {
            connectDB();
            String sql = "INSERT INTO ParkingSpotOwner (UserId, Status) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, owner.getUserId());
            statement.setInt(2, owner.getStatus());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully in parking spot owner table");

                resultSet = statement.getGeneratedKeys();

                resultSet.next();

                System.out.println(resultSet.getInt(1));

                return resultSet.getInt(1);
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

        return 0;
    }

    public void addParkingSpot(ParkingSpot spot) {
        try {
            connectDB();
            String sql = "INSERT INTO ParkingSpot (Rent, Address, Status, Rating, SpotOwnerId) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1, spot.getRent());
            statement.setString(2, spot.getAddress());
            statement.setInt(3, spot.getStatus());
            statement.setFloat(4, spot.getRating());
            statement.setInt(5, spot.getSpotOwnerId());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully in parking spot table");

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
}
