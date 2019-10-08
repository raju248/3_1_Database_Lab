/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.paint.Color;
import java.awt.Paint;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class AddPermanentSpotController implements Initializable {

    @FXML
    private Label Label;
    @FXML
    private JFXTextArea Address;
    @FXML
    private Label addressNeededLabel;
    @FXML
    private Label InvalidRentLabel;
    @FXML
    private Label selectGuardLabel;
    @FXML
    private Label invalidPhoneNoLabel;
    @FXML
    private JFXTextField Rent;
    @FXML
    private JFXRadioButton yes;
    @FXML
    private ToggleGroup tg;
    @FXML
    private JFXRadioButton no;
    @FXML
    private JFXTextField PhoneNo;
    @FXML
    private JFXButton addButton;

    boolean isMobileNoValid = true;
    boolean rentValid = true;

    Users user = LoginPageController.loggedUser;
    ParkingSpotOwner pso = ParkingSpotOwnerHomeController.ps;
    DatabaseHelper db = new DatabaseHelper();
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        init();

        PhoneNo.textProperty().addListener((observable, oldValue, newValue) -> {
            isMobileNoValid = InputValidator.checkContact(PhoneNo.getText().trim());
            System.out.println(newValue);

            if (isMobileNoValid) {
                invalidPhoneNoLabel.setVisible(false);
                invalidPhoneNoLabel.setText("");

            } else {
                invalidPhoneNoLabel.setText("This phone no. is invalid.");
                invalidPhoneNoLabel.setVisible(true);
            }

            System.out.println(isMobileNoValid);
        });

        Rent.textProperty().addListener((observable, oldValue, newValue) -> {

            String PATTERN = "[0-9]+$";    //Writing pattern and array size//
            Pattern patt = Pattern.compile(PATTERN);
            Matcher match = patt.matcher(newValue);

            if (!match.matches()) {
                rentValid = false;
                InvalidRentLabel.setVisible(true);
                InvalidRentLabel.setText("Invalid Rent");
            } else {
                rentValid = true;
                InvalidRentLabel.setVisible(false);
                InvalidRentLabel.setText("");
            }
        });

        tg.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) -> {
            RadioButton rb1 = (RadioButton) tg.getSelectedToggle();
            if (rb1 != null) {
                String s = rb1.getText();
                selectGuardLabel.setVisible(false);
            }
        });

        addButton.setOnAction(e -> {
                addSpot();
        });

    }

    
    
    void init() {
        Rent.setText("");
        Address.setText("");
        PhoneNo.setText("");
        tg.selectToggle(null);
        addressNeededLabel.setVisible(false);
        InvalidRentLabel.setVisible(false);
        invalidPhoneNoLabel.setVisible(false);
        selectGuardLabel.setVisible(false);
    }

    void addSpot() {
        if (Address.getText().isEmpty()) {
            Address.requestFocus();
            addressNeededLabel.setVisible(true);
            return;
        }

        if (Rent.getText().isEmpty()) {
            Rent.requestFocus();
            InvalidRentLabel.setVisible(true);
            InvalidRentLabel.setText("Invalid Rent");
            return;
        }

        if (PhoneNo.getText().isEmpty()) {
            PhoneNo.requestFocus();
            invalidPhoneNoLabel.setVisible(true);
            invalidPhoneNoLabel.setText("Invalid Phone No");
            return;
        }
        
        if(!rentValid)
        {
            Rent.requestFocus();
            return;
        }
        
        
        if(!isMobileNoValid)
        {
            PhoneNo.requestFocus();
            return;
        }
        

        RadioButton selectedRadioButton;

        String toogleGroupValue ="";

        if (tg.getSelectedToggle() != null) {
            selectedRadioButton = (RadioButton) tg.getSelectedToggle();
            toogleGroupValue = selectedRadioButton.getText();

        } else {
            selectGuardLabel.setVisible(true);
        }
        
        
        if(tg.getSelectedToggle()!=null && rentValid && isMobileNoValid)
        {
                String address = Address.getText();
                String rent = Rent.getText();
                
                int guard = 0;
                
                if(toogleGroupValue.equals("Available"))
                {
                    guard = 1;
                }
                
                String phoneNo = PhoneNo.getText();
                
                
                
                PermanentParkingSpot ps = new PermanentParkingSpot();
                
                ps.setAddress(address);
                ps.setRent(Integer.parseInt(rent));
                ps.setGuard(guard);
                ps.setPhoneNo(phoneNo);
                ps.setSpotOwnerId(pso.getSpotOwerId());
                
                
                        anchorPane.setEffect(new BoxBlur(3,3,3));
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Add Permanent Parking Spot");
                        alert.setHeaderText("Press OK if you want to continue");
                        
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("GeniunCoder.css");
                        dialogPane.getStyleClass().add("alert");
                        
                        Optional<ButtonType> result = alert.showAndWait();
                        anchorPane.setEffect(null); 
                        
                        if (result.get() == ButtonType.OK) {
                            int s = db.AddPermanentParkingSpot(ps);
                            
                            
                            if(s==1)
                            {
                                anchorPane.setEffect(new BoxBlur(3,3,3));
                                alert.setTitle("Successfully Added!");
                                alert.setHeaderText("Press OK to continue");
                                result = alert.showAndWait();
                                anchorPane.setEffect(null); 
                                
                                if(result.get() == ButtonType.OK)
                                {
                                    init();
                                }
                            }
                            
                            else
                            {
                                anchorPane.setEffect(new BoxBlur(3,3,3));
                                alert.setTitle("Failed to Add!");
                                alert.setHeaderText("Press OK to continue");
                                result = alert.showAndWait();
                                anchorPane.setEffect(null); 
                            }
                        }
                        else
                        {
                            return;
                        }    
        }
    }
}
