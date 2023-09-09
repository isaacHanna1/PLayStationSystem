/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Seha
 */
public class PlayStation extends Application {
    
    @Override
    public void start(Stage primaryStage) {
 
        
        
        
        try {
            Parent root;
            //load the source into root 
             root = FXMLLoader.load(getClass().getResource("login.fxml"));
             
            // make the scene that appear 
             Scene scene = new Scene(root);
             
             // put the scene into stage == stage the object that hold scene
             primaryStage.setScene(scene);
             
             
             // set the title into stage 
             primaryStage.setTitle("Home");
             
             //show the stage 
             primaryStage.show();
             
                     
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
      
        
        
    }


    public static void main(String[] args) {
        
        launch(args);
    }
    
}
