/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class SignUpController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField Name;
    @FXML
    private JFXTextField MobileNo;
    @FXML
    private Label MobileNoExistsLabel;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private Label PasswordNotLongText;
    @FXML
    private JFXButton SignUpButton;
    @FXML
    private JFXButton BackButton;

    boolean isMobileNoValid;

    DatabaseHelper db = new DatabaseHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        PasswordNotLongText.setVisible(false);
        MobileNoExistsLabel.setVisible(false);

        MobileNo.textProperty().addListener((observable, oldValue, newValue) -> {
            isMobileNoValid = InputValidator.checkContact(MobileNo.getText().trim());

            if (isMobileNoValid) {
                MobileNoExistsLabel.setVisible(false);
            } else {
                MobileNoExistsLabel.setText("This phone no. is invalid.");
                MobileNoExistsLabel.setVisible(true);
            }
        });
    }

    @FXML
    private void BackButtonAction(ActionEvent event) {

        try {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            String title = "Sign in";
            LoadStages load = new LoadStages(stage, title, "loginPage.fxml");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    void SignUpUser(ActionEvent event) {
        String name = Name.getText().trim();
        String mobileNo = MobileNo.getText().trim();
        String password = Password.getText().trim();
        boolean mobileNoExists = false;

        if (mobileNo.length() == 11) {
            String sql = "SELECT * FROM Users WHERE MobileNO = '" + mobileNo + "'";
            Statement statement;
            try {
                db.connectDB();
                statement = db.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    MobileNoExistsLabel.setVisible(true);
                    MobileNoExistsLabel.setText("This phone no. already has an account");
                    mobileNoExists = true;
                    return;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
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

        if (!mobileNoExists && isMobileNoValid && !name.isEmpty() && !password.isEmpty() && password.length() >= 6) 
        {

            try {
                
                
                        anchorPane.setEffect(new BoxBlur(3,3,3));
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Sign UP");
                        alert.setHeaderText("Press OK if you want to continue");
                        
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("GeniunCoder.css");
                        dialogPane.getStyleClass().add("alert");
                        
                        Optional<ButtonType> result = alert.showAndWait();
                         anchorPane.setEffect(null); 
                        
                        if (result.get() == ButtonType.OK) {
                            db.AddUser(name, mobileNo, password);
                            Stage stage = (Stage) SignUpButton.getScene().getWindow();
                            String title = "Sign In";
                            LoadStages load = new LoadStages(stage, title, "loginPage.fxml");      
                        } else {
                            return;
                        }

                       
//                stackPane.toFront();
//                
//                JFXAlert<ButtonType> alert = new JFXAlert<>((Stage) SignUpButton.getScene().getWindow());
//        
//                JFXDialogLayout content = new JFXDialogLayout();
//                content.setHeading(new Text("Confirm Sign Up"));
//                content.setBody(new Text("Press OK if you want to continue"));
//                alert.setContent(content);
//        
//                //JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
//                JFXButton button = new JFXButton("OK");
//                button.setButtonType(JFXButton.ButtonType.RAISED);
//                button.setStyle("-fx-background-color: #2296F2;-fx-text-fill:  #ffffff;");
//                
//                 content.setActions(button);
//                //dialog.show();
//                 
//                Optional<ButtonType> result = alert.showAndWait();
//                
////                button.setOnAction(new EventHandler<ActionEvent>() {
////                    @Override
////                    public void handle(ActionEvent event) {
////                        
////                        try {
////                            db.AddUser(name, mobileNo, password);
////                            Stage stage = (Stage) SignUpButton.getScene().getWindow();
////                            String title = "Sign In";
////                            LoadStages load = new LoadStages(stage, title, "loginPage.fxml");
////                            //dialog.close();
////                            alert.close();
////                            stackPane.toBack();
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    }
////                });
//
//                   if(result.get()==ButtonType.OK)
//                   {
//                       
//                   }
//                
               

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
