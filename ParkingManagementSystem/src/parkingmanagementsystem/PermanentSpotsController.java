/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

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
public class PermanentSpotsController {

    @FXML
    private JFXListView<?> ListView;
    @FXML
    private Label Label;

    /**
     * Initializes the controller class.
     */
        public void transferMessage(String m)
        {
            Label.setText("Available Parking Spot at "+ System.getProperty("line.separator") + m);
        }
    
}
