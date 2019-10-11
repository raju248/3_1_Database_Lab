/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class PermanentSpotsController {

    
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

    }
    
    @FXML
    private JFXListView<PermanentParkingSpot> ListView;
    @FXML
    private Label Label;
    @FXML
    private Label Label1;

    String address;
    int guard = 0;

    DatabaseHelper c = new DatabaseHelper();

    /**
     * Initializes the controller class.
     */
    public void transferMessage(String m) {
        Label1.setText(m);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public int getGuard() {
        return guard;
    }

    public void setGuard(int guard) {
        this.guard = guard;
    }
    
    
    

    public void loadData() {

        ListView.getItems().clear();
        list.clear();
        
        c.connectDB();
        
        try {

            String sql = "select * from Ads where Address like ? and Guard = ?";
            
            PreparedStatement ps = c.connection.prepareStatement(sql);
            ps.setString(1, "%" + getAddress()+ "%");
            ps.setInt(2, getGuard());
            ResultSet resultSet = ps.executeQuery();
            
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

                System.out.println(Address);
                
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

            if(list.size()==0)
            {
                ListView.setPlaceholder(new Label("No Spot Found"));
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
