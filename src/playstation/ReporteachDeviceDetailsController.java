/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.cutomerReceipt;
import classes.summaryReceipt;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class ReporteachDeviceDetailsController implements Initializable {

    @FXML
    private TableView<cutomerReceipt> tabel_receipt;
    @FXML
    private TableColumn<cutomerReceipt,Long> col_codeItem;
    @FXML
    private TableColumn<cutomerReceipt, String> col_itemName;
    @FXML
    private TableColumn<cutomerReceipt, Float> col_itemQuantity;
    @FXML
    private TableColumn<cutomerReceipt,Float> col_payPrice;
    @FXML
    private TableColumn<cutomerReceipt,Float> col_totalOrder;
    @FXML
    private TableColumn<cutomerReceipt,Integer> col_repository;
    @FXML
    private TableView<summaryReceipt> table_transaction;
    @FXML
    private TableColumn<summaryReceipt, Integer> col_serial;
    @FXML
    private TableColumn<summaryReceipt,Integer> col_transactionNumber;
    @FXML
    private TableColumn<summaryReceipt, String> col_startTime;
    @FXML
    private TableColumn<summaryReceipt, String> col_endTime;
    @FXML
    private TableColumn<summaryReceipt, String> col_single_multi;
    @FXML
    private TableColumn<summaryReceipt, Float> col_hour_price;
    @FXML
    private TableColumn<summaryReceipt, Float> col_totalHours;
    @FXML
    private TableColumn<summaryReceipt, Float> col_total_mintues;
    @FXML
    private TableColumn<summaryReceipt, Float> col_devicePrice;
    @FXML
    private TableColumn<summaryReceipt, Float> col_orderPrice;
    @FXML
    private TableColumn<summaryReceipt, Float> col_total;    
    @FXML
    private TableColumn<summaryReceipt, Float> col_payed;
    @FXML
    private TableColumn<summaryReceipt, String> col_room;
    @FXML
    private TableColumn<summaryReceipt, String> col_playerName;
    @FXML
    private ComboBox<String> comBo_DeviceName;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
      @FXML
    private Label lbl_totalHour;

    @FXML
    private Label lbl_totalOfDevice;

    @FXML
    private Label lbl_total;

    @FXML
    private Label lbl_repository;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //order table
             col_codeItem.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Long>("code"));
            col_itemName.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,String>("itemName"));
            col_itemQuantity.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("quantity"));
            col_payPrice.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("payPrice"));
            col_totalOrder.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("total"));            
            col_repository.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Integer>("repository_id"));            
            
            //transaction table summary receipt
            col_serial.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Integer>("serialNumber"));            
            col_transactionNumber.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Integer>("transactionNumber"));            
            col_startTime.setCellValueFactory(new PropertyValueFactory<summaryReceipt,String>("startTime"));            
            col_endTime.setCellValueFactory(new PropertyValueFactory<summaryReceipt,String>("endTime"));            
            col_single_multi.setCellValueFactory(new PropertyValueFactory<summaryReceipt,String>("singleOrMulti"));            
            col_hour_price.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Float>("hourPrice"));            
            col_totalHours.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Float>("hourCount"));            
            col_total_mintues.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Float>("minutesCount"));            
            col_devicePrice.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Float>("devicePrice"));            
            col_orderPrice.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Float>("orderPrice"));            
            col_total.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Float>("total"));  
            col_payed.setCellValueFactory(new PropertyValueFactory<summaryReceipt,Float>("payed"));  
            col_room.setCellValueFactory(new PropertyValueFactory<summaryReceipt,String>("room"));  
            col_playerName.setCellValueFactory(new PropertyValueFactory<summaryReceipt,String>("playerName"));  
            
            //col_styling 
            
           
            col_serial.setStyle("-fx-font: 15  bold;");
            col_transactionNumber.setStyle("-fx-font: 15  bold;");
            col_startTime.setStyle("-fx-font: 15  bold;");
            col_endTime.setStyle("-fx-font: 15  bold;");
            col_single_multi.setStyle("-fx-font: 15  bold;");
            col_hour_price.setStyle("-fx-font: 15  bold;");
            col_totalHours.setStyle("-fx-font: 15  bold;");
            col_total_mintues.setStyle("-fx-font: 15  bold;");
            col_devicePrice.setStyle("-fx-font: 15  bold;");
            col_orderPrice.setStyle("-fx-font: 15  bold;");
            col_total.setStyle("-fx-font: 15  bold;");
            col_payed.setStyle("-fx-font: 15  bold;");
            col_room.setStyle("-fx-font: 15  bold;");
            col_playerName.setStyle("-fx-font: 15  bold;");
            
                     
       table_transaction.setOnKeyReleased((event) -> {
           System.out.println("hiii");
          if(event.getCode().toString()==("DOWN") || event.getCode().toString()==("UP")) {
              getOrderOfcustomer();
          }
       });
       comBo_DeviceName.valueProperty().addListener(new ChangeListener<String>() {
       
                 @Override
                 public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                     putDataOfEachTransactionIntoTable();
                 }
    });
            
            try{
            dataBase.putingColumnFromDataBaseIntoCombox("SELECT `deviceName` FROM `newdevice` WHERE 1", comBo_DeviceName);
            startDate.setValue(LocalDate.now());
            endDate.setValue(LocalDate.now());
            }
            catch(SQLException ex){
                dataBase.showMessageJOP(ex.getMessage());
            }
            
    }    
    
    /**
     *
     * @param deviceId
     * @param startDate
     * @param endDate
     * @return
     */
    public  String[] allTranscationNumberInSpecificPeriodForDevice( String deviceId , String startDate , String endDate){
    
        String sqlQuery = "SELECT transactionNumber from followingdevice where dateToday <= '"+endDate+"' and dateToday >= '"+startDate+"' and Device_id =  '"+deviceId+"'";
        System.out.println(sqlQuery);
        String[] allTransaction = dataBase.retrivingColumnFromDataBase(sqlQuery);
        
        return  allTransaction;
        }
  
    public void putDataOfEachTransactionIntoTable() {
    
    
        table_transaction.getItems().clear();
        try{
        
            String sqlGetId ="SELECT id from newdevice WHERE deviceName = '"+comBo_DeviceName.getSelectionModel().getSelectedItem()+"'";
            System.out.println(sqlGetId+" this ");
            
            String intDeviceId = dataBase.gettingOnvalueFromTableByAggregatefunction(sqlGetId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            String startDate =this.startDate.getValue().format(formatter);
            String endDate = this.endDate.getValue().format(formatter);
            summaryReceipt [] allReceiptHaveSameTransaction = new summaryReceipt[10];
            
            String sqlGetDataOfDevice = "SELECT DISTINCT followingdevice.id , followingdevice.dateToday"
                    + " , followingdevice.transactionNumber ,followingdevice.startTime,"
                    + "followingdevice.endTime, receiptinformation.multi , "
                    + "receiptinformation.hourPrice,receiptinformation.totalHour,"
                    + "receiptinformation.totalMinutes , receiptinformation.totalOfDevice"
                    + " , receiptinformation.totalOfStock , "
                    + "(receiptinformation.totalOfDevice +receiptinformation.totalOfStock)"
                    + " , receiptinformation.paied , receiptinformation.roomName , "
                    + "receiptinformation.playerName from followingdevice join receiptinformation"
                    + " WHERE followingdevice.id = receiptinformation.id and "
                    + "followingdevice.dateToday <='"+endDate+"' and followingdevice.dateToday >= '"+startDate+"' and deviceID ='"+intDeviceId+"' ";
            System.out.println(sqlGetDataOfDevice);
            Object []table = dataBase.retrievingTableInArrayOfObject(sqlGetDataOfDevice);
            for (int i = 0; i < table.length; i++) {
                String []row = (String[]) table[i];
                summaryReceipt newOne = new summaryReceipt(i+1, Integer.parseInt(row[2]), row[3], row[4], row[5],Float.parseFloat(row[6]), Float.parseFloat(row[7]), Float.parseFloat(row[8]),Float.parseFloat(row[9]), Float.parseFloat(row[10]), Float.parseFloat(row[11]), Float.parseFloat(row[12]), row[13],row[14]);
                float hourePrice = newOne.getHourPrice();
                float numberOfMintues = newOne.getMinutesCount();
                float numberOfHours = newOne.getHourCount();
                float totalPriceOfDeviceForEachTransaction = 
                        Math.round((hourePrice/60)*numberOfMintues) + hourePrice*numberOfHours;
                newOne.setDevicePrice(totalPriceOfDeviceForEachTransaction);
                float totalOfStock = newOne.getOrderPrice();
                float totalPrice = totalOfStock+totalPriceOfDeviceForEachTransaction;
                newOne.setTotal(totalPrice);
                table_transaction.getItems().add(newOne);
                
                
                
            }
            
        } catch (SQLException ex) {
                        dataBase.showMessageJOP(ex.getMessage());

        }
        
        putTotalOFDeviceIntoLbls();
            }
                
                                
             
        
    public void putTotalOFDeviceIntoLbls(){
        
        ObservableList<summaryReceipt> items = table_transaction.getItems();
        float totalOfHours = 0;
        float totalOfMIntues = 0 ;
        float totalOfdevicePrice = 0 ;
        float totalOfRepository = 0;
        for (int i = 0; i < items.size(); i++) {
            totalOfHours = totalOfHours + items.get(i).getHourCount();
            totalOfMIntues = totalOfMIntues + items.get(i).getMinutesCount();
            totalOfRepository = totalOfRepository + items.get(i).getOrderPrice();
            totalOfdevicePrice = totalOfdevicePrice + items.get(i).getDevicePrice();
        }
        
        float total = totalOfRepository+totalOfdevicePrice;
        lbl_totalOfDevice.setText(totalOfdevicePrice+"");
        lbl_repository.setText(totalOfRepository+"");
        float reminderMintues = totalOfMIntues%60;
        totalOfHours = totalOfHours + (int)(totalOfMIntues/60);
        lbl_totalHour.setText("عدد الساعات "+" "+totalOfHours+" و  "+reminderMintues+" "+"دقيقة");
        lbl_total.setText(total+"");
        
        
    
    }
    
    public void getOrderOfcustomer() {
        
        tabel_receipt.getItems().clear();
        System.out.println("im called");
        summaryReceipt selectedRow = table_transaction.getSelectionModel().getSelectedItem();
        
        try{
            
                String getReceiptNUmber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT receiptNumber FROM customerreceiptmaininfo WHERE transactionNumber = '"+selectedRow.getTransactionNumber()+"'");
                System.out.println("receipt number :"+ getReceiptNUmber);
                if(getReceiptNUmber.equals("")){
                    return;
                }
                String getReceiptDataSql ="SELECT customerreceiptdetailedinfo.itemCode ,cafeitem.item_name , quantity , payPrice , (quantity*payPrice) , repositiry_id FROM customerreceiptdetailedinfo JOIN cafeitem where customerreceiptdetailedinfo.itemCode = cafeitem.itemCode and receiptNumber = "+getReceiptNUmber;
                
                Object[] table = dataBase.retrievingTableInArrayOfObject(getReceiptDataSql);
                for (int i = 0; i < table.length; i++) {
                        String []row = (String[]) table[i];
                        long code = Long.parseLong(row[0]);
                        String itemName = row[1];
                        float itemPay  = Float.parseFloat(row[2]);
                        float quantity = Float.parseFloat(row[3]);
                        float total    = Float.parseFloat(row[4]);
                        int repository = Integer.parseInt(row[5]);
                        cutomerReceipt newOne = new cutomerReceipt(0,code, itemName, quantity, itemPay, total, repository, "yes");
                        tabel_receipt.getItems().add(newOne);
                        
            }
        
        }
        catch(SQLException ex){
            
            dataBase.showMessageJOP(ex.getMessage());
        }
                
          }

}
