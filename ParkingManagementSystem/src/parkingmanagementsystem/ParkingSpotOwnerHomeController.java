/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class ParkingSpotOwnerHomeController implements Initializable {

    @FXML
    private Pane Pane;

    @FXML
    private AnchorPane FInalPane;

    @FXML
    private Label Name2;

    @FXML
    private Label VehicleLicense1;

    @FXML
    private Label VehicleModel1;

    @FXML
    private Label StartTime2;

    @FXML
    private Label EndTime2;

    @FXML
    private Label Amount2;

    @FXML
    private JFXButton DoneButton;

    @FXML
    private AnchorPane pane1;

    @FXML
    private JFXButton GoOnlineButton;

    @FXML
    private Label StatusLabel;

    @FXML
    private ProgressBar Progressbar;

    @FXML
    private Label IndicatorLabel;

    @FXML
    private AnchorPane RequestPane;

    @FXML
    private Label Model;

    @FXML
    private Label LicenseNo;

    @FXML
    private Label PhoneNo1;

    @FXML
    private Label Name1;

    @FXML
    private JFXButton AcceptButton;

    @FXML
    private JFXButton EndButton;

    @FXML
    private JFXButton CancelButton;

    @FXML
    private Label startLabel;

    @FXML
    private Label StartTime;

    DatabaseHelper db = new DatabaseHelper();
    Users user = LoginPageController.loggedUser;
    static ParkingSpotOwner ps = new ParkingSpotOwner();
    

    int status = 0;
    int spotStatus = 0;
    int senderId = 0;
    int requestId = 0;
    int SpotId;
    int cancelStatus = 0;
    int rentValue = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ps.setUserId(user.getUserId());
        ps.setSpotOwerId(getParkingSpotOwnerId());
        
        Pane.getChildren().clear();

        StatusLabel.setText("You are currently Offline!");
        GoOnlineButton.setText("Go Online");
        IndicatorLabel.setVisible(false);
        Progressbar.setVisible(false);

        getStatus();
        parkingSpotRent();
        //getStatus();

        if (spotStatus == 1) {
            Pane.getChildren().clear();
            Pane.getChildren().add(RequestPane);
        } else {

            Pane.getChildren().clear();
            Pane.getChildren().add(pane1);

            GoOnlineButton.setOnAction((e) -> {
                GoOnline();
            });

        }

        CancelButton.setOnAction(e -> {
            cancelRequest();

            Pane.getChildren().clear();
            Pane.getChildren().add(pane1);
            this.initialize(url, rb);
        });

        AcceptButton.setOnAction(e
                -> {
            if (AcceptButton.isVisible()) {
                acceptRequest();
            }
        });

        DoneButton.setOnAction(e
                -> {
            this.initialize(url, rb);
        });

        EndButton.setOnAction(e -> {
            if (EndButton.isVisible()) {
                EndParking();
            }
        });

    }

    void GoOnline() {

        if (status == 0) {
            StatusLabel.setText("You are Online!");
            GoOnlineButton.setText("Go offline");
            IndicatorLabel.setVisible(true);
            Progressbar.setVisible(true);
            status = 1;
            

            Task<Integer> task = new Task<Integer>() {
                @Override
                protected Integer call() {
                    int iterations;
                    for (iterations = 1; iterations > 0; iterations++) {

                        if (status == 0) {
                            System.out.println("cancelled");
                            this.isDone();
                            break;
                        }
                        //System.out.println("Iteration " + iterations);
                        int receiverId = getParkingSpotOwnerId();
                        System.out.println(receiverId);

                        //read table until location matches
                        String sql = "select * from users \n"
                                + "left join ParkingSpotOwner on\n"
                                + "Users.UserId = ParkingSpotOwner.UserId \n"
                                + "left join ParkingSpot on \n"
                                + "ParkingSpot.SpotOwnerId = ParkingSpotOwner.SpotOwnerId\n"
                                + "where Users.PhoneNo = ?";

                        DatabaseHelper db2 = new DatabaseHelper();
                        db2.connectDB();

                        PreparedStatement ps;
                        try {
                            ps = db2.connection.prepareStatement(sql);
                            ps.setString(1, user.getPhoneNo());

                            ResultSet resultSet = ps.executeQuery();

                            String address = "";

                            if (resultSet.next()) {
                                address = resultSet.getString("Address");
                            }

                            if (db2.connection != null) {
                                db2.connection.close();
                            }

                            System.out.println(address);

                            sql = "Select * from ParkingRequests where ReceiverId is null and Status = 0";
                            db2.connectDB();
                            ps = db2.connection.prepareStatement(sql);

                            resultSet = ps.executeQuery();

                            while (resultSet.next()) {
                                try {
                                    System.out.println(resultSet.getInt("RequestId"));
                                    System.out.println(resultSet.getInt("SenderId"));
                                    System.out.println(resultSet.getInt("ReceiverId"));
                                    System.out.println(resultSet.getString("Location"));

                                    requestId = resultSet.getInt("RequestId");
                                    senderId = resultSet.getInt("SenderId");
                                    int receiverIddd = resultSet.getInt("ReceiverId");
                                    String location = resultSet.getString("Location");
                                    location = location.toLowerCase();
                                    address = address.toLowerCase();

                                    if (address.contains(location)) {
                                        System.out.println("inside");

                                        //status = 0;
                                        //changeStatus();
                                        DatabaseHelper db1 = new DatabaseHelper();
                                        String sql1 = "Update ParkingRequests set ReceiverId = ? where RequestId = ?";
                                        PreparedStatement ps1 = null;
                                        try {
                                            db2.connection.close();
                                            db1.connectDB();
                                            ps1 = db1.connection.prepareStatement(sql1);
                                            System.out.println("inside1");

                                            ps1.setInt(1, receiverId);
                                            ps1.setInt(2, requestId);

                                            int row = ps1.executeUpdate();

                                            if (row > 0) {
                                                System.out.println("request found!!!");
                                                status = 0;
                                                
                                                loadRequestPane();
                                                checkIfCancelled();
                                                break;
                                            }

                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }

                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    return iterations;
                }
            };

            if (task != null) {
                System.out.println("Running");

            }

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();

        } else if (status == 1) {
            StatusLabel.setText("You are currently Offline!");
            GoOnlineButton.setText("Go Online");
            IndicatorLabel.setVisible(false);
            Progressbar.setVisible(false);
            status = 0;
            
        }

    }

    void acceptRequest() {
        AcceptButton.setVisible(false);
        CancelButton.setVisible(false);
        EndButton.setVisible(true);
        cancelStatus = 0;

        startLabel.setVisible(true);
        StartTime.setVisible(true);

        String sql = "update ParkingRequests set Status = 1 , StartTime = ?, Rent = ? where RequestId = " + requestId;

        DatabaseHelper dbc = new DatabaseHelper();

        dbc.connectDB();

        try {
            PreparedStatement ps = dbc.connection.prepareStatement(sql);
            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            ps.setTimestamp(1, timestamp);
            ps.setInt(2, rentValue);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Request Accepted");

                DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                String dateString2 = dateFormat2.format(new Date()).toString();
                StartTime.setText(dateString2);
                StartTime2.setText(dateString2);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dbc.connection != null) {
                try {
                    dbc.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    void EndParking() {
        AcceptButton.setVisible(true);
        CancelButton.setVisible(true);
        EndButton.setVisible(false);

        String sql = "update ParkingRequests set Status = 2 , EndTime = ? where RequestId = " + requestId;

        DatabaseHelper dbc = new DatabaseHelper();

        dbc.connectDB();

        try {
            PreparedStatement ps = dbc.connection.prepareStatement(sql);

            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            ps.setTimestamp(1, timestamp);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Parking Ended");
                loadFinalPane();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dbc.connection != null) {
                try {
                    dbc.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    void loadFinalPane() {
        Pane.getChildren().clear();
        startLabel.setVisible(false);
        StartTime.setVisible(false);
        VehicleDetails();
        AcceptButton.setText("Accept");
        CancelButton.setVisible(true);
        DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String dateString2 = dateFormat2.format(new Date()).toString();
        StartTime2.setText(StartTime.getText());
        EndTime2.setText(dateString2);
        Pane.getChildren().add(FInalPane);

        updateAmount();

        Amount2.setText(String.valueOf(getAmount())+" Tk. ");
    }

    void updateAmount() {
        
        String sql = "  update ParkingRequests set TotalAmount =\n"
                + "  case \n"
                + "	when CEILING(DATEDIFF(hour, EndTime, StartTime)) = 0\n"
                + "	then Rent\n"
                + "	else CEILING(DATEDIFF(hour, EndTime, StartTime))*Rent\n"
                + "  end \n"
                + "  where RequestId = " + requestId;

        DatabaseHelper db1 = new DatabaseHelper();
        db1.connectDB();

        try {

            PreparedStatement ps = db1.connection.prepareStatement(sql);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Updated Amount");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (db1.connection != null) {
                try {
                    db1.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public int getAmount() {
        String sql = "Select TotalAmount from ParkingRequests where RequestId = " + requestId;

        DatabaseHelper db1 = new DatabaseHelper();

        db1.connectDB();

        try {
            PreparedStatement ps = db1.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (db1.connection != null) {
                try {
                    db1.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return 0;
    }

    void loadRequestPane() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Update UI here
//                    StatusLabel.setText("You are currently Offline!");
//                    IndicatorLabel.setVisible(false);
//                    Progressbar.setVisible(false);
//                    GoOnlineButton.setText("Go Online");
                Pane.getChildren().clear();
                startLabel.setVisible(false);
                StartTime.setVisible(false);
                VehicleDetails();
                AcceptButton.setVisible(true);
                CancelButton.setVisible(true);
                EndButton.setVisible(false);
                Pane.getChildren().add(RequestPane);

            }
        });
    }

    void changeStatus() {

        String sql = "update ParkingSpotOwner set Status = ? where SpotOwnerId = ?";

        DatabaseHelper dbc = new DatabaseHelper();

        dbc.connectDB();

        try {
            PreparedStatement ps = dbc.connection.prepareStatement(sql);

            ps.setInt(1, status);
            ps.setInt(2, getParkingSpotOwnerId());

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Status change to " + status);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dbc.connection != null) {
                try {
                    dbc.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    void getStatus() {
        String sql = "select * from users \n"
                + "inner join ParkingSpotOwner on\n"
                + "Users.UserId = ParkingSpotOwner.UserId \n"
                + "where Users.PhoneNo = ?";

        try {
            db.connectDB();

            PreparedStatement ps = db.connection.prepareStatement(sql);

            ps.setString(1, user.getPhoneNo());

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {

                SpotId = resultSet.getInt("SpotId");

                if (resultSet.getInt(7) == 1) {
                    StatusLabel.setText("You are Online!");
                    GoOnlineButton.setText("Go offline");
                    IndicatorLabel.setVisible(true);
                    Progressbar.setVisible(true);
                    status = 1;
                } else {
                    StatusLabel.setText("You are currently Offline!");
                    GoOnlineButton.setText("Go Online");
                    IndicatorLabel.setVisible(false);
                    Progressbar.setVisible(false);
                    status = 0;
                }

                //spot = new ParkingSpot(resultSet.getString("SpotOwnerId"),resultSet.getInt("Status"),);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (db.connection != null) {
                    db.connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void checkIfCancelled() {

        cancelStatus = 1;
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() {
                int iterations;
                for (iterations = 1; iterations > 0; iterations++) {

                    if (cancelStatus == 0) {
                        System.out.println("cancelled..");
                        this.isDone();
                        break;
                    }

                    DatabaseHelper db1 = new DatabaseHelper();

                    db1.connectDB();
                    System.out.println("searching if cancelled ..." + requestId);

                    String sql = "Select * from ParkingRequests where requestId = " + requestId + " and Status = 3";

                    try {
                        PreparedStatement ps1 = db1.connection.prepareStatement(sql);

                        ResultSet rs = ps1.executeQuery();

                        if (rs.next()) {

                            changeUIIfCancelled();

                            cancelStatus = 0;

                            System.out.println("cancelled...");

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (db1.connection != null) {
                            try {
                                db1.connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }
                return iterations;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    void changeUIIfCancelled() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Update UI here
                Pane.getChildren().clear();

                StatusLabel.setText("Request cancelled!\n Try Again");
                GoOnlineButton.setText("Go Online");
                IndicatorLabel.setVisible(false);
                Progressbar.setVisible(false);
                Pane.getChildren().add(pane1);
                status = 0;
                changeStatus();
            }
        });
    }

    public int getParkingSpotOwnerId() {
        int userId = LoginPageController.loggedUser.getUserId();

        String sql = "Select p.SpotOwnerId from ParkingSpotOwner as p join Users as u on p.userId = u.userId where p.userId = " + userId;
        int receiverId = 0;
        PreparedStatement ps;
        db.connectDB();

        try {
            ps = db.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                receiverId = rs.getInt("SpotOwnerId");
                //System.out.println(receiverId);    
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (db.connection != null) {
                try {
                    db.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return receiverId;

    }

    void parkingSpotRent() {
        String sql = "Select Rent from ParkingSpot where SpotOwnerId = " + ps.getSpotOwerId();

        db.connectDB();

        try {
            PreparedStatement ps = db.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //spotStatus = rs.getInt(1);
                rentValue = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (db.connection != null) {
                try {
                    db.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    void VehicleDetails() {
        String sql = "select u.Name, v.VehicleLicenseNo, v.VehicleModel, u.PhoneNo from Users as u\n"
                + "inner join VehicleOwner as vo\n"
                + "on u.UserId = vo.UserId\n"
                + "inner join Vehicle as v\n"
                + "on vo.VehicleOwnerId = v.VehicleOwnerId\n"
                + "where vo.VehicleOwnerId = " + senderId;

        try {
            db.connectDB();
            PreparedStatement ps = db.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                VehicleLicense1.setText(rs.getString(2));
                LicenseNo.setText(rs.getString(2));
                Name1.setText(rs.getString(1));
                Name2.setText(rs.getString(1));
                VehicleModel1.setText(rs.getString(3));
                Model.setText(rs.getString(3));
                PhoneNo1.setText(rs.getString(4));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (db.connection != null) {
                try {
                    db.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    void cancelRequest() {
        String sql = "update ParkingRequests set Status = 3 where RequestId = " + requestId;

        DatabaseHelper dbc = new DatabaseHelper();

        dbc.connectDB();

        try {
            PreparedStatement ps = dbc.connection.prepareStatement(sql);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Request Cancelled");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dbc.connection != null) {
                try {
                    dbc.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    @FXML
    void viewSpot(ActionEvent event)
    {
        
    }

}
