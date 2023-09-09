/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.cutomerReceipt;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
 *
 * @author Seha
 */
public class barCodeController implements Initializable, EventHandler<KeyEvent>{

    @FXML
    private TextField txt_itemName;

    @FXML
    private TextField txt_quantity;

    @FXML
    private Button btn_add;

    @FXML
    private TableView<cutomerReceipt> table_items;

    @FXML
    private TableColumn<cutomerReceipt, Long> col_code;

    @FXML
    private TableColumn<cutomerReceipt,String> col_name;

    @FXML
    private TableColumn<cutomerReceipt, Float> col_price;
    @FXML
    private TableColumn<cutomerReceipt, Float> col_quantity;

    @FXML
    private Button btn_PRINT;

    @FXML
    private TextField txt_Code;
    String [] allItemName ;
    String [] allCode;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
            col_code.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Long>("code"));        
            col_name.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,String>("itemName"));        
            col_quantity.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("quantity"));        
            col_price.setCellValueFactory(new PropertyValueFactory<cutomerReceipt,Float>("payPrice"));        

       allItemName =  dataBase.retrivingColumnFromDataBase("SELECT item_name from cafeitem ");
       allCode =  dataBase.retrivingColumnFromDataBase("SELECT `itemCode` from cafeitem");
       TextFields.bindAutoCompletion(txt_itemName  , allItemName);
       TextFields.bindAutoCompletion(txt_Code  , allCode);
       txt_Code.setOnKeyReleased(this);
       txt_itemName.setOnKeyReleased(this);
       txt_quantity.setOnKeyReleased(this);
    }
    public void deleteItem(){
        
        int deletedItem = table_items.getSelectionModel().getSelectedIndex();
        table_items.getItems().remove(deletedItem);
    
    }
    public void addTOTable(){
        
        try{
        long itemCode = Long.parseLong(txt_Code.getText());
        String itemName = txt_itemName.getText();
        float payPrice = Float.parseFloat(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT itemPrice from cafeitem WHERE itemCode = "+itemCode));
        float quantity = Float.parseFloat(txt_quantity.getText());
                
        cutomerReceipt newOne = new cutomerReceipt(itemCode, itemName, payPrice,quantity);
        table_items.getItems().add(newOne);
        }catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
    }
    
    public void search(){
       try{
            String itemCode = txt_Code.getText();
            String itemName = txt_itemName.getText();
            String[] dataFromItemTable = dataBase.retrievingRowIntableInArray("SELECT item_name , itemCode from cafeitem WHERE itemCode = '"+itemCode+"' Or item_name = '"+itemName+"'");
            txt_Code.setText(dataFromItemTable[1]);
            txt_itemName.setText(dataFromItemTable[0]);
            txt_quantity.requestFocus();
            }
            catch(Exception ex){
            dataBase.showMessageJOP(ex.getMessage());
            }
    
    }
    
   
    
    public void addingBarcodeToDataBase(){
        try{
        ObservableList<cutomerReceipt> items = table_items.getItems();
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.get(i).getQuantity(); j++) {
                String sql = "INSERT INTO barCode VALUES ("+items.get(i).getCode()+","+"'"+items.get(i).getItemName()+"',"+items.get(i).getPayPrice()+")" ;
                dataBase.excuteNonQuery(sql);
            }
        }
    
    }
        catch(Exception ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
    }
    public void printButTon(){
    deleteBarcode();
    addingBarcodeToDataBase();
    PrintReport();
    }
    
       public void PrintReport(){
        try {
            JasperDesign jd = JRXmlLoader.load("barCode.jrxml");
            String sql = "SELECT * FROM barCode";
            System.out.println(sql);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,null,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
            
        } catch (JRException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
   
   }
       public void deleteBarcode(){
           try{
       String sql = "delete from barcode";
       dataBase.excuteNonQuery(sql);
           }catch(Exception ex){
           dataBase.showMessageJOP(ex.getMessage());
           }
       
       }
    
    @Override
    public void handle(KeyEvent event) {
    
    
        if(event.getCode().toString().equals("ENTER") ){
            if(txt_quantity.getText().equals("")){
                         search();
            }else{
          String itemCode = txt_Code.getText();
          String itemName = txt_itemName.getText();
          String quantity = txt_quantity.getText();
            if(itemCode.equals("")|| itemName.equals("")||quantity.equals("")){
                return;
            }else{
            addTOTable();
            txt_quantity.setText("");
            txt_itemName.requestFocus();
            txt_Code.setText("");
            
            }
        }
        }
        if(event.getCode().toString().equals("RIGHT") ){
        
         search();
            
        }
        
     if(event.getCode().toString().equals("up"))   {
     
         deleteItem();
     }
    
    }
    
}
