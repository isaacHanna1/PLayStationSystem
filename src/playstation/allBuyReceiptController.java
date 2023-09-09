/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.lifeCycleOfTransaction;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Seha
 */
public class allBuyReceiptController implements Initializable{
@FXML
    private BorderPane container;

    @FXML
    private Label lbl_totalCost;

    @FXML
    private DatePicker datePickerStart;

    @FXML
    private DatePicker datePickerEnd;

    @FXML
    private TableView<lifeCycleOfTransaction> tableOfAllReceipt;

    @FXML
    private TableColumn<lifeCycleOfTransaction, String> col_trancationDate;

    @FXML
    private TableColumn<lifeCycleOfTransaction,String> col_transactionDetails;

    @FXML
    private TableColumn<lifeCycleOfTransaction, Float> col_totalReceipt;

    @FXML
    private TableColumn<lifeCycleOfTransaction, Integer> col_transactionNumber;

    @FXML
    private Label lbl_totalProft;

    @FXML
    private Label lbl_allReceipt;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        col_trancationDate.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,String>("transactionDate"));
        col_transactionDetails.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,String>("transactionDetails"));
        col_totalReceipt.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,Float>("addMoney"));
        col_transactionNumber.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,Integer>("transactionNumber"));
        
         datePickerStart.setValue(LocalDate.now().minus(Period.ofYears(1)));
         datePickerEnd.setValue(LocalDate.now());
         getTransaction();
    }
    
    public void getTransaction(){
        
        tableOfAllReceipt.getItems().clear();
        try{
      
        LocalDate start = datePickerStart.getValue();
        LocalDate end = datePickerEnd.getValue();
        String sql = "SELECT  lifeofcustomer.dateOfTransaction , lifeofcustomer.detailsOfTransaction , lifeofcustomer.minsMoney , lifeofcustomer.transactionNumber FROM `lifeofcustomer` WHERE lifeofcustomer.typeOfTransaction = 5 and (dateOfTransaction <=  '"+end+"' and dateOfTransaction >= '"+start+"')";
            System.out.println(sql);
            float total = 0 ;
        Object [] table = dataBase.retrievingTableInArrayOfObject(sql);
            for (int i = 0; i < table.length; i++) {
                 
                String [] row =(String[]) table[i];
                int transactionNumber = Integer.parseInt(row[3]);
                String date = row[0];
                String details = row[1];
                float addMony = Float.parseFloat(row[2]);
                total =  total + addMony ;
                tableOfAllReceipt.getItems().add(new lifeCycleOfTransaction(transactionNumber, date, details, addMony));
            }
     
           lbl_allReceipt.setText(total+"");
   
        }
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
            System.out.println(ex.getMessage());
        
        }
        
    }
    
    
    public void ShowPreveriosBuyReceipt(){
       int transaction = tableOfAllReceipt.getSelectionModel().getSelectedItem().getTransactionNumber();
       System.out.println("ttransaction "+transaction);
       try{
           String sql = "SELECT receiptNumber FROM cafebuymaininfo WHERE transactionNumber = "+transaction;
           System.out.println(sql);
          int reciptNumberBuy = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction(sql));
          oldMoneyController.reciptNumberBuy = reciptNumberBuy;
           System.out.println(" rece" + reciptNumberBuy);
       openRequestWindow(5);
       }
       catch(SQLException ex){
       
       dataBase.showMessageJOP(ex.getMessage());
               
       }
        
    }
    
    public void openRequestWindow(int requsetedWindow){
           
        if(requsetedWindow == 1 ){
            Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("liveReceiptEdit"+".fxml"));
                Stage stage = new Stage();
                stage.setTitle("فاتورة بيع مباشر");
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
        
        }else if (requsetedWindow == 5){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("CafeBuyGoodsEdit"+".fxml"));
                Stage stage = new Stage();
                stage.setTitle("فاتورة شراء ");
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
        }
           
    }
    
    
}
