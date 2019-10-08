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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Istiaque Hashem
 */
public class VehicalOwnerHistoryController implements Initializable {

    @FXML
    private JFXListView<History> ListView;
    /**
     * Initializes the controller class.
     */
    DatabaseHelper c = new DatabaseHelper();

    Users user = LoginPageController.loggedUser;

    static class XCell extends ListCell<History> {

        History lastItem;
        VBox vbox = new VBox();
        Label NameHeading = new Label("Spot Owner Name : ");
        Label Name = new Label();
        HBox hbox1 = new HBox();

        Label AddressHeading = new Label("Parking Spot Address :");
        Label Address = new Label("");
        HBox hbox2 = new HBox();

        Label PhoneNoLabel = new Label("Phone No : ");
        Label PhoneNo = new Label("");
        HBox hbox5 = new HBox();

        Label amountLabel = new Label("Amount Paid : ");
        Label amount = new Label("");
        HBox hbox3 = new HBox();

        Label ratingLabel = new Label("Given Rating : ");
        Label rating = new Label("");
        HBox hbox4 = new HBox();

        Label spotdate = new Label("Date : ");
        Label Date = new Label("");
        HBox hbox6 = new HBox();

        Label Starttimelabel = new Label("Start Time : ");
        Label Starttime = new Label("");
        HBox hbox7 = new HBox();

        Label endtimeLabel = new Label("End Time : ");
        Label endtime = new Label("");
        HBox hbox8 = new HBox();

        //Rating star = new Rating();
        
        //
        public XCell() {
            super();
            this.hbox1.setPadding(new Insets(10,0,0,0));
            this.hbox1.getChildren().addAll(AddressHeading, Address);
            this.hbox2.getChildren().addAll(NameHeading, Name);
            this.hbox3.getChildren().addAll(PhoneNoLabel, PhoneNo);
            this.hbox4.getChildren().addAll(amountLabel, amount);
            this.hbox5.getChildren().addAll(ratingLabel, rating);
            //this.hbox6.getChildren().addAll(spotdate, Date);
            this.hbox7.getChildren().addAll(Starttimelabel, Starttime);
            this.hbox8.getChildren().addAll(endtimeLabel, endtime);
            //this.hbox6.getChildren().addAll(EarningLabel,Earning);
            this.hbox8.setPadding(new Insets(0,0,20,0));
            this.vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, hbox7, hbox8);
            
            //star.setDisable(true);
            
            this.vbox.setPadding(new Insets(10, 10, 10, 10));
            String cssLayout = "-fx-border-color: #00001a;\n"
                    + "-fx-border-insets: 2;\n"
                    + "-fx-border-width: 3;\n"
                    + "-fx-font-size : 14px;"
                    + "-fx-font-weight: bold";
            this.vbox.setStyle(cssLayout);
            
        }

        protected void updateItem(History item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {

                System.out.println("In " + item.getAddress());

                lastItem = item;
                Name.setText(item.getName());
                Address.setText(item.getAddress());
                PhoneNo.setText(item.getPhoneNo());
                amount.setText(item.getAmount()+" Tk.");
                rating.setText(item.getRating()+"/5");
                //Date.setText(item.getDate());
                Starttime.setText(item.getStarttime());
                endtime.setText(item.getEndTime());
                //star.setRating(Double.parseDouble(item.getRating()));
                // Earning.setText(item.getEarning());
                //label.setText(item != null ? item.getName() : "<null>");
                setGraphic(vbox);

                //double minWidth = 5 * item.getName().length();
                //double maxWidth = 10 * item.getName().length();
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
        
        System.out.println(user.getUserId());

        c.connectDB();
        try {

            String sql = "  select pr.RequestId as RequestId , pr.StartTime as StartTime , pr.EndTime as EndTime, \n"
                    + "  pr.TotalAmount as TotalAmount, pr.Rating as Rating, ps.Address as Location, pr.Rent as Rent , u2.PhoneNo as PhoneNo, u2.Name as SpotOwnerName\n"
                    + "  from Users as u join \n"
                    + "  VehicleOwner as vo on u.UserId = vo.UserId join\n"
                    + "  ParkingRequests as pr on vo.VehicleOwnerId = pr.SenderId join\n"
                    + "  ParkingSpotOwner as po on po.SpotOwnerId = pr.ReceiverId join\n"
                    + "  ParkingSpot as ps on ps.SpotOwnerId = po.SpotOwnerId join\n"
                    + "  Users as u2 on u2.UserId = po.UserId\n"
                    + "  where u.UserId = " + user.getUserId() +" and pr.ReceiverId is not null order by pr.StartTime desc";

            PreparedStatement statement = c.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<History> dataForTable = new ArrayList();

            while (resultSet.next()) {
                String name = resultSet.getString("SpotOwnerName");
                String PhoneNo = resultSet.getString("PhoneNo");
                String address = resultSet.getString("Location");

                String Rating = String.valueOf(resultSet.getInt("Rating"));

                String Rent = "";
                
                int r =resultSet.getInt("Rent");
                
                if(resultSet.wasNull())
                {
                    Rent = "N/A";
                }
                else
                {
                    Rent = String.valueOf(r);
                }

                //String Date = String.valueOf(resultSet.getDate("Date"));
               

                Date date = null;
                Timestamp timestamp = resultSet.getTimestamp("StartTime");
                if (timestamp != null) 
                {
                    date = new java.util.Date(timestamp.getTime());
                }
            
                SimpleDateFormat sm = new SimpleDateFormat("EE dd-MMMM-yyyy hh:mm a");
                System.out.println(sm.format(date));
//                String StartTime = String.valueOf(resultSet.getDate("StartTime"));
                String StartTime = sm.format(date);
                
                timestamp = resultSet.getTimestamp("EndTime");
                if (timestamp != null) 
                {
                    date = new java.util.Date(timestamp.getTime());
                }
                
                String EndTime = sm.format(date);
                //String earning = resultSet.getString("p.Earning");
                String amount = String.valueOf(resultSet.getInt("TotalAmount"));

                //System.out.println("inin " + name);

                
                //System.out.println(StartTime+ " \n"+  EndTime);

                History history = new History();
                
                history.setName(name);
                history.setAddress(address);
                history.setStarttime(StartTime);
                history.setEndTime(EndTime);
                history.setRating(Rating);
                history.setPhoneNo(PhoneNo);
                history.setAmount(amount);
                
                
                //dataForTable.add(history);
                list.add(history);
                //list.add(history);
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
 * select
 * u.Name,u.PhoneNo,p.Address,p.Rent,p.Rating,pr.DATE,pr.StartTime,pr.EndTime
 * from Vehicle ve inner join ParkingRecords pr on ve.VehicleId=pr.VehicleId
 * inner join ParkingSpot p on p.SpotId=pr.SpotId inner join ParkingSpotOwner pa
 * on pa.SpotOwnerId=p.SpotOwnerId inner join Users u on u.UserId=pa.UserId and
 * ve.VehicleId=2002
 *
 *
 */


/*select u2.Name 'SpotOwner', ps.Address 'Address', pr.Amount 'Amount',
pr.StartTime 'StartTime', pr.EndTime 'EndTime'
from Users as u
inner join VehicleOwner as vo
on u.UserId = vo.UserId
 join Vehicle as v
on v.VehicleOwnerId = vo.VehicleOwnerId
inner join ParkingRecords as pr
on pr.VehicleId = v.VehicleId
inner join ParkingSpot as ps
on ps.SpotId = pr.SpotId
inner join ParkingSpotOwner as pso
on pso.SpotOwnerId = ps.SpotOwnerId
inner join Users as u2
on pso.UserId = u2.UserId*/
