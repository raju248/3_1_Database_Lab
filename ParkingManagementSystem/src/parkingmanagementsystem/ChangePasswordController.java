/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private JFXPasswordField oldPassword;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private Label PasswordNotLongText;
    @FXML
    private JFXButton Save;
    @FXML
    private Label currentPasswordNotMatch;

    Users user = LoginPageController.loggedUser;
    DatabaseHelper db = new DatabaseHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PasswordNotLongText.setVisible(false);
        currentPasswordNotMatch.setVisible(false);

    }

    @FXML
    private void EditProfileButtonAction(ActionEvent event) {
        String oldpassword = oldPassword.getText();
        String newpassword = newPassword.getText();

        if (oldpassword.isEmpty()) {
            oldPassword.requestFocus();
            return;
        }

        if (newpassword.isEmpty()) {
            newPassword.requestFocus();
            PasswordNotLongText.setVisible(true);
            return;
        }

        if (newpassword.length() < 6) {
            newPassword.requestFocus();
            PasswordNotLongText.setVisible(true);
            return;
        }

        if (!oldpassword.equals(user.getPassword())) {
            oldPassword.requestFocus();
            currentPasswordNotMatch.setVisible(true);
            return;
        }

        if (oldpassword.equals(user.getPassword()) && newpassword.length() >= 6) {
            String sql = "update users set Password = " + newpassword + " where Password = " + oldpassword+" and PhoneNo = " + user.getPhoneNo();
            
            db.connectDB();

            try {
                PreparedStatement ps = db.connection.prepareStatement(sql);
                int row = ps.executeUpdate();

                if (row > 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Profile Updated");
                    alert.setHeaderText("Press OK to continue");

                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add("GeniunCoder.css");
                    dialogPane.getStyleClass().add("alert");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        AnchorPane root = null;

                        Pane parent = (Pane) Save.getParent();

                        try {
                            if (user.type == 1) {
                                root = FXMLLoader.load(getClass().getResource("MyProfileSpotOwner.fxml"));
                            } else if (user.type == 2) {
                                root = FXMLLoader.load(getClass().getResource("MyProfileVehicleOwner.fxml"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            //System.out.println(e.toString());
                        }

                        root.prefHeightProperty().bind(parent.heightProperty());
                        root.prefWidthProperty().bind(parent.widthProperty());

                        parent.getChildren().add(root);
                    }

                }
                
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
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

}
