package playstation;

import classes.lifeCycleOfTransaction;
import classes.receiptProfit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import static playstation.oldMoneyController.receiptNumber;
public class proftOfReceiptsController implements Initializable {

    
    @FXML
    private BorderPane container;

    @FXML
    private Label lbl_totalCost;

    @FXML
    private DatePicker datePickerStart;

    @FXML
    private DatePicker datePickerEnd;

    
    @FXML
    private TableView<receiptProfit> tableOfAllReceipt;
    @FXML
    private TableColumn<receiptProfit, String> col_trancationDate;

    @FXML
    private TableColumn<receiptProfit,String> col_transactionDetails;

    @FXML
    private TableColumn<receiptProfit,Float> col_cost;

    @FXML
    private TableColumn<receiptProfit, Float> col_totalReceipt;
    @FXML
    private TableColumn<receiptProfit, Float> col_finalProft;

    @FXML
    private TableColumn<receiptProfit, Integer> col_transactionNumber;

    @FXML
    private Label lbl_totalProft;
    
    
    @FXML
    private Label lbl_allReceipt;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
         datePickerEnd.setValue(LocalDate.now());
         datePickerStart.setValue(LocalDate.now());
        col_cost.setCellValueFactory(new PropertyValueFactory<receiptProfit,Float>("receiptCost"));
        col_finalProft.setCellValueFactory(new PropertyValueFactory<receiptProfit,Float>("receiptProft"));
        col_trancationDate.setCellValueFactory(new PropertyValueFactory<receiptProfit,String>("receiptDate"));
        col_transactionDetails.setCellValueFactory(new PropertyValueFactory<receiptProfit,String>("receiptDetails"));
        col_transactionNumber.setCellValueFactory(new PropertyValueFactory<receiptProfit,Integer>("receiptTransaction"));
            col_totalReceipt.setCellValueFactory(new PropertyValueFactory<receiptProfit,Float>("receiptPrice"));
    }
    
    public void getReceiptInformtion (){
        tableOfAllReceipt.getItems().clear();
        LocalDate start = datePickerStart.getValue();
        LocalDate end = datePickerEnd.getValue();
        
        String sql = "SELECT lifeofcustomer.transactionNumber , customerreceiptmaininfo.receiptNumber  , dateOfTransaction ,detailsOfTransaction,  addMoney   from lifeofcustomer JOIN customerreceiptmaininfo  WHERE  `typeOfTransaction` = 1 and (dateOfTransaction <=  '"+end+"' and dateOfTransaction >= '"+start+"') and lifeofcustomer.transactionNumber = customerreceiptmaininfo.transactionNumber";
        try{
          Object [] table = dataBase.retrievingTableInArrayOfObject(sql);
          
           for (int i = 0; i < table.length; i++) {
                 
                String [] row =(String[]) table[i];
                 String receiptTransactionNumber = row[0];
                String receiptNumber = row[1];
                String receiptDate = row[2];
                String receiptDetails = row[3];
                String receiptTotal = row[4];
                String sqlReceiptCost = "SELECT sum(cafeitem.itemBuy*customerreceiptdetailedinfo.quantity )from customerreceiptdetailedinfo JOIN cafeitem WHERE receiptNumber = "+receiptNumber+" and cafeitem.itemCode = customerreceiptdetailedinfo.itemCode ";
                String receiptCost = dataBase.gettingOnvalueFromTableByAggregatefunction(sqlReceiptCost);
                receiptProfit anotherOne = new receiptProfit(receiptDate, receiptDetails, Float.parseFloat(receiptTotal), Float.parseFloat(receiptCost), (Float.parseFloat(receiptTotal)-Float.parseFloat(receiptCost)), Integer.parseInt(receiptTransactionNumber));
                System.out.println(receiptTotal);
                tableOfAllReceipt.getItems().add(anotherOne);
                
                
           }
           float sumOfTotalReceipt = 0 ;
           float sumOfTotalCost = 0 ;
           float sumOfprofit = 0 ;
            for (int i = 0; i < tableOfAllReceipt.getItems().size(); i++) {
                sumOfTotalCost = sumOfTotalCost + tableOfAllReceipt.getItems().get(i).getReceiptCost();
                sumOfprofit = sumOfprofit +tableOfAllReceipt.getItems().get(i).getReceiptProft();
                sumOfTotalReceipt =  sumOfTotalReceipt + tableOfAllReceipt.getItems().get(i).getReceiptPrice();
            }
            lbl_totalCost.setText(sumOfTotalCost+"");
            lbl_totalProft.setText(sumOfprofit+"");
            lbl_allReceipt.setText(sumOfTotalReceipt+"");
        }
        catch(SQLException ex){
        
            dataBase.showMessageJOP(sql);
        }
    
    }
    public void showPerviousReceipt(){
   
       
       int transaction = tableOfAllReceipt.getSelectionModel().getSelectedItem().getReceiptTransaction();
       System.out.println("ttransaction "+transaction);
       try{
        receiptNumber = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT receiptNumber from customerreceiptmaininfo WHERE transactionNumber = '"+transaction+"'"));
           System.out.println(" rece" + receiptNumber);
       openRequestWindow(1);
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
        }
           
    }
}
