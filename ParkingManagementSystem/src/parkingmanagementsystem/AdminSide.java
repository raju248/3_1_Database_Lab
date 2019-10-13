


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Tech Land
 */
public class AdminSide extends Application {

    @Override
    public void start(Stage pstage) throws IOException {
        
//        DatabaseHelper d = new DatabaseHelper();
//        try
//        {
//             // d.AddUser("Raju","01851908349","22448800");
//            //connectDB c = new connectDB();
//            //c.connectDB();
//            d.read();
//        }
//        catch(Exception e)
//        {
//            System.out.println("mara");
//            e.printStackTrace();
//        }
        try {
            Scene scene;
            Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
            scene = new Scene(root);

            pstage.setTitle("Parking Management System");

            pstage.setScene(scene);
            pstage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            
            System.out.println(timestamp.toString());

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

}
