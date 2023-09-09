
package playstation;

import classes.cutomerReceipt;
import classes.player;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;


public class playerController implements Initializable ,  EventHandler<KeyEvent> {
    
    
    @FXML
    private BorderPane container;
    @FXML
    private TextField player_txt;
    @FXML
    private Label lbl_message;

    @FXML
    private TextField tel_txt;
    
       @FXML
    private Label deviceNumberLabl;

    @FXML
    private Label priceLable;

    @FXML
    private Button btn_receive;
    @FXML
    private Label roomNameLabel;

    @FXML
    private Label sectionLbl;
    
    String []allPlayerName = {};
    String []allPlayernumber = {};
    static player currentPlayer ;
    private int playerID ;
    @FXML
    private TableView<player> table_players;
    
    @FXML
    private TableColumn<player, Integer> col_id;

    @FXML
    private TableColumn<player, String> col_playerName;

    @FXML
    private TableColumn<player, String> col_telephone;
    
    int playerId =0 ;
    String oldName = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
          allPlayerName =  dataBase.retrivingColumnFromDataBase("SELECT name from son  where father_id = 6 or father_id = 5 ");
          TextFields.bindAutoCompletion(player_txt, allPlayerName);
          allPlayernumber = dataBase.gettingColumnFromDatabaseIntoArray("players", "telPhone", "id");
          TextFields.bindAutoCompletion(tel_txt, allPlayernumber);
          tel_txt.setOnKeyPressed(this);
          player_txt.setOnKeyPressed(this);
         // btn_receive.setVisible(false);
          
             col_id.setCellValueFactory(new PropertyValueFactory<player,Integer>("playerID"));            
             col_playerName.setCellValueFactory(new PropertyValueFactory<player,String>("playerName"));            
             col_telephone.setCellValueFactory(new PropertyValueFactory<player,String>("telPhone"));            
          getingallPlayers();

          
          player_txt.setOnKeyReleased((event)->{
        if(event.getCode().toString()=="ENTER"){
          try{
            String playerName = player_txt.getText();
            if(playerName.equals("")){
                lbl_message.setText("اسم العميل فارغ !");
                return;
            }
            String sql = "SELECT playerName , telPhone from players WHERE playerName = '"+playerName+"'";
            System.out.println(sql);
            String row[] =dataBase.retrievingRowIntableInArray(sql);
            player_txt.setText(row[0]);
            tel_txt.setText(row[1]);
        }
        catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
          
          }
          });
          tel_txt.setOnKeyReleased((event)->{
          
             if(event.getCode().toString()=="ENTER"){
                  try{
                    String playerTel = tel_txt.getText();
                    if(playerTel.equals("")){
                    lbl_message.setText("رقم العميل فارغ !");
                    return;
            }
                    String sql = "SELECT playerName , telPhone from players WHERE telPhone   = '"+playerTel+"'";
                    System.out.println(sql);
                    String row[] =dataBase.retrievingRowIntableInArray(sql);
                    player_txt.setText(row[0]);
                    tel_txt.setText(row[1]);
        }
        catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
             
             }
          });
          
    }
    
    public void addNewPlayer (){
    
        try {
            String result = dataBase.addNewPlayer(player_txt.getText(), (tel_txt.getText()),0);
            lbl_message.setText(result);
            allPlayerName =  dataBase.retrivingColumnFromDataBase("SELECT name from son  where father_id = 6 or father_id = 5 ");
            allPlayernumber = dataBase.gettingColumnFromDatabaseIntoArray("players", "telPhone", "id");
            TextFields.bindAutoCompletion(player_txt, allPlayerName);
            TextFields.bindAutoCompletion(tel_txt, allPlayernumber);
            playerID =Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("select count(id) from players"));
            int fatherAccount_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id FROM father WHERE Name = '"+"العملاء"+"'"));
            dataBase.addSonAccout(fatherAccount_id, player_txt.getText());
            getingallPlayers();
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            return;
        }

    
    }
    
    public void getingallPlayers (){
    
        table_players.getItems().clear();
        try{
        Object []table =  dataBase.retrievingTableInArrayOfObject("SELECT son.Id , name , players.telPhone FROM son join players WHERE son.name = players.playerName ");
            for (int i = 0; i < table.length; i++) {
                String [] row = (String[]) table[i];
                table_players.getItems().add(new player(Integer.parseInt(row[0]), row[1], row[2], 0));
                
            }
        }
        catch(SQLException ex){
        
        }
    
    }
    public void deletePlayerText(){
        player_txt.setText("");
        tel_txt.requestFocus();
    
    }
    @Override
    public void handle(KeyEvent event) {
    if(event.getCode().toString()=="ENTER"){

        try{
        String playerName = player_txt.getText();
        String playerTel  = tel_txt.getText();
        player_txt.setText("");
        tel_txt.setText("");
        String sql = "SELECT playerName , telPhone from players WHERE playerName = '"+playerName+"' OR telPhone = '"+playerTel+"' ";
            System.out.println(sql  );
        String row[] =dataBase.retrievingRowIntableInArray(sql);
        player_txt.setText(row[0]);
        tel_txt.setText(row[1]);
        }
        catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
          
      }
    
        
             
    }
    public int  search (String key , String []array){
    int i =0;
        for ( i = 0; i < array.length; i++) {
            if(key.equals(array[i])){
                System.err.println("i is "+ i);
                playerID = i +1 ;
                return i;
            }
            }

        return -1;
    }
    public void startReserve(){

        try {
            String found = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id FROM son WHERE name = '"+player_txt.getText()+"'");
            System.out.println("show if we found "+found);
            if(found.equals("")){
                lbl_message.setText("قم باضافة الاعب اولا");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(playerController.class.getName()).log(Level.SEVERE, null, ex);
        }
      container.getScene().getWindow().hide();
      currentPlayer = new player(playerID,player_txt.getText(),tel_txt.getText(), 0);
      System.out.println(currentPlayer.getPlayerName());
      Parent root;

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
    
    public void selecectedPlayerAnd (){
        
        player selectedOne = table_players.getSelectionModel().getSelectedItem();
        player_txt.setText(selectedOne.getPlayerName());
        tel_txt.setText(selectedOne.getTelPhone());
        playerID = selectedOne.getPlayerID();
        oldName = table_players.getSelectionModel().getSelectedItem().getPlayerName();
    
    
    }
        public void editPlayer(){
            
            try {
            String newName = player_txt.getText();
            String newtel  = tel_txt.getText();
            
                
            String sql = "UPDATE son set name = '"+newName+"'where Id = "+playerID;
            String sql2 = "UPDATE players SET playerName ='"+newName+"' , telPhone ='"+newtel+"' WHERE playerName = '"+oldName+"'";
            dataBase.excuteNonQuery(sql);
            dataBase.excuteNonQuery(sql2);
            getingallPlayers();
            
            } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
            }
            catch(NullPointerException ex){
            lbl_message.setText("قم الاول بتحديد العميل ");
            }
           
        }
    
    
      }
    
    
    
    

    
    
    
   
    

