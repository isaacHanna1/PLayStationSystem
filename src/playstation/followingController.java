  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.RunningDevice;
import classes.cutomerReceipt;
import classes.offerClass;
import classes.player;
import classes.safe;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
import org.joda.time.LocalDate;
import static playstation.contollerOfcontrol.allDevicesRunningNow;
import static playstation.dataBase.autoTranscation;

/**
 *
 * @author Seha
 */
public class followingController implements Initializable, EventHandler<javafx.event.ActionEvent>{

    
    @FXML
    private BorderPane container;
    @FXML
    private Pane orderPane;
    @FXML
    private Pane payPane;

    @FXML
    private Pane cafePane;

    @FXML
    private Label lbl_palyerName;

    @FXML
    private Label lbl_roomName;

    @FXML
    private Label lbl_sectionName;

    @FXML
    private Label lbl_deviceBumber;

    @FXML
    private ComboBox<String> combo_prices;

    @FXML
    private TextField txt_startTime;

    @FXML
    private TextField txt_endTime;

    @FXML
    private Button btnSaveAndChangeState;
    @FXML
    private TextField txt_devicePrice;
    @FXML
    private TableView<cutomerReceipt> tabel_receipt;

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
    private TextField txt_codeItem;
    @FXML
    private RadioButton radioName;

    @FXML
    private Label lbl_total;

    @FXML
    private ImageView settingIcon;
    @FXML
    private Label lbl_reciptNumber;
    @FXML
    private TextField txt_quntity;
    @FXML
    private ComboBox<String> combo_repositorty;
    @FXML
    private Button startBtn;
     @FXML
   private  ToolBar groupOFButton ;
     @FXML
    private TextField txt_payedBefor;
    @FXML
    private Pane controlTimePane;
    
    @FXML
    private TextField txt_mustpayed;
     int sectionID;
    offerClass[] allOffer = {};
    String[] allItem={};
    Button[] allOfferButton;
    float singlePrice = 0;
    float multiPrice = 0;
    float gamePrice = 0;
    boolean isVIP = false;
    int room_id = 0;
    boolean isOpen = false;
    boolean isOffer = false ;
    static int offer_id = -1 ;
    float handlePrice = 0 ; // السعر اللي اتحاسب بيه العميل

    @FXML
    private TextField txt_disscount;

    @FXML
    private TextField txt_order_andDevice;

    @FXML
    private TextField txt_payed;

    @FXML
    private TextField txt_reminder;

    @FXML
    private TextField txt_order;
    @FXML
    private Button btn_startChange;
    
    @FXML
    private TextArea txt_fieldALLINfo;

    @FXML
    private Button buttonAddOrder;
    @FXML
    private Pane paneOrder;
    @FXML
    private Button btn_editTime;
   @FXML
    private ToolBar buttonscontroll;
    int numberOfItemInReceiptTable = 0 ; // دة انا عاملة علشان اسجل العناصر الجديدة بس في جدول الفاتورة
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
        
        try {
            txt_payed.setOnKeyReleased((event) -> {
                finalPrice();
            });
            controlTimePane.setVisible(false);
            combo_prices.getItems().addAll("single", "multi");           
            //to show that device is open or not 
            String isAreadyOpen = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Device_id from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID +" and stillRunning = 1");
            paneOrder.setVisible(false);
            buttonAddOrder.setVisible(true);
            txt_fieldALLINfo.setVisible(false);
            if(isAreadyOpen.equals("")){
                // device off 
            btn_editTime.setVisible(false);
            buttonscontroll.setVisible(false);
            payPane.setVisible(false);
            buttonAddOrder.setVisible(false);
            System.out.println("start new time here ");
            lbl_palyerName.setText(playerController.currentPlayer.getPlayerName());
            System.out.println(playerController.currentPlayer.getPlayerID());
            String[] dataOfDevice = dataBase.retrievingRowIntableInArray("select rooms.roomName , section.sectionName ,deviceName ,section_id , rooms.Id FROM newdevice JOIN rooms , section where rooms.Id = newdevice.room_id and section.id = newdevice.section_id and newdevice.id = " + contollerOfcontrol.deviceID);
            lbl_deviceBumber.setText(dataOfDevice[2]);
            lbl_roomName.setText(dataOfDevice[0]);
            lbl_sectionName.setText(dataOfDevice[1]);
            String getValueOFISVip = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT isVIB from rooms where Id = " + dataOfDevice[4]);
            combo_prices.getSelectionModel().selectFirst();
            getCurrentTime();
            sectionID = Integer.parseInt(dataOfDevice[3]);
            addOfferISFound();
            System.out.println("the room id : "+dataOfDevice[4]);
            if (Integer.parseInt(getValueOFISVip) == 1) {
                isVIP = true;
                System.out.println("the room is vip");
                room_id = Integer.parseInt(dataOfDevice[4]);
                combo_prices.getItems().remove(2);
            }
            }else{
            btn_editTime.setVisible(true);
            payPane.setVisible(false);
            String [] somedeviceInfo = dataBase.retrievingRowIntableInArray("SELECT newdevice.deviceNumber ,newdevice.deviceName , section.sectionName , rooms.roomName from newdevice join section, rooms WHERE section.id = newdevice.section_id and newdevice.room_id = rooms.Id and newdevice.id = "+contollerOfcontrol.deviceID);
            String[] dataOfDevice = dataBase.retrievingRowIntableInArray("select rooms.roomName , section.sectionName ,deviceName ,section_id , rooms.Id FROM newdevice JOIN rooms , section where rooms.Id = newdevice.room_id and section.id = newdevice.section_id and newdevice.id = " + contollerOfcontrol.deviceID);
            String getValueOFISVip = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT isVIB from rooms where Id = " + dataOfDevice[4]);
            System.out.println("the room id : "+dataOfDevice[4]);
            if (Integer.parseInt(getValueOFISVip) == 1) {
                isVIP = true;
                room_id = Integer.parseInt(dataOfDevice[4]);
                combo_prices.getItems().remove(2);
            }
            sectionID = Integer.parseInt(dataOfDevice[3]);
            lbl_deviceBumber.setText( somedeviceInfo[1]);
            lbl_sectionName.setText(somedeviceInfo[2]);
            lbl_roomName.setText(somedeviceInfo[3]);
            String maxID = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT MAX(id) FROM followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID);
            String [] remainingDeviceInfo = dataBase.retrievingRowIntableInArray("SELECT  son.name , followingdevice.startTime , followingdevice.endTime , followingdevice.timeType ,  son.Id  from followingdevice JOIN son WHERE son.Id = followingdevice.player_id and followingdevice.stillRunning = 1  and followingdevice.id = '"+maxID+"' and followingdevice.Device_id = "+contollerOfcontrol.deviceID);
           
            lbl_palyerName.setText(remainingDeviceInfo[0]);
            txt_startTime.setText(remainingDeviceInfo[1]);
            txt_endTime.setText(remainingDeviceInfo[2]);   
            combo_prices.getSelectionModel().select(Integer.parseInt(remainingDeviceInfo[3]));
            playerController.currentPlayer = new player(Integer.parseInt(remainingDeviceInfo[4]),lbl_palyerName.getText(), "0", 0);
            startBtn.setVisible(false);
            searchAboutOrder();
            }
             lbl_reciptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
             allItem = dataBase.gettingColumnFromDatabaseIntoArray("cafeitem", "itemCode", "id");
            TextFields.bindAutoCompletion(txt_codeItem  , allItem);
            dataBase.putingColumnFromDataBaseIntoCombox("SELECT repositoryName from repository ORDER BY id", combo_repositorty);
            combo_repositorty.getSelectionModel().select(0);
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
        }
        
            });
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            dataBase.showMessageJOP(ex.getMessage());
        }

        numberOfItemInReceiptTable = tabel_receipt.getItems().size();
        System.out.println("the number of items "+numberOfItemInReceiptTable);
        calcTotalOfOrder();
        btn_startChange.setVisible(false);
        btnSaveAndChangeState.setVisible(false);
      
    
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
            cutomerReceipt newOne = new cutomerReceipt(0,codeITem, itemName, quantity, pay,totoal,(combo_repositorty.getSelectionModel().getSelectedIndex()+1),"no");
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
        
   public boolean  saveMainData(){
   
        
         
        try{
        String  receieptNumber = lbl_reciptNumber.getText();
        int userID   = loginController.loginUser.getID() ;       
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        String receiptDate = ( sdf.format(new Date()));
        Date today = new Date();
        java.util.Date dateStr = sdf.parse(receiptDate);
        java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
        String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+" and stillRunning =1 "); 
        String isLoadedInMainData = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT `receiptNumber` FROM `customerreceiptmaininfo` WHERE transactionNumber = '"+transactionNumber+"'");
        int player_id = playerController.currentPlayer.getPlayerID();
        if(isLoadedInMainData.equals("")){
        String result = dataBase.saveMainCustomerCafeReceipt(Integer.parseInt(receieptNumber), Integer.parseInt(transactionNumber),userID, dateDB,player_id);
        return true ; //لو سجل فاتورة جديدة يبقي في رقم جديد 
        }
        else{
        
        return false ; // يبقي لازم اجيب رقم الفاتورة القديم
        }
        }catch(Exception ex){ 
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
         
        
        return false;

        }
        
        
        
   
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
        tabel_receipt.getItems().clear();
            searchAboutOrder();
       }
       else{
                   
       String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+" and stillRunning =1 "); 
       String receiptNumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT `receiptNumber` FROM `customerreceiptmaininfo` WHERE transactionNumber = '"+transactionNumber+"'");
        System.out.println("items count "+ numberOfItemInReceiptTable);
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
         } catch(Exception ex ){
                System.out.println(ex.getMessage());
            }
           

   }
   
   public void saveButton(){
   
       if(tabel_receipt.getItems().size()<=0){
             Alert alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("تحذير");
                alert.setContentText("لا توجد بيانات لحفظها");
                alert.showAndWait();
                 
             
           
           return;
       
       }
        try {
            
            saveDetailedData();
            numberOfItemInReceiptTable = tabel_receipt.getItems().size();
            lbl_reciptNumber.setText(dataBase.autoNumber("customerReceiptMAinInfo", "receiptNumber"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
   }
   public void calcTotalOfOrder(){
   
       float total = 0 ;
       for (int i = 0; i < tabel_receipt.getItems().size() ; i++) {
            total = total + tabel_receipt.getItems().get(i).getTotal();
       }
       lbl_total.setText(total+"");
   }
   public void searchAboutOrder(){
       ObservableList<cutomerReceipt> allITems = FXCollections.observableArrayList();
        try {
            String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
            
            String receiptNumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT receiptNumber from customerreceiptmaininfo where transactionNumber = "+transactionNumber);
            Object[] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM customerreceiptdetailedinfo WHERE receiptNumber = '"+receiptNumber+"'");
           if(!(table.length == 0)){
            
                for (int i = 0; i < table.length; i++) {
                 String [] row  = (String[]) table[i];
                 int id   = Integer.parseInt(row[0]);
                 long itemCode = Long.parseLong(row[2]);
                 String itemName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT item_name from cafeitem WHERE itemCode = "+itemCode);
                 float quantity = Float.parseFloat(row[3]);
                 float pay = Float.parseFloat(row[4]);
                 int repository_id = Integer.parseInt(row[5]);
                 cutomerReceipt newOne = new cutomerReceipt(0,itemCode, itemName, quantity, pay, pay*quantity, repository_id,"yes");
                 allITems.add(newOne);
                 
                }
                tabel_receipt.setItems(allITems);
            }
           calcTotalOfOrder();
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            dataBase.showMessageJOP(ex.getMessage());
        }
   }
    public float[] getDifferenceBetweenTwoTime(String time2, String time1) {

        
        float[] times = new float[3];
        
        
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                        "HH:mm:ss"); 

        Date d1;
        Date d2;
        
        try {
            
            String split1 = time1.substring(0, 2);
            String split2 = time2.substring(0, 2);
            
            int split1ToInteger =  Integer.parseInt(split1);
            int split2ToInteger =  Integer.parseInt(split2);
            if(split1ToInteger>split2ToInteger){
            split1ToInteger = split1ToInteger -12 ;
            split2ToInteger = split2ToInteger +12 ;
            time1 = split1ToInteger+""+time1.substring(2,7);
            time2 = split2ToInteger+""+time2.substring(2,7);
            
            }
            
            d1 = sdf.parse(time1);
            d2 = sdf.parse(time2);
            
            
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.println(diffDays + " days, ");
            System.out.println(diffHours + " hours, ");
            times[1] = diffHours;
            System.out.println(diffMinutes + " minutes, ");
            times[0] = diffMinutes;
            System.out.println(diffSeconds + " seconds.");
            times[0] = diffMinutes;
            
        
        } catch (ParseException ex) {
            Logger.getLogger(PlayStation.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        txt_fieldALLINfo.appendText("عدد الساعات : "+times[1]+"\n");
        txt_fieldALLINfo.appendText("عدد الدقايق "+times[0]+"\n");
        System.out.println("the number of minits is : " + times[0]);
        System.out.println("the number of hour is : " + times[1]);
        return times;
    }

    public void getCurrentTime() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        txt_startTime.setText(dtf.format(localTime));
        txt_endTime.setText(dtf.format(localTime));

    }

    public void addOfferISFound() {

        try {
            Object[] table = dataBase.retrievingTableInArrayOfObject("SELECT id , offerName , mainUnit , addUnit from offer where DATE(startDate)<=CURDATE() and CURDATE()<=DATE(endDate) and section_id = " + sectionID);
            System.out.println("the length of table offer is : "+table.length);
            allOffer = new offerClass[table.length];
            for (int i = 0; i < table.length; i++) {
                String[] row = (String[]) table[i];
                int offer_id = Integer.parseInt(row[0]);
                String offerName = row[1];
                float mainUnit = Float.parseFloat(row[2]);
                float addUnit = Float.parseFloat(row[3]);
                System.out.println("data is "+row[0]+"   "+row[1]+"   "+row[2]+"   "+row[3]+"   ");
                offerClass newOne = new offerClass(offer_id, offerName, mainUnit, addUnit);
                allOffer[i] = newOne;
                
                Button newOfferButton = new Button(newOne.getOfferName());
                allOfferButton = new Button[table.length];
                allOfferButton[i] = newOfferButton;
                System.out.println("the length of button "+ allOfferButton.length);
                newOfferButton.setStyle("-fx-background-color:#7952B3;" + "-fx-font-size:14;" + "-fx-background-radius: 5px;" + "-fx-text-fill:#fff;");
                System.out.println("style done");
                groupOFButton.getItems().add(newOfferButton);
                System.out.println("group of buttons is bdan ");
                if (!isOpen) {
                    newOfferButton.setOnAction(this);
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }
        catch( Exception ex){
            System.out.println("");
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }

    }

    public void addHourToCurrentTime() {
        System.out.println("hand held");
        if (!isOpen && !isOffer) {
            try {
                if(groupOFButton.getItems().size()>6){
                groupOFButton.getItems().remove(6, 6+allOfferButton.length);
                }
                
                Calendar now = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date dateInStartText = format.parse(txt_endTime.getText());
                now.setTime(dateInStartText);
                now.add(Calendar.HOUR, 1);
                txt_endTime.setText(format.format(now.getTime()) + "");
            } catch (ParseException ex) {
                dataBase.showMessageJOP(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }

    }

    public void addHalfToCurrentTime() {
        if (!isOpen && !isOffer) {
            try {
                if(groupOFButton.getItems().size()>6){
                groupOFButton.getItems().remove(6, 6+allOfferButton.length);
                }
                Calendar now = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date dateInStartText = format.parse(txt_endTime.getText());
                now.setTime(dateInStartText);
                now.add(Calendar.MINUTE, 30);
                txt_endTime.setText(format.format(now.getTime()) + "");
            } catch (ParseException ex) {
                dataBase.showMessageJOP(ex.getMessage());
                System.out.println(ex.getMessage());
            }

        }
    }

    public void addThirdToCurrentTime() {
        if (!isOpen && !isOffer) {
            try {
                if(groupOFButton.getItems().size()>6){
                groupOFButton.getItems().remove(6, +allOfferButton.length);
                }
                Calendar now = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date dateInStartText = format.parse(txt_endTime.getText());
                now.setTime(dateInStartText);
                now.add(Calendar.MINUTE, 20);
                txt_endTime.setText(format.format(now.getTime()) + "");
            } catch (ParseException ex) {
                dataBase.showMessageJOP(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }
    }

    public void addQuartarToCurrentTime() {
        if (!isOpen && !isOffer) {
            try {
                if(groupOFButton.getItems().size()>6){
                groupOFButton.getItems().remove(6, 6+allOfferButton.length);
                }
                Calendar now = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date dateInStartText = format.parse(txt_endTime.getText());
                now.setTime(dateInStartText);
                now.add(Calendar.MINUTE, 15);
                txt_endTime.setText(format.format(now.getTime()) + "");
            
            }catch (ParseException ex) {
                dataBase.showMessageJOP(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }

    }
    
    public void add5MinitsToCurrentTime() {
        if (!isOpen && !isOffer) {
            try {
                if(groupOFButton.getItems().size()>6){
                groupOFButton.getItems().remove(6, 6+allOfferButton.length);
                }
                Calendar now = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date dateInStartText = format.parse(txt_endTime.getText());
                now.setTime(dateInStartText);
                now.add(Calendar.MINUTE, 5);
                txt_endTime.setText(format.format(now.getTime()) + "");
            
            }catch (ParseException ex) {
                dataBase.showMessageJOP(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }

    }

    public void calcTotalTime (){
    
      if (isVIP) {

                    String[] data = getPriceIfRoomVip();
                    System.out.println(data[0]);
                    System.out.println(data[1]);

                    float total = 0;
                    if (isOpen) {
                        txt_devicePrice.setText("?");
                        return;
                    }

                    if (combo_prices.getSelectionModel().getSelectedIndex() == 0) {
                        singlePrice = Float.parseFloat(data[0]);
                        float priceOfMinit = singlePrice / 60;
                        float[] time = getDifferenceBetweenTwoTime(txt_endTime.getText(), txt_startTime.getText());
                        // time[0] = number of hours  
                        // time[1] = number of mints 
                        total = time[0] * priceOfMinit + time[1] * singlePrice;
                        System.out.println("total  :" + total);

                    } else if (combo_prices.getSelectionModel().getSelectedIndex() == 1) {
                        multiPrice = Float.parseFloat(data[1]);

                        float priceOfMinit = multiPrice / 60;
                        float[] time = getDifferenceBetweenTwoTime(txt_endTime.getText(), txt_startTime.getText());
                        total = time[0] * priceOfMinit + time[1] * multiPrice;
                        System.out.println("total   :" + total);

                    }

                    NumberFormat df = DecimalFormat.getInstance();
                    df.setMinimumFractionDigits(2);
                    df.setMaximumFractionDigits(2);

                    txt_devicePrice.setText(df.format(total) + "");

                } else {
                    String[] data = getPriceIfNotVip();
                    float total;
                    if (isOpen) {
                        txt_devicePrice.setText("?");
                        return;
                    }

                    if (combo_prices.getSelectionModel().getSelectedIndex() == 0) {
                        singlePrice = Float.parseFloat(data[0]);
                        float priceOfMinit = singlePrice / 60;
                        float[] time = getDifferenceBetweenTwoTime(txt_endTime.getText(), txt_startTime.getText());
                        total = (time[0] * priceOfMinit) + (time[1] * singlePrice);
                        System.out.println("total  :" + total);

                    } else if (combo_prices.getSelectionModel().getSelectedIndex() == 1) {
                        multiPrice = Float.parseFloat(data[1]);

                        float priceOfMinit = multiPrice / 60;
                        float[] time = getDifferenceBetweenTwoTime(txt_endTime.getText(), txt_startTime.getText());
                        total = (time[0] * priceOfMinit) + (time[1] * multiPrice);
                        System.out.println("total   :" + total);

                    } else {
                        gamePrice = Float.parseFloat(data[2]);
                        String numberOfGames = JOptionPane.showInputDialog("ادخل عدد ؟");
                        total = Float.parseFloat(numberOfGames) * gamePrice;
                        System.out.println("total   :" + total);
                    }

                    NumberFormat df = DecimalFormat.getInstance();
                    df.setMinimumFractionDigits(2);
                    df.setMaximumFractionDigits(2);

                    txt_devicePrice.setText(df.format(total) + "");

                }
        
    }
    
    public void getTotalPriceOfDevice(){
        float total = 0;
        System.out.println(isVIP);
        if(isVIP){
            
            try {
                String []vipPrice = getPriceIfRoomVip();
                
                String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
                System.out.println("the transaction number     :" + transactionNumber);
                Object []table = dataBase.retrievingTableInArrayOfObject("SELECT startTime , endTime , isOffer , offer_id , Device_id  , timeType  from followingdevice  WHERE transactionNumber = '"+transactionNumber+"'");
                System.out.println(table.length);
                if(!(table.length == 0)){
                System.out.println("enter here");
                for (int i = 0; i < table.length; i++) {
                 String [] row  = (String[]) table[i];
                 System.out.println(row.length);
                 String startTime  = row[0];
                 String endTime  = row[1];
                 int isOffer     = Integer.parseInt(row[2]);
                 int offeriD     = Integer.parseInt(row[3]);
                 int deviceId = Integer.parseInt(row[4]);
                 int timeType = Integer.parseInt(row[5]);
                 System.out.println("here");
                 if(offer_id != -1){
                     System.out.println("we here ya sehaaaaa");
                     txt_fieldALLINfo.appendText("العرض\n");
                     String []mainAddUnit = dataBase.retrievingRowIntableInArray("SELECT mainUnit , addUnit FROM offer where id = '"+offeriD+"'");
                     float mainUnit = Float.parseFloat(mainAddUnit[0]);
                     float addUnit  = Float.parseFloat(mainAddUnit[1]);
                     float []times    =getDifferenceBetweenTwoTime(endTime, startTime);
                     float totalRealTimesInMinits = ((times[1])*60)+times[0];
                     float totalOfOfferInMinits = mainUnit+addUnit;
                     if(totalRealTimesInMinits > totalOfOfferInMinits){
                     
                         if(timeType == 0){
                             float Difference = totalRealTimesInMinits - totalOfOfferInMinits ;
                            float priceOfMinit = Float.parseFloat(vipPrice[0]) /60;
                             total = total+ Difference * priceOfMinit;
                             handlePrice = Float.parseFloat(vipPrice[0]);
                         }
                         else{
                             float Difference = totalRealTimesInMinits - totalOfOfferInMinits ;
                             float priceOfMinit = Float.parseFloat(vipPrice[1]) /60;
                             total = total+ Difference * priceOfMinit;
                             handlePrice = Float.parseFloat(vipPrice[1]);
                         }
                     }
                     
                         if(timeType == 0){
                         float numberofOffer = totalRealTimesInMinits / totalOfOfferInMinits ;
                             System.out.println(numberofOffer);
                         total = total + (numberofOffer * Float.parseFloat(vipPrice[0]));
                         txt_fieldALLINfo.appendText("فردي\n");
                          handlePrice = Float.parseFloat(vipPrice[0]);
                             
                         }
                         else{
                         float numberofOffer = totalRealTimesInMinits / totalOfOfferInMinits ;
                           total = total+ (numberofOffer * Float.parseFloat(vipPrice[1]));
                         txt_fieldALLINfo.appendText("زوجي\n");
                         handlePrice = Float.parseFloat(vipPrice[1]);
                         }
                         txt_fieldALLINfo.appendText("الحساب\n"+total);
                         txt_fieldALLINfo.appendText("------------\n");
                     
                     }
                 
                 else{
                     txt_fieldALLINfo.appendText("بدون عرض\n");
                 float []times = getDifferenceBetweenTwoTime(endTime, startTime);
                 
                 if(timeType == 0){
                     System.out.println("single");
                     txt_fieldALLINfo.appendText("فردي\n");
                  float priceOfMinit = Float.parseFloat(vipPrice[0])/60;
                   total = total+((times[1]*Float.parseFloat(vipPrice[0]))+(times[0]*priceOfMinit));
                     System.out.println(total);
                     txt_fieldALLINfo.appendText("الحساب"+total+"\n");
                     txt_fieldALLINfo.appendText("--------------\n");
                    handlePrice = Float.parseFloat(vipPrice[0]);
                 }
                 else{
                     System.out.println("multi");
                     txt_fieldALLINfo.appendText("زوجي\n");
                     System.out.println("total in single " +total);
                  float priceOfMinit = Float.parseFloat(vipPrice[1])/60;
                   total = total+((times[1]*Float.parseFloat(vipPrice[1]))+(times[0]*priceOfMinit));
                  handlePrice = Float.parseFloat(vipPrice[1]);
                    System.out.println(total);
                    txt_fieldALLINfo.appendText("الحساب"+total+"\n");
                 }
                 
                 }
                 
                }

                
                    NumberFormat df = DecimalFormat.getInstance();
                    df.setMinimumFractionDigits(2);
                    df.setMaximumFractionDigits(2);

                    txt_devicePrice.setText(Math.round(total)+ "");
            }
            }catch (SQLException ex) {
                    System.out.println(ex.getCause());
                    System.out.println(ex.getMessage());
            }
    
            }
        else{
            txt_fieldALLINfo.appendText("ليست vip \n");
            try {
                String []NOTvipPrice = getPriceIfNotVip();
                System.out.println("the length of notvip is "+NOTvipPrice.length);
                String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
                System.out.println("the transaction number     :" + transactionNumber);
                Object []table = dataBase.retrievingTableInArrayOfObject("SELECT startTime , endTime , isOffer , offer_id , Device_id  , timeType  from followingdevice  WHERE transactionNumber = '"+transactionNumber+"'");
                System.out.println(table.length);
                if(!(table.length == 0)){
                    System.out.println("enter here");
                    for (int i = 0; i < table.length; i++) {
                        String [] row  = (String[]) table[i];
                        System.out.println(row.length);
                        String startTime  = row[0];
                        String endTime  = row[1];
                        int isOffer     = Integer.parseInt(row[2]);
                        int offeriD     = Integer.parseInt(row[3]);
                        int deviceId = Integer.parseInt(row[4]);
                        int timeType = Integer.parseInt(row[5]);
                        System.out.println("here");
                        if(offer_id != -1){
                            txt_fieldALLINfo.appendText("عرض\n");
                            System.out.println("we here ya sehaaaaa");
                            String []mainAddUnit = dataBase.retrievingRowIntableInArray("SELECT mainUnit , addUnit FROM offer where id = '"+offeriD+"'");
                            float mainUnit = Float.parseFloat(mainAddUnit[0]);
                            float addUnit  = Float.parseFloat(mainAddUnit[1]);
                            float []times    =getDifferenceBetweenTwoTime(endTime, startTime);
                            float totalRealTimesInMinits = ((times[1])*60)+times[0];
                            float totalOfOfferInMinits = mainUnit+addUnit;
                            if(totalRealTimesInMinits > totalOfOfferInMinits){
                                
                                if(timeType == 0){
                                    float Difference = totalRealTimesInMinits - totalOfOfferInMinits ;
                                    float priceOfMinit = Float.parseFloat(NOTvipPrice[0]) /60;
                                    total =total+  Difference * priceOfMinit;
                                    handlePrice = Float.parseFloat(NOTvipPrice[0]);
                                }
                                else{
                                    float Difference = totalRealTimesInMinits - totalOfOfferInMinits ;
                                    float priceOfMinit = Float.parseFloat(NOTvipPrice[1]) /60;
                                    total = Difference * priceOfMinit;
                                    handlePrice = Float.parseFloat(NOTvipPrice[1]);
                                }
                            }
                            
                            if(timeType == 0){
                                txt_fieldALLINfo.appendText("فردي\n");
                                float numberofOffer = totalRealTimesInMinits / totalOfOfferInMinits ;
                                System.out.println(numberofOffer);
                                total = total + (numberofOffer * Float.parseFloat(NOTvipPrice[0]));
                                txt_fieldALLINfo.appendText("الحساب"+total+"\n");
                                handlePrice = Float.parseFloat(NOTvipPrice[0]);
                            }
                            else{
                                 txt_fieldALLINfo.appendText("زرجي\n");
                                float numberofOffer = totalRealTimesInMinits / totalOfOfferInMinits ;
                                total = total+ (numberofOffer * Float.parseFloat(NOTvipPrice[1]));
                                 txt_fieldALLINfo.appendText("الحساب"+total+"\n");
                                 handlePrice = Float.parseFloat(NOTvipPrice[1]);
                                 
                            }
                            
                        }
                        
                        else{
                            float []times = getDifferenceBetweenTwoTime(endTime, startTime);
                            
                            if(timeType == 0){
                                System.out.println("single");
                            txt_fieldALLINfo.appendText("فردي"+"\n");
                                float priceOfMinit = Float.parseFloat(NOTvipPrice[0])/60;
                                total = total+((times[1]*Float.parseFloat(NOTvipPrice[0]))+(times[0]*priceOfMinit));
                                System.out.println(total);
                               txt_fieldALLINfo.appendText("الحساب"+total+"\n");
                               handlePrice = Float.parseFloat(NOTvipPrice[0]);
                            }
                            else{
                                System.out.println("multi");
                                txt_fieldALLINfo.appendText("زوجي"+"\n");
                                System.out.println("total in single " +total);
                                float priceOfMinit = Float.parseFloat(NOTvipPrice[1])/60;
                                total = total+((times[1]*Float.parseFloat(NOTvipPrice[1]))+(times[0]*priceOfMinit));
                               txt_fieldALLINfo.appendText("الحساب"+total+"\n");
                                System.out.println(total);
                                handlePrice = Float.parseFloat(NOTvipPrice[1]);
                            }
                            
                        }
                        
                        
                        
                        
                    }
                    
                    
                    txt_devicePrice.setText(Math.round(total)+ "");
                }       } catch (SQLException ex) {
                            System.out.println(ex.getCause());
                            System.out.println(ex.getMessage());
            }
        }
    }
    
    public void open() {
        txt_endTime.setText("؟");
        isOpen = true;

    }
    
    public void FinishTime(){
        try {
            String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
            String maxID             =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Max(id) from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
            
           LocalTime localTime = LocalTime.now();
           Calendar c = Calendar.getInstance();
           c.add(Calendar.MINUTE, -1);
           Date currentDateMinsOne = c.getTime();
          dataBase.excuteNonQuery("UPDATE followingdevice set endTime = '"+dateFormat2.format(currentDateMinsOne)+"' WHERE transactionNumber = '"+transactionNumber+"' and id = '"+maxID+"'" );
          getTotalPriceOfDevice();
          searchAboutOrder();
          txt_order.setText(lbl_total.getText());
          int customerAccountID =Integer.parseInt( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son where name = '"+lbl_palyerName.getText()+"'"));
          String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+customerAccountID+"");
          
          txt_payedBefor.setText(((Integer.parseInt(lifeCycleOfCustomer))*-1)+"");
        float total = Float.parseFloat(txt_order.getText())+Float.parseFloat(txt_devicePrice.getText());
        txt_order_andDevice.setText(total+"");
        float PaidBefor = Float.parseFloat(txt_payedBefor.getText());
        
         float mustPaid = total -PaidBefor ;
         
          txt_mustpayed.setText(mustPaid+"");
          btnSaveAndChangeState.setVisible(true);
          payPane.setVisible(true);
          paneOrder.setVisible(true);
          txt_fieldALLINfo.setVisible(true);
          
          txt_payed.requestFocus();
        } catch (SQLException ex) {
                System.out.println(ex.getCause());
                System.out.println(ex.getMessage());
        }
    
    }
    public void finalPrice (){
    
        float paied = Float.parseFloat(txt_payed.getText());
        float mustPaid = Float.parseFloat(txt_order_andDevice.getText()) - Float.parseFloat(txt_payedBefor.getText());
        txt_mustpayed.setText(mustPaid+"");
        float reminder = paied -mustPaid; 
        txt_reminder.setText(reminder+"");
        
    }
    public void deleteItem(){
       try{
       int index = tabel_receipt.getSelectionModel().getSelectedIndex();
       cutomerReceipt selectedItem = tabel_receipt.getSelectionModel().getSelectedItem();
       long code = selectedItem.getCode();
       float quantity = selectedItem.getQuantity();
       String transactionNumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+" and stillRunning =1 "); 
       String receiptNumber = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT receiptNumber from customerreceiptmaininfo WHERE transactionNumber = '"+transactionNumber+"'");
       String itemId        = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from customerreceiptdetailedinfo WHERE receiptNumber = '" +receiptNumber+"' and itemCode = '"+code+"' and quantity = '"+quantity+"'");
       if(tabel_receipt.getItems().get(index).getSavedInDataBase().equals("yes")){
       dataBase.excuteNonQuery("DELETE from customerreceiptdetailedinfo WHERE receiptNumber = '" +receiptNumber+"' and itemCode = '"+code+"' and quantity = '"+quantity+"' and id = '"+itemId+"'");
       tabel_receipt.getItems().remove(index);
       }else{
       tabel_receipt.getItems().remove(index);
       }
       
       }catch(SQLException ex){
           dataBase.showMessageJOP(ex.getMessage());
       }
       calcTotalOfOrder();
       
       
   }
   
    public void cleartxtEnd() {

        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        txt_endTime.setText(txt_startTime.getText());
        txt_devicePrice.setText("");
        isOpen = false;
        isOffer = false ;
        if(groupOFButton.getItems().size()==5){
        addOfferISFound();
        }
        
    }

    public String[] getPriceIfNotVip() {

        try {
            System.out.println("the section id is "+sectionID);
            return dataBase.retrievingRowIntableInArray("SELECT sectionHourPrice , multiHourPrice  from section where id = " + sectionID+"");
            
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            return new String[0];
        }
    }

    public String[] getPriceIfRoomVip() {

        try {
            return dataBase.retrievingRowIntableInArray("SELECT vibHourPrice , vipPriceMulti from rooms where Id = " + room_id);
        } catch (SQLException ex) {
            Logger.getLogger(followingController.class.getName()).log(Level.SEVERE, null, ex);
            return new String[0];
        }
    }
    
     public void startNewTime(){
     
        try {
            int isOffer = 1 ;
            if(this.isOffer==false){
                isOffer = 0;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date today = new Date();
            String todayStr = sdf.format(today);
            int transationNumer = Integer.parseInt(autoTranscation("followingdevice","transactionNumber"));
            java.util.Date dateStr = sdf.parse(todayStr);
            java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
             int player_id_account=Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+playerController.currentPlayer.getPlayerName()+"'"));
            String result  = dataBase.addnewfollowingDeivce(transationNumer,txt_startTime.getText(), txt_endTime.getText(),combo_prices.getSelectionModel().getSelectedIndex(),contollerOfcontrol.deviceID,isOffer,offer_id,player_id_account,loginController.loginUser.getID(),dateDB);
            String deviceName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT deviceName FROM newdevice WHERE id = '"+contollerOfcontrol.deviceID+"'");
            RunningDevice newOne = new RunningDevice (contollerOfcontrol.deviceID,deviceName,txt_startTime.getText(), txt_endTime.getText());
            contollerOfcontrol.allDevicesRunningNow.add(newOne);
            container.getScene().getWindow().hide();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            dataBase.showMessageJOP(ex.getMessage());
            System.out.println(ex.getMessage());
        }
         
    
    }
     
     public void changeSingleToMulti(){
      
         String id = "0";
         txt_endTime.setStyle("-fx-background-color:#F2FA5A");
        try {
        Object []transactionNumberTable =  dataBase.retrievingTableInArrayOfObject("SELECT id , transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
         for (int i = transactionNumberTable.length-1; i < transactionNumberTable.length; i++) {
                        String [] row  = (String[]) transactionNumberTable[i];        
                         id  = row[0];
         }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        dataBase.excuteNonQuery("UPDATE followingdevice set endTime = '"+dtf.format(localTime)+"' WHERE id = '"+id+"'");
        getCurrentTime();
        if(combo_prices.getSelectionModel().getSelectedIndex() == 0){
        combo_prices.getSelectionModel().select(1);
        }else{
        combo_prices.getSelectionModel().select(0);
        }
        btn_startChange.setVisible(true);
           for (int i = 0; i < contollerOfcontrol.allDevicesRunningNow.size(); i++) {
                System.out.println("the device id "+ contollerOfcontrol.allDevicesRunningNow.get(i).getDeviceID());
                if(contollerOfcontrol.allDevicesRunningNow.get(i).getDeviceID()== contollerOfcontrol.deviceID){
                    contollerOfcontrol.allDevicesRunningNow.remove(i);
                }
            }
        
        } catch (SQLException ex) { 
            dataBase.showMessageJOP(ex.getMessage());
        }
         
         
     
     }

     public void startTimeAfterChangeState(){
         if(txt_startTime.getText().equals(txt_endTime.getText())){
             
              Alert alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("تحذير");
                alert.setContentText("قم بضبط الوقت اولا");
                alert.showAndWait();
                 
             
             
             return;
         }
        try {
            int isOffer = 1 ;
            if(this.isOffer == false){
                isOffer = 0;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date today = new Date();
            String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
         
            
            
            String todayStr = sdf.format(today);
           
            java.util.Date dateStr = sdf.parse(todayStr);
            java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
            
             String result  = dataBase.addnewfollowingDeivce(Integer.parseInt(transactionNumber),txt_startTime.getText(), txt_endTime.getText(),combo_prices.getSelectionModel().getSelectedIndex(),contollerOfcontrol.deviceID,isOffer,offer_id,playerController.currentPlayer.getPlayerID(),loginController.loginUser.getID(),dateDB);   
            btn_startChange.setVisible(false);
            
            for (int i = 0; i < contollerOfcontrol.allDevicesRunningNow.size(); i++) {
                if(contollerOfcontrol.deviceID == contollerOfcontrol.allDevicesRunningNow.get(i).getDeviceID()){
//                    contollerOfcontrol.allDevicesRunningNow.get(i).setStartTime(txt_startTime.getText());
//                    contollerOfcontrol.allDevicesRunningNow.get(i).setEndTime(txt_endTime.getText());    
                      contollerOfcontrol.allDevicesRunningNow.remove(i);
                }
            }
                System.out.println("    the length of all running array device : "+ allDevicesRunningNow.size());
                String deviceName = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT deviceName FROM newdevice WHERE id = '"+contollerOfcontrol.deviceID+"'");
                RunningDevice newOne = new RunningDevice (contollerOfcontrol.deviceID,deviceName,txt_startTime.getText(), txt_endTime.getText());
                contollerOfcontrol.allDevicesRunningNow.add(newOne);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
     
     }
     
     
     public void changeState(){
     btnSaveAndChangeState.setVisible(isVIP);
        try {
            String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+"  and stillRunning =1 ");
            System.out.println("the transaction number     :" + transactionNumber);            
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date today = new Date();
            String todayStr = sdf.format(today);
            java.util.Date dateStr = sdf.parse(todayStr);
            java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
            
            // 1 يعبر عن ارباح الاجهزة     2 يعبر عن ارباح الكافية 
            
            
            
            receipt();
            dataBase.excuteNonQuery("UPDATE followingdevice set stillRunning = 0 WHERE transactionNumber  = '"+transactionNumber+"'");
            contollerOfcontrol.gettingAllRunningDevice();
            for (int i = 0; i < contollerOfcontrol.allDevicesRunningNow.size(); i++) {
                if(contollerOfcontrol.allDevicesRunningNow.get(i).getDeviceID() == contollerOfcontrol.deviceID ){
                    System.out.println("we found it ");
                    System.out.println("the length befor delete "+contollerOfcontrol.allDevicesRunningNow.size());
                    allDevicesRunningNow.remove(i);
                    System.out.println("the length after delete "+contollerOfcontrol.allDevicesRunningNow.size());
                    
                }
            }
            contollerOfcontrol.gettingAllRunningDevice();
            int player_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM son WHERE name = '"+playerController.currentPlayer.getPlayerName()+"'"));
            dataBase.customerLife(Integer.parseInt(transactionNumber), 2, Float.parseFloat(txt_devicePrice.getText()), 0, "حساب ايحار البلايستيشن",player_id, dateDB);
            if(!(Float.parseFloat(txt_order.getText())==0)){
            dataBase.customerLife(Integer.parseInt(transactionNumber), 1, Float.parseFloat(txt_order.getText()), 0, "مشتريات من المخزن",player_id, dateDB);
            }
            
            if(Float.parseFloat(txt_payed.getText())>=Float.parseFloat( txt_order_andDevice.getText())){
             
                 int idOfAddedMoney = dataBase.customerLife(11,3, 0, Float.parseFloat(txt_order_andDevice.getText())," سداد  الي الخزينة من "+playerController.currentPlayer.getPlayerName(), player_id, dateDB);
                 java.sql.Date sqlDate=java.sql.Date.valueOf(java.time.LocalDate.now());
                 String idAccountFrom  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id from son WHERE name = '"+playerController.currentPlayer.getPlayerName()+"'");  
                 dataBase.enterAndOutMoney(11,Float.parseFloat(txt_order_andDevice.getText()),safe.getId() ,loginController.loginUser.getID(),sqlDate,Integer.parseInt(idAccountFrom)," استلام النقدية من حساب "+playerController.currentPlayer.getPlayerName(),idOfAddedMoney);
                 String lifeCycleOfCustomer =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(addMoney)-SUM(minsMoney)  FROM lifeofcustomer WHERE customerAccountId = "+player_id+"");
                 if(Float.parseFloat(lifeCycleOfCustomer) > 0){
                 Alert alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("تحذير");
                 alert.setContentText(playerController.currentPlayer.getPlayerName()+" لديك حساب سابق قيمته: "+ lifeCycleOfCustomer);
                 alert.showAndWait();
                 openNewWindow("receivingMoneyFrom", "استلام النقدية ");
                 }
                   container.getScene().getWindow().hide();

             }else{
                         openNewWindow("receivingMoneyFrom", "استلام النقدية ");

             } 
        
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
        } catch (ParseException ex) {
            Logger.getLogger(followingController.class.getName()).log(Level.SEVERE, null, ex);
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
   
     //هنسجل البييانات بعض ما خلص علشان الفاتورة 
     // الفاتورة عدد الساعات ودقايق وسعر الساعة واسم الاعب وكل بيانات الازمة
     public void receipt(){
         
         try{

             
         String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+" and stillRunning =1 "); 
       
         String shopName       = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT company_name from companydata");
         String playerName     = playerController.currentPlayer.getPlayerName();
         String casherName     = loginController.loginUser.getUserName();
         String sectionName    = lbl_sectionName.getText();
         int deviceID = contollerOfcontrol.deviceID;         
         float totalOfStock  = Float.parseFloat(txt_order.getText());
         float paied = Float.parseFloat(txt_payed.getText());
         float toatalOfDEvice  = Float.parseFloat(txt_devicePrice.getText());    
         String roomName = lbl_roomName.getText();
         
         Object []table = dataBase.retrievingTableInArrayOfObject("SELECT startTime , endTime , timeType from followingdevice WHERE transactionNumber = '"+transactionNumber+"'");
         
         
         
          for (int i = 0; i < table.length; i++) {
                String [] row  = (String[]) table[i];
                String startTime = row[0];
                String endTime   = row[1];
                String mult       ="";
                if(Integer.parseInt(row[2])== 0){
                mult ="فردي";
                }else{
                mult ="زوجي";
                }
                float hourPrice = 0.0f;
                if(mult.equals("فردي" )){
                  hourPrice =Float.parseFloat( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT section.sectionHourPrice from section WHERE section.id = "+sectionID));
                
                }else{
                    hourPrice  =Float.parseFloat( dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT section.multiHourPrice from section WHERE section.id = "+sectionID));
                
                }
                float []times = getDifferenceBetweenTwoTime(endTime, startTime);
                float hours = times[1] ;
                float minuts = times[0] ;
                int startHour = Integer.parseInt(startTime.substring(0, 2));
                //00 to 06 بعد منتصف الليل  0
                //06 to 12   الصباح 1
                //13 to 07      الظهيرة 2
                //07 to 23        مساء 3
                int period = -1;
                if(startHour >= 0 && startHour <= 6){
                    period = 0 ;
                }else if( startHour >6 && startHour <=12){
                    period = 1 ;
                }else if( startHour >13 && startHour <=19){
                    period = 2 ;
                }
                else{
                period = 3 ;
                }
                if(i>0){
                totalOfStock = 0 ;
                }
                dataBase.insertDataToReciptTable(Integer.parseInt(transactionNumber), shopName, playerName, casherName, hours, minuts, sectionName, toatalOfDEvice, totalOfStock, paied, startTime, endTime, mult,hourPrice ,roomName, deviceID, period);
          }    
         
                         printReceipt();

        
                 
                 
                 }
         
         catch(SQLException ex){
             dataBase.showMessageJOP(ex.getMessage());
         }
         catch(Exception ex){
             dataBase.showMessageJOP(ex.getMessage());
         }
         //dataBase.insertDataToReciptTable
     }
     
     public void printReceipt(){
       try {
            String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+" and stillRunning =1 "); 
            JasperDesign jd = JRXmlLoader.load("finalBill.jrxml");
            String sql ="SELECT roomName , sectionName , newdevice.deviceName , cacherName , shopName , totalHour , totalMinutes , startTime , endTime , hourPrice , multi from receiptinformation JOIN newdevice WHERE receiptinformation.deviceID = newdevice.id and transactionNumber = '"+transactionNumber+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            HashMap <String,Object> para = new HashMap<>();
            String []shopData = dataBase.retrievingRowIntableInArray("SELECT address , tel FROM companydata ;");
            para.put("address",shopData[0]);
            para.put("contacts",shopData[1]);
             transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+" and stillRunning =1 "); 
            para.put("transactionNumber", transactionNumber);
            if(tabel_receipt.getItems().size()==0){
            para.put("receiptNumber",(Integer.parseInt(lbl_reciptNumber.getText())));
            }
            
            else{
                para.put("receiptNumber",(Integer.parseInt(lbl_reciptNumber.getText())-1));
            }
            para.put("totalOfDevice", txt_devicePrice.getText());
            para.put("totalOfStock", txt_order.getText());
            para.put("totalOfBoth",txt_order_andDevice.getText());
            para.put("payedBefor",txt_payedBefor.getText());
            para.put("payed",txt_payed.getText());
            para.put("reminder",txt_reminder.getText());
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
             } catch (JRException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
   
            
     
     }
public void editTime(){

    
    for (int i = 0; i < contollerOfcontrol.allDevicesRunningNow.size(); i++) {
        if(contollerOfcontrol.allDevicesRunningNow.get(i).getDeviceID() == contollerOfcontrol.deviceID){
            contollerOfcontrol.allDevicesRunningNow.get(i).setEndTime(txt_endTime.getText());
            try {
                String maxID = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT MAX(id) FROM followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID);
                String transactionNumber =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT transactionNumber from followingdevice WHERE Device_id = "+contollerOfcontrol.deviceID+" and stillRunning =1 "); 
                dataBase.excuteNonQuery("UPDATE followingdevice set endTime = '"+txt_endTime.getText()+"' WHERE transactionNumber = '"+transactionNumber+"' and id = "+maxID);
            } catch (Exception e) {
                dataBase.showMessageJOP(e.getMessage());
            }
            
        }
    }
    

}
     public void openReceiveMoney(){
         
         openNewWindow("receivingMoneyFrom", "استلام نقود ضمن الحساب");
     
     }
     // function to show table of receipt when user clicked 
     public void showPaneOreder(){

     paneOrder.setVisible(true);
     txt_codeItem.requestFocus();//to easy for user to enter code direct

}
     public void showTimeCOntrolPane(){
         
         controlTimePane.setVisible(true);
         settingIcon.setVisible(false);
         }
    @Override
    public void handle(javafx.event.ActionEvent event) {

        
        if (!isOpen) {
            for (int i = 0; i < allOfferButton.length; i++) {
                if (event.getSource() == (allOfferButton[i])) {
                    
                    try {
                        
                        isOffer = true ;
                        offer_id = allOffer[i].getID();
                        System.out.println(allOffer[i].getOfferName());
                        System.out.println(allOffer[i].getID() );
                        String[] data = dataBase.retrievingRowIntableInArray("SELECT mainUnit , addUnit from offer WHERE id = " + allOffer[i].getID());
                        try {
                            Calendar now = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            Date dateInStartText = format.parse(txt_endTime.getText());
                            now.setTime(dateInStartText);
                            now.add(Calendar.MINUTE, Integer.parseInt(data[0]));
                            now.add(Calendar.MINUTE, Integer.parseInt(data[1]));
                            txt_endTime.setText(format.format(now.getTime()) + "");
                        } catch (ParseException ex) {
                            dataBase.showMessageJOP(ex.getMessage());
                            System.out.println(ex.getMessage());
                        }
                    } catch (SQLException ex) {
                        dataBase.showMessageJOP(ex.getMessage());
                        System.out.println(ex.getCause());
                    }
                    return;

                }

            }
        }
    }
}
