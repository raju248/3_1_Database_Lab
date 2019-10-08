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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

//    @FXML
//    private AnchorPane HomePage;
//    
//    @FXML 
//    private AnchorPane Profile;
    AnchorPane HomePage, Profile;
    @FXML
    private Separator seperator;
    @FXML
    private JFXButton History;
    @FXML
    private JFXButton addSpot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            // loadHome();
            //HomePage.setVisible(true);
            //Profile.setVisible(false);
            HomePage = FXMLLoader.load(getClass().getResource("ParkingSpotOwnerHome.fxml"));
            //Profile = FXMLLoader.load(getClass().getResource("ParkingSpotOwnerMainMenu.fxml"));
            pane.getChildren().add(0, HomePage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    void loadHome() {
        /*AnchorPane root = null;

        System.out.println("Profile");

        try {
            root = FXMLLoader.load(getClass().getResource("ParkingSpotOwnerHome.fxml"));
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println(e.toString());
        }

        root.prefHeightProperty().bind(pane.heightProperty());
        root.prefWidthProperty().bind(pane.widthProperty());

        pane.getChildren().add(root);
        bordePane.setRight(null);
        bordePane.setTop(null);*/
        pane.getChildren().clear();
        pane.getChildren().addAll(seperator, HomePage);
        bordePane.setRight(null);
        bordePane.setTop(null);
    }

    @FXML
    void home(ActionEvent event) {
        loadHome();

    }

    @FXML
    void MyProfileButtonAction(ActionEvent event) {

        String Path = "MyProfileSpotOwner.fxml";
        loadFXML(Path);

    }

    @FXML
    void SpotOwnerhistory(ActionEvent event) {
        String Path = "parkingSpotOwnerHistory.fxml";
        loadFXML(Path);
    }

    @FXML
    void AddSpot(ActionEvent event) {
        String Path = "AddPermanentSpot.fxml";
        loadFXML(Path);
    }

    void loadFXML(String path) {
        AnchorPane root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(path));
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
    void viewSpot(ActionEvent event) {
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("YourParkingPermanentSpot.fxml"));
            Parent root = loader.load();

            //Get controller of scene2
            YourParkingPermanentSpotController scene2Controller = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            

            //Show scene 2 in new window            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
