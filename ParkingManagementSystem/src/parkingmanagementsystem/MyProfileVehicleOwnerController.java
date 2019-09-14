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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class MyProfileVehicleOwnerController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label phoneNo;

    @FXML
    private Label AccountType;

    @FXML
    private Label LicenseNo;

    @FXML
    private Label ModelNo;

    @FXML
    private JFXButton EditButton;

    @FXML
    private Label ChangePasswordButton;

    Users user = LoginPageController.loggedUser;
    DatabaseHelper db = new DatabaseHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        name.setText(user.getName());
        phoneNo.setText(user.getPhoneNo());
        AccountType.setText("Vehicle Owner");

        getLicenseFromTable();

        ChangePasswordButton.setOnMouseClicked(e
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

    void getLicenseFromTable() {
        String sql = "select * from users \n"
                + "left join VehicleOwner on\n"
                + "Users.UserId = VehicleOwner.UserId \n"
                + "left join Vehicle on \n"
                + "Vehicle.VehicleOwnerId = VehicleOwner.VehicleOwnerId\n"
                + "where Users.PhoneNo = ?";

        try {
            db.connectDB();

            PreparedStatement ps = db.connection.prepareStatement(sql);

            ps.setString(1, user.getPhoneNo());

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                LicenseNo.setText(resultSet.getString("VehicleLicenseNo"));
                ModelNo.setText(resultSet.getString("VehicleModel"));

            }
        } catch (Exception e) {

        }

    }
    
    @FXML
    void EditButtonAction(ActionEvent event) {
        AnchorPane root = null;

        Pane parent = (Pane) EditButton.getParent();

        try {
            root = FXMLLoader.load(getClass().getResource("EditProfileVehicleOwner.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println(e.toString());
        }

        root.prefHeightProperty().bind(parent.heightProperty());
        root.prefWidthProperty().bind(parent.widthProperty());

        parent.getChildren().add(root);

    }


}
