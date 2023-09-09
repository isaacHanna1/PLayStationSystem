/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.Room;
import classes.User;
import java.io.IOException;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
/**
 *
 * @author Seha
 */
public class userController implements Initializable {

   @FXML
    private TextField userName_txt;

    @FXML
    private ComboBox<String> permission_combo;
    
    
    @FXML
    private PasswordField password_txt;
    
    
    @FXML
    private Label lbl_message;
    
    String []allUsers = {};

    int userId ;
    
    
    String oldPassword = "";//used for edit or enter nre password ;
    @FXML
    private TableView<User> table_user;

    @FXML
    private Label lbl_password;
    @FXML
    private TableColumn<User, String> col_userName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        
        userName_txt.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                password_txt.requestFocus();
                }
            });
        password_txt.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                permission_combo.requestFocus();
                }
            });
        permission_combo.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){
                if(event.getCode().toString() == "ENTER"){

                addBtn();
                }
                }
            });
        
        
        
        
        
        permission_combo.getItems().add("مشرف");
        permission_combo.getItems().add("مدير للنظام");
        
        
           allUsers = dataBase.gettingColumnFromDatabaseIntoArray("users", "userName","id");
           TextFields.bindAutoCompletion(userName_txt,allUsers);
           col_userName.setCellValueFactory(new PropertyValueFactory<User,String>("UserName"));
           getAllUserName();
    }
    public void addBtn(){
        System.out.println("hello");
        
        String userName = userName_txt.getText() ;
        
        String pasword = password_txt.getText() ;
        int permission = permission_combo.getSelectionModel().getSelectedIndex();
        
        if(userName == "" || pasword == "" || permission == -1){
            lbl_message.setStyle("-fx-text-fill:#E64848");
            lbl_message.setText("تاكد من ادخال جميع البيانات المطلوبة    ");
            userName_txt.requestFocus();
            return;
        }
          
         if (dataBase.insertIntoUserTable(userName,pasword ,permission) == 1){
             lbl_message.setStyle("-fx-text-fill:#fff");
             lbl_message.setText("تمت الاضافة بنجاح ");
              dataBase.inserActions("تمت اضافة حساب جديد بأسم "+userName);

         }
         else{
         
             lbl_message.setText("هذا المستخدم موجود بالفعل !!");
         }
     
       table_user.getItems().clear();
       getAllUserName();    
    }
    
    public void searchBtn(){
        
        String userName = userName_txt.getText();
       try {
           String [] userData = dataBase.retrievingRowIntableInArray("SELECT * FROM `users` WHERE userName = '"+userName+"'");
            userId = Integer.parseInt(userData[0]);
            userName_txt.setText(userData[1]);
           // password_txt.setText(userData[2]);
            oldPassword = userData[2];
            permission_combo.getSelectionModel().select(Integer.parseInt(userData[3]));
            
       } catch (SQLException ex) {
           dataBase.showMessageJOP(ex.getMessage());
       }
    
    }
    
    public void editButton(){
        String userName = userName_txt.getText();
        String password = password_txt.getText();
        int permision = permission_combo.getSelectionModel().getSelectedIndex();
             
        if(userName == "" || password == "" || permision == -1){
            lbl_message.setText("تاكد من ادخال جميع البيانات المطلوبة    ");
            return;
        }
       


           PasswordField pwd = new PasswordField();
           pwd.setAlignment(Pos.CENTER);
           Button okButton = new Button("تعديل");
           Label message = new Label("ادخل الرمز السري القديم");
           Label errMessage =  new Label();
           errMessage.setMinWidth(370);
           errMessage.setAlignment(Pos.CENTER);
           message.setMinWidth(140);
           pwd.setMinWidth(220);
           okButton.setMinWidth(320);
           HBox content = new HBox(5, pwd,message);
           
           VBox contentV = new VBox(5,content,okButton,errMessage);
            Stage stage = new Stage();
            stage.setTitle("التحقق من الرمز السري القديم");
            Scene scene = new Scene(contentV,350,100);
            
            stage.setScene(scene);
               okButton.setOnMouseClicked((event)->{
                System.out.println("hiiiiiii");
                System.out.println("old passord is "+oldPassword);
                System.out.println("pwd "+ pwd.getText());
                if(pwd.getText().equals(oldPassword)){
                    
                    try {
                        dataBase.excuteNonQuery("UPDATE users set userName = '"+userName+"' , password = '"+password+"' , permission = " +permision+" WHERE id = "+userId);
                        stage.close();
                    } catch (SQLException ex) {
                            dataBase.showMessageJOP(ex.getMessage());
                    }
                    lbl_password.setText("الرمز السري");
           lbl_message.setText("تم التعديل");
                }
             else{
            errMessage.setStyle("-fx-text-fill:#E64848");
            errMessage.setText("الرمز السري القديم حطأ");
            
            }
            
            });
            
            stage.showAndWait();
         
    
            
       table_user.getItems().clear();
       getAllUserName();
    
    }
    
    public void getAllUserName(){
     try {
            ObservableList<User> allUser = FXCollections.observableArrayList();
              String sql = "SELECT id , userName  FROM `users` ORDER by id";
            Object[] table = dataBase.retrievingTableInArrayOfObject(sql);
            
            
            for (int i = 0; i < table.length; i++) {
                String [] row  = (String[]) table[i];
                String userName = row[1];
                int id          = Integer.parseInt(row[0]);
                User newOne = new User(id , userName);
                allUser.add(newOne);
            }
                 table_user.setItems(allUser);
                
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        } 
}
    
    
    public void onMouseClickedInTable(){
    
        User newOne = table_user.getSelectionModel().getSelectedItem();
        userName_txt.setText(newOne.getUserName());
        lbl_password.setText("الرمز السري الجديد");
        searchBtn();
    
    }
    
    

}