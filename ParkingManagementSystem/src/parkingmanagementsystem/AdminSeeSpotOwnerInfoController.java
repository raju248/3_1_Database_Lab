/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class AdminSeeSpotOwnerInfoController implements Initializable {

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
    private Label RentPerHour;
    @FXML
    private JFXListView<?> ListView;
    @FXML
    private JFXButton Back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
