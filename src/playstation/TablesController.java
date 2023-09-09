/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.section;
import classes.tables;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Seha
 */
public class TablesController implements Initializable {

    @FXML
    private Pane containerOfTable;

    @FXML
    private FlowPane AllTables;
    
    tables [] alltables ;
    
    static String tableNumber = "";
    static Button buttonOFOPenedTable;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        buildButton();
    }    
    
    
    
    public void buildButton(){
        
        AllTables.getChildren().clear();
         try {
          Object [] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM `tables` ORDER by id ");
          
          alltables = new tables[table.length];
                for (int i = 0; i < table.length; i++) {
                    String [] row =(String[]) table[i];
                    int id = Integer.parseInt(row[0]);
                    String number = row[1];
                    int free = Integer.parseInt(row[2]);
                    tables newOne = new tables(id, number, free);
                    alltables[i] = newOne;
                    Button newTable = new Button(newOne.getNumber());
                    newTable.getStylesheets().add("styleSheetForTables.css");
                    newTable.getStyleClass().add("tableButton");  
                    AllTables.getChildren().add(newTable);
                    newTable.setOnAction((event) -> {

                       this.tableNumber = newOne.getNumber();
                       buttonOFOPenedTable = newTable;
                       TableOrderController.tableName = number;
                       openNewWindow("tableOrder", newOne.getNumber());
                       
                     
                    });
                
              
                    if(newOne.getFree()!= 0){
                    newTable.setStyle("-fx-background-color: #FF6363");
                    }
                    }
                
         
      } catch (SQLException ex) {
          System.out.println(ex.getCause()+"   "+ex.getMessage());
          dataBase.showMessageJOP(ex.getMessage());
      }
                    
    
    
    }
    public void addNewTable(){
    
        
        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle(" الطاولة");
        dialog.setHeaderText("رقم الطاولة");
        dialog.setContentText("ادخل رقم الطاولة");
        Optional<String> result = dialog.showAndWait();
        String value = "طاولة "+result.get();
        result.ifPresent(name -> {
        boolean isInserted = dataBase.insertNewTable(value);
        
        if(isInserted){
            dataBase.addSonAccout(6, value);
            dataBase.addNewPlayer(value, 0+"", 0);
        }
        });
        buildButton();

        
    }
     public void openNewWindow(String name , String title){
    Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(name+".fxml"));
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
  
    
    }
    
   
    
}
