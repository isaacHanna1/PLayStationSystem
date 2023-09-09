/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.safe;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Seha
 */
public class receivingMoneyFromController implements Initializable{
    @FXML
    private RadioButton radioAccountName;

    @FXML
    private RadioButton radioSafeName;

    @FXML
    private ComboBox<String> combo_account_safe;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> combo_safe;
    @FXML
    private TextField txt_receivingTotalMoney;
    
    @FXML
    private BorderPane container;

    @FXML
    private Label lbl_currenctBallance;
    
    @FXML
    private TextField txt_details;

    @FXML
    private Label lbl_message;
    
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
    private Button delete_btn;
    
    @FXML
    private Button showBtn;
    safe [] items = {}; //all receiving money 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
        datePickerSearch.setValue(LocalDate.now());
        if(loginController.loginUser.getPermission()==0){
            delete_btn.setVisible(false);
            datePickerSearch.setDisable(true);
            radioSafeName.setDisable(true);
            radioAccountName.setSelected(true);
            table_safe.setEditable(false);
            ifAccountRadioISSelected();
            dataBase.putingColumnFromDataBaseIntoCombox(" SELECT nameOfSafe FROM `safe` WHERE id > 1", combo_safe);
            table_safe.getItems().remove(0, table_safe.getItems().size()-5);//show only last 5 transztion for money 
            
            
        }else{
            dataBase.putingColumnFromDataBaseIntoCombox(" SELECT nameOfSafe FROM `safe` ", combo_safe);
        
        }
        
            
            combo_account_safe.getSelectionModel().select(1);
            combo_safe.getSelectionModel().select(0);
            radioSafeName.setSelected(true);           
            String safeName = combo_account_safe.getSelectionModel().getSelectedItem();
            String id  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from safe WHERE nameOfSafe = '"+safeName+"'");
            System.out.println("the id is : "+id);
            String total = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(total) FROM enteroutmoney WHERE safty_id = '"+id+"'");
            txt_receivingTotalMoney.setText(total+"");
            
            
            radioAccountName.setSelected(true);
            radioSafeName.setSelected(false);
            System.out.println("the safe id is "+safe.id);
            combo_safe.getSelectionModel().select(safe.id-1);
            try{
            String nameOfCustomer = playerController.currentPlayer.getPlayerName();
            combo_account_safe.getSelectionModel().select(nameOfCustomer);
            int customerAccountID =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+nameOfCustomer+"'"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date today = new Date();
            datePicker.setValue(LocalDate.now());
            String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+customerAccountID+"");
            lbl_currenctBallance.setText(lifeCycleOfCustomer);
            txt_receivingTotalMoney.setText(lifeCycleOfCustomer);
            txt_receivingTotalMoney.requestFocus();
            txt_receivingTotalMoney.positionCaret(txt_receivingTotalMoney.getLength());
            }
            catch(Exception ex){

            radioSafeName.setSelected(false);
            radioAccountName.setSelected(false);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date today = new Date();
            datePicker.setValue(LocalDate.now());
            
            txt_receivingTotalMoney.setText("0");
            
            }
            
            
            combo_account_safe.valueProperty().addListener(new ChangeListener<String>() {
                
        @Override
        public void changed(ObservableValue ov, String t, String t1) {
          if(radioSafeName.isSelected()){
              try{
            String safeName = combo_account_safe.getSelectionModel().getSelectedItem();
            String id  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from safe WHERE nameOfSafe = '"+safeName+"'");
            System.out.println("the id is : "+id);
            String total = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(total) FROM enteroutmoney WHERE safty_id = '"+id+"'");
            txt_receivingTotalMoney.setText(total+"");
              }catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
          }
        if(radioAccountName.isSelected()){
            String nameOfCustomer = combo_account_safe.getSelectionModel().getSelectedItem();
            try{
            int customerAccountID =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+nameOfCustomer+"'"));
            String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+customerAccountID+"");
            lbl_currenctBallance.setText(lifeCycleOfCustomer);
            }catch(SQLException ex) {
            
                dataBase.showMessageJOP(ex.getMessage());
            }
                
        }
        
        }
        });
            

            
        } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
        
        
                    col_id.setCellValueFactory(new PropertyValueFactory<safe,Integer>("id_edit"));
                    colDetails.setCellValueFactory(new PropertyValueFactory<safe,String>("details"));
                    col_money.setCellValueFactory(new PropertyValueFactory<safe,Float>("money"));
                    col_date.setCellValueFactory(new PropertyValueFactory<safe,String>("date"));
                    
                    getALLmoney();

        
    }
    
    public void ifAccountRadioISSelected(){
    
        combo_account_safe.getSelectionModel().clearSelection();
        combo_account_safe.getItems().clear();
        radioSafeName.setSelected(false);
                try {
                dataBase.putingColumnFromDataBaseIntoCombox("SELECT name FROM son", combo_account_safe);
                } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
    }
                
    public void ifSafetRadioISSelected(){
    
        combo_account_safe.getSelectionModel().clearSelection();
        combo_account_safe.getItems().clear();
        radioAccountName.setSelected(false);
                try {
            dataBase.putingColumnFromDataBaseIntoCombox("SELECT nameOfSafe from safe ", combo_account_safe);
                } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
                
    }
    
    public void receivingMoneyFromSafe(){
        try{
            String safeNameFrom = combo_account_safe.getSelectionModel().getSelectedItem();

            String idSafeFrom  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from safe WHERE nameOfSafe = '"+safeNameFrom+"'");
            
            String safeNameTo = combo_safe.getSelectionModel().getSelectedItem();
            
            String id  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from safe WHERE nameOfSafe = '"+safeNameTo+"'");
            dataBase.excuteNonQuery("UPDATE enteroutmoney set safty_id = '"+id+"' WHERE safty_id = '"+idSafeFrom+"'");
                  container.getScene().getWindow().hide();
        getALLmoney();

        } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
}
    public void receivingMoneyFromAccount(){
    
        try{
        String accountName = combo_account_safe.getSelectionModel().getSelectedItem();
        String idAccountFrom  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son WHERE name = '"+accountName+"'");
        float total = Float.parseFloat(txt_receivingTotalMoney.getText());
        String safeNameTo = combo_safe.getSelectionModel().getSelectedItem();
        String idSafeTo  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from safe WHERE nameOfSafe = '"+safeNameTo+"'");      
        LocalDate localStartDate = datePicker.getValue();
        java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);

        int idOfaddedMoney = dataBase.customerLife(11,3, 0, total, txt_details.getText()+"   "+accountName, Integer.parseInt(idAccountFrom), sqlDate);
        //اي حساب عندي هديله رقم حركة 11
        dataBase.enterAndOutMoney(11,total,Integer.parseInt(idSafeTo) ,loginController.loginUser.getID(),sqlDate,Integer.parseInt(idAccountFrom)," استلام النقدية من حساب "+accountName,idOfaddedMoney);        
        lbl_message.setText("تم الاستلام");
        txt_receivingTotalMoney.setText("0");
        txt_details.setText("");
        getALLmoney();
        
 } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
    }
    
    public void handleReceiveBtn(){
    if(radioAccountName.isSelected()){
        receivingMoneyFromAccount();
    }
    if(radioSafeName.isSelected()){
        receivingMoneyFromSafe();
    }
    
    }
    
    
    public void getALLmoney(){
        table_safe.getItems().clear();
    try{
    LocalDate value = datePickerSearch.getValue();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedString = value.format(formatter);
    String sql = "SELECT id , dateOfTransaction , detailsOfTransaction , minsMoney from lifeofcustomer where transactionNumber = 11 and dateOfTransaction ='"+formattedString+"'";
        System.out.println(sql);
    Object[] table = dataBase.retrievingTableInArrayOfObject(sql);
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
        
          System.out.println("the size is "+items.length);
    }
    catch(SQLException ex){
    
    }
    
    }
public void getMonenyInSpecificDate(){
    getALLmoney();//انا كسلت اعدلها يعني هي هي اصلي كنت عامل فانكشن بدور في جدول
}    
    public void getSelectedItem(){
        try{
        safe selectedItem = table_safe.getSelectionModel().getSelectedItem();
        txt_details.setText(selectedItem.getDetails());
        txt_receivingTotalMoney.setText(selectedItem.getMoney()+"");
        String sql = "SELECT son.name FROM son JOIN lifeofcustomer WHERE son.Id = lifeofcustomer.customerAccountId and lifeofcustomer.id = "+selectedItem.getId_edit();
        String sonNameAccount = dataBase.gettingOnvalueFromTableByAggregatefunction(sql);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = selectedItem.getDate();

         //convert String to LocalDate
         LocalDate localDate = LocalDate.parse(date, formatter);
         datePicker.setValue(localDate);
         radioAccountName.setSelected(true);
        combo_account_safe.setValue(sonNameAccount);
        }
        catch(Exception ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
    
    }
    public void deleteMoney(){
    
        try{
        int  selectedId = table_safe.getSelectionModel().getSelectedItem().getId_edit();
        String sql = "DELETE from lifeofcustomer WHERE id = '"+selectedId+"'";
        String sqlgetAccountId ="SELECT customerAccountId FROM lifeofcustomer WHERE id = '"+selectedId+"'";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("تأكيد المسح ؟");
            alert.setHeaderText("مسح");
            alert.setContentText("هل تريد بالفعل المسح ؟");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
           String account_id =dataBase.gettingOnvalueFromTableByAggregatefunction(sqlgetAccountId);
           int id = table_safe.getSelectionModel().getSelectedItem().getId_edit();
           String sqlDeleteEnterOutMoney = "DELETE FROM enteroutmoney WHERE account_id = '"+account_id+"' and total = '"+txt_receivingTotalMoney.getText()+" 'and date = '"+datePicker.getValue()+"' and details = '"+txt_details.getText()+"'";    
           System.out.println(sqlDeleteEnterOutMoney);
           dataBase.excuteNonQuery(sql);
           dataBase.excuteNonQuery(sqlDeleteEnterOutMoney);           
                System.out.println(sqlDeleteEnterOutMoney);
           table_safe.getItems().remove(table_safe.getSelectionModel().getSelectedIndex());
           getALLmoney();
         }
        }
        catch(Exception ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
    }


}

