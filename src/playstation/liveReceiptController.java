/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.CafeSection;
import classes.buyReceipt;
import classes.cafeItem;
import classes.cutomerReceipt;
import classes.player;
import classes.safe;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
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
import static playstation.homeController.companyName;
import javafx.scene.layout.FlowPane;


/**
 *
 * @author Seha
 */
public class liveReceiptController implements Initializable , EventHandler<KeyEvent>{

    @FXML
    private TextField txt_itemCode;

    @FXML
    private TextField txt_receiptQuantity;

    @FXML
    private TableView<cutomerReceipt> table_cafeBuy;

    @FXML
    private TableColumn<cutomerReceipt, Integer> col_codeItem;

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
    private TextField txt_playerName;
    @FXML
    private ComboBox<String> comboSafe;
    @FXML
    private VBox sectionVB;
    
    @FXML
    private FlowPane itemPane;
    
    String [] allItem ;
    String [] allPlayer ; 
    int selecetedIndex = -1 ;
    boolean cach = false ;
    
    public static List<cutomerReceipt> itemInTable = new ArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             txt_payed.setOnKeyReleased((event) -> {
                checkReminder();
            });
            
             homeController.companyName = dataBase.gettingOnvalueFromTableByAggregatefunction("select company_name from companydata");
            col_codeItem.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Integer>("code"));
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
            txt_payed.setOnKeyPressed(this);
            txt_total.setOnKeyPressed(this);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(sdf.format(new Date()), formatter);

            datePicker.setValue(localDate);
            dataBase.putingColumnFromDataBaseIntoCombox( "SELECT nameOfSafe from safe where id > 1 ORDER by id", comboSafe);
            //comboSafe.getSelectionModel().selectFirst();
          //  String safeName = comboSafe.getSelectionModel().getSelectedItem();
            //safe.id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT nameOfSafe from safe WHERE nameOfSafe = '"+safeName+"'"));
            if(loginController.loginUser.getPermission() == 0){
            
                txt_playerName.setEditable(false);
            }
            String numberOfSafe = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT count(nameOfSafe) from safe WHERE id> 1 ");
            // IF there is one safe 
               if(Integer.parseInt(numberOfSafe)==1){
                    comboSafe.getSelectionModel().selectFirst();            
                    comboSafe.setDisable(true);
                    safe.id = (comboSafe.getSelectionModel().getSelectedIndex())+2;             
                    txt_itemCode.setEditable(true);
                    txt_receiptQuantity.setVisible(true);
                    txt_itemCode.requestFocus();   
            }

            //انا خفتيهم علشان مش هيظهروا غير لما يختار الخزنة
               else{
            txt_receiptQuantity.setText(1+"");
            txt_receiptQuantity.setVisible(false);
               }
          

        comboSafe.valueProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue ov, String t, String t1) {
            try{
                String safeName = comboSafe.getSelectionModel().getSelectedItem();
                System.out.println("safe name :"+safeName );
                String sql = ("SELECT id from safe WHERE nameOfSafe = '"+safeName+"'");
                System.out.println(sql);
                safe.id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction(sql));
                txt_itemCode.setEditable(true);                
                txt_receiptQuantity.setVisible(true);
                txt_itemCode.requestFocus();                
                
            }
            catch(SQLException ex){
                dataBase.showMessageJOP(ex.getMessage());
            }
        }    

            
    });
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }

        AllSection();
        table_cafeBuy.getItems().addAll(itemInTable);
        calcTotalOfOrder();
    }
    
    public void chooseSafe(){
    
        if(comboSafe.getSelectionModel().getSelectedIndex()>=0){
        
        comboSafe.setDisable(true);
        
        safe.id = (comboSafe.getSelectionModel().getSelectedIndex())+2;
            
            
        }
        
    
    }
public  boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
     public void addITemToTable(){
   
        long codeITem = -1 ;
        try {
            if(!(isNumeric(txt_itemCode.getText()))){
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
        radioName.setSelected(false);
        allItem = new String []{};           
        radioNameSelected();
   
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
    

public void selectAndShow(){
      
    txt_itemCode.setText("");
    cutomerReceipt selectedItem = table_cafeBuy.getSelectionModel().getSelectedItem();
    selecetedIndex = table_cafeBuy.getSelectionModel().getSelectedIndex();
    txt_itemCode.setText(selectedItem.getCode()+"");
    txt_receiptQuantity.setText(selectedItem.getQuantity()+"");
    txt_receiptPay.setText(selectedItem.getPayPrice()+"");
    txt_receiptQuantity.requestFocus();
    lbl_itemName.setText(selectedItem.getItemName());
    
    
    }
    
    
     public void editITemToTable(){
   
         
         try{
            int seclectItemIndex = table_cafeBuy.getSelectionModel().getFocusedIndex();
            cutomerReceipt selectedItem = table_cafeBuy.getSelectionModel().getSelectedItem();
            long code =Long.parseLong( txt_itemCode.getText());
            String itemName = lbl_itemName.getText();
            float quantity = Float.parseFloat(txt_receiptQuantity.getText());
            float pay =Float.parseFloat( txt_receiptPay.getText());
            float totalOfReceipt = quantity*pay;
            
            cutomerReceipt newOne = new cutomerReceipt(0,code, itemName, quantity, pay, totalOfReceipt, selectedItem.getRepository_id(),"yes");
            table_cafeBuy.getItems().remove(selecetedIndex);
            table_cafeBuy.getItems().add(selecetedIndex, newOne);
             
         }catch(NullPointerException ex){
         
             dataBase.showMessageJOP("قم بتحديد الصف المراد تعديله");
         }
         
        float total = 0;
        for (int i = 0; i < table_cafeBuy.getItems().size(); i++) {
            
         total = total + table_cafeBuy.getItems().get(i).getTotal();
           txt_total.setText(total+"");
       }
        txt_receiptPay.setText("");
        txt_itemCode.requestFocus();
   
   
   }
    
   public void  saveMainData(){
   
        
         
        try{
        String  receieptNumber = txt_receiptNumber.getText();
        int userID   = loginController.loginUser.getID() ;       
      
        LocalDate localStartDate = datePicker.getValue();
        java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);
        String newtransactionnumber = dataBase.autoTranscationForLiveReceipt("customerreceiptmaininfo", "transactionNumber");
        int transactionNumber = Integer.parseInt((newtransactionnumber)) ;         
        int player_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+txt_playerName.getText()+"'"));
        
        playerController.currentPlayer = new player(player_id, txt_playerName.getText(), "0", 0);
        if(!cach){
            System.out.println(" cash");
        String result = dataBase.saveMainCustomerCafeReceipt(Integer.parseInt(receieptNumber), transactionNumber,userID, sqlDate,player_id);
        dataBase.customerLife(transactionNumber, 1, Float.parseFloat(txt_total.getText()), 0, "شراء من مخزن  "+txt_playerName.getText(),player_id,sqlDate);
        int idOfAddedMoney = dataBase.customerLife(11,3, 0, Float.parseFloat(txt_total.getText())," سداد  الي الخزينة من "+txt_playerName.getText(), player_id, sqlDate);
        dataBase.enterAndOutMoney(transactionNumber,Float.parseFloat(txt_total.getText()),safe.id, loginController.loginUser.getID(),sqlDate,31,txt_playerName.getText()+"مشتريات من مخزون مباشر  ",idOfAddedMoney);
        }else{
            System.out.println("not cash cash");
        String result = dataBase.saveMainCustomerCafeReceipt(Integer.parseInt(receieptNumber), transactionNumber,userID, sqlDate,player_id);                
        dataBase.customerLife(transactionNumber, 1, Float.parseFloat(txt_total.getText()), 0,  "شراء من مخزن  "+txt_playerName.getText(),player_id,sqlDate);
        openNewWindow("receivingMoneyFrom", "استلام النقدية ");
        cach = false ;
        
        }
        
        }catch(Exception ex){ 
            System.out.println(ex.getMessage());
         
        
        return;

        }
        
       
   }
   
   public void getTotalAfterAddnewItemToTable(){
       float total = 0;
        for (int i = 0; i < table_cafeBuy.getItems().size(); i++) {
            
         total = total + table_cafeBuy.getItems().get(i).getTotal();
           txt_total.setText(total+"");
       }
   }
       public void openAddcustomerWindow(){
               openNewWindow("players", "عميل جديد  ");

           
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
       System.out.println("text name player"+txt_playerName.getText());
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
   if(txt_playerName.getText().equals("")){
   
           dataBase.showMessageJOP("ادخل اسم العميل اولا");
           return;
   
   }
       if(table_cafeBuy.getItems().size()<=0){
           dataBase.showMessageJOP("لا توجد بيانات لحفظها");
           return;
       
       }
        try {
            saveMainData();
            saveDetailedData();
             PrintReport();
            txt_receiptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
            table_cafeBuy.getItems().clear();
            txt_playerName.setText("كاش");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
   
   public void deleteItem(){
    
       int index = table_cafeBuy.getSelectionModel().getSelectedIndex();
       table_cafeBuy.getItems().remove(index);
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
            
            
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("تحذير");
                alert.setContentText("الصنف غير موجود بقاعدة البيانات");
                alert.showAndWait();
                txt_itemCode.requestFocus();

        }
   
   }
   
   public void checkReminder(){
   
      float total = Float.parseFloat(txt_total.getText() );
      float payed = Float.parseFloat(txt_payed.getText());
      float reminder = payed - total ;
      txt_reminder.setText(reminder+"");
      
   }

public void cash(){
    System.out.println("see all ");
    txt_playerName.setEditable(true);
    cach = true ;
    System.out.println(cach +" from method");

allPlayer =  allPlayer = dataBase.retrivingColumnFromDataBase("SELECT name from son  where father_id = 6 or father_id = 5 ");
TextFields.bindAutoCompletion(txt_playerName  , allPlayer);

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
                        txt_itemCode.setText(item.getItemCode()+"");
                        cutomerReceipt newOne = new cutomerReceipt(0,item.getItemCode(), item.getItemName(), 1, item.getItemSell(),item.getItemSell(),(combo_repositorty.getSelectionModel().getSelectedIndex()+1),"yes");
                        table_cafeBuy.getItems().add(newOne);
                        txt_receiptPay.setText(item.getItemSell()+"");
                        getTotalAfterAddnewItemToTable();
                        
                   });
                   
                }
            });
        }
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
         if(event.getCode().toString() == "up"){
                    deleteItem();
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
     if(event.getCode().toString().equals("F12")) {
         txt_payed.requestFocus();
     }
     if(event.getCode().toString().equals("F1")) {
         checkReminder();
     }
     
    }
    
}
