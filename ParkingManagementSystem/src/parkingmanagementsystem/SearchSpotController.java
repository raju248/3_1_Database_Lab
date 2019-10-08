/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class SearchSpotController implements Initializable {

    @FXML
    private JFXTextField address;
    @FXML
    private JFXButton searchButton;
    @FXML
    private VBox vbox;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label spotSearchingLabel;
    @FXML
    private Button cancel;
    @FXML
    private AnchorPane aPane;
    
    private VehicleOwnerHomeController v;
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vbox.setVisible(false);

        
        searchButton.setOnAction((e)->
        {
            vbox.setVisible(true);
            address.setEditable(false);
            searchButton.setDisable(true);
        });
        
        
        cancel.setOnAction(e->
        {
            if(vbox.isVisible())
            {
                vbox.setVisible(false);
                address.setEditable(true);
                searchButton.setDisable(false);
            }
        });
    }


    void loadSpotFoundPage()
    {
        
    }
    
    public void setParentController(VehicleOwnerHomeController v)
    {
        this.v = v;
    }
    
 
    
}
