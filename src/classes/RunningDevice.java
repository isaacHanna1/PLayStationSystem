/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import playstation.contollerOfcontrol;
import playstation.dataBase;

/**
 *
 * @author Seha
 */
public class RunningDevice implements LineListener{
    
    private int deviceID ;
    private String deviceName ; 
    private String startTime ; 
    private String endTime ; 
    private int isRunning ;
    int seccond ;
    boolean playCompleted;
    public RunningDevice(int deviceID, String deviceName, String startTime, String endTime ) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isRunning = 1;
        seccond = 0 ;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(int isRunning) {
        this.isRunning = isRunning;
    }


        public float[] getDifferenceBetweenTwoTime(String time1, String time2) {

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

            times[1] = diffHours;
            times[0] = diffMinutes;
            times[2] = diffSeconds;
        
        } catch (ParseException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
            
       
        return times;
        }
        
        
    
    public void changeTimeEverySecond(String deviceName , Label timeLabel ){   
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        String timeNow = dtf.format(localTime);
        if(endTime.equals("؟")){
            
            float[]times = getDifferenceBetweenTwoTime(startTime,timeNow);
            timeLabel.setText("الي الان"+"  س:  "+(int)times[1]+"    "+"د :"+(int)times[0]);
            
        }else{
            

            float[] times = getDifferenceBetweenTwoTime(timeNow,endTime);
            timeLabel.setText("متبقي  "+" س: "+(int)times[1]+"    "+"د :"+(int)times[0]);
            System.out.println("                 "+times[1]+"      :   "+times[0]+"      :   "+times[2]); 
            if((times[1])== 0 && times[0] == 0 && times[2] == 0){

           Stage stage = new Stage();
                 // set title for the stage
           stage.setTitle("notification اشعار ");
   
                
        // create a button
        Button button = new Button("اخفاء");
        button.setFont(new Font("arial", 22));
        button.setPrefWidth(100);
        button.setStyle("-fx-background-color:#30AADD;");
     

        // create a tile pane
        TilePane tilepane = new TilePane();
   
        // create a label
        Label label = new Label(deviceName+"   قد انتهي وقت   "+" ");
    
        // create a popup
        Popup popup = new Popup();
   
        // set background
        tilepane.setStyle(" -fx-background-color: #444941; ");
    
        
        // add the label
        
        VBox v = new VBox(label,button);
        v.setSpacing(20);
        v.prefWidthProperty().bind(stage.widthProperty());
        v.prefHeightProperty().bind(stage.heightProperty());
         v.setAlignment(Pos.CENTER);
        label.setFont(new Font(23));
        label.setStyle("-fx-font-style:bold; -fx-text-fill:#fff");
      
   
        // action event
        EventHandler<ActionEvent> event = 
        new EventHandler<ActionEvent>() {
   
            public void handle(ActionEvent e)
            {
                if (!popup.isShowing())
                    popup.show(stage);
                else
                    dataBase.showMessageJOP("dasdasdas");
                    stage.hide();
            }
        };
   
        // when button is pressed
        button.setOnAction(event);
   
        // add button
        tilepane.getChildren().addAll(v);
   
        // create a scene
        Scene scene = new Scene(tilepane,  650, 150);
   
        // set the scene
        stage.setScene(scene);
   
        stage.show();
        playSound("buzer");
        
        // old design stage 
//                Stage dialog = new Stage(); // new stage
//                Parent root;
//                //Creating a Label
//                Label label = new Label(deviceName+"   قد انتهي وقت   "+" ");
//                //Setting font to the label
//                Font font = Font.font("times no roman", FontWeight.BOLD, FontPosture.REGULAR, 25);
//                label.setFont(font);
//                //Filling color to the label
//                label.setTextFill(Color.BROWN);
//                //Setting the position
//                label.setTranslateX(150);
//                label.setTranslateY(25);
//                Group newGroup = new Group();
//                newGroup.getChildren().add(label);
//                Scene scene = new Scene(newGroup, 595, 150, Color.BEIGE);
//                dialog.setTitle("اشعار جديد");
//                dialog.setScene(scene);
//                centerStage(dialog, 700, 200);
//                playSound(deviceName);
//                dialog.show();
//
//                
//            }
//        }
//    
    
         
             
                  
    
    }
        }
    }
    
        private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth()) );
        stage.setY((screenBounds.getHeight()));
        stage.centerOnScreen();
    }
        private void playSound(String nameOfMusic){
            try{
                
           File audioFile = new File(nameOfMusic+".wav");
 
 
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
             
            audioClip.start();
             
            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
             
            audioClip.close();
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
            }
            catch(Exception ex){
            dataBase.showMessageJOP(ex.getMessage());
            }
}

    @Override
    public void update(LineEvent event) {

                LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }

    }
        
        
}