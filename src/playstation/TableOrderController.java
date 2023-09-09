/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.CafeSection;
import classes.cafeItem;
import classes.cutomerReceipt;
import classes.player;
import classes.safe;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class TableOrderController implements Initializable {

    @FXML
    private Pane paneOrder;
    @FXML
    private TableView<cutomerReceipt> tabel_receipt;
     @FXML
    private TableColumn<cutomerReceipt, Integer> col_id;

    @FXML
    private TableColumn<cutomerReceipt, Integer> col_codeItem;

    @FXML
    private TableColumn<cutomerReceipt, String> col_itemName;

    @FXML
    private TableColumn<cutomerReceipt, Float> col_itemQuantity;

    @FXML
    private TableColumn<cutomerReceipt, Float> col_payPrice;

    @FXML
    private TableColumn<cutomerReceipt, Float> col_total;
    @FXML
    private TableColumn<cutomerReceipt,Integer > col_repository;
     @FXML
    private TableColumn<cutomerReceipt, String> col_saved;

    @FXML
    private TextField txt_payed;

    @FXML
    private TextField txt_reminder;
    @FXML
    private TextField txt_codeItem;
    @FXML
    private RadioButton radioName;
    @FXML
    private Label lbl_total;
    @FXML
    private ComboBox<String> combo_repositorty;
    @FXML
    private TextField txt_quntity;
    @FXML
    private Label lbl_reciptNumber;
    @FXML
    private Button btnSaveAndChangeState;
    @FXML
    private TextField txt_customer_name;
    @FXML
    private TextField txt_downPayment;
    @FXML
    private Pane paymentPane;
    @FXML
    private TextField txt_mustPayed;
      @FXML
    private FlowPane itemPane;
    @FXML
    private VBox sectionVB;
    static  String tableName ;
    String[] allItem={};
    String [] all_customer_name ; //all customers
    int numberOfItemInReceiptTable = 0 ; // دة انا عاملة علشان اسجل العناصر الجديدة بس في جدول الفاتورة

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             paymentPane.setVisible(false);
             txt_customer_name.requestFocus();
             all_customer_name = dataBase.retrivingColumnFromDataBase("SELECT name from son  where father_id = 6 or father_id = 5 ");
             //paneOrder.setVisible(false);
            TextFields.bindAutoCompletion(txt_customer_name  , all_customer_name);   
            txt_payed.setOnKeyReleased((event) -> {
                if(txt_payed.getText().equals("")){
                    return;
                }
            float payed =  Float.parseFloat(txt_payed.getText());
            float mustPayed = Float.parseFloat(txt_mustPayed.getText());
            float reminder = payed-mustPayed;
            txt_reminder.setText(reminder+"");
        });
            txt_customer_name.setDisable(true);
            txt_customer_name.setText(tableName);
//            txt_customer_name.setOnKeyReleased((event)->{
//                boolean showAlert = false;
//                if(event.getCode().toString().equals("ENTER")){
//                    for (int i = 0; i < all_customer_name.length; i++) {
//                    if(txt_customer_name.getText().equals(all_customer_name[i])){
//                        paneOrder.setVisible(true);
//                        txt_codeItem.requestFocus();
//                        showAlert = true;
//                        break;
//                        
//                    }
//                  
//                }
//                    try{
//                        //انا عامل متغير فيه قيمة فولص علشان اعرف الاسم دة موجود قبل كدة ولا لا 
//                        // علشان لو مش موجود اسجله انا في قواعد البيانات
//                        if(!showAlert){
//                        TextInputDialog alert = new TextInputDialog("رقم تليفون العميل");
//                        alert.setTitle("اضافة عميل جديد");
//                        alert.setHeaderText("تنبيه سوف تقوم بادخل عميل جديد  باسم "+" "+txt_customer_name.getText());
//                        
//                        Optional<String> result = alert.showAndWait();
//                        if (result.isPresent()) {
//                        int fatherAccount_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id FROM father WHERE Name = '"+"العملاء"+"'"));
//                        //اسجله في جدول player
//                        dataBase.addNewPlayer(txt_customer_name.getText(),result.get(),0);
//                        //اسجله في جدول الابناء باب عميل
//                        dataBase.addSonAccout(fatherAccount_id, txt_customer_name.getText());
//                        paneOrder.setVisible(true);
//                        txt_codeItem.requestFocus();
//                    }
//                        }
//                    }catch(SQLException ex){
//                        dataBase.showMessageJOP(ex.getMessage());
//                    }
//                }
//                
//            
//            });
        try{
             lbl_reciptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
             allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "itemCode", "id");
            TextFields.bindAutoCompletion(txt_codeItem  , allItem);
            dataBase.putingColumnFromDataBaseIntoCombox("SELECT repositoryName from repository ORDER BY id", combo_repositorty);
            combo_repositorty.getSelectionModel().select(0);
            col_id.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Integer>("id"));
            col_codeItem.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Integer>("code"));
            col_itemName.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,String>("itemName"));
            col_itemQuantity.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("quantity"));
            col_payPrice.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("payPrice"));
            col_total.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("total"));            
            col_repository.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Integer>("repository_id"));   
            col_saved.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,String>("savedInDataBase"));            
             txt_quntity.setOnKeyPressed((event) -> {
                if(event.getCode().toString() == "DOWN"){
                    addITemToTable();
                    txt_codeItem.requestFocus();
        }   
        
            });
               txt_codeItem.setOnKeyPressed((event) -> {
                if(event.getCode().toString() == "LEFT"){
                   
                 txt_quntity.requestFocus();
                
        }   else if(event.getCode().toString().equals("ENTER")){

            saveButton();
        }else if(event.getCode().toString().equals("F5")){
            if(radioName.isSelected()){
            radioName.setSelected(false);
            } 
            else{
            radioName.setSelected(true);
            radioNameSelected();
            }
            
            radioNameSelected();
        }else if(event.getCode().toString().equals("F12")){
              
                txt_payed.requestFocus();
            }
        
            });
             searchAboutOrder();
             numberOfItemInReceiptTable = tabel_receipt.getItems().size();
             calcTotalOfOrder();
        
        }
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
        AllSection();
        tabel_receipt.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                
    }    
     public void searchAboutOrder(){
       ObservableList<cutomerReceipt> allITems = FXCollections.observableArrayList();
        try {
            
            
            String sql = "SELECT currentTransactionNUm from tables where number = '"+TablesController.tableNumber+"'";
            
            String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction(sql);

            String receiptNumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT receiptNumber from customerreceiptmaininfo where transactionNumber = '"+transactionNumber+"'");
    
            Object[] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM customerreceiptdetailedinfo WHERE receiptNumber = '"+receiptNumber+"'");
       
           if(!(table.length == 0)){
               lbl_reciptNumber.setText(receiptNumber+"");
                String customer_id = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT son_id FROM tables WHERE currentTransactionNUm = '"+transactionNumber+"'");
                String customer_name = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT name FROM son WHERE Id ='"+customer_id+"'");
                txt_customer_name.setText(customer_name);
                txt_customer_name.setEditable(false);
                for (int i = 0; i < table.length; i++) {
                 String [] row  = (String[]) table[i];
                 int id   = Integer.parseInt(row[0]);
                 long itemCode = Long.parseLong(row[2]);
                 String itemName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT item_name from cafeitem WHERE itemCode = "+itemCode);
                 float quantity = Float.parseFloat(row[3]);
                 float pay = Float.parseFloat(row[4]);
                 int repository_id = Integer.parseInt(row[5]);
                 cutomerReceipt newOne = new cutomerReceipt(id,itemCode, itemName, quantity, pay, pay*quantity, repository_id,"yes");
                 allITems.add(newOne);
                 
                }
                tabel_receipt.setItems(allITems);
                paneOrder.setVisible(true);
                txt_codeItem.requestFocus();
            }
           calcTotalOfOrder();
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            dataBase.showMessageJOP(ex.getMessage());
        }
   }
  public void addITemToTable(){

        long codeITem = -1 ;
        try {
            if(radioName.isSelected()){
            codeITem = Long.parseLong(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT itemCode from cafeitem WHERE item_name = '"+txt_codeItem.getText()+"'"));
           
            }
            else{
                
             codeITem = Long.parseLong(txt_codeItem.getText());
             
            }
           String itemName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT item_name from cafeitem WHERE itemCode = "+codeITem); 
            float quantity = Float.parseFloat(txt_quntity.getText());
           String[]data =  getBuyPayPrice();
            float pay = Float.parseFloat(data[0]);
            float totoal = quantity * pay;
            cutomerReceipt newOne = new cutomerReceipt(1,codeITem, itemName, quantity, pay,totoal,(combo_repositorty.getSelectionModel().getSelectedIndex()+1),"no");
            tabel_receipt.getItems().add(newOne);
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
        catch(Exception ex){
            if(ex.getMessage().contains(" input string")){
                dataBase.showMessageJOP("تاكد من نوع البحث");
            }
        }
        float total = 0;
        for (int i = 0; i < tabel_receipt.getItems().size(); i++) {
            
         total = total + tabel_receipt.getItems().get(i).getTotal();
           lbl_total.setText(total+"");
       }
   
   
   }
    public void radioNameSelected(){
       
       if(radioName.isSelected()){
      allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "item_name", "id");
      TextFields.bindAutoCompletion(txt_codeItem  , allItem);
       }
       else{
       
      allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "itemCode", "id");
      TextFields.bindAutoCompletion(txt_codeItem  , allItem);
       }
       
}

     public String []  getBuyPayPrice(){
       
        
        try {
            String []row = dataBase.retrievingRowIntableInArray("Select  itemPrice , item_name from cafeitem where itemCode = '"+txt_codeItem.getText()+ "' || item_name = '"+txt_codeItem.getText()+"'");
            return row;
        } catch (SQLException ex) {
            
            dataBase.showMessageJOP(ex.getMessage());
            return new String []{};
        }

   }
     public void saveButton(){
   
       if(tabel_receipt.getItems().size()<=0){
           dataBase.showMessageJOP("لا توجد بيانات لحفظها");
           return;
       }
        try {
            saveDetailedData();
//      lbl_reciptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
            numberOfItemInReceiptTable = tabel_receipt.getItems().size();
//        lbl_reciptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
            int player_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+txt_customer_name.getText()+"'"));
            //this query to udate the name of current cutomer that received table in tables table
            String transactionnumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT currentTransactionNUm from tables WHERE number = '"+TablesController.tableNumber+"'");
            String sqlUpdateSon_idTONewCustomerReceivedTable ="UPDATE `tables` set`son_id` = '"+player_id+"' WHERE currentTransactionNUm = '"+transactionnumber+"'";
            dataBase.excuteNonQuery(sqlUpdateSon_idTONewCustomerReceivedTable);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
   }
     public boolean  saveMainData(){
    
        try{
        String  receieptNumber = lbl_reciptNumber.getText();
        int userID   = loginController.loginUser.getID() ;       
      
        LocalDate localStartDate = LocalDate.now();
        java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);
        String transactionnumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT currentTransactionNUm from tables WHERE number = '"+TablesController.tableNumber+"'");
        int transactionNumber = Integer.parseInt((transactionnumber)) ;         
        int player_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+txt_customer_name.getText()+"'"));
        String newtransactionnumber = dataBase.autoTranscationForLiveReceipt("customerreceiptmaininfo", "transactionNumber");
        String isLoadedInMainData = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT `receiptNumber` FROM `customerreceiptmaininfo` WHERE transactionNumber = '"+transactionNumber+"'");
        if(isLoadedInMainData.equals("")){
            //هعمل فاتورة جديدة
        String result = dataBase.saveMainCustomerCafeReceipt(Integer.parseInt(receieptNumber),Integer.parseInt( newtransactionnumber),userID, sqlDate,player_id);      
        String sqlUpdateTableTranscationNumber ="UPDATE `tables` SET `currentTransactionNUm`= '"+newtransactionnumber+"' WHERE number = '"+TablesController.tableNumber+"'";
        dataBase.excuteNonQuery(sqlUpdateTableTranscationNumber);
        return true ; //لو سجل فاتورة جديدة يبقي في رقم جديد 
        }
        else{
        return false ; // يبقي لازم اجيب رقم الفاتورة القديم        
        } 
        }catch(Exception ex){ 
            System.out.println(ex.getMessage());   
      return false;
        }
   }
     public void deleteItem(){
        ObservableList<cutomerReceipt> selectedItems = tabel_receipt.getSelectionModel().getSelectedItems();
        int numOfItemInTable  = tabel_receipt.getItems().size();
        int reminderAfterSelection = numOfItemInTable - selectedItems.size();
        int billNum = (Integer.parseInt(lbl_reciptNumber.getText()));
      try{
          if(reminderAfterSelection == 0){
                          for (int i = 0; i < selectedItems.size(); i++) {
                                 cutomerReceipt c = selectedItems.get(i);
                                 dataBase.deleteItemFromBill(c.getId(),billNum);
                                dataBase.deleteBill((billNum));
                          }
                        TablesController.buttonOFOPenedTable.setStyle("-fx-background-color: ##C2DED1");
                        //reset table to defaults value
                        String sqlUpdateTableTranscationNumber ="UPDATE `tables` SET `currentTransactionNUm`= '"+0+"'  , son_id = '"+0+"' WHERE number = '"+TablesController.tableNumber+"'";
                        dataBase.excuteNonQuery(sqlUpdateTableTranscationNumber);                          
          }
          else {
          for (int i = 0; i < selectedItems.size(); i++) {
                  cutomerReceipt c = selectedItems.get(i);
                  if(reminderAfterSelection >= 1 ){
                        dataBase.deleteItemFromBill(c.getId(),billNum);
                        System.out.println(c.getCode());
                }
                    else{
                        dataBase.deleteItemFromBill(billNum,billNum);    
                        dataBase.deleteBill((billNum));
                        TablesController.buttonOFOPenedTable.setStyle("-fx-background-color: ##C2DED1");
                        //reset table to defaults value
                        String sqlUpdateTableTranscationNumber ="UPDATE `tables` SET `currentTransactionNUm`= '"+0+"'  , son_id = '"+0+"' WHERE number = '"+TablesController.tableNumber+"'";
                        dataBase.excuteNonQuery(sqlUpdateTableTranscationNumber);
                }
                 
                        
          }
            
      }
        calcTotalOfOrder();
        tabel_receipt.getItems().removeAll(selectedItems);
      }catch(SQLException ex){
          dataBase.showMessageJOP(ex.getMessage());
      }
      catch(Exception ex){
          dataBase.showMessageJOP(ex.getMessage());
      }
   }

        public void calcTotalOfOrder(){
   
       float total = 0 ;
       for (int i = 0; i < tabel_receipt.getItems().size() ; i++) {
            total = total + tabel_receipt.getItems().get(i).getTotal();
       }
       lbl_total.setText(total+"");
   }
   
     public void saveDetailedData(){
       
   try{
        ObservableList<cutomerReceipt> items = tabel_receipt.getItems();
       
       if(saveMainData()){
        for (int i = 0; i <= items.size(); i++) {
            
            
            int receiptNumber = Integer.parseInt(lbl_reciptNumber.getText());
            long itemCode = items.get(i).getCode();
            float quantity = items.get(i).getQuantity();
            float payPrice = items.get(i).getPayPrice();
            int repository_id = (combo_repositorty.getSelectionModel().getSelectedIndex())+1;
            tabel_receipt.getItems().get(i).setSavedInDataBase("yes");
            dataBase.saveDetailedDataOFCustomerReceipt(receiptNumber, itemCode, quantity, payPrice, repository_id); 
        }
       }
       else{
                   
        String transactionnumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT currentTransactionNUm from tables WHERE number = '"+TablesController.tableNumber+"'");
        String receiptNumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT `receiptNumber` FROM `customerreceiptmaininfo` WHERE transactionNumber = '"+transactionnumber+"'");
       for (int i = numberOfItemInReceiptTable; i <= items.size(); i++) {
            
            try{
            long itemCode = items.get(i).getCode();
            float quantity = items.get(i).getQuantity();
            float payPrice = items.get(i).getPayPrice();
            int repository_id = (combo_repositorty.getSelectionModel().getSelectedIndex())+1;
            tabel_receipt.getItems().get(i).setSavedInDataBase("yes");
            
            dataBase.saveDetailedDataOFCustomerReceipt(Integer.parseInt(receiptNumber), itemCode, quantity, payPrice, repository_id);
           
            } catch(Exception ex ){
                System.out.println(ex.getMessage());
            }
           
        }
        tabel_receipt.getItems().clear();
            searchAboutOrder();
       
       }
       // increasing number of bill 
      //  lbl_reciptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
         } catch(Exception ex ){
                System.out.println(ex.getMessage());
            }
           
          TablesController.buttonOFOPenedTable.setStyle("-fx-background-color: #FF6363");
   }
     
     public void printAndChangeStateOfTable(){
     
            PrintReport();
            try{
            
            LocalDate localStartDate = LocalDate.now();
            java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);
            String transactionnumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT currentTransactionNUm from tables WHERE number = '"+TablesController.tableNumber+"'");
            int transactionNumber = Integer.parseInt((transactionnumber)) ;         
            int player_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+txt_customer_name.getText()+"'"));            
            String accountName = txt_customer_name.getText();
            String idAccountFrom  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son WHERE name = '"+accountName+"'");
            dataBase.customerLife(transactionNumber, 1, Float.parseFloat(lbl_total.getText()), 0, "شراء من مخزن للطاولة  "+TablesController.tableNumber,player_id,sqlDate);
            int idOfAddMoney = dataBase.customerLife(11,3, 0, Float.parseFloat(txt_mustPayed.getText())," سداد  الي الخزينة من "+txt_customer_name.getText(), player_id, sqlDate);
            dataBase.enterAndOutMoney(transactionNumber,Float.parseFloat(txt_mustPayed.getText()),safe.id, loginController.loginUser.getID(),sqlDate,Integer.parseInt(idAccountFrom),TablesController.tableNumber+"حساب طاولة",idOfAddMoney);
            
            //reset table to defaults value
            String sqlUpdateTableTranscationNumber ="UPDATE `tables` SET `currentTransactionNUm`= '"+0+"'  , son_id = '"+0+"' WHERE number = '"+TablesController.tableNumber+"'";
            dataBase.excuteNonQuery(sqlUpdateTableTranscationNumber);
            
            TablesController.buttonOFOPenedTable.setStyle("-fx-background-color: ##C2DED1");
            
            tabel_receipt.getItems().clear();
            txt_payed.setText("");
            txt_quntity.setText("");
            txt_reminder.setText("");
            lbl_total.setText("");
             
            }
            catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
            }
     }
     public void finishPayment(){
         paymentPane.setVisible(true);
         txt_payed.requestFocus();
         try{
         int customerAccountID =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+txt_customer_name.getText()+"'"));
         String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+customerAccountID+"");
         txt_downPayment.setText(((Integer.parseInt(lifeCycleOfCustomer))*-1)+"");
         float total = Float.parseFloat(lbl_total.getText());
         float downPayment = Float.parseFloat(txt_downPayment.getText());
         float mustPayed = total -  downPayment ;
         txt_mustPayed.setText(mustPayed+"");
         }
         catch(SQLException ex){
             JOptionPane.showMessageDialog(null, ex.getMessage());
         }
     }
        public void openReceiveMoney(){
         
         playerController.currentPlayer = new player(txt_customer_name.getText());
         openNewWindow("receivingMoneyFrom", "استلام نقود ضمن الحساب");
     
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
   
      public void PrintReport(){
        try {
            JasperDesign jd = JRXmlLoader.load("bill.jrxml");
            int labelReceiptNumber = Integer.parseInt(lbl_reciptNumber.getText());
            String sql = "SELECT cafeitem.item_name , customerreceiptdetailedinfo.quantity , customerreceiptdetailedinfo.payPrice ,customerreceiptdetailedinfo.quantity * customerreceiptdetailedinfo.payPrice from cafeitem JOIN customerreceiptdetailedinfo WHERE cafeitem.itemCode = customerreceiptdetailedinfo.itemCode and customerreceiptdetailedinfo.receiptNumber = '"+labelReceiptNumber+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            HashMap <String,Object> para = new HashMap<>();
            para.put("receiptNumber", lbl_reciptNumber.getText());
            para.put("receiptTotal", lbl_total.getText());
            para.put("payed", txt_payed.getText());
            para.put("reminder", txt_reminder.getText());
            para.put("customerName", TablesController.tableNumber);
            para.put("downPayment", txt_downPayment.getText());
            para.put("mustPayed", txt_mustPayed.getText());
            para.put("loginUser",loginController.loginUser.getUserName());
            para.put("companyName", homeController.companyName);
            String []shopData = dataBase.retrievingRowIntableInArray("SELECT address , tel FROM companydata ;");
            para.put("address", shopData[0]);
            para.put("contact", shopData[1]);
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
            lbl_reciptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));

        } catch (JRException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(TableOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

   }

    public void AllSection (){

        List<CafeSection> allSection = dataBase.gettingAllSection();
        sectionVB.getStyleClass().clear();
        sectionVB.getStyleClass().add("vbContainer");
        for(CafeSection cafeSection : allSection){
            Button sectionBtn = new Button(cafeSection.getSectionName());
            sectionBtn.getStyleClass().clear();
            sectionBtn.getStyleClass().add("btn");
            sectionVB.getChildren().add(sectionBtn);
            sectionBtn.setOnMouseClicked((event)->{
                List<cafeItem> allItem = dataBase.gettingallItemsInSuchCafeSection(cafeSection.getId());
                itemPane.getChildren().clear();
                for(cafeItem item : allItem){
                   Button itemBtn = new Button(item.getItemName());
                   itemPane.getChildren().add(itemBtn);
                        itemBtn.getStyleClass().clear();
                        itemBtn.getStyleClass().add("btnItem");
                   itemBtn.setOnMouseClicked((event2)->{
                        txt_codeItem.setText(item.getItemCode()+"");
                        cutomerReceipt newOne = new cutomerReceipt(0,item.getItemCode(), item.getItemName(), 1, item.getItemSell(),item.getItemSell(),(combo_repositorty.getSelectionModel().getSelectedIndex()+1),"yes");
                        tabel_receipt.getItems().add(newOne);
                        saveButton();
                        calcTotalAfterEachAddedItem();
                        
                   });
                   
                }
            });
        }
}
public void openLiveBill(){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("تحذير");
    alert.setHeaderText("تحذير");
    alert.setResizable(false);
    alert.setContentText("سيتم نقل الطلبات من الطاولة الي فاتورة مباشرة");
    Optional<ButtonType> result = alert.showAndWait();
    if(!result.isPresent()){
    // alert is exited, no button has been pressed.
    }
    else if(result.get() == ButtonType.OK){
       ObservableList<cutomerReceipt> selectedItems = tabel_receipt.getSelectionModel().getSelectedItems();
        liveReceiptController.itemInTable = selectedItems;
        openNewWindow("liveReceipt", "فاتورة مباشرة");
        deleteItem();
   
        }
        else if(result.get() == ButtonType.CANCEL){
        alert.close();
        }
 
}
      
   
    public void calcTotalAfterEachAddedItem(){
        float total = 0;
        for (int i = 0; i < tabel_receipt.getItems().size(); i++) {   
         total = total + tabel_receipt.getItems().get(i).getTotal();
           lbl_total.setText(total+"");
       }
    
    }
    
}
