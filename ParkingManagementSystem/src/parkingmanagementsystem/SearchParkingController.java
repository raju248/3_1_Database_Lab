/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class SearchParkingController implements Initializable {

    @FXML
    private TableView<Users> MainTable;
    @FXML
    private TableColumn<Users, String> NameColumn;
    @FXML
    private TableColumn<Users, String> PhoneNoColumn;
    @FXML
    private TableColumn<Users, String> PasswordColumn;
    
    @FXML
    private AnchorPane anchorPane;

    DatabaseHelper c = new DatabaseHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        loadDataToTable();
    }
    
    
    private void loadDataToTable() {
        c.connectDB();
        
        
        MainTable.prefHeightProperty().bind(anchorPane.heightProperty());
        MainTable.prefWidthProperty().bind(anchorPane.widthProperty());
        
        NameColumn.prefWidthProperty().bind(MainTable.prefHeightProperty().divide(3.1));
        PhoneNoColumn.prefWidthProperty().bind(MainTable.prefHeightProperty().divide(3.1));
        PasswordColumn.prefWidthProperty().bind(MainTable.prefHeightProperty().divide(3.1));
        
        

        try {
            String sql = "Select Name, PhoneNo, Password , Type from Users";

            Statement statement = c.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            ArrayList<Users> dataForTable = new ArrayList();
            
            while(resultSet.next())
            {
                String name = resultSet.getString("Name");
                String mobileNo = resultSet.getString("PhoneNo");
                String password = resultSet.getString("Password");
                
                        
                        System.out.println(resultSet.getString(1));
                        System.out.println(resultSet.getString(2));
                        System.out.println(resultSet.getString(3));
                
                Users user = new Users(name,mobileNo,password, resultSet.getInt(4));
                
                dataForTable.add(user);
            }
            
            
            ObservableList<Users> tableData = FXCollections.observableArrayList(dataForTable);
            
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            PhoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
            PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            
            MainTable.setItems(tableData);
            
        } catch (SQLException e) {

        }

    }

}
