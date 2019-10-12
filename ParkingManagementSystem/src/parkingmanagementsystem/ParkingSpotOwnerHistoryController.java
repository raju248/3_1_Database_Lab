/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXListView;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Istiaque Hashem
 */
public class ParkingSpotOwnerHistoryController implements Initializable {

    @FXML
    private JFXListView<History> ListView;
    DatabaseHelper c = new DatabaseHelper();

    Users user = LoginPageController.loggedUser;

    /**
     * Initializes the controller class.
     */
    static class XCell extends ListCell<History> {

        History lastItem;
        VBox vbox = new VBox();
        Label NameHeading = new Label("Vehicle Owner Name :");
        Label Name = new Label();
        HBox hbox1 = new HBox();

        Label PhoneNoLabel = new Label("Phone No : ");
        Label PhoneNo = new Label("");
        HBox hbox2 = new HBox();

        Label VehicleLicenseNoHeading = new Label("Vehicle License No : ");
        Label VehicleLicenseNo = new Label("");
        HBox hbox3 = new HBox();

        Label VehicleModelLabel = new Label("Vehicle Model : ");
        Label VehicleModel = new Label("");
        HBox hbox4 = new HBox();

        Label Vehicledate = new Label("Date ");
        Label Date = new Label("");
        HBox hbox5 = new HBox();

        Label Starttimelabel = new Label("Start Time : ");
        Label Starttime = new Label("");
        HBox hbox6 = new HBox();

        Label endtimeLabel = new Label("End Time : ");
        Label endtime = new Label("");
        HBox hbox7 = new HBox();

        Label amountLabel = new Label("Amount : ");
        Label amount = new Label("");
        HBox hbox8 = new HBox();

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

            this.hbox1.getChildren().addAll(NameHeading, Name);
            this.hbox2.getChildren().addAll(PhoneNoLabel, PhoneNo);
            this.hbox3.getChildren().addAll(VehicleLicenseNoHeading, VehicleLicenseNo);
            this.hbox4.getChildren().addAll(VehicleModelLabel, VehicleModel);
            //this.hbox5.getChildren().addAll(Vehicledate, Date);
            this.hbox6.getChildren().addAll(Starttimelabel, Starttime);
            this.hbox7.getChildren().addAll(endtimeLabel, endtime);
            this.hbox8.getChildren().addAll(amountLabel, amount);
            // this.hbox7.setPadding(new Insets(10, 50, 50, 50));
            //this.hbox5.getChildren().addAll(ratingLabel,rating);
            //this.hbox6.getChildren().addAll(EarningLabel,Earning);

            this.vbox.setPadding(new Insets(10, 10, 10, 10));
            String cssLayout = "-fx-border-color: #00001a;\n"
                    + "-fx-border-insets: 2;\n"
                    + "-fx-border-width: 3;\n"
                    + ""
                    + "-fx-font-weight: bold";

            this.vbox.setStyle(cssLayout);

            this.vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox6, hbox7, hbox8);
        }

        protected void updateItem(History item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                Name.setText(item.getName());
                PhoneNo.setText(item.getPhoneNo());
                VehicleLicenseNo.setText(item.getVehicleLicenseNo());
                VehicleModel.setText(item.getVehicleModel());
                //Date.setText(item.getDate());
                Starttime.setText(item.getStarttime());
                endtime.setText(item.getEndTime());
                amount.setText(item.getAmount() + " Tk.");
                //rating.setText(item.getRating());
                // Earning.setText(item.getEarning());
                //label.setText(item != null ? item.getName() : "<null>");
                setGraphic(vbox);

            }

        }

        {
            // update HBox every time the selection changes
           selectedProperty().addListener((observable, oldValue, newValue) -> {
                History item = getItem();
                if (!isEmpty() && item != null) {
                    updateItemSelection(item, oldValue);
                    //System.out.println(item.getAddress());
                }
            });
        }

        private void updateItemSelection(History item, boolean selected) {
            // update for HBox for non-empty cells based on selection
            if (selected) {
                System.out.println(item.getName());
                System.out.println(item.getAddress());
                System.out.println(item.getDate());
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ListView.getStylesheets().add("GeniunCoder.css");
        ListView.setExpanded(true);

        ListView.depthProperty().set(1);

        loadData();

    }

    public void loadData() {

        ObservableList<History> list = FXCollections.observableArrayList();

        c.connectDB();
        try {

            String sql = "  select pr.StartTime as StartTime, pr.EndTime as EndTime, pr.TotalAmount as TotalAmount, u2.PhoneNo,\n"
                    + "  v.VehicleLicenseNo as VehicleLicenseNo, v.VehicleModel as VehicleModel, \n"
                    + "  u2.Name as Name \n"
                    + "  from Users as u join\n"
                    + "  ParkingSpotOwner as ps on u.UserId = ps.UserId join\n"
                    + "  ParkingRequests as pr on ps.SpotOwnerId = pr.ReceiverId join\n"
                    + "  VehicleOwner as vo on  pr.SenderId = vo.VehicleOwnerId join\n"
                    + "  Vehicle as v on vo.VehicleOwnerId = v.VehicleOwnerId join\n"
                    + "  Users as u2 on u2.UserId = vo.UserId\n"
                    + "  where u.UserId = " + user.getUserId() + " and StartTime is not null and  EndTime is not null order by pr.StartTime desc";

            System.out.println(sql);

            PreparedStatement statement = c.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<History> dataForTable = new ArrayList();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String PhoneNo = resultSet.getString("PhoneNo");
                String VehicleLicenseNo = resultSet.getString("VehicleLicenseNo");

                String VehicleModel = resultSet.getString("VehicleModel");

                String amount = String.valueOf(resultSet.getInt("TotalAmount"));

                //String earning = resultSet.getString("p.Earning");
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));

                History history = new History();
                history.setName(name);
                history.setVehicleLicenseNo(VehicleLicenseNo);
                history.setVehicleModel(VehicleModel);
                history.setPhoneNo(PhoneNo);

                Date date = null;
                Timestamp timestamp = resultSet.getTimestamp("StartTime");
                if (timestamp != null) {
                    date = new java.util.Date(timestamp.getTime());
                }

                SimpleDateFormat sm = new SimpleDateFormat("EE dd-MMMM-yyyy hh:mm a");
                System.out.println(sm.format(date));
//                String StartTime = String.valueOf(resultSet.getDate("StartTime"));
                String StartTime = sm.format(date);

                timestamp = resultSet.getTimestamp("EndTime");
                if (timestamp != null) {
                    date = new java.util.Date(timestamp.getTime());
                }

                String EndTime = sm.format(date);

                history.setStarttime(StartTime);
                history.setEndTime(EndTime);
                history.setAmount(amount);

                list.add(history);
            }

            ListView.setItems(list);

            ListView.setCellFactory(new Callback<ListView<History>, ListCell<History>>() {

                @Override
                public ListCell<History> call(ListView<History> param) {
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
                    Logger.getLogger(ParkingSpotOwnerHistoryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
/**
 * select u.Name,u.PhoneNo,ve.VehicleLicenseNo,ve.VehicleModel from Users u
 * inner join VehicleOwner v on v.UserId=u.UserId inner join Vehicle ve on
 * ve.VehicleOwnerId=v.VehicleOwnerId
 *
 *
 * select
 * u.Name,u.PhoneNo,ve.VehicleLicenseNo,ve.VehicleModel,pr.DATE,pr.StartTime,pr.EndTime
 * from ParkingSpot ps inner join ParkingRecords pr on ps.SpotId=pr.SpotId inner
 * join Vehicle ve on ve.VehicleId=pr.VehicleId inner join VehicleOwner v on
 * v.VehicleOwnerId=ve.VehicleOwnerId inner join Users u on u.UserId=v.UserId
 *
 *
 * select
 * u.Name,u.PhoneNo,ve.VehicleLicenseNo,ve.VehicleModel,pr.DATE,pr.StartTime,pr.EndTime
 * from ParkingSpot ps inner join ParkingRecords pr on ps.SpotId=pr.SpotId inner
 * join Vehicle ve on ve.VehicleId=pr.VehicleId inner join VehicleOwner v on
 * v.VehicleOwnerId=ve.VehicleOwnerId inner join Users u on u.UserId=v.UserId
 * and ps.SpotId=1
 */
