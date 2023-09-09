 
package playstation;

import classes.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class loginController  implements Initializable{

    
    @FXML
    private TextField userName_txt;

    @FXML
    private PasswordField password_txt;

    @FXML
    private Label lbl_message;
    
      static User loginUser ;
      
    @FXML
    private Button btn_login;
    
    @FXML
    private BorderPane container;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    userName_txt.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                password_txt.requestFocus();
                }
            });
                password_txt.setOnKeyReleased((event) -> {
                        if(event.getCode().toString() == "ENTER"){
                        if(event.getCode().toString() == "ENTER"){
                            
                            checkUserNameAndPassword();
                        }
                        }
            });
    }
    
    
    
    
    
    
    public void checkUserNameAndPassword(){
    
            try {
                
                 loginUser = dataBase.checkUserNameAndPassword(userName_txt.getText(), password_txt.getText());
    
                if(!(loginUser.getUserName().equals("not found"))){
                     Parent root;
                        try {
                            
                            container.getScene().getWindow().hide();
                             dataBase.inserActions("تم تسجيل الدخول الي السيستم من قبل " + userName_txt.getText());
                             String title = "";
                             boolean maximaized = true ;
                             if(loginUser.getPermission()== 1){
                                  title = "home";
                             }else{
                             //title = "control";
                             title ="userHome";
                             maximaized = false;
                             }
                            root = FXMLLoader.load (getClass().getResource(title+".fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("صفحة الرئيسية");
                                stage.setScene(new Scene(root));
                                stage.setMaximized(maximaized);
                                stage.show();
                                   stage.setOnCloseRequest((event) -> {
                                    event.consume();
                                    System.exit(0);
                                 });
                        
                             
                        }catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            System.out.println(ex.getCause());
                            dataBase.showMessageJOP(ex.getMessage());
                        }catch(Exception ex){
                            System.out.println(ex.getMessage());
                            System.out.println(ex.getCause());
                            dataBase.showMessageJOP(ex.getMessage());
                        }
                }
                else{
                    lbl_message.setStyle("-fx-text-fill:#E64848");
                    lbl_message.setText("يرجي اتأكد من البيانات المدخلة ");
                    userName_txt.requestFocus();
                    return;
   
                }
            } catch (SQLException ex) {
                lbl_message.setStyle("-fx-text-fill:#E64848");
                lbl_message.setText("حدث خطأ عند الاتصال ");
                userName_txt.requestFocus();
                dataBase.showMessageJOP(ex.getMessage());
            }
    
    
    }
    
    
    
}
