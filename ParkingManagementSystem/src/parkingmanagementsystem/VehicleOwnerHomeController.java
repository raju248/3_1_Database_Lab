/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import com.gluonhq.charm.glisten.control.ToggleButtonGroup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class VehicleOwnerHomeController implements Initializable {

//    @FXML
//    private SearchSpotController searchController;
//    
//    @FXML
//    private SpotFoundController foundController;
    @FXML
    private Pane Pane;

    @FXML
    private AnchorPane pane2;

    @FXML
    private JFXTextArea address1;

    @FXML
    private Label Owner;

    @FXML
    private Label rent;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label Rating;

    @FXML
    private Label PhoneNo;

    @FXML
    private AnchorPane aPane;

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
    private AnchorPane StatusPane;

    @FXML
    private JFXTextArea address2;

    @FXML
    private Label Owner1;

    @FXML
    private Label rent1;

    @FXML
    private Label StartTime;

    @FXML
    private Label PhoneNo1;

    @FXML
    private AnchorPane FinalPane;

    @FXML
    private JFXTextArea address21;

    @FXML
    private Label rent2;

    @FXML
    private Label owner2;

    @FXML
    private Label StartTime1;

    @FXML
    private Label EndTime1;

    @FXML
    private Label Amount;

    @FXML
    private Rating star;

    @FXML
    private JFXButton DoneButton;

    @FXML
    private ProgressIndicator circle;

    @FXML
    private ToggleButtonGroup group;

    ToggleGroup tg = new ToggleGroup();

    @FXML
    private JFXRadioButton permanentButton;

    @FXML
    private JFXRadioButton instantButton;
    
    @FXML
    private JFXCheckBox guardCheck;
    

    DatabaseHelper db = new DatabaseHelper();

    int rentValue = 0;
    int status = 0;
    int SenderId = 0;
    int EndStatus = 0;
    int requestId = 0;
    int receiverId = 0;
    int starValue = 0;
    int cancelStatus = 1;
    int startedStatus = 0;
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("permanentSpots.fxml"));
    Parent root ;

    PermanentSpotsController scene2Controller ;
    
    Stage stage = new Stage();
    
    
    /**
     * *
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Pane.getChildren().clear();
            
            root = loader.load();
            scene2Controller = loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
 
                        
            
            permanentButton.setToggleGroup(tg);
            instantButton.setToggleGroup(tg);
            
            vbox.setVisible(false);
            guardCheck.setVisible(false);
            
            DoneButton.setOnAction(e
                    -> {
                if(starValue>0)
                    giveRating();
                
                this.initialize(url, rb);
            });
            
            star.ratingProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    starValue = newValue.intValue();
                }
            });
            
            
            tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> ob,
                        Toggle o, Toggle n) {
                    
                    RadioButton rb = (RadioButton) tg.getSelectedToggle();
                    
                    if (rb != null) {
                        String s = rb.getText();
                        
                        if(s.equals("Permanent"))
                        {
                            guardCheck.setVisible(true);
                        }
                        else
                        {
                            guardCheck.setVisible(false);
                        }
                    }
                }
            });
            
            searchButton.setOnAction((e)
                    -> {
                
                if(address.getText().toString().isEmpty())
                {
                    address.requestFocus();
                }
                
                
                RadioButton selectedRadioButton;
                
                String toogleGroupValue ;
                
                
                if(tg.getSelectedToggle()!=null)
                {
                    selectedRadioButton = (RadioButton) tg.getSelectedToggle();
                    toogleGroupValue = selectedRadioButton.getText();
                }
                
                else
                {
                    return;
                }
                
                
                
                if (!address.getText().toString().isEmpty() && toogleGroupValue.equals("Instant")) {
                    try {
                        
                        vbox.setVisible(true);
                        address.setEditable(false);
                        searchButton.setDisable(true);
                        String location = address.getText().toString().trim();
                        
                        db.connectDB();
                        
                        int userId = LoginPageController.loggedUser.getUserId();
                        
                        String sql = "Select v.VehicleOwnerId from VehicleOwner as v join Users as u on v.userId = u.userId where v.userId = " + userId;
                        
                        PreparedStatement ps;
                        
                        ps = db.connection.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        
                        if (rs.next()) {
                            SenderId = rs.getInt("VehicleOwnerId");
                            System.out.println(SenderId);
                        }
                        if (db.connection != null) {
                            try {
                                db.connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        sql = "Insert into ParkingRequests (Location, SenderId, Status) values (?,?,?)";
                        
                        db.connectDB();
                        
                        ps = db.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, location);
                        ps.setInt(2, SenderId);
                        ps.setInt(3, 0); //0 for pending request
                        
                        int rowInserted = ps.executeUpdate();
                        
                        requestId = 0;
                        
                        status = 1; //looking for parking
                        
                        if (rowInserted > 0) {
                            rs = ps.getGeneratedKeys();
                            rs.next();
                            requestId = rs.getInt(1);
                            
                            System.out.println(requestId);
                            
                            checkIfAccepted();
                        }
                        if (db.connection != null) {
                            try {
                                db.connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                if (!address.getText().toString().isEmpty() && toogleGroupValue.equals("Permanent")) {
                    
                    int guard = 0;
                    
                    if(guardCheck.isSelected())
                        guard = 1;
                    
                    
                        scene2Controller.transferMessage(address.getText());
                        scene2Controller.setAddress(address.getText());
                        scene2Controller.setGuard(guard);
                        scene2Controller.loadData();

                        //Stage stage = new Stage();
                        //stage.setScene(new Scene(root));
                        //stage.setTitle("Second Window");
                        stage.show();
//                    } catch (IOException ex) {
//                        System.err.println(ex);
//                    }
                }
                
            }
            );
            
            cancel.setOnAction(e
                    -> {
                if (vbox.isVisible()) {
                    vbox.setVisible(false);
                    address.setEditable(true);
                    searchButton.setDisable(false);
                    
                    DatabaseHelper db2 = new DatabaseHelper();
                    
                    String sql = "Update ParkingRequests set Status = 3 where RequestId = " + requestId;
                    
                    try {
                        db2.connectDB();
                        PreparedStatement ps1 = db2.connection.prepareStatement(sql);
                        int row = ps1.executeUpdate();
                        
                        if (row > 0) {
                            System.out.println("Request Cancelled");
                            status = 0;
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            Pane.getChildren().add(aPane);
            
            cancelButton.setOnAction(e
                    -> {
                cancelFoundRequest();
            });
        } catch (IOException ex) {
            Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void cancelFoundRequest() {
        DatabaseHelper db2 = new DatabaseHelper();

        String sql = "Update ParkingRequests set Status = 3 where RequestId = " + requestId;

        try {
            db2.connectDB();
            PreparedStatement ps1 = db2.connection.prepareStatement(sql);
            int row = ps1.executeUpdate();

            if (row > 0) {
                System.out.println("Found Request Cancelled");
                status = 0;
            }

        } catch (SQLException ex) {
            Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void checkIfAccepted() {
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() {
                int iterations;
                for (iterations = 1; iterations > 0; iterations++) {

                    if (status == 0) {
                        System.out.println("cancelled");
                        this.isDone();
                        break;
                    }

                    DatabaseHelper db1 = new DatabaseHelper();

                    db1.connectDB();
                    System.out.println("searching..." + requestId);

                    String sql = "Select * from ParkingRequests where requestId = " + requestId + " and ReceiverId is not null";

                    try {
                        PreparedStatement ps1 = db1.connection.prepareStatement(sql);

                        ResultSet rs = ps1.executeQuery();

                        if (rs.next()) {

                            receiverId = rs.getInt("ReceiverId");

                            changeUIIfAccepted();

                            status = 0;

                            System.out.println("searching in...");

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (db1.connection != null) {
                            try {
                                db1.connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }
                return iterations;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    void giveRating() {
        String sql = "update ParkingRequests set Rating = ? where RequestId = " + requestId;

        DatabaseHelper dbc = new DatabaseHelper();

        dbc.connectDB();

        try {
            PreparedStatement ps = dbc.connection.prepareStatement(sql);

            ps.setInt(1, starValue);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Rating Given");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkingSpotOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dbc.connection != null) {
                try {
                    dbc.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    void checkIfCancelledOrStarted() {

        cancelStatus = 1;
        startedStatus = 0;
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() {
                int iterations;
                for (iterations = 1; iterations > 0; iterations++) {

                    if (startedStatus == 1) {
                        System.out.println("Started Parking..");
                        cancelStatus = 0;
                        this.isDone();
                        break;
                    }

                    if (cancelStatus == 0) {
                        System.out.println("cancelled..");
                        this.isDone();
                        break;
                    }

                    DatabaseHelper db1 = new DatabaseHelper();

                    db1.connectDB();
                    System.out.println("searching if cancelled ..." + requestId);

                    String sql = "Select * from ParkingRequests where requestId = " + requestId;

                    try {
                        PreparedStatement ps1 = db1.connection.prepareStatement(sql);

                        ResultSet rs = ps1.executeQuery();

                        if (rs.next()) {

                            int s = rs.getInt("Status");
                            if (s == 3) {
                                changeUIIfCancelled();
                                cancelStatus = 0;
                            }

                            if (s == 1) {
                                changeUIIfStarted();
                                startedStatus = 1;
                                cancelStatus = 0;
                            }

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (db1.connection != null) {
                            try {
                                db1.connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }
                return iterations;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    void checkIfEnded() {
        EndStatus = 0;
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() {
                int iterations;
                for (iterations = 1; iterations > 0; iterations++) {

                    if (EndStatus == 1) {
                        System.out.println("Done Parking..");
                        this.isDone();
                        break;
                    }

                    DatabaseHelper db1 = new DatabaseHelper();

                    db1.connectDB();
                    System.out.println("searching if Ended ..." + requestId);

                    String sql = "Select * from ParkingRequests where requestId = " + requestId + " and Status = 2"; //2 for ended

                    try {
                        PreparedStatement ps1 = db1.connection.prepareStatement(sql);

                        ResultSet rs = ps1.executeQuery();

                        if (rs.next()) {
                            int s = rs.getInt("Status");
                            EndStatus = 1;
                            changeUIIfEnded();
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (db1.connection != null) {
                            try {
                                db1.connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }
                return iterations;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);

        t.start();

    }

    void changeUIIfStarted() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Update UI here
                Pane.getChildren().clear();
                circle.setVisible(false);
                Pane.getChildren().add(StatusPane);
                DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                String dateString2 = dateFormat2.format(new Date()).toString();
                StartTime.setText(dateString2);
                StartTime1.setText(dateString2);
            }
        });
        // Pane.getChildren().add(pane2);
        checkIfEnded();
    }

    void changeUIIfAccepted() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Update UI here
                Pane.getChildren().clear();

                Pane.getChildren().add(pane2);
                ParkingSpotOwnerDetails();

            }
        });
        // Pane.getChildren().add(pane2);
        checkIfCancelledOrStarted();
    }

    void changeUIIfCancelled() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Update UI here
                Pane.getChildren().clear();
                Pane.getChildren().add(aPane);
                vbox.setVisible(false);
                address.setEditable(true);
                searchButton.setDisable(false);
            }
        });
    }

    void changeUIIfEnded() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Update UI here
                Pane.getChildren().clear();
                Pane.getChildren().add(FinalPane);

                vbox.setVisible(false);
                address.setEditable(true);
                searchButton.setDisable(false);
                DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
                String dateString2 = dateFormat2.format(new Date()).toString();
                EndTime1.setText(dateString2);
                Amount.setText(String.valueOf(getAmount()) + " Tk.");
            }
        });
    }

    public int getAmount() {
        String sql = "Select TotalAmount from ParkingRequests where RequestId = " + requestId;

        DatabaseHelper db1 = new DatabaseHelper();

        db1.connectDB();

        try {
            PreparedStatement ps = db1.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    void ParkingSpotOwnerDetails() {
        String sql = "Select * from ParkingSpot where SpotOwnerId = " + receiverId;

        DatabaseHelper db1 = new DatabaseHelper();

        try {
            db1.connectDB();
            PreparedStatement ps = db1.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                address1.setText(rs.getString("Address"));
                address2.setText(rs.getString("Address"));
                address21.setText(rs.getString("Address"));

                rent.setText(String.valueOf(rs.getInt("Rent")) + " Tk.");
                rent1.setText(String.valueOf(rs.getInt("Rent")) + " Tk.");
                rent2.setText(String.valueOf(rs.getInt("Rent")) + " Tk.");
                rentValue = rs.getInt("Rent");

                float rating = rs.getFloat("Rating");

                if (rating == 0.0) {
                    Rating.setText("N/A");

                } else {
                    Rating.setText(String.valueOf(rating) + "/5");
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (db1.connection != null) {
                try {
                    db1.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        sql = "Select Name,PhoneNo from Users where UserId in (Select UserId from ParkingSpotOwner where SpotOwnerId = ?)";

        try {
            db1.connectDB();
            PreparedStatement ps = db1.connection.prepareStatement(sql);
            ps.setInt(1, receiverId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Owner.setText(rs.getString("Name"));
                Owner1.setText(rs.getString("Name"));
                owner2.setText(rs.getString("Name"));
                PhoneNo.setText(rs.getString("PhoneNo"));
                PhoneNo1.setText(rs.getString("PhoneNo"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleOwnerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
