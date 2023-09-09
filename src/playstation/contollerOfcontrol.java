/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.newDevice;
import classes.safe;
import classes.section;
import classes.RunningDevice;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.batik.svggen.font.table.Table;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 * @author Seha
 */
public class contollerOfcontrol implements  Initializable ,  EventHandler<ActionEvent> {
  @FXML
    private Label lbl_activeUser;
        @FXML
    private Label lbl_companyName;
        
    @FXML
    private Label lbl_allDevicesIHave;

    @FXML
    private Label lbl_runningDeviceNow;
        
    @FXML
    private VBox VSectionBox;
    
    @FXML
    private VBox vRoomBox;
    
    @FXML
    private ScrollPane scrorPane;
    @FXML
    private ImageView sehaImageV;
   @FXML
    private ComboBox<String> comboBox_safe;
        section [] allSection = {} ;
        Button [] allSectionButton = {};
       static newDevice [] allDevices = {};
        ArrayList<newDevice> devicesInSectionInRoom =new ArrayList<newDevice>();
        static int deviceID ;
        
        ArrayList<Button> allButtonForFollowingButton = new ArrayList<Button>();
        ArrayList<Label>allactivedevicesName = new ArrayList<Label>();
             @FXML
    private Button liveReceiptButton;
             
   static ArrayList<RunningDevice>allDevicesRunningNow = new ArrayList<RunningDevice>();     
   static ArrayList<RunningDevice>allDevicesClosedNow = new ArrayList<RunningDevice>();     
   String [] allDecivesNameWeHaveInSystem ; // انا عامها علشان تحتوي علي كل اسماء الاجهزة علشان انا بقارن بالاسم 
    public static Timer timer ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
           try {
            lbl_activeUser.setText(loginController.loginUser.getUserName());
            String companyName = dataBase.gettingOnvalueFromTableByAggregatefunction("select company_name from companydata");
            lbl_companyName.setText(companyName);
            dataBase.putingColumnFromDataBaseIntoCombox( "SELECT nameOfSafe from safe where id > 1 ORDER by id", comboBox_safe);
            String numberOfSafe = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT count(nameOfSafe) from safe WHERE id> 1 ");
            
            comboBox_safe.getSelectionModel();
            liveReceiptButton.setVisible(false);
            buildingSectionButton();
            allDecivesNameWeHaveInSystem = dataBase.gettingColumnFromDatabaseIntoArray("newdevice", "deviceName", "id");
            gettingAllRunningDevice();
            if(Integer.parseInt(numberOfSafe)==1){
            comboBox_safe.getSelectionModel().selectFirst();
            VSectionBox.setVisible(true);
            comboBox_safe.setDisable(true);
            liveReceiptButton.setVisible(true);
            safe.id = (comboBox_safe.getSelectionModel().getSelectedIndex())+2;             
            }
            else{
              VSectionBox.setVisible(false);
            }
              timer = new Timer();
            Timer timer2 = new Timer();
            timer.schedule(new TimerTask() {
              @Override
              public void run() {
                  Platform.runLater(()->{
                 
                  checkStillRunning();
                  
                  });
                  
              }
            }, 0, 1000);



//wait 0 ms before doing the action and do it evry 1000ms (1second)
//            timer2.schedule(new TimerTask() {
//              @Override
//              public void run() {
//                  Platform.runLater(()->{
//                  
//                  checkNotRunning();
//                  
//                  });
//                  
//              }
//            }, 0, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second)


               
        } catch (SQLException ex) {
           dataBase.showMessageJOP(ex.getMessage());
               System.out.println(ex.getCause());
        }
           
           
           scrorPane.setVbarPolicy(ScrollBarPolicy.NEVER);
           scrorPane.setHbarPolicy(ScrollBarPolicy.NEVER);
           
        comboBox_safe.valueProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue ov, String t, String t1) {
          chooseSafe();
        }    

        
            
    });

    }
    
    public void gettingAllSection(){
    
      try {
          Object [] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM `section` ORDER by id ");
          
          allSection = new section[table.length];
                for (int i = 0; i < table.length; i++) {
              
                    String [] row =(String[]) table[i];
                    int id = Integer.parseInt(row[0]);
                    String name = row[1];
                    float hourPrice = Float.parseFloat(row[2]);
                    float multiPrice = Float.parseFloat(row[3]);
                    float gamePrice  = Float.parseFloat(row[4]);
                    String imagePAth = row[5];
                   allSection[i] = new section(id, name, hourPrice, multiPrice, gamePrice,imagePAth);
                   
          }
                
         
      } catch (SQLException ex) {
          System.out.println(ex.getCause()+"   "+ex.getMessage());
          dataBase.showMessageJOP(ex.getMessage());
      }
        
    }
    public static void gettingallDevices(){
            allDevicesRunningNow.clear();

      try {
          Object [] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM `newdevice` ordered by id");
          allDevices = new newDevice[table.length];
                for (int i = 0; i < table.length; i++) {
              
                    String [] row =(String[]) table[i];
                    int id = Integer.parseInt(row[0]);
                    int section_id = Integer.parseInt(row[1]);
                    String deviceNumber = row[2];
                    String deviceName = row[3];
                    int room_id = Integer.parseInt(row[4]);
                    float devicePrice = Float.parseFloat(row[5]);
                    String date = row[6];
                    String note = row[6];
                    newDevice newOne = new newDevice(id, section_id, deviceNumber, deviceName, room_id, devicePrice, date, note);
                    allDevices[i]= newOne;
                    
          }
                  
      } catch (SQLException ex) {
          dataBase.showMessageJOP(ex.getMessage());
      }
         
    
    }
    public void buildingSectionButton (){
    
        gettingAllSection();
        allSectionButton = new Button[allSection.length];
        
        for (int i = 0; i < allSection.length; i++) {
            
            Button newOne = new Button(allSection[i].getSectionName());
            newOne.setStyle("-fx-font-size:20");
            newOne.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
             File file = new File(allSection[i].getImagePath());
             Image img = new Image(file.toURI().toString());
             ImageView view = new ImageView(img);
             view.setFitHeight(20);
             view.setFitWidth(20);
             newOne.setGraphic(view);           
             VSectionBox.setSpacing(14);
             VSectionBox.getChildren().add(newOne);
             allSectionButton[i]=newOne;
             newOne.setOnAction(this) ;
        
    }
  
    
}
    
    public void chooseSafe(){
    
        if(comboBox_safe.getSelectionModel().getSelectedIndex()>=0){
        VSectionBox.setVisible(true);
        comboBox_safe.setDisable(true);
        liveReceiptButton.setVisible(true);
        safe.id = (comboBox_safe.getSelectionModel().getSelectedIndex())+2;
            System.out.println(safe.id);
            
        }
        
    
    }

    @Override
    public void handle(ActionEvent event) {
        
        Pane contentPane = new Pane();
         int roomNameCounter = 0 ;
        for (int i = 0; i <allSectionButton.length; i++) {
            
            
            //gettingAllRunningDevice();
            if(event.getSource().equals(allSectionButton[i])){
                ObservableList<Node> children = vRoomBox.getChildren();
           
                children.remove(1, children.size());
                allButtonForFollowingButton.clear();
                devicesInSectionInRoom.clear();
                //allactivedevicesName.clear();
                //السطر الجاي بيجيب كل الاجهزة اللي موجودة في الغرف باسم الغرفة ونوع القسم
                String[] column = dataBase.gettingColumnFromDatabaseIntoArray("rooms", "DISTINCT roomName ", "newdevice"," ( newdevice.section_id = "+allSection[i].getId()+" ) and rooms.Id = newdevice.room_id");
                for (int j = 0; j < column.length; j++) {
                    FlowPane newRoom = new FlowPane();
                    newRoom.setVgap(10);
                    newRoom.setHgap(10);

                    contentPane.getChildren().add(newRoom);
                    VBox roomName = new VBox();
                    Label roomNameLabel = new Label(column[j]);
                    roomNameLabel.setMinHeight(150);
                    roomNameLabel.setMinWidth(130);
                    roomNameLabel.setStyle("-fx-text-fill: #fff;"+"-fx-font-size:16;");
                    roomName.getChildren().add(roomNameLabel);
                    newRoom.getChildren().add(roomName);
                    try {
                        String room_id = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from rooms WHERE roomName = '"+column[j]+"'");
                        
                        Object [] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM `newdevice` WHERE room_id = "+room_id+" and section_id = "+allSection[i].getId());
                   
                for (int m = 0; m < table.length; m++) {
              
                    String [] row =(String[]) table[m];
                    int id = Integer.parseInt(row[0]);
                    int section_id = Integer.parseInt(row[1]);
                    String deviceNumber = row[2];
                    String deviceName = row[3];
                    int roomId = Integer.parseInt(row[4]);
                    float devicePrice = Float.parseFloat(row[5]);
                    String date = row[6];
                    String note = row[6];
                    newDevice newOne = new newDevice(id, section_id, deviceNumber, deviceName, roomId, devicePrice, date, note);
                    devicesInSectionInRoom.add(newOne);
                    
                }
                 
                    
                        for (int k = 0; k <table.length ; k++) {

                            Pane NewDevice = new Pane();
                            
                            NewDevice.setStyle(" -fx-padding: 10;");
                            NewDevice.setMinHeight(120);
                            NewDevice.setMinWidth(130);
                            NewDevice.setStyle("-fx-background-color:#7952B3;"+"-fx-font-size:14;"+"-fx-background-radius: 20px;");
                            
                            VBox eachDevice = new VBox();
                            eachDevice.setPadding(new Insets(10, 10, 10, 10));
                            eachDevice.setSpacing(10);
                            eachDevice.setAlignment(Pos.CENTER);
                            NewDevice.getChildren().add(eachDevice);
                            File file = new File(allSection[i].getImagePath());
                            Image img = new Image(file.toURI().toString());
                            ImageView view = new ImageView(img);
                            view.setFitHeight(40);
                            view.setFitWidth(40);         
                            eachDevice.getChildren().add(view);
                            Label activeDeviceName = new Label(devicesInSectionInRoom.get(roomNameCounter).getDeviceName());
                            roomNameCounter++;
                            activeDeviceName.setPrefWidth(130);
                            
                            allactivedevicesName.add(activeDeviceName);
                            Label time = new Label("00:00");
                            time.setPrefWidth(160);
                            time.setAlignment(Pos.CENTER);
                            Button newCustomer = new Button("فتح");
                            allButtonForFollowingButton.add(newCustomer);
                            newCustomer.setOnAction(this);
                            newCustomer.setMinWidth(100);
                            time.setStyle("-fx-text-fill:#000;"+"-fx-font-weight: bold");
                            activeDeviceName.setPrefWidth(160);
                            activeDeviceName.setAlignment(Pos.CENTER);
                            activeDeviceName.setStyle("-fx-text-fill:#FFF;"+"-fx-font-weight: bold");
                            eachDevice.getChildren().addAll(time,activeDeviceName,newCustomer);
                            newRoom.getChildren().add(NewDevice);
                           
                        }
                    
                    }catch (SQLException ex) {
                        Logger.getLogger(contollerOfcontrol.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vRoomBox.getChildren().add(newRoom);
                    newRoom.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: #7ECA9C;");

                }
                
            }

    }
                    
            for (int j = 0; j < allButtonForFollowingButton.size(); j++) {
                if(event.getSource().equals(allButtonForFollowingButton.get(j))&&allButtonForFollowingButton.get(j).getText()=="فتح"){
                  deviceID = devicesInSectionInRoom.get(j).getId();
                    openNewWindow("players", "الزائرين");
                  
                }
                else if(event.getSource().equals(allButtonForFollowingButton.get(j))&&allButtonForFollowingButton.get(j).getText()=="متابعة"){
                 Parent root;
                  deviceID = devicesInSectionInRoom.get(j).getId();

                 try{
                                root = FXMLLoader.load (getClass().getResource("following"+".fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("الصفحة الحجز");
                                stage.setScene(new Scene(root));
                                stage.setMaximized(false);
                                stage.show();
                                
                        } catch (IOException ex) {
                            dataBase.showMessageJOP(ex.getMessage());
                            ex.getMessage();
                            System.out.println(ex.getCause());
                        }
                }
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
    
    
    public static void gettingAllRunningDevice(){
        allDevicesRunningNow.clear();
        
      try {
          Object [] table = dataBase.retrievingTableInArrayOfObject("SELECT  Device_id , newdevice.deviceName , startTime , endTime FROM followingdevice JOIN newdevice " +
                  "WHERE followingdevice.Device_id = newdevice.id and stillRunning = 1");

      
            for (int i = 0; i < table.length; i++) {
                
                String [] row  = (String[]) table[i];
                 int DeviceID  = Integer.parseInt(row[0]);
                 String deviceName = row[1];    
                 String startTime = row[2];
                 String endTime   = row[3];
                 RunningDevice newOne = new RunningDevice(DeviceID, deviceName, startTime, endTime);
                 allDevicesRunningNow.add(newOne);
            }
         

                 
                   
      } catch (SQLException ex) {
          dataBase.showMessageJOP(ex.getMessage());
      }
    
    }
    
    
    public void checkStillRunning(){
        System.err.println("the length of active device "+allactivedevicesName.size()+"  the running "+ allDevicesRunningNow.size());
        lbl_allDevicesIHave.setText(allDecivesNameWeHaveInSystem.length+"");
        lbl_runningDeviceNow.setText(allDevicesRunningNow.size()+"");
        long startNano = System.nanoTime();
       getClosedDevices();
        for (int i = 0; i < allDevicesRunningNow.size(); i++) {
            for (int j = 0; j < allactivedevicesName.size(); j++) {
                
                if(allDevicesRunningNow.get(i).getDeviceName().equals(allactivedevicesName.get(j).getText())){
                    allactivedevicesName.get(j).getParent().setStyle("-fx-background-color:#FF3D68;"+"-fx-background-radius: 20px;");
                    System.out.println("the active device name is "+ allactivedevicesName.get(j).getText()+"the index is :"+j);
                    ObservableList<Node> childernOfNewDevice = allactivedevicesName.get(j).getParent().getChildrenUnmodifiable();
                    Node changeButton = childernOfNewDevice.get(3);
                    Node changeLabel = childernOfNewDevice.get(1);
                    Button name = (Button)changeButton;
                    Label time = (Label)changeLabel ;
                    name.setText("متابعة");
                    String startTime = allDevicesRunningNow.get(i).getStartTime();
                    String end = allDevicesRunningNow.get(i).getEndTime();
                    allDevicesRunningNow.get(i).changeTimeEverySecond(allDevicesRunningNow.get(i).getDeviceName(), time );
                    
                    
                }
            }
        }
        long endNano = System.nanoTime();
        System.out.println("the total take time : "+ (endNano-startNano));
        
        }
    
    public void getClosedDevices(){
    
        for (int i = 0; i < allactivedevicesName.size(); i++) {
            boolean found = false ;
            int index = -1 ;
            for (int j = 0; j < allDevicesRunningNow.size(); j++) {
                if(allactivedevicesName.get(i).equals(allDevicesRunningNow.get(j).getDeviceName())){
                    found = true ;
                    continue;
                }
            }
            if(!found){
             ObservableList<Node> childernOfNewDevice = allactivedevicesName.get(i).getParent().getChildrenUnmodifiable();
                    allactivedevicesName.get(i).getParent().setStyle("-fx-background-color:#7952B3;"+"-fx-background-radius: 20px;");
                    Node changeButton = childernOfNewDevice.get(3);
                    Button name = (Button)changeButton;
                    name.setText("فتح"); 
                    Node changeLabel = childernOfNewDevice.get(1);
                    Label time = (Label)changeLabel ;
                    time.setText("00:00");
                    name.setText("فتح");
            }
            
        }
    
    }
        public float[] getDifferenceBetweenTwoTime(String time2, String time1) {

        float[] times = new float[3];
        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                        "HH:mm:ss");

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(time1);
            Date d2 = sdf.parse(time2);
            
            
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            
            

        } 
        catch (ParseException e) {
            e.printStackTrace();
         
        }
        
        System.out.println("the number of hour is : " + times[1]);
//        if(times[1]<0){
//        times[1] = 12 - Math.abs(times[1]);
//        }
        System.out.println("the number of minits is : " + times[0]);
        System.out.println("the number of hour is : " + times[1]);
        return times;
        }
    public void changeTimeEverySecond(String deviceName , Label timeLabel){
    
      try {
                String endTime = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT followingdevice.endTime from followingdevice JOIN newdevice WHERE followingdevice.Device_id = newdevice.id and newdevice.deviceName = '"+deviceName+"' and followingdevice.stillRunning = 1 ");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime localTime = LocalTime.now();
                String timeNow = dtf.format(localTime);
                
                
          if(endTime.equals("؟")){
               String startTime = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT followingdevice.startTime from followingdevice JOIN newdevice WHERE followingdevice.Device_id = newdevice.id and newdevice.deviceName = '"+deviceName+"' and followingdevice.stillRunning = 1 ");
                float[]times = getDifferenceBetweenTwoTime(timeNow,startTime);
                timeLabel.setText("الي الان"+"  س:  "+(int)times[1]+"    "+"د :"+(int)times[0]);    
                
          }else{
                
               String startTime = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT followingdevice.endTime from followingdevice JOIN newdevice WHERE followingdevice.Device_id = newdevice.id and newdevice.deviceName = '"+deviceName+"' and followingdevice.stillRunning = 1");
               float[] times = getDifferenceBetweenTwoTime(startTime,timeNow);
               timeLabel.setText("متبقي  "+" س: "+(int)times[1]+"    "+"د :"+(int)times[0]);
      
               if((times[1])== 0 && times[0] == 0 && times[2] == 0){

                              Stage dialog = new Stage(); // new stage
                              Parent root;
                              //Creating a Label
                              Label label = new Label(deviceName+"   قد انتهي وقت   "+" ");
                              //Setting font to the label
                              Font font = Font.font("times no roman", FontWeight.BOLD, FontPosture.REGULAR, 25);
                              label.setFont(font);
                              //Filling color to the label
                              label.setTextFill(Color.BROWN);
                              //Setting the position
                              label.setTranslateX(150);
                              label.setTranslateY(25);
                              Group newGroup = new Group();
                              newGroup.getChildren().add(label);
                              Scene scene = new Scene(newGroup, 595, 150, Color.BEIGE);
                              dialog.setTitle("اشعار جديد");
                              dialog.setScene(scene);
                              centerStage(dialog, 595, 150);
                              dialog.show();
                              playSound();
                              
                                
                        }
               }
           
          }catch (SQLException ex) {
          dataBase.showMessageJOP(ex.getMessage());
      }
    
    
         
             
                  
    
    }
        private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) );
        stage.setY((screenBounds.getHeight() - height));
    }
        private void playSound(){
            AudioInputStream audioStream = null;
      try {
          File audioFile = new File("timeOut.mp3");
          audioStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
          AudioFormat format = audioStream.getFormat();
          DataLine.Info info = new DataLine.Info(Clip.class, format);
          Clip audioClip = (Clip) AudioSystem.getLine(info);
          audioClip.open(audioStream);
          audioClip.start();
      } catch (UnsupportedAudioFileException ex) {
          Logger.getLogger(contollerOfcontrol.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(contollerOfcontrol.class.getName()).log(Level.SEVERE, null, ex);
      } catch (LineUnavailableException ex) {
          Logger.getLogger(contollerOfcontrol.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
          try {
              audioStream.close();
          } catch (IOException ex) {
              Logger.getLogger(contollerOfcontrol.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
        }
    public void checkNotRunning(){

//            if(running.length == 0){
//            for (int i = 0; i < allactivedevicesName.size(); i++) {
//                ObservableList<Node> childernOfNewDevice = allactivedevicesName.get(i).getParent().getChildrenUnmodifiable();
//                    allactivedevicesName.get(i).getParent().setStyle("-fx-background-color:#7952B3;"+"-fx-background-radius: 20px;");
//                    Node changeButton = childernOfNewDevice.get(3);
//                    Button name = (Button)changeButton;
//                    name.setText("فتح");    
//            }
//        
//        }
             
        }
         public void openTableWindo(){
               openNewWindow("tables", "الطاولات");
     
    }
         
    public Calendar minisorAddTime(int minints){
                Calendar now = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                now.setTime(new Date());
                now.add(Calendar.MINUTE, minints);
                return now ;
    
    }
     public void openReceiveMoney(){
         
         openNewWindow("receivingMoneyFrom", "استلام نقود ضمن الحساب");
     
     }
     
    
     public void openLiveWindow(){
           
            Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("liveReceipt"+".fxml"));
                Stage stage = new Stage();
                stage.setTitle("فاتورة بيع مباشر");
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }

           
    }
        
}
                
