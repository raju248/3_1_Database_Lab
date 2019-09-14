/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class EditProfileParkingSpotOwnerController implements Initializable {

    @FXML
    private JFXTextField Name;
    @FXML
    private JFXTextField PhoneNo;
    @FXML
    private JFXTextField Rent;
    @FXML
    private JFXTextField Address;
    @FXML
    private JFXButton SaveButton;

    @FXML
    private Label InvalidRentLabel;

    DatabaseHelper db = new DatabaseHelper();
    Users user = LoginPageController.loggedUser;

    //ParkingSpot(int spotOwnerId, int status, float rent, String address, float rating, float earning)
    String spotAddress;
    float spotRent;
    int ownerId;

    //ParkingSpotOwner(int userId, int status)
    ParkingSpotOwner spotOwner;

    boolean rentValid = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        getDataFromTable();
        InvalidRentLabel.setText("");

        Rent.textProperty().addListener((observable, oldValue, newValue) -> {

            String PATTERN = "[0-9]?([0-9]*[.])?[0-9]+";    //Writing pattern and array size//
            Pattern patt = Pattern.compile(PATTERN);
            Matcher match = patt.matcher(newValue);

            if (!match.matches()) {
                rentValid = false;
                InvalidRentLabel.setText("Invalid Rent");
            } else {
                rentValid = true;
                InvalidRentLabel.setText("");
            }
        });

    }

    @FXML
    void SaveButtonAction(ActionEvent event) {
        if (rentValid && !Rent.getText().trim().isEmpty() && !Name.getText().trim().isEmpty() && !Address.getText().trim().isEmpty()) {
            updateChanges();
        }
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
                Name.setText(resultSet.getString("Name"));
                PhoneNo.setText(resultSet.getString("PhoneNo"));
                Address.setText(resultSet.getString("Address"));
                Rent.setText(String.valueOf(resultSet.getFloat("Rent")));

                //ParkingSpotOwner(int userId, int status)
                //ParkingSpot(int spotOwnerId, int status, float rent, String address, float rating, float earning)
                //Users(String name, String phoneNo, String password, int type)
                spotAddress = resultSet.getString("Address");
                spotRent = resultSet.getFloat("Rent");
                ownerId = resultSet.getInt("SpotOwnerId");

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

    void updateChanges() {
        //        UPDATE table_name
        //        SET column1 = value1, column2 = value2, ...
        //        WHERE condition;

        String name = Name.getText().trim();
        String address = Address.getText().trim();
        float rent = 0;
        boolean update1 = false, update2 = false;

        if (rentValid) {
            rent = Float.parseFloat(Rent.getText().trim());
        }

        String sql = "update users set Name = ? where PhoneNo = ?";

        try {
            db.connectDB();

            PreparedStatement ps = db.connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, user.getPhoneNo());

            int row = ps.executeUpdate();

            if (row > 0) {

                update1 = true;
                user.setName(name);

                System.out.println("Updated Users");
            }

            ps.close();

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

        try {
            db.connectDB();

            System.out.println(ownerId);

            sql = "update ParkingSpot set Address = ?, Rent = ? where SpotOwnerId = ?";

            PreparedStatement ps1 = db.connection.prepareStatement(sql);

            System.out.println("Hello " + ownerId);

            ps1.setString(1, address);
            ps1.setFloat(2, rent);
            ps1.setInt(3, ownerId);

            int row = ps1.executeUpdate();

            if (row > 0) {
                update2 = true;
                System.out.println("Updated ParkingSpot");
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

        if (update1 || update2) {

            //parent.setEffect(new BoxBlur(3, 3, 3));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Profile Updated");
            alert.setHeaderText("Press OK to continue");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("GeniunCoder.css");
            dialogPane.getStyleClass().add("alert");

            Optional<ButtonType> result = alert.showAndWait();
            // parent.setEffect(null);

            if (result.get() == ButtonType.OK) {
                AnchorPane root = null;

                Pane parent = (Pane) SaveButton.getParent();

                try {
                    root = FXMLLoader.load(getClass().getResource("MyProfileSpotOwner.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                    //System.out.println(e.toString());
                }

                root.prefHeightProperty().bind(parent.heightProperty());
                root.prefWidthProperty().bind(parent.widthProperty());

                parent.getChildren().add(root);
            }

        }
    }
}