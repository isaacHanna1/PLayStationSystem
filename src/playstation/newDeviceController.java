/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.newDevice;
import classes.offerClass;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author Seha
 */
public class newDeviceController  implements Initializable{

    @FXML
    private TextField txt_sectionID;

    @FXML
    private TextField txt_deviceNumber;

      @FXML
    private TextField txt_deviceName;

    @FXML
    private TableView<newDevice> receiptTable;
    
    @FXML
    private Label lbl_total;
    
    @FXML
    private TableColumn<newDevice, Integer> col_id;
    @FXML
    private TableColumn<newDevice, Integer> col_section;

    @FXML
    private TableColumn<newDevice, String> col_deviceNumber;

    @FXML
    private TableColumn<newDevice,String> col_deviceName;

    @FXML
    private TableColumn<newDevice,Integer> txt_roomID;

    @FXML
    private TableColumn<newDevice,Float> col_devicePrice;

    @FXML
    private TableColumn<newDevice, String> col_date;

    @FXML
    private TableColumn<newDevice, String> col_note;

    @FXML
    private TextField txt_roomId;

    @FXML
    private TextField txt_notice;

    @FXML
    private DatePicker txt_buyDate;
    
    @FXML
    private TextField txt_price;
    @FXML
    private Label lbl_message;
    String [] allSections ;
    String [] allRooms;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        txt_sectionID.requestFocus();
        
        
        txt_sectionID.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_deviceNumber.requestFocus();
                }
            });
        txt_deviceNumber.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_deviceName.requestFocus();
                }
            });
        
        txt_deviceName.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_roomId.requestFocus();
                }
            });
        txt_roomId.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_price.requestFocus();
                }
            });
        txt_price.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_buyDate.requestFocus();
                }
            });
        txt_buyDate.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_notice.requestFocus();
                }
            });
        
        txt_notice.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){
                    if(event.getCode().toString() == "ENTER"){
                        
                        addBtn();
                                    }

                
                }
            });
        
        
        
        
        
        
        
       allSections = dataBase.gettingColumnFromDatabaseIntoArray("section", "sectionName", "id");
       TextFields.bindAutoCompletion(txt_sectionID, allSections);
       allRooms = dataBase.gettingColumnFromDatabaseIntoArray("rooms", "roomName", "id");
       TextFields.bindAutoCompletion(txt_roomId, allRooms);
      
      col_id.setCellValueFactory(new PropertyValueFactory<newDevice,Integer>("id"));
      col_section.setCellValueFactory(new PropertyValueFactory<newDevice,Integer>("section_id"));
      col_deviceNumber.setCellValueFactory(new PropertyValueFactory<newDevice,String>("deviceNumber"));
      col_deviceName.setCellValueFactory(new PropertyValueFactory<newDevice,String>("deviceName"));
      txt_roomID.setCellValueFactory(new PropertyValueFactory<newDevice,Integer>("room_id"));
      col_devicePrice.setCellValueFactory(new PropertyValueFactory<newDevice,Float>("devicePrice"));
      col_date.setCellValueFactory(new PropertyValueFactory<newDevice,String>("buyDate"));
      col_note.setCellValueFactory(new PropertyValueFactory<newDevice,String>("note"));
      
        gettingAllDevice();
        totalOfBuyingDevices();
    }
    
    public void addBtn(){
        try{
        int sectionID = findID(txt_sectionID.getText(),"section","sectionName");
     
        if(txt_sectionID.getText().equals("")){
            lbl_message.setText("لابد من ادخال القسم ");
            txt_sectionID.requestFocus();
            return;
        }
        String deviceNumber = txt_deviceNumber.getText();
        String deviceName = txt_deviceName.getText();
        if(txt_deviceNumber.getText().equals("") || txt_deviceName.getText().equals("") ){
            lbl_message.setText("لابد من ادخال اسم الجهاز ورقمه");
            txt_sectionID.requestFocus();
            return;
        }
        
        if(txt_roomId.getText().equals("")){
        lbl_message.setText("لابد من ادخال اسم الغرفة");
        txt_sectionID.requestFocus();
            return;
        }
        int roomId = findID(txt_roomId.getText(),"rooms","roomName");
   
        float price = Float.parseFloat(txt_price.getText());
        String date = txt_buyDate.getValue().toString();
        String note = txt_notice.getText();
        int id = 1; 
            if(receiptTable.getItems().size() != 0){
         
        ObservableList<newDevice> items = receiptTable.getItems();
        newDevice getlast = items.get(items.size()-1);
             
        id = getlast.getId()+1;
        
         }
        newDevice newOne = new newDevice(id, sectionID, deviceNumber, deviceName, roomId, price, date, note);
        String result = dataBase.insertNewDevice(sectionID, deviceNumber, deviceName, roomId, price, date, note);
        if(result!=("تمت الاضافة بنجاح")){
            if(result.contains("Duplicate")){
                lbl_message.setText("هناك جهاز بنفس الاسم او نفس");
            }
            return;
        }else{
        receiptTable.getItems().add(newOne);        
        lbl_message.setText(result);
        
        }
        }
        catch(NullPointerException ex){
        
        lbl_message.setText("ادخل البيانات بشكل صحيح ");
        txt_sectionID.requestFocus();
        }
        catch(NumberFormatException ex){
        lbl_message.setText("ادخل البيانات بشكل صحيح ");
        txt_sectionID.requestFocus();
        }
        totalOfBuyingDevices();
        totalOfBuyingDevices();
        txt_sectionID.requestFocus();
    }
     public void gettingAllDevice(){
   
        try {
           
            ObservableList<newDevice> allDevice = FXCollections.observableArrayList();
           
            String sql = "SELECT * FROM `newdevice` ORDER by id";
            Object[] table = dataBase.retrievingTableInArrayOfObject(sql);
           
           
                for (int i = 0; i < table.length; i++) {
                 String [] row  = (String[]) table[i];
                 int id   = Integer.parseInt(row[0]);
                
                int sectionID = Integer.parseInt(row[1]);
                String deviceNumber = row[2];
                String deviceName = row[3];
                int roomId = Integer.parseInt(row[4]);
                float price = Float.parseFloat(row[5]);
                String date = row[6];
                String note = row[7];
                         
                    
        newDevice newOne = new newDevice(id, sectionID, deviceNumber, deviceName, roomId, price, date, note);
        allDevice.add(newOne);
                 
                }
                receiptTable.setItems(allDevice);
                
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
            System.out.println(ex.getCause());
        } 
       
       
   }
   
   public int findID(String data, String tableName , String columnName  ){
    int sectionId = 0;
       try{
       String sql = "SELECT id from "+tableName+" WHERE "+columnName+" = '"+data+"'";
        sectionId = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction(sql));
       }
       catch(SQLException ex){
           dataBase.showMessageJOP(ex.getMessage());
           return 0;
       }
               return sectionId;
    }    
   
   
   public void showDeviceToText(){
       newDevice selected = receiptTable.getSelectionModel().getSelectedItem();
       txt_deviceName.setText(selected.getDeviceName());
       txt_deviceNumber.setText(selected.getDeviceNumber());
       txt_price.setText(selected.getDevicePrice()+"");
       txt_notice.setText(selected.getNote());
       try{
       txt_roomId.setText(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT roomName FROM rooms where Id = '"+selected.getRoom_id()+"'"));
       txt_sectionID.setText(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sectionName FROM section WHERE id = '"+selected.getSection_id()+"'"));
       }catch(SQLException ex){
           
        dataBase.showMessageJOP(ex.getMessage());
       }
       
       
   }
   
   public void edit(){
   
       newDevice selected = receiptTable.getSelectionModel().getSelectedItem();
       try{
       dataBase.excuteNonQuery("DELETE FROM `newdevice` WHERE id = '"+selected.getId()+"'");
       addBtn();
        }
       catch(SQLException ex){
           dataBase.showMessageJOP(ex.getMessage());
       }
   
   }
   
   public void totalOfBuyingDevices(){
       
        ObservableList<newDevice> items = receiptTable.getItems();
        float total = 0;
        for (int i = 0; i < items.size(); i++) {
           total = total + items.get(i).getDevicePrice();
           
       }
        lbl_total.setText(total+"");
       
   }
}
