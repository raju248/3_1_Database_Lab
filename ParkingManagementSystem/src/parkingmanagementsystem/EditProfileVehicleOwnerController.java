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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class EditProfileVehicleOwnerController implements Initializable {

    @FXML
    private JFXTextField Name;
    @FXML
    private JFXTextField PhoneNo;
    @FXML
    private JFXTextField VehicleLicense;
    @FXML
    private JFXTextField VehicleModel;
    @FXML
    private JFXButton SaveButton;

    DatabaseHelper db = new DatabaseHelper();
    Users user = LoginPageController.loggedUser;
    
    int VehicleOwnerID;
    int VehicleID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getDataFromTable();
    }

    
    @FXML
    private void SaveButtonAction(ActionEvent event) {
        
        if(Name.getText().isEmpty())
        {
            Name.requestFocus();
            return;
        }
        
        if(VehicleLicense.getText().isEmpty())
        {
            VehicleLicense.requestFocus();
            return;
        }
        
        if(VehicleModel.getText().isEmpty())
        {
            VehicleModel.requestFocus();
            return;
        }
        
        if(!Name.getText().isEmpty() && !VehicleLicense.getText().isEmpty() && !VehicleModel.getText().isEmpty())
                updateChanges();
    }
    
    void getDataFromTable() {
        String sql = "select * from Users\n"
                + "inner join VehicleOwner \n"
                + "on Users.UserId = VehicleOwner.UserId\n"
                + "inner join Vehicle\n"
                + "on Vehicle.VehicleOwnerId = VehicleOwner.VehicleOwnerId where PhoneNo = ?";

        try {
            db.connectDB();

            PreparedStatement ps = db.connection.prepareStatement(sql);

            ps.setString(1, user.getPhoneNo());

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Name.setText(resultSet.getString("Name"));
                PhoneNo.setText(resultSet.getString("PhoneNo"));
                VehicleLicense.setText(resultSet.getString("VehicleLicenseNo"));
                VehicleModel.setText(resultSet.getString("VehicleModel"));

                //ParkingSpotOwner(int userId, int status)
                //ParkingSpot(int spotOwnerId, int status, float rent, String address, float rating, float earning)
                //Users(String name, String phoneNo, String password, int type)
                VehicleOwnerID = resultSet.getInt("VehicleOwnerId");
                VehicleID = resultSet.getInt("VehicleId");
                
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
        boolean update1 = false, update2 = false;

       
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
         
            sql = "update Vehicle set VehicleLicenseNo = ? , VehicleModel = ? where VehicleOwnerId = ?";

            PreparedStatement ps1 = db.connection.prepareStatement(sql);

            ps1.setString(1, VehicleLicense.getText().toString());
            ps1.setString(2,VehicleModel.getText().toString());
            ps1.setInt(3, VehicleOwnerID);
            
            int row = ps1.executeUpdate();

            if (row > 0) {
                update2 = true;
                System.out.println("Updated Vehicle");
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
                    root = FXMLLoader.load(getClass().getResource("MyProfileVehicleOwner.fxml"));
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
