
package playstation;

import classes.cutomerReceipt;
import classes.lifeCycleOfTransaction;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class oldMoneyController  implements Initializable  {

    
    @FXML
    private DatePicker datePickerStart;

    @FXML
    private DatePicker datePickerEnd;

    @FXML
    private TextField txt_playerName;

    @FXML
    private Label lbl_totalOut;

    @FXML
    private Label lbl_totalGet;
    
    @FXML
    private Label lbl_totalReminder;

    @FXML
    private Label lbl_Ratio;
        @FXML
    private TableView<lifeCycleOfTransaction> tableOfAllTransaction;

    
    @FXML
    private TableColumn<lifeCycleOfTransaction, Integer> col_transactionNumber;

    @FXML
    private TableColumn<lifeCycleOfTransaction, Integer> col_transcationType;

    @FXML
    private TableColumn<lifeCycleOfTransaction, String> col_trancationDate;

    @FXML
    private TableColumn<lifeCycleOfTransaction,String> col_transactionDetails;

    @FXML
    private TableColumn<lifeCycleOfTransaction,Float> col_addMoney;

    @FXML
    private TableColumn<lifeCycleOfTransaction,Float> col_minsMoney;

    @FXML
    private TableColumn<lifeCycleOfTransaction,Float> col_finalPriceAfterTransaction;
    
    @FXML
    private Label lbl_PayOrGet;
    
    @FXML
    private Label lbl_deal;
    lifeCycleOfTransaction [] allTranscation = {};
    static int receiptNumber = 0;
    static int reciptNumberBuy  = 0 ;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
         String [] allAccountOfPLayer = dataBase.retrivingColumnFromDataBase("SELECT name from son  where father_id = 6 or father_id = 5 ");
         TextFields.bindAutoCompletion(txt_playerName, allAccountOfPLayer);
         
         
        col_addMoney.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,Float>("addMoney"));
        col_minsMoney.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,Float>("minsMoney"));
        col_transcationType.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,Integer>("transactionType"));
        
        col_trancationDate.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,String>("transactionDate"));
        col_transactionDetails.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,String>("transactionDetails"));
        col_transactionNumber.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,Integer>("transactionNumber"));
        col_finalPriceAfterTransaction.setCellValueFactory(new PropertyValueFactory<lifeCycleOfTransaction,Float>("finalPriceAfterTrancation"));

         datePickerStart.setValue(LocalDate.now().minus(Period.ofYears(1)));
         datePickerEnd.setValue(LocalDate.now());
         
         txt_playerName.setOnKeyPressed((event)->{
             if(event.getCode().toString().equals("ENTER")){
                 getTransaction();
             }
             
         });
         

    }
    
    
    public void getTransaction(){
        
        tableOfAllTransaction.getItems().clear();
        try{
        String playerName = txt_playerName.getText();
        String playerId = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+playerName+"'");
        String father_id = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT father_id FROM son WHERE name = '"+playerName+"'");
        
    
        LocalDate start = datePickerStart.getValue();
        LocalDate end = datePickerEnd.getValue();
        String sql = "SELECT transactionNumber , typeOfTransaction , dateOfTransaction ,detailsOfTransaction,  addMoney , minsMoney  from lifeofcustomer WHERE customerAccountId = '"+playerId+" 'and (dateOfTransaction <=  '"+end+"' and dateOfTransaction >= '"+start+"')";
            System.out.println(sql);
        Object [] table = dataBase.retrievingTableInArrayOfObject(sql);
           allTranscation  = new lifeCycleOfTransaction[table.length];
            System.out.println(allTranscation.length);
            for (int i = 0; i < table.length; i++) {
                 
                String [] row =(String[]) table[i];
                int transactionNumber = Integer.parseInt(row[0]);
                int transactionType = Integer.parseInt(row[1]);
                String date = row[2];
                String details = row[3];
                float addMony = Float.parseFloat(row[4]);
                float minsMoney = Float.parseFloat(row[5]);
                if(i == 0){
                float priceAftertransaction = addMony-minsMoney ;
                allTranscation[i] = new lifeCycleOfTransaction(transactionNumber, transactionType, date, details, addMony,minsMoney, priceAftertransaction);
                
                }else{
                float priceAftertranscation = allTranscation[i-1].getFinalPriceAfterTrancation()+addMony-minsMoney;
                allTranscation[i] = new lifeCycleOfTransaction(transactionNumber, transactionType, date, details, addMony, minsMoney, priceAftertranscation);
                }
                tableOfAllTransaction.getItems().add(allTranscation[i]);
                
            }
     
            totalOfLifeCycle(Integer.parseInt(father_id));
   
        }
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
            System.out.println(ex.getMessage());
        
        }
        
    }
    
    
    public void totalOfLifeCycle(int supplierOrCustomer){
    float totalOut = 0 ;
    float totalIn  = 0 ;
        for (int i = 0; i < allTranscation.length; i++) {
            
             totalOut = totalOut + allTranscation[i].getAddMoney();
             totalIn  = totalIn  + allTranscation[i].getMinsMoney();
        }
        lbl_totalOut.setText(totalOut+"");
        lbl_totalGet.setText(totalIn+"");
        float remaining = totalOut - totalIn ;
        lbl_totalReminder.setText(remaining+"");
        if(totalIn>totalOut){
            supplierOrCustomer = 5 ;
        }
        float ratio = (totalIn/totalOut)*100;
        if(supplierOrCustomer == 5){
            System.out.println("yellow");
        ratio = (totalOut/totalIn)*100 ;
        }
        lbl_Ratio.setText(Math.round(ratio)+"");
              if(supplierOrCustomer == 5){
            col_addMoney.setText("مدين");
            col_minsMoney.setText("دائن");
                   lbl_deal.setText("تم الدفع   :");
                  lbl_PayOrGet.setText("اجمالي الدين:");
            
        }else{
            col_addMoney.setText("دائن");
            col_minsMoney.setText("مدين");
            lbl_PayOrGet.setText("اجمالي التحصيل :");
            lbl_deal.setText("اجمالي التعامل :");
        }
        
    }
    public void allCustomerMoney(){
        tableOfAllTransaction.getItems().clear();
        try{
        String sql = "SELECT son.name ,son.father_id,customerAccountId ,sum(addMoney) ,SUM(minsMoney) FROM lifeofcustomer join son WHERE son.Id = lifeofcustomer.customerAccountId and son.father_id =6 GROUP by customerAccountId ";
        Object []table = dataBase.retrievingTableInArrayOfObject(sql);
            System.out.println(table.length+"asdasdas");
            for (int i = 0; i < table.length; i++) {
                String [] row =(String[]) table[i];
                int transactionNumber = 0;
                int transactionType = 0;
                String date = "0000";
                String details = row[0];
                float addMony = Float.parseFloat(row[3]);
                float minsMoney = Float.parseFloat(row[4]);
                
                float priceAftertransaction = addMony-minsMoney ;
                lifeCycleOfTransaction one = new lifeCycleOfTransaction(transactionNumber, transactionType, date, details, addMony,minsMoney, priceAftertransaction);
                tableOfAllTransaction.getItems().add(one);
               
            }
        }
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
    }
    public void allSupplierMoney(){
        tableOfAllTransaction.getItems().clear();
        try{
        String sql = "SELECT son.name ,son.father_id,customerAccountId ,sum(addMoney) ,SUM(minsMoney) FROM lifeofcustomer join son WHERE son.Id = lifeofcustomer.customerAccountId and son.father_id = 5 GROUP by customerAccountId ";
        Object []table = dataBase.retrievingTableInArrayOfObject(sql);
            System.out.println(table.length+"asdasdas");
            for (int i = 0; i < table.length; i++) {
                String [] row =(String[]) table[i];
                int transactionNumber = 0;
                int transactionType = 0;
                String date = "0000";
                String details = row[0];
                float minsMoney = Float.parseFloat(row[3]);
                float addMony = Float.parseFloat(row[4]);
                
                float priceAftertransaction = minsMoney-addMony ;
                lifeCycleOfTransaction one = new lifeCycleOfTransaction(transactionNumber, transactionType, date, details, addMony,minsMoney, priceAftertransaction);
                tableOfAllTransaction.getItems().add(one);
               
            }
        }
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
    }
   public void showPerviousReceipt(){
   
       
       int transaction = tableOfAllTransaction.getSelectionModel().getSelectedItem().getTransactionNumber();
       System.out.println("ttransaction "+transaction);
       try{
           if(tableOfAllTransaction.getSelectionModel().getSelectedItem().getTransactionType()!= 5){
            receiptNumber = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT receiptNumber from customerreceiptmaininfo WHERE transactionNumber = '"+transaction+"'"));
            openRequestWindow(tableOfAllTransaction.getSelectionModel().getSelectedItem().getTransactionType());

           System.out.println(" rece" + receiptNumber);
           }
           else{
            System.out.println("we are here"); 
           ShowPreveriosBuyReceipt();
           }
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
    
    public void ShowPreveriosBuyReceipt(){
       int transaction = tableOfAllTransaction.getSelectionModel().getSelectedItem().getTransactionNumber();
       System.out.println("ttransaction "+transaction);
       try{
           String sql = "SELECT receiptNumber FROM cafebuymaininfo WHERE transactionNumber = "+transaction;
           System.out.println(sql);
           reciptNumberBuy = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction(sql));
           System.out.println(" rece" + reciptNumberBuy);
       openRequestWindow(tableOfAllTransaction.getSelectionModel().getSelectedItem().getTransactionType());
       }
       catch(SQLException ex){
       
       dataBase.showMessageJOP(ex.getMessage());
               
       }
        
    }
    
    
   
     
}
