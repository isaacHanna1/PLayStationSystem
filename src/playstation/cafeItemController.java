/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.cafeItem;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;


/**
 *
 * @author Seha
 */
public class cafeItemController implements Initializable  , EventHandler<KeyEvent>{

    
    @FXML
    private TextField txt_code;

    @FXML
    private TextField txt_itemName;

    @FXML
    private TextField txt_itemBuy;

    @FXML
    private TextField txt_itemPrice;

    @FXML
    private TextField txt_section_id;

    @FXML
    private Label lbl_message;
   
    @FXML
    private TextField txt_notificationNumber;
  
    @FXML
    private TextField txt_sulpplierName;
    
    String [] allSection ; 
    private int sectionID ;
    @FXML
    private TableView<cafeItem> table_item;

    @FXML
    private TableColumn<cafeItem, Integer> col_id;

    @FXML
    private TableColumn<cafeItem, Long> col_code;
    
    @FXML
    private TableColumn<cafeItem, String> col_sectionName;

    @FXML
    private TableColumn<cafeItem, String> col_name;

    @FXML
    private TableColumn<cafeItem,Float> col_buy;

    @FXML
    private TableColumn<cafeItem,Float> col_price;

    @FXML
    private TableColumn<cafeItem, Integer> col_notification;

    cafeItem [] allitem = {};

    int item_id ;
    TextField [] allFields = {};
    int textFildIndex = 0;
    long oldItemID = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    allSection = dataBase.gettingColumnFromDatabaseIntoArray("cafesection", "cafeSectionName", "id");
    TextFields.bindAutoCompletion(txt_section_id, allSection);
    col_id.setCellValueFactory(new PropertyValueFactory<cafeItem,Integer>("itemid"));
    col_code.setCellValueFactory(new PropertyValueFactory<cafeItem,Long>("itemCode"));
    col_name.setCellValueFactory(new PropertyValueFactory<cafeItem,String>("itemName"));
    col_sectionName.setCellValueFactory(new PropertyValueFactory<cafeItem,String>("sectionName"));
    col_buy.setCellValueFactory(new PropertyValueFactory<cafeItem,Float>("itembuy"));
    col_price.setCellValueFactory(new PropertyValueFactory<cafeItem,Float>("itemSell"));
    col_notification.setCellValueFactory(new PropertyValueFactory<cafeItem,Integer>("notifaction"));
     
    gettingAllCafeItem();
    allFields =new TextField[]{txt_code,txt_section_id,txt_itemName,txt_itemBuy,txt_itemPrice,txt_notificationNumber};
        for (int i = 0; i < allFields.length; i++) {
            allFields[i].setOnKeyPressed(this);
        }
    }
    public void addnewItem(){
    
    String result  = dataBase.insertNewCafeItem(Long.parseLong(txt_code.getText()), search(txt_section_id.getText(), allSection), txt_itemName.getText() ,Float.parseFloat(txt_itemBuy.getText()), Float.parseFloat(txt_itemPrice.getText()),Integer.parseInt(txt_notificationNumber.getText()));
    lbl_message.setText(result);
    gettingAllCafeItem();
        for (int i = 0; i < allFields.length; i++) {
            allFields[i].setText("");
        }
    }
    
     public int  search (String key , String []array){
    int i =0;
        for ( i = 0; i < array.length; i++) {
            if(key.equals(array[i])){
                System.err.println("i is "+ i);
                sectionID = i +1 ;
                return sectionID;
            }
            }

        return -1;
    }
    
     public void gettingAllCafeItem(){
     
         table_item.getItems().clear();
        try{
        Object []table =  dataBase.retrievingTableInArrayOfObject("SELECT cafeitem.id , itemCode , cafesection.cafeSectionName , item_name ,itemBuy , itemPrice , notificatioNumber FROM cafeitem JOIN cafesection WHERE cafeitem.secction_id = cafesection.id");
        allitem = new cafeItem[table.length];
            for (int i = 0; i < table.length; i++) {
                String [] row = (String[]) table[i];
                cafeItem newone = new cafeItem(Integer.parseInt(row[0]),Long.parseLong( row[1]), row[3], row[2],Float.parseFloat(row[4]), Float.parseFloat(row[5]),Integer.parseInt(row[6]));
                table_item.getItems().add(newone);
                allitem[i] = newone ;
            }
        }
        catch(SQLException ex){
        lbl_message.setText(ex.getMessage());
        }
    
     
     
     }
    public void search(){
    
    
        
        try{
        if(txt_code.getText().length()>1 || txt_itemName.getText().length()>1){
            table_item.getItems().clear();
            for (int i = 0; i < allitem.length; i++) {
                    if( (allitem[i].getItemName()+"").contains(txt_itemName.getText())){
                                    
                        System.out.println(allitem[i].getItemCode());
                        table_item.getItems().add(allitem[i]);
                    
                    }
            }
            
        
        
        }
        }
        catch(NumberFormatException ex){
        
        }

    }
     public void select (){
     
         cafeItem selected = table_item.getSelectionModel().getSelectedItem();
         item_id = selected.getItemid();
         txt_code.setText(selected.getItemCode()+"");
         oldItemID = selected.getItemCode();
         txt_itemBuy.setText(selected.getItembuy()+"");
         txt_itemName.setText(selected.getItemName()+"");
         txt_itemPrice.setText(selected.getItemSell()+"");
         txt_notificationNumber.setText(selected.getNotifaction()+"");
         txt_section_id.setText(selected.getSectionName());
         
     
     }
     
     public  void edit (){
     
         try{
         
             String sql = "DELETE FROM `cafeitem` WHERE  id = "+item_id ;
             String sqlUpdateBuyReceipt = "UPDATE cafebuydetails set itemCode = '"+txt_code.getText()+"'WHERE itemCode = "+oldItemID;
             String sqlupdateCustomerReceipt = "UPDATE customerreceiptdetailedinfo set itemCode = '"+txt_code.getText()+"'WHERE itemCode = "+oldItemID;
             dataBase.excuteNonQuery(sqlUpdateBuyReceipt);
             dataBase.excuteNonQuery(sqlupdateCustomerReceipt);
             dataBase.excuteNonQuery(sql);
             addnewItem();
             lbl_message.setText("تم التعديل");
             gettingAllCafeItem();
         }
         catch(SQLException ex){
         lbl_message.setText(ex.getMessage());
         }
         
     }
    public void transferBetweenTextField(){

        if(textFildIndex < allFields.length){
          if(txt_code.getText().equals("")){
          lbl_message.setText("ادخل الكود بطريقة صحيحة (لابد ان لا يبدتي  ب 0)");
          }else{
                      textFildIndex++;

        allFields[textFildIndex].requestFocus();
          }
        }
        else{
        textFildIndex = -1 ;
        }

    
    }

    @Override
    public void handle(KeyEvent event) {
        search();
        
        if(event.getCode().toString().equals("ENTER")){
        transferBetweenTextField();
        }
        if(event.getCode().toString().equals("ENTER")){
            if(event.getCode().toString().equals("ENTER")){
            
                addnewItem();
                txt_code.requestFocus();
            }
            
        
        }
        
    }
    
}
