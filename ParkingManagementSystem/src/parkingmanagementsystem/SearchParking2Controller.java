/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class SearchParking2Controller implements Initializable {

    @FXML
    private AnchorPane achorPaneParent;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane anchorPaneChild;

    @FXML
    private JFXListView<Users> ListView;

    
    @FXML
    private URL location;
    

    DatabaseHelper c = new DatabaseHelper();

    static class XCell extends ListCell<Users> {

        Users lastItem;
        boolean requestSent = false;

        VBox vbox = new VBox();
        Label NameHeading = new Label("Name :");
        Label Name = new Label();
        HBox hbox1 = new HBox();

        Label AddressHeading = new Label("Address :");
        Label Address = new Label("");

        HBox hbox2 = new HBox();

        JFXButton Button = new JFXButton("Send Parking Request");
        JFXButton cancelButton = new JFXButton("Cancel");

        public XCell() {
            super();

            this.getStylesheets().add("GeniunCoder.css");

            this.hbox1.getChildren().addAll(NameHeading, Name);

            this.hbox2.getChildren().addAll(AddressHeading, Address);

            this.vbox.getChildren().addAll(hbox1, hbox2, Button);

            Button.getStylesheets().add("GeniunCoder.css");

            Button.getStyleClass().add("jfx-button-black");

            cancelButton.getStylesheets().add("GeniunCoder.css");

            cancelButton.getStyleClass().add("jfx-button-black");

            
            if (!requestSent) {
                Button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(lastItem.getPhoneNo() + " : " + event);
                        Button.setText("Cancel Request");
                        requestSent = true;
                    }
                });
            } else {
                   
            }

        }

        @Override
        protected void updateItem(Users item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                Name.setText(item.getName());
                Address.setText(item.getPassword());
                //label.setText(item != null ? item.getName() : "<null>");
                setGraphic(vbox);

                //double minWidth = 5 * item.getName().length();
                //double maxWidth = 10 * item.getName().length();

            }
        }
    }

    
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        achorPaneParent.setPrefWidth(800);
        achorPaneParent.setPrefHeight(800);
        
        ListView.getStylesheets().add("GeniunCoder.css");
            ListView.setExpanded(true);
            
        ListView.setVerticalGap(20.0);

        try {
            loadData();
        } catch (IOException ex) {
            Logger.getLogger(SearchParking2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadData() throws IOException {
        
        
        ObservableList<Users> list = FXCollections.observableArrayList();

        c.connectDB();

        try {

            String sql = "Select Name, MobileNo, Password from Users";

            Statement statement = c.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Users> dataForTable = new ArrayList();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String mobileNo = resultSet.getString("MobileNo");
                String password = resultSet.getString("Password");

                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));

                Users user = new Users(name, mobileNo, password);

                
               
                list.add(user);
            }


            ListView.setItems(list);

            ListView.setCellFactory(new Callback<ListView<Users>, ListCell<Users>>() {

                @Override
                public ListCell<Users> call(ListView<Users> param) {

                    return new XCell();
                }

            });

        
         
        } catch (SQLException e) {

        }

        //ArrayList<VBox> Vboxes = new ArrayList();
        //ObservableList<VBox> dataListView = FXCollections.observableArrayList(Vboxes);
//        for(int i =0;i<5;i++)
//        {
//            //VBox vbox = FXMLLoader.load(getClass().getResource("Container.fxml"));
//            VBox vbox = new VBox();
//            
//            Label NameHeading = new Label("Name :");
//            Label Name = new Label("raju");
//            
//            HBox hbox1 = new HBox();
//            hbox1.getChildren().addAll(NameHeading,Name);
//            
//              
//            
//            Label AddressHeading = new Label("Address :");
//            JFXTextArea Address = new JFXTextArea("Patuatuly dashldahsldasl kldaas lkdasljdaslkj lk asdkljasdjlk");
//            
//            HBox hbox2 = new HBox();
//            hbox2.getChildren().addAll(AddressHeading,Address );
//            
//            JFXButton Button = new JFXButton("Send Parking Request");
//            
//            vbox.getChildren().addAll(hbox1,hbox2,Button);
//            
//            ListView.getItems().add(vbox);
//        }
    }

    
   
}
