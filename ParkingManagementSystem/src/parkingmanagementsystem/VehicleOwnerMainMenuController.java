/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class VehicleOwnerMainMenuController implements Initializable {

    @FXML
    private BorderPane bordePane;
    @FXML
    private JFXButton MyProfile;
    @FXML
    private JFXButton Exit;
    @FXML
    private Pane pane;

    @FXML
    private JFXButton Home;

    @FXML
    private Separator seperator;

    AnchorPane HomePage, Profile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        try {

            HomePage = FXMLLoader.load(getClass().getResource("VehicleOwnerHome.fxml"));

            pane.getChildren().add(0, HomePage);
        } catch (IOException ex) {
            Logger.getLogger(ParkingSpotOwnerMainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void Home(ActionEvent event) {
        loadHome();
    }

    void loadHome() {

        pane.getChildren().clear();
        pane.getChildren().addAll(seperator, HomePage);
        bordePane.setRight(null);
        bordePane.setTop(null);
    }

    @FXML
    void MyProfileButtonAction(ActionEvent event) {

        AnchorPane root = null;

        System.out.println("Profile");

        try {
            root = FXMLLoader.load(getClass().getResource("MyProfileVehicleOwner.fxml"));
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

    @FXML
    void VehicleOwnerhistory(ActionEvent event) {

        AnchorPane root = null;

        System.out.println("history");

        try {
            root = FXMLLoader.load(getClass().getResource("VehicalOwnerHistory.fxml"));
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
