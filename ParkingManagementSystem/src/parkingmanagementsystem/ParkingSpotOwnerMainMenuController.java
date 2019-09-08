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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class ParkingSpotOwnerMainMenuController implements Initializable {

    @FXML
    private BorderPane bordePane;
    @FXML
    private JFXButton Home;
    @FXML
    private JFXButton MyProfile;
    @FXML
    private JFXButton Exit;
    @FXML
    private Pane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     @FXML
    void home(ActionEvent event) {

    }

    @FXML
    void MyProfileButtonAction(ActionEvent event) {
        
        AnchorPane root = null;

        System.out.println("Profile");

        try {
            root = FXMLLoader.load(getClass().getResource("MyProfileSpotOwner.fxml"));
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println(e.toString());
        }

        root.prefHeightProperty().bind(pane.heightProperty());
        root.prefWidthProperty().bind(pane.widthProperty());

        pane.getChildren().add(root);
        bordePane.setRight(null);
        bordePane.setTop(null);
        
    }
    
}
