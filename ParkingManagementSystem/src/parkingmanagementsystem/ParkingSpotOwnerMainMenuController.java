/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Separator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static parkingmanagementsystem.LoginPageController.loggedUser;

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

        Exit.setOnAction(e
                -> {

            bordePane.setEffect(new BoxBlur(3, 3, 3));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Log out");
            alert.setHeaderText("Press OK if you want to continue");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("GeniunCoder.css");
            dialogPane.getStyleClass().add("alert");

            Optional<ButtonType> result = alert.showAndWait();
            bordePane.setEffect(null);

            if (result.get() == ButtonType.OK) {
                changeUserStatusOnLogOut();
            } else {
                bordePane.requestFocus();
            }

        });

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

    @FXML
    void viewOtherSpot(ActionEvent event) {
        String Path = "OtherPermanentParkingSpot.fxml";
        loadFXML(Path);
    }

    void loadFXML(String path) {
        AnchorPane root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.prefHeightProperty().bind(pane.heightProperty());
        root.prefWidthProperty().bind(pane.widthProperty());

        pane.getChildren().add(root);
        bordePane.setRight(null);
        bordePane.setTop(null);
    }

    void changeUserStatusOnLogOut() {
        DatabaseHelper db = new DatabaseHelper();

        String sql = "update Users set Status = ? where UserId = " + loggedUser.getUserId();

        db.connectDB();
        PreparedStatement ps;
        try {
            ps = db.connection.prepareStatement(sql);
            ps.setInt(1, 0); //0 for logged out 
            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Updated status");
                Stage stage = (Stage) bordePane.getScene().getWindow();
                String title = "Login";
                LoadStages load;

                try {
                    load = new LoadStages(stage, title, "loginPage.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(VehicleOwnerMainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
