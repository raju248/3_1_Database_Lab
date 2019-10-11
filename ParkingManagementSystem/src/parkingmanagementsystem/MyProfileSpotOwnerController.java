/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class MyProfileSpotOwnerController implements Initializable {

    @FXML
    private Label Name;
    @FXML
    private Label PhoneNo;
    @FXML
    private Label AccountType;
    @FXML
    private Label Rating;
    @FXML
    private Label Earnings;
    @FXML
    private Label Address;
    @FXML
    private JFXButton EditButton;
    @FXML
    private Label changePassword;
    @FXML
    private Label RentPerHour;
    
    @FXML
    private Label ViewSpots;


    Users user = LoginPageController.loggedUser;
    ParkingSpot spot;
    ParkingSpotOwner owner = new ParkingSpotOwner();
    int spotOwnerId = 0;

    DatabaseHelper db = new DatabaseHelper();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Name.setText(user.getName());
        PhoneNo.setText(user.getPhoneNo());
        AccountType.setText("Parking Spot Owner");

        getDataFromTable();
        totalEarning();
        totalRating();

        changePassword.setOnMouseClicked((e)
                -> {
            AnchorPane root = null;

            Pane parent = (Pane) EditButton.getParent();

           
            try {
                root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
          

            root.prefHeightProperty().bind(parent.heightProperty());
            root.prefWidthProperty().bind(parent.widthProperty());

            parent.getChildren().add(root);
        });
        
        ViewSpots.setOnMouseClicked(e->
        {
                 try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("YourParkingPermanentSpot.fxml"));
            Parent root = loader.load();

            //Get controller of scene2
            YourParkingPermanentSpotController scene2Controller = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            

            //Show scene 2 in new window            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        });

    }

    void getDataFromTable() {
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
                float rating = resultSet.getFloat("Rating");

                if (rating == 0.0) {
                    Rating.setText("N/A");
                } else {
                    Rating.setText(String.valueOf(rating) + "/5");
                }

                Address.setText(resultSet.getString("Address"));

                RentPerHour.setText(resultSet.getString("Rent") + " Tk.");
                
                //System.out.println(resultSet.getInt("SpotOwnerId"));
                spotOwnerId = resultSet.getInt("SpotOwnerId");
                
                

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
    
    void totalEarning()
    {
        db.connectDB();
        
        String sql = " select SUM(TotalAmount) as total from ParkingRequests where ReceiverId = "+ spotOwnerId ;
        
        try {
            PreparedStatement ps = db.connection.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                int earn = rs.getInt(1);
                
                
                if(earn == 0)
                {
                    Earnings.setText("N/A");
                }
                else
                {
                    Earnings.setText(String.valueOf(earn)+" Tk.");
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MyProfileSpotOwnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(db.connection!=null)
            {
                try {
                    db.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MyProfileSpotOwnerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }

    @FXML
    void EditProfileButtonAction(ActionEvent event) {
        AnchorPane root = null;

        Pane parent = (Pane) EditButton.getParent();

        try {
            root = FXMLLoader.load(getClass().getResource("EditProfileParkingSpotOwner.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println(e.toString());
        }

        root.prefHeightProperty().bind(parent.heightProperty());
        root.prefWidthProperty().bind(parent.widthProperty());

        parent.getChildren().add(root);

    }
    
    
    void totalRating()
    {
        db.connectDB();
        
        String sql = "select AVG(Cast(Rating as float)) as RatingAv from ParkingRequests where Rating is not null and ReceiverId = "+ spotOwnerId ;
        
        try {
            PreparedStatement ps = db.connection.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                float rate = rs.getFloat(1);
                
                
                if(rate == 0.0)
                {
                    Rating.setText("N/A");
                }
                else
                {
                    Rating.setText(String.valueOf(rate));
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MyProfileSpotOwnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(db.connection!=null)
            {
                try {
                    db.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MyProfileSpotOwnerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
//    @FXML
//    void viewSpot(ActionEvent event) {
//        try {
//            //Load second scene
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("YourParkingPermanentSpot.fxml"));
//            Parent root = loader.load();
//
//            //Get controller of scene2
//            YourParkingPermanentSpotController scene2Controller = loader.getController();
//            //Pass whatever data you want. You can have multiple method calls here
//            
//
//            //Show scene 2 in new window            
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Second Window");
//            stage.show();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }



}
