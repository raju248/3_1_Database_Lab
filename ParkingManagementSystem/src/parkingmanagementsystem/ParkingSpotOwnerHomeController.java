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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class ParkingSpotOwnerHomeController implements Initializable {

    @FXML
    private JFXButton GoOnlineButton;
    @FXML
    private Label StatusLabel;
    @FXML
    private Label IndicatorLabel;

    DatabaseHelper db = new DatabaseHelper();
    Users user = LoginPageController.loggedUser;

    int status = 0;

    @FXML
    private ProgressBar Progressbar;
    
    int SpotId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        getStatus();

        GoOnlineButton.setOnAction((e) -> {
            GoOnline();
            
        });

    }

    void GoOnline() {
       
   
        if (status == 0) {
            StatusLabel.setText("You are Online!");
            GoOnlineButton.setText("Go offline");
            IndicatorLabel.setVisible(true);
            Progressbar.setVisible(true);
            status = 1;
            changeStatus();

            Task<Integer> task = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int iterations;
                    for (iterations = 1; iterations > 0; iterations++) {

                        if (status == 0) {
                            System.out.println("cancelled");
                            this.isDone();
                            break;
                        }
                        System.out.println("Iteration " + iterations);
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
            changeStatus();
         
        }

    }
    
    void changeStatus()
    {
        
        String sql = "update ParkingSpot set Status = ? where SpotId = ?";
        
        db.connectDB();
        
        try {
            PreparedStatement ps = db.connection.prepareStatement(sql);
            
            ps.setInt(1, status);
            ps.setInt(2, SpotId);
            
            int row =  ps.executeUpdate();
            
            if(row>0)
            {
                System.out.println("Status change to "+ status);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    void getStatus() {
        String sql = "select * from users \n"
                + "left join ParkingSpotOwner on\n"
                + "Users.UserId = ParkingSpotOwner.UserId \n"
                + "left join ParkingSpot on \n"
                + "ParkingSpot.SpotOwnerId = ParkingSpotOwner.SpotOwnerId\n"
                + "where Users.PhoneNo = ?";

        try {
            db.connectDB();

            PreparedStatement ps = db.connection.prepareStatement(sql);

            ps.setString(1, user.getPhoneNo());

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                
                SpotId = resultSet.getInt("SpotId");

                if (resultSet.getInt(12) == 1) {
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

}
