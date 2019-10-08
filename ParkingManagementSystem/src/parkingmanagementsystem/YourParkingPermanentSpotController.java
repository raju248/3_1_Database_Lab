/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class YourParkingPermanentSpotController implements Initializable {

    @FXML
    private JFXListView<PermanentParkingSpot> ListView;
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
    private JFXButton Edit;
    @FXML
    private JFXButton Delete;

    DatabaseHelper c = new DatabaseHelper();
    ParkingSpotOwner pso = ParkingSpotOwnerHomeController.ps;

    boolean isMobileNoValid = true;
    boolean rentValid = true;
    int selectedId = 0;
    @FXML
    private AnchorPane anchorPane;
    
    ObservableList<PermanentParkingSpot> list = FXCollections.observableArrayList();

    class XCell extends ListCell<PermanentParkingSpot> {

        PermanentParkingSpot lastItem;

        VBox vbox = new VBox();
        
        Label IDLabel = new Label("ID : ");
        Label ID = new Label();
        HBox hbox0 = new HBox();

        Label AddressLabel = new Label("Address : ");
        Label Address1 = new Label();
        HBox hbox1 = new HBox();

        Label PhoneNoLabel = new Label("Phone No : ");
        Label PhoneNo1 = new Label("");
        HBox hbox2 = new HBox();

        Label RentLabel = new Label("Rent : ");
        Label Rent1 = new Label("");
        HBox hbox3 = new HBox();

        Label GuardLabel = new Label("Guard : ");
        Label Guard = new Label("");
        HBox hbox4 = new HBox();

        Label dateLabel = new Label("Added : ");
        Label Date = new Label("");
        HBox hbox5 = new HBox();

//        Label Starttimelabel = new Label("Start Time : ");
//        Label Starttime = new Label("");
//        HBox hbox6 = new HBox();
//
//        Label endtimeLabel = new Label("End Time : ");
//        Label endtime = new Label("");
//        HBox hbox7 = new HBox();
//
//        Label amountLabel = new Label("Amount : ");
//        Label amount = new Label("");
//        HBox hbox8 = new HBox();
        // Label ratingLabel = new Label("given Rate: ");
        //  Label rating = new Label("");
        //  HBox hbox5 = new HBox();
        //  Label EarningLabel = new Label("total earning: ");
        //  Label Earning = new Label("");
        //  HBox hbox6 = new HBox();
        //
        public XCell() {
            super();

            this.getStylesheets().add("GeniunCoder.css");

            this.hbox0.getChildren().addAll(IDLabel, ID);
            this.hbox1.getChildren().addAll(AddressLabel, Address1);
            this.hbox2.getChildren().addAll(RentLabel, Rent1);
            this.hbox3.getChildren().addAll(PhoneNoLabel, PhoneNo1);
            this.hbox4.getChildren().addAll(GuardLabel, Guard);
            this.hbox5.getChildren().addAll(dateLabel, Date);

            this.vbox.setPadding(new Insets(10, 10, 10, 10));
            String cssLayout = "-fx-border-color: #00001a;\n"
                    + "-fx-border-insets: 2;\n"
                    + "-fx-border-width: 3;\n"
                    + "-fx-font-weight: bold";

            this.vbox.setStyle(cssLayout);

            this.vbox.getChildren().addAll(hbox0, hbox1, hbox2, hbox3, hbox4, hbox5);
        }

        protected void updateItem(PermanentParkingSpot item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;

                this.Address1.setText(item.getAddress());
                this.Rent1.setText(String.valueOf(item.getRent()) + " Tk.");
                this.PhoneNo1.setText(item.getPhoneNo());
                this.ID.setText(String.valueOf(item.getID()));

                if (item.getGuard() == 1) {
                    Guard.setText("Available");
                } else {
                    Guard.setText("Not Available");
                }

                Date.setText(item.getAddedDate());

                setGraphic(vbox);
            }

        }

        {
            // update HBox every time the selection changes
            selectedProperty().addListener((observable, oldValue, newValue) -> {
                PermanentParkingSpot item = getItem();
                if (!isEmpty() && item != null) {
                    updateItemSelection(item, newValue);
                    System.out.println(item.getAddress());
                }
            });
        }

        private void updateItemSelection(PermanentParkingSpot item, boolean selected) {
            // update for HBox for non-empty cells based on selection
            if (selected) {
                Address.setText(item.getAddress());
                Rent.setText(String.valueOf(item.getRent()));
                PhoneNo.setText(item.getPhoneNo());

                if (item.getGuard() == 1) {
                    tg.selectToggle(yes);
                } else {
                    tg.selectToggle(no);
                }

                selectedId = item.getID();
            }
        }

    }

    /**
     * Initializes the controller class.
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

        loadData();
        
        Edit.setOnAction(e->
        {
            if(list.size()>0)
                         EditData();
        });
        
        Delete.setOnAction(e->
        {
            DeleteData();
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

    void EditData() {

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

        if (!rentValid) {
            Rent.requestFocus();
            return;
        }

        if (!isMobileNoValid) {
            PhoneNo.requestFocus();
            return;
        }

        RadioButton selectedRadioButton;

        String toogleGroupValue = "";

        if (tg.getSelectedToggle() != null) {
            selectedRadioButton = (RadioButton) tg.getSelectedToggle();
            toogleGroupValue = selectedRadioButton.getText();

        } else {
            selectGuardLabel.setVisible(true);
        }

        String address = Address.getText();
        String rent = Rent.getText();

        int guard = 0;

        if (toogleGroupValue.equals("Available")) {
            guard = 1;
        }

        String phoneNo = PhoneNo.getText();

        String sql = "Update Ads set Address = ?, Rent = ?, Guard = ?, Contact = ? where AddID = " + selectedId;

        DatabaseHelper dbc = new DatabaseHelper();

        try {
            dbc.connectDB();
            PreparedStatement ps = dbc.connection.prepareStatement(sql);

            ps.setString(1, address);
            ps.setInt(2, Integer.parseInt(rent));
            ps.setInt(3, guard);
            ps.setString(4, phoneNo);

            int row = ps.executeUpdate();

            if (row > 0) {
                anchorPane.setEffect(new BoxBlur(1, 1, 1));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Changes saved successfully!");
                alert.setHeaderText("Press OK if you want to continue");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add("GeniunCoder.css");
                dialogPane.getStyleClass().add("alert");

                Optional<ButtonType> result = alert.showAndWait();
                anchorPane.setEffect(null);

                if (result.get() == ButtonType.OK) {
                    ListView.getItems().clear();
                    loadData();
                    init();
                }
                else
                {
                    ListView.getItems().clear();
                    loadData();
                    init();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(YourParkingPermanentSpotController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if(dbc.connection!=null)
            {
                try {
                    dbc.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(YourParkingPermanentSpotController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    void DeleteData() {
        
        String sql = "Delete from Ads where AddID = " + selectedId;
        
        DatabaseHelper dbc = new DatabaseHelper();
        try {
            dbc.connectDB();
            PreparedStatement ps = dbc.connection.prepareStatement(sql);

            int row = ps.executeUpdate();

            if (row > 0) {
                anchorPane.setEffect(new BoxBlur(1, 1, 1));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleted successfully!");
                alert.setHeaderText("Press OK if you want to continue");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add("GeniunCoder.css");
                dialogPane.getStyleClass().add("alert");

                Optional<ButtonType> result = alert.showAndWait();
                anchorPane.setEffect(null);

                if (result.get() == ButtonType.OK) {
                    ListView.getItems().clear();
                    loadData();
                    init();
                }
                else
                {
                    ListView.getItems().clear();
                    loadData();
                    init();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(YourParkingPermanentSpotController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if(dbc.connection!=null)
            {
                try {
                    dbc.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(YourParkingPermanentSpotController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void loadData() {

        

        c.connectDB();
        try {

            String sql = " select * from Ads where SpotOwnerId = " + pso.getSpotOwerId();

            System.out.println(sql);

            PreparedStatement statement = c.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<PermanentParkingSpot> dataForTable = new ArrayList();

            while (resultSet.next()) {

                PermanentParkingSpot pps = new PermanentParkingSpot();

                Date date = null;
                Timestamp timestamp = resultSet.getTimestamp("addedDate");
                if (timestamp != null) {
                    date = new java.util.Date(timestamp.getTime());
                }

                SimpleDateFormat sm = new SimpleDateFormat("EE dd-MMMM-yyyy hh:mm a");

//                String StartTime = String.valueOf(resultSet.getDate("StartTime"));
                String addedDate = sm.format(date);
                String Address = resultSet.getString("Address");
                String contact = resultSet.getString("Contact");

                int guard = resultSet.getInt("Guard");
                int rent = resultSet.getInt("Rent");

                pps.setAddedDate(addedDate);
                pps.setAddress(Address);

                pps.setGuard(guard);
                pps.setPhoneNo(contact);
                pps.setRent(rent);
                pps.setID(resultSet.getInt("AddID"));

                list.add(pps);
            }

            ListView.setItems(list);

            ListView.setCellFactory(new Callback<ListView<PermanentParkingSpot>, ListCell<PermanentParkingSpot>>() {

                @Override
                public ListCell<PermanentParkingSpot> call(ListView<PermanentParkingSpot> param) {
                    return new XCell();
                }

            });

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c.connection != null) {
                try {
                    c.connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

}
