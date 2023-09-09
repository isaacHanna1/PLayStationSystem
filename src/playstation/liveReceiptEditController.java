/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

/**
 *
 * @author Seha
 */
// يا اسحاق خد بالك انت كنت عامل كوبي من كلاس اللي اسمه 
// liveReceiptEditController
//فممكن تلاقي ان بعد method متعادة ومش هنستخدمها 
import classes.buyReceipt;
import classes.cutomerReceipt;
import classes.player;
import classes.safe;
import com.jtattoo.plaf.BaseIcons;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;
import static playstation.liveReceiptController.itemInTable;

public class liveReceiptEditController implements Initializable , EventHandler<KeyEvent>{

  @FXML
    private TextField txt_itemCode;

    @FXML
    private TextField txt_receiptQuantity;

    @FXML
    private TableView<cutomerReceipt> table_cafeBuy;

    @FXML
    private TableColumn<cutomerReceipt, Long> col_codeItem;

    @FXML
    private TableColumn<cutomerReceipt, String> col_codeName;

    @FXML
    private TableColumn<cutomerReceipt, Float> col_codeQuantity;

    @FXML
    private TableColumn<cutomerReceipt,Float> colPayPrice;

    @FXML
    private TableColumn<cutomerReceipt, Float> col_total;

    @FXML
    private TableColumn<cutomerReceipt, Integer> col_repository;

    @FXML
    private TextField txt_receiptPay;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txt_total;

    @FXML
    private TextField txt_receiptNumber;

    @FXML
    private RadioButton radioName;

    @FXML
    private ComboBox<String> combo_repositorty;

    @FXML
    private TextField txt_payed;

    @FXML
    private TextField txt_reminder;

    @FXML
    private Label lbl_itemName;
        @FXML
    private Label lbl_userId;
    @FXML
    private TextField txt_playerName;
    
    String [] allItem ;
    String [] allPlayer ; 
    int selecetedIndex = -1 ;
    
    String transacation = "";
        @FXML
    private BorderPane container;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            col_codeItem.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Long>("code"));
            col_codeName.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,String>("itemName"));
            col_codeQuantity.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("quantity"));
            colPayPrice.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("payPrice"));
            col_total.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("total"));            
            col_repository.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Integer>("repository_id"));            
            allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "itemCode", "id");
            TextFields.bindAutoCompletion(txt_itemCode  , allItem);
            allPlayer = dataBase.retrivingColumnFromDataBase("SELECT name from son  where father_id = 6 or father_id = 5 ");
            TextFields.bindAutoCompletion(txt_playerName  , allPlayer);
            dataBase.putingColumnFromDataBaseIntoCombox("SELECT repositoryName from repository ORDER BY id", combo_repositorty);
            txt_receiptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
            combo_repositorty.getSelectionModel().select(0);
            txt_itemCode.setOnKeyPressed(this);
            txt_receiptQuantity.setOnKeyPressed(this);
            txt_receiptPay.setOnKeyPressed(this);
            txt_payed.setOnKeyPressed(this);
            txt_total.setOnKeyPressed(this);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(sdf.format(new Date()), formatter);

            datePicker.setValue(localDate);
        
            showReceipt(oldMoneyController.receiptNumber);
            puttingOldData();
            calcTotalOfOrder();
     
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }

    }
     public void addITemToTable(){
   
        long codeITem = -1 ;
        try {
            if(radioName.isSelected()){
            codeITem = Long.parseLong(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT itemCode from cafeitem WHERE item_name = '"+txt_itemCode.getText()+"'"));
           
            }
            else{
                
             codeITem = Long.parseLong(txt_itemCode.getText());
             
            }
           String itemName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT item_name from cafeitem WHERE itemCode = "+codeITem); 
            float quantity = Float.parseFloat(txt_receiptQuantity.getText());
            float pay = Float.parseFloat(txt_receiptPay.getText());
            float totoal = quantity * pay;
            cutomerReceipt newOne = new cutomerReceipt(0,codeITem, itemName, quantity, pay,totoal,(combo_repositorty.getSelectionModel().getSelectedIndex()+1),"yes");
            table_cafeBuy.getItems().add(newOne);
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
        catch(Exception ex){
            if(ex.getMessage().contains(" input string")){
                dataBase.showMessageJOP("تاكد من ادخال الكمية");
            }
        }
        float total = 0;
        for (int i = 0; i < table_cafeBuy.getItems().size(); i++) {
            
         total = total + table_cafeBuy.getItems().get(i).getTotal();
           txt_total.setText(total+"");
       }
        txt_receiptPay.setText("");
        txt_itemCode.requestFocus();
   
   
   }
     


     public void editITemToTable(){
   
        long codeITem = -1 ;
        try {
            if(radioName.isSelected()){
            codeITem = Long.parseLong(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT itemCode from cafeitem WHERE item_name = '"+txt_itemCode.getText()+"'"));
           
            }
            else{
                
             codeITem = Long.parseLong(txt_itemCode.getText());
             
            }
            String itemName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT item_name from cafeitem WHERE itemCode = "+codeITem); 
            float quantity  = Float.parseFloat(txt_receiptQuantity.getText());
            float pay       = Float.parseFloat(txt_receiptPay.getText());
            float totoal    = quantity * pay;
            
            cutomerReceipt selectedItem = table_cafeBuy.getSelectionModel().getSelectedItem();
            selectedItem.setCode(codeITem);
            selectedItem.setPayPrice(pay);
            selectedItem.setQuantity(quantity);
            selectedItem.setTotal(totoal);
            deleteItem();
            cutomerReceipt newOne = new cutomerReceipt(0,codeITem, itemName, quantity, pay,totoal,(combo_repositorty.getSelectionModel().getSelectedIndex()+1),"yes");
            table_cafeBuy.getItems().add(newOne);
            
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
        catch(Exception ex){
            if(ex.getMessage().contains(" input string")){
                dataBase.showMessageJOP("تاكد من ادخال الكمية");
            }
        }
        float total = 0;
        for (int i = 0; i < table_cafeBuy.getItems().size(); i++) {
            
         total = total + table_cafeBuy.getItems().get(i).getTotal();
           txt_total.setText(total+"");
       }
        txt_receiptPay.setText("");
        txt_itemCode.requestFocus();
   
   
   }
     
   public void radioNameSelected(){
       
       if(radioName.isSelected()){
      allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "item_name", "id");
      TextFields.bindAutoCompletion(txt_itemCode  , allItem);
       }
       else{
       
      allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "itemCode", "id");
      TextFields.bindAutoCompletion(txt_itemCode  , allItem);
       }
       
}
   
   public void  saveMainData(){
   
       
        try{
        String  receieptNumber = txt_receiptNumber.getText();
        int userID   = loginController.loginUser.getID() ;       
      
        LocalDate localStartDate = datePicker.getValue();
        java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);
         transacation = dataBase.autoTranscationForLiveReceipt("customerreceiptmaininfo", "transactionNumber");
        int transactionNumber = Integer.parseInt((transacation)) ;         
        int player_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+txt_playerName.getText()+"'")); 
        playerController.currentPlayer = new player(player_id, txt_playerName.getText(), "0", 0);      
        String result = dataBase.saveMainCustomerCafeReceipt(Integer.parseInt(receieptNumber), transactionNumber,userID, sqlDate,player_id);
       //dataBase.enterAndOutMoney(transactionNumber,Float.parseFloat(txt_payed.getText()),safe.id, loginController.loginUser.getID(),sqlDate,2,"مشتريات من مخزون مباشر");
        dataBase.customerLife(transactionNumber, 1, Float.parseFloat(txt_total.getText()), 0, txt_playerName.getText()+"شراء من مخزن الي (تعديل)",player_id,sqlDate);
            System.out.println("edit data done in customer life cycle");
        }catch(SQLException ex){ 
             dataBase.showMessageJOP(ex.getMessage());
        }
        
        
        
   
   }
   public void openNewWindow(String name , String title){
    Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(name+".fxml"));
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
  
    
    }
   
   public void saveDetailedData(){
   
        ObservableList<cutomerReceipt> items = table_cafeBuy.getItems();
        
        for (int i = 0; i < items.size(); i++) {
            
            try{
            int receiptNumber = Integer.parseInt(txt_receiptNumber.getText());
            long itemCode = items.get(i).getCode();
            float quantity = items.get(i).getQuantity();
            float buyPrice = items.get(i).getBuyPrice();
            float payPrice = items.get(i).getPayPrice();
            int repository_id = (combo_repositorty.getSelectionModel().getSelectedIndex())+1;
            dataBase.saveDetailedDataOFCustomerReceipt(receiptNumber, itemCode, quantity, payPrice, repository_id);

            } catch(Exception ex ){
                System.out.println(ex.getMessage());
            }
           
           
       }
        

   }
   
   public void saveButton(){
   
       if(table_cafeBuy.getItems().size()<=0){
           dataBase.showMessageJOP("لا توجد بيانات لحفظها");
           return;
       
       }
       
            saveMainData();
            System.out.println("save main data successeded");
            saveDetailedData();
            System.out.println("save details suscesseded");
            //txt_receiptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
            //table_cafeBuy.getItems().clear();
       
   }
   
   public void deleteItem(){
    
    
       table_cafeBuy.getItems().remove(selecetedIndex);
       calcTotalOfOrder();
       
   }
   public void calcTotalOfOrder(){
   
       float total = 0 ;
       for (int i = 0; i < table_cafeBuy.getItems().size() ; i++) {
            total = total + table_cafeBuy.getItems().get(i).getTotal();
       }
       txt_total.setText(total+"");
   }
   
   
   public void  getBuyPayPrice(){
       
        try {
            String []row = dataBase.retrievingRowIntableInArray("Select  itemPrice , item_name from cafeitem where itemCode = '"+txt_itemCode.getText()+ "' || item_name = '"+txt_itemCode.getText()+"'");
            txt_receiptPay.setText(row[0]);
            lbl_itemName.setText(row[1]);
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }
   
   }
   
   public void checkReminder(){
   
      float total = Float.parseFloat(txt_total.getText() );
      float payed = Float.parseFloat(txt_payed.getText());
      float reminder = payed - total ;
      txt_reminder.setText(reminder+"");
      
   }


    @Override
    public void handle(KeyEvent event) {
     
        if(event.getCode().toString()=="ENTER"){
            getBuyPayPrice();
            txt_receiptQuantity.requestFocus();
            
        }
        if(event.getCode().toString() == "DOWN"){
        addITemToTable();
        }   
            if(event.getCode().toString() == "RIGHT"){
                editITemToTable();
                
        }   
         
        
        if(event.getCode().toString()=="F5"){
            if(radioName.isSelected()){
            radioName.setSelected(false);
            }
            else{
            radioName.setSelected(true);
            radioNameSelected();
            }
            
        }        
     if(event.getCode().toString().equals("F1")) {
         txt_payed.requestFocus();
     }
     if(event.getCode().toString().equals("F12")) {
         checkReminder();
     }
     
    }
    
    public void showReceipt(int receiptNumber ){
    
        try{
        Object[] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM `customerreceiptdetailedinfo` WHERE receiptNumber = "+receiptNumber);
        
            for (int i = 0; i < table.length; i++) {
                String [] row =(String[]) table[i];
                
            
            long itemCode = Long.parseLong(row[2]);
            float quantity = Float.parseFloat(row[3]);
            float payPrice = Float.parseFloat(row[4]);
            int repository_id = Integer.parseInt(row[5]);
            String itemName  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT item_name from cafeitem WHERE itemCode = '"+itemCode+"'");
            cutomerReceipt oldOne = new cutomerReceipt(0,itemCode, itemName, quantity, payPrice, quantity, repository_id, "no");
            table_cafeBuy.getItems().add(oldOne);
            }
        }catch(SQLException ex){
        
        
        }
    }
    public void puttingOldData(){
        
        try{
        String []data = dataBase.retrievingRowIntableInArray("SELECT user_id , date , player_id FROM `customerreceiptmaininfo` WHERE receiptNumber = '"+oldMoneyController.receiptNumber+"'");
        String userName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT userName FROM users WHERE id = "+data[0]);
        lbl_userId.setText(userName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        datePicker.setValue(LocalDate.parse(data[1],formatter));
        //put date
        String playerName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT name from son WHERE Id = '"+data[2]+"'");
        txt_playerName.setText(playerName);
        txt_receiptNumber.setText(oldMoneyController.receiptNumber+"");
        String repostory = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT repository.repositoryName from repository JOIN customerreceiptdetailedinfo WHERE repository.id = customerreceiptdetailedinfo.repositiry_id and receiptNumber = '"+oldMoneyController.receiptNumber+"'");
        combo_repositorty.getSelectionModel().select(repostory);
        
        }
        catch(SQLException ex){
        
        dataBase.showMessageJOP(ex.getMessage());
        }
    
    }
    
    
    public void selectAndShow(){
      
    txt_itemCode.setText("");
    cutomerReceipt selectedItem = table_cafeBuy.getSelectionModel().getSelectedItem();
    selecetedIndex = table_cafeBuy.getSelectionModel().getSelectedIndex();
    txt_itemCode.setText(selectedItem.getCode()+"");
    txt_receiptQuantity.setText(selectedItem.getQuantity()+"");
    txt_receiptPay.setText(selectedItem.getPayPrice()+"");
    txt_receiptQuantity.requestFocus();
      
    
    
    }
    
    public void editReceipt(){
    
        try{
         transacation = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from customerreceiptmaininfo WHERE receiptNumber = '"+txt_receiptNumber.getText()+"'");
        String receiptNumber = txt_receiptNumber.getText();
        dataBase.excuteNonQuery("DELETE from customerreceiptmaininfo WHERE receiptNumber = '"+receiptNumber+"'");
           System.out.println("done");
        dataBase.excuteNonQuery("DELETE from customerreceiptdetailedinfo WHERE receiptNumber = '"+receiptNumber+"'");
            System.out.println("done");
        dataBase.excuteNonQuery("DELETE FROM lifeofcustomer WHERE transactionNumber  = "+transacation);
            System.out.println("done");
        
            saveButton();
        dataBase.showMessageJOP("اذا قمت بتعديل الفاتورة وتغيرت الاسعار لابد من تغير مقدار الاموال في الخزينة ايضا ");
        }
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
        catch(Exception ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
    }
    public void deleteReceipt(){
        
        int option = JOptionPane.showConfirmDialog(null, "هل تريد حقا مسح الفاتورة من حساب العميل");
        if(option == 0){
        try{
         transacation = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from customerreceiptmaininfo WHERE receiptNumber = "+txt_receiptNumber.getText());
        dataBase.excuteNonQuery("DELETE FROM lifeofcustomer WHERE transactionNumber = "+transacation);
        dataBase.excuteNonQuery("DELETE from  customerreceiptmaininfo WHERE receiptNumber = "+txt_receiptNumber.getText());
        dataBase.excuteNonQuery("DELETE from customerreceiptdetailedinfo WHERE receiptNumber = "+txt_receiptNumber.getText());
         container.getScene().getWindow().hide();
        }
        catch(SQLException ex){
        
            
            dataBase.showMessageJOP(ex.getMessage());
        }
        }
    
    }
    public void PrintReport(){
       table_cafeBuy.getItems().clear();
       itemInTable = table_cafeBuy.getItems();
        try {
            JasperDesign jd = JRXmlLoader.load("bill.jrxml");
            String sql = "SELECT cafeitem.item_name , customerreceiptdetailedinfo.quantity , customerreceiptdetailedinfo.payPrice ,customerreceiptdetailedinfo.quantity * customerreceiptdetailedinfo.payPrice from cafeitem JOIN customerreceiptdetailedinfo WHERE cafeitem.itemCode = customerreceiptdetailedinfo.itemCode and customerreceiptdetailedinfo.receiptNumber = '"+txt_receiptNumber.getText()+"'";
            System.out.println(sql);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            HashMap <String,Object> para = new HashMap<>();
            para.put("receiptNumber", txt_receiptNumber.getText());
            para.put("receiptTotal", txt_total.getText());
            para.put("payed", txt_payed.getText());
            para.put("reminder", txt_reminder.getText());
            para.put("customerName", txt_playerName.getText());
            para.put("downPayment", 0);
            para.put("mustPayed", txt_total.getText());
            para.put("loginUser",loginController.loginUser.getUserName());
            String []shopData = dataBase.retrievingRowIntableInArray("SELECT address , tel FROM companydata ;");
            para.put("address", shopData[0]);
            para.put("contact", shopData[1]);
            System.out.println("the name is "+txt_playerName.getText());
            System.out.println(homeController.companyName);
            para.put("companyName", homeController.companyName);
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
        } catch (JRException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(liveReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   }
   
}

    

