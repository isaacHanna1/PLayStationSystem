package playstation;

import classes.buyReceipt;
import classes.offerClass;
import classes.player;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
public class CafeBuyGoodsController implements Initializable , EventHandler<KeyEvent>{
    
    @FXML
    private TextField txt_itemCode;

    @FXML
    private TextField txt_receiptQuantity;

    @FXML
    private TableView<buyReceipt> table_cafeBuy;

    @FXML
    private TableColumn<buyReceipt, Long> col_codeItem;

    @FXML
    private TableColumn<buyReceipt, String> col_codeName;

    @FXML
    private TableColumn<buyReceipt, Float> col_codeQuantity;

    @FXML
    private TableColumn<buyReceipt, Float> col_buyPrice;

    @FXML
    private TableColumn<buyReceipt, Float> colPayPrice;
    @FXML
    private TableColumn<buyReceipt, Float> col_total;
    
    @FXML
    private TableColumn<buyReceipt, Integer> col_repository;
    @FXML
    private TextField txt_receiptBuyPrice;

    @FXML
    private TextField txt_receiptPay;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txt_receiptNumber;

    @FXML
    private RadioButton radioCode;

    
    @FXML
    private Label lbl_itemName;
    @FXML
    private TextField txt_total;
    
    @FXML
    private RadioButton radioName;
        @FXML
    private ComboBox<String> combo_repositorty;
    
        
    @FXML
    private TextField txt_sulpplierName;
    int transaction = -1;
    String [] allItem ;
    @Override
   public void initialize(URL location, ResourceBundle resources) {
         txt_sulpplierName.requestFocus();
         txt_itemCode.setVisible(false);
        txt_sulpplierName.setOnKeyReleased((event) -> {
                if(event.getCode().toString().equals( "ENTER")){
        
                        txt_itemCode.setVisible(true);
                        txt_itemCode.requestFocus();
                }
                
            });
    
        try {
            col_codeItem.setCellValueFactory(new PropertyValueFactory<buyReceipt,Long>("code"));
            col_codeName.setCellValueFactory(new PropertyValueFactory<buyReceipt,String>("itemName"));
            col_codeQuantity.setCellValueFactory(new PropertyValueFactory<buyReceipt,Float>("quantity"));
            col_buyPrice.setCellValueFactory(new PropertyValueFactory<buyReceipt,Float>("buyPrice"));
            colPayPrice.setCellValueFactory(new PropertyValueFactory<buyReceipt,Float>("payPrice"));
            col_total.setCellValueFactory(new PropertyValueFactory<buyReceipt,Float>("total"));            
            col_repository.setCellValueFactory(new PropertyValueFactory<buyReceipt,Integer>("repository_id"));            
            allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "itemCode", "id");
            TextFields.bindAutoCompletion(txt_itemCode  , allItem);
            dataBase.putingColumnFromDataBaseIntoCombox("SELECT repositoryName from repository ORDER BY id", combo_repositorty);
            txt_receiptNumber.setText(dataBase.autoNumber("cafebuymaininfo", "receiptNumber"));
            combo_repositorty.getSelectionModel().select(0);
            txt_itemCode.setOnKeyPressed(this);
            txt_receiptQuantity.setOnKeyPressed(this);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(sdf.format(new Date()), formatter);

            datePicker.setValue(localDate);
            String []allSupplier = dataBase.retrivingColumnFromDataBase("SELECT name from son where father_id =5");
            TextFields.bindAutoCompletion(txt_sulpplierName  , allSupplier);
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
            float buy = Float.parseFloat(txt_receiptBuyPrice.getText());
            float pay = Float.parseFloat(txt_receiptPay.getText());
            float totoal = quantity * buy;
            buyReceipt newOne = new buyReceipt(codeITem, itemName, quantity, buy, pay,totoal,(combo_repositorty.getSelectionModel().getSelectedIndex()+1));
            table_cafeBuy.getItems().add(newOne);
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        catch(Exception ex){
            if(ex.getMessage().contains(" input string")){
                dataBase.showMessageJOP("تاكد من ادخال الكمية");
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        float total = 0;
        for (int i = 0; i < table_cafeBuy.getItems().size(); i++) {
            
         total = total + table_cafeBuy.getItems().get(i).getTotal();
           txt_total.setText(total+"");
       }
        txt_receiptPay.setText("");
        txt_receiptBuyPrice.setText("");
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
        String receiptDate = ( datePicker.getValue().toString());
        String supplierName  = txt_sulpplierName.getText();
        if(supplierName.equals("")){
            dataBase.showMessageJOP("قم باضافة اسم المورد");
            return;
        }
        int supplierId = Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from son WHERE name = '"+supplierName+"'"));
        
        LocalDate localStartDate = datePicker.getValue();
        java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);
         transaction = Integer.parseInt(dataBase.autoTranscation("cafebuymaininfo", "transactionNumber"));
        
        String result = dataBase.saveMainBuyCafeReceipt(transaction,Integer.parseInt(receieptNumber), userID, sqlDate, supplierId);
            System.out.println(result);
        }catch(Exception ex){ 
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println(ex.getMessage());
         
        
        return;

        }
        
        
        
   
   }
     public void select(){
        
         buyReceipt selectedItem = table_cafeBuy.getSelectionModel().getSelectedItem();
         txt_itemCode.setText(selectedItem.getCode()+"");
         txt_receiptBuyPrice.setText(selectedItem.getBuyPrice()+"");
         txt_receiptPay.setText(selectedItem.getPayPrice()+"");
         txt_receiptQuantity.setText(selectedItem.getQuantity()+"");
         
    
    }
    public void deleteItem(){
   
    table_cafeBuy.getItems().remove(table_cafeBuy.getSelectionModel().getSelectedIndex());
     float total = 0;
        for (int i = 0; i < table_cafeBuy.getItems().size(); i++) {
            
         total = total + table_cafeBuy.getItems().get(i).getTotal();
           txt_total.setText(total+"");
       }
    
   }
    public void editItem(){
   
       int selectedIndex = table_cafeBuy.getSelectionModel().getSelectedIndex();
     
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
            float buy = Float.parseFloat(txt_receiptBuyPrice.getText());
            float pay = Float.parseFloat(txt_receiptPay.getText());
            float totoal = quantity * buy;
            buyReceipt newOne = new buyReceipt(codeITem, itemName, quantity, buy, pay,totoal,(combo_repositorty.getSelectionModel().getSelectedIndex()+1));
            deleteItem();            
            table_cafeBuy.getItems().add(selectedIndex, newOne);
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        catch(Exception ex){
            if(ex.getMessage().contains(" input string")){
                dataBase.showMessageJOP("تاكد من ادخال الكمية");
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        float total = 0;
        for (int i = 0; i < table_cafeBuy.getItems().size(); i++) {
            
         total = total + table_cafeBuy.getItems().get(i).getTotal();
           txt_total.setText(total+"");
       }
        txt_receiptPay.setText("");
        txt_receiptBuyPrice.setText("");
        txt_itemCode.requestFocus();
   
   }
   
   public void saveDetailedData(){
   
        ObservableList<buyReceipt> items = table_cafeBuy.getItems();
        
        for (int i = 0; i < items.size(); i++) {
            
            try{
            int receiptNumber = Integer.parseInt(txt_receiptNumber.getText());
            long itemCode = items.get(i).getCode();
            float quantity = items.get(i).getQuantity();
            float buyPrice = items.get(i).getBuyPrice();
            float payPrice = items.get(i).getPayPrice();
            int repository_id = (combo_repositorty.getSelectionModel().getSelectedIndex())+1;
            dataBase.saveDetailedDataOFBuyReceipt(receiptNumber, itemCode, quantity, buyPrice, payPrice, repository_id);

            } catch(Exception ex ){
                JOptionPane.showMessageDialog(null, ex.getMessage());
                System.out.println(ex.getMessage());
            }
           
           
       }
        

   }
   
   public void saveButton(){
   
       if(table_cafeBuy.getItems().size()<=0){
           dataBase.showMessageJOP("لا توجد بيانات لحفظها");
           return;
       
       }
        try {
            saveMainData();
            saveDetailedData();
            txt_receiptNumber.setText(dataBase.autoNumber("cafebuymaininfo", "receiptNumber"));
            table_cafeBuy.getItems().clear();
            LocalDate localStartDate = datePicker.getValue();
            java.sql.Date sqlDate=java.sql.Date.valueOf(localStartDate);
            int player_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+txt_sulpplierName.getText()+"'"));
            playerController.currentPlayer = new player(player_id, txt_sulpplierName.getText(), "0", 0);
            dataBase.customerLife(transaction, 5 ,0, Float.parseFloat(txt_total.getText()), "قامت الشركة بشراء من  "+txt_sulpplierName.getText(),player_id,sqlDate);
              FXMLLoader loader = new FXMLLoader(getClass().getResource("outMoneyTo.fxml"));
               Parent root = loader.load();
               outMoneyToController  controller = loader.getController();
               controller.setAccountName(txt_sulpplierName.getText());
               controller.setReceiptValue(txt_total.getText());
               controller.setLblDebit(txt_sulpplierName.getText());
               printReceipt();
               Scene scene = new Scene(root);
               Stage newStage = new Stage();
               newStage.setScene(scene);
                newStage.setTitle("صرف نقدية");
                newStage.show();
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(CafeBuyGoodsController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   public void  getBuyPayPrice(){
       
        try {
            String []row = dataBase.retrievingRowIntableInArray("Select itemBuy , itemPrice , item_name from cafeitem where itemCode = '"+txt_itemCode.getText()+ "' || item_name = '"+txt_itemCode.getText()+"'");
            txt_receiptBuyPrice.setText(row[0]);
            txt_receiptPay.setText(row[1]);
            lbl_itemName.setText(row[2]);
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
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
        editItem();
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
            
        
    }
    
    public void printReceipt(){
        
         try {
            JasperDesign jd = JRXmlLoader.load("buyReceipt.jrxml");
            String sql = "SELECT cafeitem.item_name , cafebuydetails.quantity , cafebuydetails.buyPrice , (cafebuydetails.quantity * cafebuydetails.buyPrice) from cafebuydetails JOIN cafeitem WHERE cafeitem.itemCode = cafebuydetails.itemCode and cafebuydetails.receiptNumber = '"+txt_receiptNumber.getText()+"'";
            System.out.println(sql);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            HashMap <String,Object> para = new HashMap<>();
            
            para.put("receiptNumber", txt_receiptNumber.getText());                    
            para.put("p_supplierName", txt_sulpplierName.getText());
            para.put("total", txt_total.getText());  
            para.put("p_casherName",loginController.loginUser.getUserName());
            String []shopData = dataBase.retrievingRowIntableInArray("SELECT address , tel FROM companydata ;");
            para.put("p_address", shopData[0]);
            para.put("p_contact", shopData[1]);
            para.put("P_companyName", homeController.companyName);
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
            
        } catch (JRException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(liveReceiptController.class.getName()).log(Level.SEVERE, null, ex);
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

    
}
