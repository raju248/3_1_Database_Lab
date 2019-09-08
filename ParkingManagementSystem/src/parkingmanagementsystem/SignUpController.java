/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private VBox contentVbox;

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
    private JFXComboBox<String> AccountTypeCombo;

    @FXML
    private JFXTextField LicenseAndAddress;

    @FXML
    private Label VehicleExistsLabel;

    @FXML
    private JFXTextField RentOrVehicleModel;

    @FXML
    private Label InvalidRentLabel;

    @FXML
    private JFXButton SignUpButton;

    @FXML
    private JFXButton BackButton;


    boolean isMobileNoValid;
    boolean rentValid;

    DatabaseHelper db = new DatabaseHelper();
    
    int userType=0; //1 = parking spot owner 2 = Vehicle owner

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
            System.out.println(newValue);

            if (isMobileNoValid) {
                MobileNoExistsLabel.setVisible(false);
                MobileNoExistsLabel.setText("");
                
            } else {
                MobileNoExistsLabel.setText("This phone no. is invalid.");
                MobileNoExistsLabel.setVisible(true);
            }
            
            System.out.println(isMobileNoValid);
        });
        
        LicenseAndAddress.setPromptText("");
        LicenseAndAddress.setVisible(false);
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Parking Spot Owner", "Vehicle Owner");
        AccountTypeCombo.getItems().addAll(list);
        AccountTypeCombo.setPromptText("Select Your Account Type");
        
        AccountTypeCombo.setOnAction(new EventHandler <ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                LicenseAndAddress.setDisable(false);
                if(AccountTypeCombo.getValue().toString().equals("Parking Spot Owner"))
                {
                    LicenseAndAddress.setPromptText("Parking Spot Address");
                    userType = 1;
                    System.out.println(userType);
                    RentOrVehicleModel.setVisible(true);
                    RentOrVehicleModel.setPromptText("Rent");
                    LicenseAndAddress.setVisible(true);
                }
                    
                else
                {
                    RentOrVehicleModel.setVisible(true);
                    RentOrVehicleModel.setPromptText("Vehicle Model");
                    LicenseAndAddress.setPromptText("Vehicle License No");
                    userType = 2;
                    System.out.println(userType);
                    LicenseAndAddress.setVisible(true);
                }     
            }
        });
   
        VehicleExistsLabel.setText("");
        InvalidRentLabel.setText("");
        RentOrVehicleModel.setVisible(false);
        
       
            RentOrVehicleModel.textProperty().addListener((observable, oldValue, newValue) -> {
             
            String PATTERN = "[0-9]?([0-9]*[.])?[0-9]+";    //Writing pattern and array size//
            Pattern patt = Pattern.compile(PATTERN);
            Matcher match = patt.matcher(newValue);
            
            if (userType ==1 && !match.matches()) {
                rentValid = false;
                InvalidRentLabel.setText("Invalid Rent");
                
//                if(newValue.startsWith(""))
//                    Rent.setText("");
//                
//                if(newValue.length()>=2 && newValue.endsWith(" "))
//                    Rent.setText(newValue.trim());
//                
            } else {
                rentValid = true;
                InvalidRentLabel.setText("");
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
        String licenseandAddress = LicenseAndAddress.getText().trim();
        float rent = 0;
        if(rentValid && userType==1)
              rent = Float.parseFloat(RentOrVehicleModel.getText().trim());
        
        String model= "";
        
        if(userType ==2 && !RentOrVehicleModel.getText().trim().isEmpty())
            model = RentOrVehicleModel.getText().trim();
            
        
        boolean mobileNoExists = false;
        boolean VehicleExists = false;

        if (mobileNo.length() == 11) {
            String sql = "SELECT * FROM Users WHERE PhoneNo = '" + mobileNo + "'";
            Statement statement;
            try {
                db.connectDB();
                statement = db.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    MobileNoExistsLabel.setVisible(true);
                    MobileNoExistsLabel.setText("This phone no. already has an account");
                    MobileNo.requestFocus();
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
        
        if(userType==2 && !licenseandAddress.isEmpty())
        {
            VehicleExists = db.checkVehicleExist(licenseandAddress);
            
            if(VehicleExists)
            {
                VehicleExistsLabel.setText("This vehicle is already registered under another user");
            }
            else
            {
                VehicleExistsLabel.setText("");
            }
        }

        if (!mobileNoExists && isMobileNoValid && !name.isEmpty() && !password.isEmpty() && password.length() >= 6 && !RentOrVehicleModel.getText().trim().isEmpty()) 
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
                        
                        
                        Users user = new Users(name, mobileNo, password, userType);
                        
                        if (result.get() == ButtonType.OK) {
                            
                            if(userType==2 && !VehicleExists) 
                            {
                                        int GeneratedUserId = db.AddUser(user);
                            
                                        VehicleOwner owner = new VehicleOwner(GeneratedUserId, 0);
                            
                                        int GeneratedOwnerId = db.AddVehicleOwner(owner);
                            
                                        Vehicle vehicle = new Vehicle(GeneratedOwnerId, licenseandAddress, model,0);
                            
                                        db.addVehicle(vehicle);
                                        
                                        Stage stage = (Stage) SignUpButton.getScene().getWindow();
                                        String title = "Sign In";
                                        LoadStages load = new LoadStages(stage, title, "loginPage.fxml");  
                            }
                            
                            else if(VehicleExists)
                            {
                                LicenseAndAddress.requestFocus();
                            }
                            
                            
                            if(userType==1 && !licenseandAddress.isEmpty() && rentValid)
                            {
                                int GeneratedUserId = db.AddUser(user);
                                
                                ParkingSpotOwner owner = new ParkingSpotOwner(GeneratedUserId, 0);
                                
                                int GeneratedOwnerId = db.AddParkingSpotOwner(owner);
                                
                                
                                //(int spotOwnerId, int status, float rent, String address, int rating)
                                
                                ParkingSpot spot = new ParkingSpot(GeneratedOwnerId, 0, rent, licenseandAddress, (float) 0.0, (float) 0.0);
                                
                                db.addParkingSpot(spot);
                                
                                Stage stage = (Stage) SignUpButton.getScene().getWindow();
                                String title = "Sign In";
                                LoadStages load = new LoadStages(stage, title, "loginPage.fxml");  
                            }
  
                        } else {
                            return;
                        }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
