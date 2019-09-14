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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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

    Users user = LoginPageController.loggedUser;
    ParkingSpot spot;
    ParkingSpotOwner owner = new ParkingSpotOwner();

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

                float earnings = resultSet.getFloat("Earnings");

                if (earnings == 0.0) {
                    Earnings.setText("N/A");
                } else {
                    Earnings.setText(String.valueOf(earnings) + " Tk.");
                }

                Address.setText(resultSet.getString("Address"));

                RentPerHour.setText(resultSet.getString("Rent") + " Tk.");

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

}
