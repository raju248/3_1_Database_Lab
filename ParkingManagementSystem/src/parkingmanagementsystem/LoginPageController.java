/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class LoginPageController implements Initializable {

    @FXML
    private JFXTextField PhoneNo;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private Label IncorrectLabel;
    @FXML
    private JFXButton SignIn;
    @FXML
    private Label SignUpLabel;

    DatabaseHelper db = new DatabaseHelper();
    boolean isMobileNoValid;

    static Users loggedUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        IncorrectLabel.setVisible(false);

        PhoneNo.textProperty().addListener((observable, oldValue, newValue) -> {
            isMobileNoValid = InputValidator.checkContact(PhoneNo.getText().trim());
            IncorrectLabel.setVisible(false);
//            if (isMobileNoValid) {
//                IncorrectLabel.setVisible(false);
//            } else {
//                IncorrectLabel.setVisible(true);
//            }
        });

        Password.textProperty().addListener((observable, oldValue, newValue) -> {
            IncorrectLabel.setVisible(false);
        });

    }

    @FXML
    void CreateNewAccount(MouseEvent event) {
        try {
            Stage stage = (Stage) SignUpLabel.getScene().getWindow();
            String title = "Sign Up";
            LoadStages load = new LoadStages(stage, title, "SignUp.fxml");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    void signInUser(ActionEvent event) {

        String phoneNo = PhoneNo.getText().trim();
        String password = Password.getText().trim();

        System.out.println(isMobileNoValid);

        if (isMobileNoValid && phoneNo.length() == 11 && !password.isEmpty()) {
            String sql = "SELECT * FROM Users WHERE PhoneNo =? and Password = ?";

            try {
                db.connectDB();
                PreparedStatement ps = db.connection.prepareStatement(sql);

                ps.setString(1, phoneNo);
                ps.setString(2, password);

                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
//                        System.out.println(resultSet.getString("UserId"));
//                        System.out.println(resultSet.getString("Name"));
//                        System.out.println(resultSet.getString("MobileNo"));
//                        System.out.println(resultSet.getString("Password"));
                    int userId = resultSet.getInt("UserId");
                    String name = resultSet.getString("Name");
                    String pass = resultSet.getString("Password");
                    String mobile = resultSet.getString("PhoneNo");
                    int type = resultSet.getInt("Type");

                    loggedUser = new Users(userId, name, mobile, pass, type);

                    changeUserStatusOnLogin();

                    try {
                        Stage stage = (Stage) SignIn.getScene().getWindow();
                        String title = "Main Menu";
                        LoadStages load;

                        if (type == 2) {
                            load = new LoadStages(stage, title, "VehicleOwnerMainMenu.fxml");
                        } else {
                            load = new LoadStages(stage, title, "ParkingSpotOwnerMainMenu.fxml");
                        }

                    } catch (IOException e) {
                        System.out.println(e);
                    }

                } else {
                    IncorrectLabel.setVisible(true);
                }
                try {
                    if (db.connection != null) {
                        db.connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            IncorrectLabel.setVisible(true);
        }

    }

    void changeUserStatusOnLogin() {

        String sql = "update Users set Status = ? where UserId = " + loggedUser.getUserId();

        db.connectDB();
        PreparedStatement ps;
        try {
            ps = db.connection.prepareStatement(sql);
            ps.setInt(1, 1); //1 for logged in 
            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Updated status");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
