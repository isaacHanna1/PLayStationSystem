/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import  classes.StockTaking;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import org.controlsfx.control.textfield.TextFields;
/**
 *
 * @author Seha
 */
    public class StocktakingController implements Initializable, EventHandler<KeyEvent>{
    @FXML
    private TableView<StockTaking> table_stocktaking;

    @FXML
    private TableColumn<StockTaking,Long> col_itemCode;

    @FXML
    private TableColumn<StockTaking,String> col_itemName;

    @FXML
    private TableColumn<StockTaking, Float> col_EnterNumber;

    @FXML
    private TableColumn<StockTaking, Float> col_outNumber;
    
    @FXML
    private TableColumn<StockTaking, Float> col_remaining;
  

    @FXML
    private TableColumn<StockTaking, Float> col_buyPrice;

    @FXML
    private TableColumn<StockTaking, Float> col_total;

    
    @FXML
    private TableColumn<StockTaking, Integer> col_section;
    
    @FXML
    private TextField txt_itemName_code;
    
    @FXML
    private Label lblSectionOrItems;
    @FXML
    private Label lbl_totalOfReminder;
    @FXML
    private RadioButton radioItme;
      StockTaking [] items = {};
       String []allSection = {};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
      col_itemCode.setCellValueFactory(new PropertyValueFactory<StockTaking,Long>("itemCode"));
      col_itemName.setCellValueFactory(new PropertyValueFactory<StockTaking,String>("itemName"));
      col_EnterNumber.setCellValueFactory(new PropertyValueFactory<StockTaking,Float>("itemEnterNumber"));
      col_outNumber.setCellValueFactory(new PropertyValueFactory<StockTaking,Float>("itemOutNumber"));
      col_remaining.setCellValueFactory(new PropertyValueFactory<StockTaking,Float>("remaningNumber"));
      col_section.setCellValueFactory(new PropertyValueFactory<StockTaking,Integer>("sectionId"));
      col_buyPrice.setCellValueFactory(new PropertyValueFactory<StockTaking,Float>("buyPrice"));
      col_total.setCellValueFactory(new PropertyValueFactory<StockTaking,Float>("total"));
      
      allSection = dataBase.gettingColumnFromDatabaseIntoArray( "cafesection", "cafeSectionName", "id");
      System.out.println("all section length "+allSection.length);

      
      txt_itemName_code.setOnKeyPressed(this);
        System.out.println("end");
        gettingTableInformationFromDataBase();
        System.out.println("afterEnd");
       txt_itemName_code.requestFocus();
       col_itemName.setStyle("-fx-font: 16  bold;");
       col_remaining.setStyle("-fx-font: 16  bold;");
       col_EnterNumber.setStyle("-fx-font: 16  bold;");
       col_outNumber.setStyle("-fx-font: 16  bold;");
       
       

    }
    //item code , item name , item enter Number , item out number 
    public void gettingTableInformationFromDataBase(){

        clearTable();
        float totalOfReminder = 0;
        try {
            Object []table = dataBase.retrievingTableInArrayOfObject("SELECT cafeitem.itemCode , cafeitem.item_name , SUM(cafebuydetails.quantity),cafeitem.itemBuy,cafeitem.secction_id  FROM cafeitem JOIN cafebuydetails WHERE cafeitem.itemCode = cafebuydetails.itemCode GROUP BY cafeitem.itemCode ORDER BY cafeitem.id ");
                items = new StockTaking[table.length];
            for (int i = 0; i < table.length; i++) {
              
                    String [] row =(String[]) table[i];
                    long itemCode = Long.parseLong(row[0]);
                    String itemName = row[1];
                    float QuantitEnter = Float.parseFloat(row[2]);
                    float quantityOut = 0 ;
                    float buyPrice = Float.parseFloat(row[3]);
                    int   sectionId  = Integer.parseInt(row[4]);
                    quantityOut = Float.parseFloat(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT SUM(customerreceiptdetailedinfo.quantity) FROM customerreceiptdetailedinfo where " +"itemCode = '"+row[0]+"'"));
                    StockTaking newOne = new StockTaking(itemCode, itemName, QuantitEnter, quantityOut,buyPrice,sectionId);
                    table_stocktaking.getItems().add(newOne);
                    items[i] = newOne;
                    totalOfReminder = totalOfReminder + newOne.getTotal();
            }
            lbl_totalOfReminder.setText(totalOfReminder+"");
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
        
    }
    public void searchAboutItem(){
  
        if(!radioItme.isSelected()){
        String codeOrName = txt_itemName_code.getText();
        if(codeOrName.length()>2){
            clearTable();
            for (int i = 0; i < items.length; i++) {
                    if(items[i].getItemName().contains(codeOrName)|| (items[i].getItemCode()+"").contains(codeOrName)){
                        table_stocktaking.getItems().add(items[i]);
                    
                    }
            }
            
        
        
        
        }
        }else{
              TextFields.bindAutoCompletion(txt_itemName_code  , allSection);

            String sectionName = txt_itemName_code.getText();
            try {
            int id =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id  FROM cafesection WHERE cafeSectionName = '"+sectionName+"'"));    
            if(txt_itemName_code.getText().length()>2){
            clearTable();
            for (int i = 0; i < items.length; i++) {
                    if(items[i].getSectionId()== id){
                        table_stocktaking.getItems().add(items[i]);
                    
                    }
            }
            
        
        
        }
            } catch (SQLException e) {
                dataBase.showMessageJOP(e.getMessage());
            }
            
            
        }
        
    }
   public void clearTable(){
       
           table_stocktaking.getItems().clear();

   }

    @Override
    public void handle(KeyEvent event) {
        System.out.println(event.getCode().toString());
        if(!(event.getCode().toString().equals("BACK_SPACE"))){
        searchAboutItem();
        }
    }
}
