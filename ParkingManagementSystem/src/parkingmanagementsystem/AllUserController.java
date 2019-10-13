/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class AllUserController implements Initializable {

    @FXML
    private JFXComboBox<String> searchBy;
    @FXML
    private JFXRadioButton Po;
    @FXML
    private ToggleGroup tg;
    @FXML
    private JFXRadioButton Vo;
    
    @FXML
    private JFXListView<Users> ListView;
    
    @FXML
    private JFXTextField input;
    @FXML
    private JFXButton search;
    
    DatabaseHelper c = new DatabaseHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Phone no", "User Id", "Name");
        searchBy.getItems().addAll(list);
        searchBy.setPromptText("Select a option");
        
        
        searchBy.valueProperty().addListener(new ChangeListener<String>() {
            //checks for changes in any comboBox
            //if any change occurs , loads data in the table according to the change

            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                //loadData();
                input.setPromptText(t1);
            }
                
        });
        
        search.setOnAction(e->
        {
                if(input.getText().isEmpty())
                {
                    input.requestFocus();
                    return;
                }
                    
                
        });
        
        
    }    
    
    
    
    void loadEveryThingFirst()
    {
        
    }
    
    ObservableList<Users> list = FXCollections.observableArrayList();
    
    class XCell extends ListCell<Users> {

        Users lastItem;

        VBox vbox = new VBox();
        
        Label IDLabel = new Label("User ID : ");
        Label ID = new Label();
        HBox hbox0 = new HBox();

        Label AddressLabel = new Label("Name : ");
        Label Address1 = new Label();
        HBox hbox1 = new HBox();

        Label PhoneNoLabel = new Label("Phone No : ");
        Label PhoneNo1 = new Label("");
        HBox hbox2 = new HBox();



        public XCell() {
            super();

            this.getStylesheets().add("GeniunCoder.css");

            this.hbox0.getChildren().addAll(IDLabel, ID);
            this.hbox1.getChildren().addAll(AddressLabel, Address1);
            
            this.hbox2.getChildren().addAll(PhoneNoLabel, PhoneNo1);
            

            this.vbox.setPadding(new Insets(10, 10, 10, 10));
            String cssLayout = "-fx-border-color: #00001a;\n"
                    + "-fx-border-insets: 2;\n"
                    + "-fx-border-width: 3;\n"
                    + "-fx-font-weight: bold";

            this.vbox.setStyle(cssLayout);

            this.vbox.getChildren().addAll(hbox0, hbox1, hbox2);
        }

        protected void updateItem(Users item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;

                

               

                setGraphic(vbox);
            }

        }

        {
            // update HBox every time the selection changes
            selectedProperty().addListener((observable, oldValue, newValue) -> {
                Users item = getItem();
                if (!isEmpty() && item != null) {
                    updateItemSelection(item, newValue);
                    
                }
            });
        }

        private void updateItemSelection(Users item, boolean selected) {
            // update for HBox for non-empty cells based on selection
            if (selected) {
               

                ;
            }
        }

    }
    
public void loadData() {

        ListView.getItems().clear();
        list.clear();

        c.connectDB();
        try {

            String sql = " select * from User";

            System.out.println(sql);

            PreparedStatement statement = c.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Users> dataForTable = new ArrayList();

            while (resultSet.next()) {

                Users pps = new Users();

               

               

                list.add(pps);
            }

            
            if(list.size()==0)
            {
                ListView.setPlaceholder(new Label("No Spot Found"));
            }
            
            ListView.setItems(list);

            ListView.setCellFactory(new Callback<ListView<Users>, ListCell<Users>>() {

                @Override
                public ListCell<Users> call(ListView<Users> param) {
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
    
    
    
    
    
    
    
    
    
    
    
        /*in controller*/
     /*        try {
            Stage stage = (Stage)SignUpLabel.getScene().getWindow();
            String title = "Sign Up";
            LoadStages load = new LoadStages(stage, title, "SignUp.fxml");
        } catch (IOException e) {
            System.out.println(e);
        }

    */
    
    
    
}
