
package playstation;

import classes.Room;
import classes.offerClass;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;

public class roomAdminController implements Initializable{

    @FXML
    private TextField txt_roomNum;

    @FXML
    private TextField txt_roomName;

    @FXML
    private RadioButton radioIsVip;

    @FXML
    private TextField txt_roomVipPrice;
    @FXML
    private TextField txt_roomVipPriceMulti;
        @FXML
    private Label lbl_message;
    
    @FXML
    private Label lblPrice;
    
    @FXML
    private Label lblPriceMulti;
    int isSelected = 0 ;
    
    String [] allRoomName = {};
    
    @FXML
    private TableView<Room> table_room;

    @FXML
    private TableColumn<Room, String> col_roomNumber;

    @FXML
    private TableColumn<Room, String> col_roomName;

    @FXML
    private TableColumn<Room, Integer> col_roomType;

    @FXML
    private TableColumn<Room,Integer> col_roomDevice;
    
    int roomID ;//used to edit room data 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // this enter event used to navigate between textbox when user enter Enter Key 
        txt_roomNum.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_roomName.requestFocus();
                }
            });
        txt_roomVipPrice.setOnKeyReleased((event) -> {
            
                if(event.getCode().toString() == "ENTER"){

                txt_roomVipPriceMulti.requestFocus();
                }
            });
     
        
        
                txt_roomName.setOnKeyReleased((event) -> {
                    if( !(radioIsVip.isSelected())){
                        if(event.getCode().toString() == "ENTER"){
                        if(event.getCode().toString() == "ENTER"){
                            System.out.println("hiiiiiiii");
                            btnAdd();
                            txt_roomNum.requestFocus();
                        }
                        }
            }
                    else{
                        
                         txt_roomVipPrice.requestFocus();

                    }});
                txt_roomVipPriceMulti.setOnKeyReleased((event) -> {
                    
                        if(event.getCode().toString() == "ENTER"){
                        if(event.getCode().toString() == "ENTER"){
                            
                            btnAdd();
                            txt_roomNum.requestFocus();
                        }
                        }
            });
        
           txt_roomVipPrice.setVisible(false);
           lblPrice.setVisible(false);
           txt_roomVipPriceMulti.setVisible(false);
           lblPriceMulti.setVisible(false);
           allRoomName = dataBase.gettingColumnFromDatabaseIntoArray("rooms", "roomName","Id");
           TextFields.bindAutoCompletion(txt_roomName, allRoomName);
           
             col_roomNumber.setCellValueFactory(new PropertyValueFactory<Room,String>("roomNumber"));
             col_roomName.setCellValueFactory(new PropertyValueFactory<Room,String>("roomName"));
             col_roomType.setCellValueFactory(new PropertyValueFactory<Room,Integer>("isVip"));
             col_roomDevice.setCellValueFactory(new PropertyValueFactory<Room,Integer>("numberOfDevices"));
             getAllRoom();
             txt_roomNum.requestFocus();
    }
    
    public void radioSelected(){
        
        if(radioIsVip.isSelected()){
        txt_roomVipPrice.setVisible(true);
        lblPrice.setVisible(true);
        txt_roomVipPriceMulti.setVisible(true);
        lblPriceMulti.setVisible(true);
        isSelected = 1;
        txt_roomNum.requestFocus();
        }
        else{
        txt_roomVipPrice.setVisible(false);
        lblPrice.setVisible(false);
        txt_roomVipPriceMulti.setVisible(false);
        lblPriceMulti.setVisible(false);
        lblPriceMulti.setVisible(false);
        txt_roomVipPrice.setText(0+"");
        txt_roomVipPriceMulti.setText(0+"");
        isSelected = 0;
        }
    
    }
    
    public void btnAdd(){
    
        try {
            
    String roomNumber = txt_roomNum.getText();
    String roomName   = txt_roomName.getText();
    if(roomName.equals("")|| roomNumber.equals("")){
    lbl_message.setText("لابد من ادخال جميع البيانات");
    return;
    }
      } catch (NumberFormatException ex) {
          lbl_message.setText("لابد من ادخال جميع البيانات المطلوبة");
     }
    if(radioIsVip.isSelected()){
        
        try {
        float vipSinglePrice = Float.parseFloat(txt_roomVipPrice.getText());
        float vipMultiPrice = Float.parseFloat(txt_roomVipPriceMulti.getText());
            System.out.println(vipSinglePrice);
        if(vipMultiPrice <= 0 || vipSinglePrice  <= 0 || txt_roomVipPrice.getText().equals("") || txt_roomVipPriceMulti.getText().equals("")){
        lbl_message.setText("لابد من ادخال جميع البيانات بشكل صحيح اذا انت مختار نوع الغرفة vip ");
        return;
        }
        
        } catch (NumberFormatException ex) {
         lbl_message.setText("لابد من ادخال جميع البيانات اذا انت مختار نوع الغرفة vip ");
        }
    
        
    }
    
   
    
    String result  = dataBase.insertDataOfRoom(txt_roomNum.getText(), txt_roomName.getText(),isSelected, Float.parseFloat(txt_roomVipPrice.getText()),Float.parseFloat(txt_roomVipPriceMulti.getText()));
    if(result.contains("Duplicate")){
        lbl_message.setText("هذا الرقم او الاسم موجودين بالفعل ");
    }else{
    lbl_message.setText(result);
    }
    System.out.println(lbl_message);
    dataBase.inserActions(loginController.loginUser.getUserName() +"قام باضافة غرفة جديدة");

        table_room.getItems().clear();
        getAllRoom();    
    }
    
    public void SearchBtn(){
        String roomName = txt_roomName.getText();
        try {
            String [] roomData = dataBase.retrievingRowIntableInArray("SELECT id , roomNumber , roomName , isVIB , vibHourPrice , vipPriceMulti FROM rooms WHERE roomName = '"+roomName+"'");
            roomID = Integer.parseInt(roomData[0]);
            txt_roomName.setText(roomData[2]);
            txt_roomNum.setText(roomData[1]);
            int isVIP = Integer.parseInt(roomData[3]);
            if(isVIP == 1){
                    txt_roomVipPrice.setVisible(true);
                    lblPrice.setVisible(true);
                    txt_roomVipPriceMulti.setVisible(true);
                    lblPriceMulti.setVisible(true);
                    radioIsVip.setSelected(true);
                    txt_roomVipPrice.setText(roomData[4]);
                    txt_roomVipPriceMulti.setText(roomData[5]);
            }else{
                txt_roomVipPrice.setVisible(false);
                    lblPrice.setVisible(false);
                    txt_roomVipPriceMulti.setVisible(false);
                    lblPriceMulti.setVisible(false);
                    radioIsVip.setSelected(false);
            
            }
            
            
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
    }
    
    public void editBtn(){
    
        String roomName = txt_roomName.getText();
        String roomNumber = txt_roomNum.getText();
        int isVIP = 0 ;
        if(radioIsVip.isSelected()){
        isVIP = 1 ;
        }
        float vibHourPrice = Float.parseFloat(txt_roomVipPrice.getText());
        float vibHourPriceMulti =Float.parseFloat(txt_roomVipPriceMulti.getText());
        
        
        try {
            dataBase.excuteNonQuery("UPDATE rooms set roomNumber = '"+roomNumber+"', roomName = '"+roomName+"' , isVIB = "+isVIP+",vibHourPrice = "+vibHourPrice+", vipPriceMulti = "+vibHourPriceMulti+"WHERE Id = "+roomID);
            lbl_message.setText("تم التعديل");
        } catch (SQLException ex) {
            if(ex.getMessage().contains("Duplicate")){
            lbl_message.setText("هناك غرفة بهذا الاسم او هذا الرقم من قبل");
            }
        }
        table_room.getItems().clear();
        getAllRoom();
    }
    
    
    public void getAllRoom(){
        
    
        try {
            ObservableList<Room> allRoom = FXCollections.observableArrayList();
           
            String sql = "SELECT `roomNumber`,`roomName`,`isVIB`  FROM `rooms` ORDER by Id";
            Object[] table = dataBase.retrievingTableInArrayOfObject(sql);
            
            
            for (int i = 0; i < table.length; i++) {
                String [] row  = (String[]) table[i];
                
                String roomNumber = row[0];
                String roomName = row[1];
                int isVip = Integer.parseInt(row[2]);
                
                Room newOne = new Room(roomNumber, roomName, isVip, 0);
                allRoom.add(newOne);
            }
            table_room.setItems(allRoom);
        
        } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
}
    
    public void onMouseClickedInTable(){
        Room object = table_room.getSelectionModel().getSelectedItem();
        txt_roomName.setText(object.getRoomName());
        SearchBtn();
    
    }
}