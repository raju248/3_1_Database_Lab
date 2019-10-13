/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class AdminController implements Initializable {

    @FXML
    private JFXButton onGoingParking;
    @FXML
    private JFXButton History;
    @FXML
    private JFXButton onlineUsers;
    @FXML
    private JFXButton permanentSpots;
    @FXML
    private JFXButton allusers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          
    }   

    @FXML
    private void OnGoingParking(ActionEvent event) {
        
       
        
    }

    @FXML
    private void UsersHistory(ActionEvent event) {
        
    }

    @FXML
    private void OnlineUsers(ActionEvent event) {
        
    }

    @FXML
    private void Spots(ActionEvent event) {
        
    }

    @FXML
    private void AllUsers(ActionEvent event) {
          try {
            Stage stage = (Stage)History.getScene().getWindow();
            String title = "Sign Up";
            LoadStages load = new LoadStages(stage, title, "AllUser.fxml");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    
    
    
    
}
