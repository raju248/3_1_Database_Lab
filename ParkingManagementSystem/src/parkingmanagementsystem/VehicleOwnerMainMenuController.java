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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    /**
     * Initializes the controller class.
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

     

    }

//    @FXML
//    public void searchParkingButtonAction(ActionEvent event) {
//        AnchorPane root = null;
//        
//        System.out.println("Hoise");
//        
//        try
//        {
//            root = FXMLLoader.load(getClass().getResource("searchParking.fxml"));
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//        
//        root.prefHeightProperty().bind(pane.heightProperty());
//        root.prefWidthProperty().bind(pane.widthProperty());
//        
//        pane.getChildren().add(root);
//        bordePane.setRight(null);
//        bordePane.setTop(null);
//    }
    @FXML
    public void searchParkingButtonAction(ActionEvent event) {

        AnchorPane root = null;

        System.out.println("Parking Spot Find");

        try {
            root = FXMLLoader.load(getClass().getResource("searchParking.fxml"));
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
    

}
