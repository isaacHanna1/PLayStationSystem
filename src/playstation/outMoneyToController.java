/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.safe;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.controlsfx.control.textfield.TextFields;
/**
 *
 * @author Seha
 */
public class outMoneyToController implements Initializable {
    @FXML
    private TextField txt_account;

    @FXML
    private TextField txt_details;

    @FXML
    private DatePicker date_picker;
    
        @FXML
    private ComboBox<String> combo_Safe;
        
    @FXML
    private TextField txt_value;
    
    @FXML
    private TableView<safe> table_safe;

    @FXML
    private TableColumn<safe, Integer> col_id;

    @FXML
    private TableColumn<safe, String> colDetails;

    @FXML
    private TableColumn<safe,Float> col_money;

    @FXML
    private TableColumn<safe, String> col_date;
    @FXML
    private DatePicker datePickerSearch;
    
    @FXML
    private Label lbl_debt;
    @FXML
    private Label lbl_message;
        safe [] items = {}; //all out money 
    
    @FXML
    private Label lbl_deptOrprofit;
    @Override
    
    
    public void initialize(URL location, ResourceBundle resources) {
        try{
            
        String [] allAccount = dataBase.retrivingColumnFromDataBase("SELECT name FROM son");
        TextFields.bindAutoCompletion(txt_account, allAccount);
        date_picker.setValue(LocalDate.now());
        datePickerSearch.setValue(LocalDate.now());
        dataBase.putingColumnFromDataBaseIntoCombox("SELECT nameOfSafe from safe", combo_Safe);
        combo_Safe.getSelectionModel().select(0);
    
        col_id.setCellValueFactory(new PropertyValueFactory<safe,Integer>("id_edit"));
        colDetails.setCellValueFactory(new PropertyValueFactory<safe,String>("details"));
        col_money.setCellValueFactory(new PropertyValueFactory<safe,Float>("money"));
        col_date.setCellValueFactory(new PropertyValueFactory<safe,String>("date"));

        getALLmoney();
        }catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
        catch(Exception ex){
            txt_account.setText("");
        }
        txt_account.requestFocus();
        
        txt_account.setOnKeyPressed((event)->{
            if(event.getCode().toString().equals("ENTER")){
                txt_value.requestFocus();
                try{
                    //بجيب الحساب القديم اللي عليا 
                int customerAccountID =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+txt_account.getText()+"'"));
                String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+customerAccountID+"");
                if(Float.parseFloat(lifeCycleOfCustomer)>0){
                lbl_deptOrprofit.setText("مستحقاتي");
                }
                else{
                    lbl_deptOrprofit.setText("مستحقاته");
                }
                lbl_debt.setText(""+ Math.abs(Float.parseFloat(lifeCycleOfCustomer)));
                }catch(SQLException ex ){
                    lbl_message.setText(ex.getMessage());
                }
            }
        });
        txt_value.setOnKeyPressed((event)->{
            if(event.getCode().toString().equals("ENTER")){
                txt_details.requestFocus();
            }
        });
        
    }
    
public void outMoney()    {
try{
String accountID =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id FROM son WHERE Name = '"+txt_account.getText()+"'");
int transactionNumber= 556;
float value = Float.parseFloat(txt_value.getText());
int safe_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from safe WHERE nameOfSafe = '"+combo_Safe.getSelectionModel().getSelectedItem()+"'"));
LocalDate localStartDate = date_picker.getValue();
java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);
   Alert alertForPaied = new Alert(AlertType.WARNING);
   alertForPaied.setTitle("صرف نقدي؟");
   alertForPaied.setContentText("هل تريد بالفعل صرف "+txt_value.getText()+" لحساب "+txt_account.getText()+" ?");
   Optional<ButtonType> result = alertForPaied.showAndWait(); 
   if(result.get() ==ButtonType.OK){
   
        int idOfoutMoney = dataBase.customerLife(transactionNumber, 3, Float.parseFloat(txt_value.getText()),0, txt_details.getText(),Integer.parseInt(accountID),sqlDate);
        dataBase.enterAndOutMoney(transactionNumber,value*-1,safe_id, loginController.loginUser.getID(),sqlDate,Integer.parseInt(accountID) ,txt_details.getText(),idOfoutMoney);
        try{
        int customerAccountID =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+txt_account.getText()+"'"));
        String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+customerAccountID+"");
          if(Float.parseFloat(lifeCycleOfCustomer)>0){
                lbl_deptOrprofit.setText("مستحقاتي");
                }
                else{
                    lbl_deptOrprofit.setText("مستحقاته");
                }
                lbl_debt.setText(""+ Math.abs(Float.parseFloat(lifeCycleOfCustomer)));
                }catch(SQLException ex ){
                    lbl_message.setText(ex.getMessage());
                }
        txt_account.requestFocus();
        getALLmoney();
        }
   else{
   
       lbl_message.setText("لم يتم حفظ العملية");
   }
}
catch(SQLException ex){
dataBase.showMessageJOP(ex.getMessage());
}
}
     public void getALLmoney(){
        table_safe.getItems().clear();
         System.out.println("here");
    try{
    LocalDate value = datePickerSearch.getValue();
        System.out.println(value);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedString = value.format(formatter);
    String sql = "SELECT id , dateOfTransaction , detailsOfTransaction , addMoney from lifeofcustomer where transactionNumber = 556 and dateOfTransaction ='"+formattedString+"'";
        System.out.println(sql);
    Object[] table = dataBase.retrievingTableInArrayOfObject(sql);
        System.out.println("table size" + table.length);
    items = new safe[table.length];
    for (int i = 0; i < table.length; i++) {
                String [] row = (String[]) table[i];
                System.out.println("id "+row[0]);
                int edit_id = Integer.parseInt(row[0]);
                String date = row[1];
                String details = row[2];
                float money = Float.parseFloat(row[3]);
                safe one = new safe(edit_id, date, details, money);
                table_safe.getItems().add(one);
                items[i]= one;
        }
        
          System.out.println("the size is   adas "+items.length);
    }
    catch(SQLException ex){
    
    }
    
    }
     public void getSelectedItem(){
        try{
        safe selectedItem = table_safe.getSelectionModel().getSelectedItem();
        txt_details.setText(selectedItem.getDetails());
        txt_value.setText(selectedItem.getMoney()+"");
        String sql = "SELECT son.name FROM son JOIN lifeofcustomer WHERE son.Id = lifeofcustomer.customerAccountId and lifeofcustomer.id = "+selectedItem.getId_edit();
        String sonNameAccount = dataBase.gettingOnvalueFromTableByAggregatefunction(sql);
        txt_account.setText(sonNameAccount);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = selectedItem.getDate();

         //convert String to LocalDate
         LocalDate localDate = LocalDate.parse(date, formatter);
         date_picker.setValue(localDate);
        }
        catch(Exception ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
    
    }
     public void getMonenyInSpecificDate(){
    getALLmoney();//انا كسلت اعدلها يعني هي هي اصلي كنت عامل فانكشن بدور في جدول
}    
     public void deleteMoney(){
    
        try{
        int  selectedId = table_safe.getSelectionModel().getSelectedItem().getId_edit();
        String sql = "DELETE from lifeofcustomer WHERE id = '"+selectedId+"'";
        String sqlgetAccountId ="SELECT customerAccountId FROM lifeofcustomer WHERE id = '"+selectedId+"'";
         Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("تأكيد المسح ؟");
            alert.setHeaderText("مسح");
            alert.setContentText("هل تريد بالفعل المسح ؟");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
           String account_id =dataBase.gettingOnvalueFromTableByAggregatefunction(sqlgetAccountId);
           String sqlDeleteEnterOutMoney = "DELETE FROM enteroutmoney WHERE account_id = '"+account_id+"' and total = -'"+txt_value.getText()+" 'and date = '"+date_picker.getValue()+"'";
           System.out.println(sqlDeleteEnterOutMoney);
           dataBase.excuteNonQuery(sql);
           dataBase.excuteNonQuery(sqlDeleteEnterOutMoney);           
           table_safe.getItems().remove(table_safe.getSelectionModel().getSelectedIndex());
           getALLmoney();
         }
        }
        catch(Exception ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
    }
     public void setAccountName(String accountName){
     
     txt_account.setText(accountName);
     }
     
     public void setReceiptValue(String value){
         txt_value.setText(value);
     }
     
     public void setLblDebit(String accountName){
          try{
        int customerAccountID =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+accountName+"'"));
        String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+customerAccountID+"");
          if(Float.parseFloat(lifeCycleOfCustomer)>0){
                lbl_deptOrprofit.setText("مستحقاتي");
                }
                else{
                    lbl_deptOrprofit.setText("مستحقاته");
                }
                lbl_debt.setText(""+ Math.abs(Float.parseFloat(lifeCycleOfCustomer)));
                }catch(SQLException ex ){
                    lbl_message.setText(ex.getMessage());
                }
     
     
     }
}
