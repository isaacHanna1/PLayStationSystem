
package playstation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;


public class mainDataController implements Initializable {

    
    @FXML
    private TextField txt_companyName;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_telephone;

    @FXML
    private TextField txt_welcome;

    @FXML
    private TextField txt_tail;

    @FXML
    private TextField txt_logoPath;

    @FXML
    private TextField txt_instaLink;

    @FXML
    private TextField txt_faceBookLink;
    
    @FXML
    private Label lbl_message;
    
    @FXML
    private BorderPane container;
    
    

    @FXML
    private Button btn_edit;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        txt_companyName.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_address.requestFocus();
                }
            });
        txt_address.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_telephone.requestFocus();
                }
            });
        txt_telephone.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_welcome.requestFocus();
                }
            });
        txt_welcome.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_tail.requestFocus();
                }
            });
        txt_tail.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_logoPath.requestFocus();
                }
            });
        
        txt_logoPath.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_faceBookLink.requestFocus();
                }
            });
        txt_faceBookLink.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_instaLink.requestFocus();
                }
            });
    
        
    
      txt_instaLink.setOnKeyReleased((event) -> {
                        if(event.getCode().toString() == "ENTER"){
                        if(event.getCode().toString() == "ENTER"){
                            
                            addBtn();
                        }
                        }
            });
        

       
        
        
        System.out.println("done");
        try {
            System.out.println("here we start 1 ");
            String[] row = dataBase.retrievingRowIntableInArray("SELECT * FROM `companydata` WHERE 1");
            System.out.println("here we start 2 ");
               txt_companyName.setText(row[1]);
               System.out.println("here we start 3 ");
               txt_address.setText(row[2]);
               System.out.println("here we start 4 ");
               txt_telephone.setText(row[3]);
               System.out.println("here we start 5 ");
               txt_welcome.setText(row[4]);
               System.out.println("here we start 6 ");
               txt_tail.setText(row[5]);
               System.out.println("here we start 7 ");
               txt_logoPath.setText(row[6]);
               System.out.println("here we start 8 ");
               txt_faceBookLink.setText(row[7]);
               System.out.println("here we start 9");
               txt_instaLink.setText(row[8]);
               System.out.println("here we start 10 ");
            
        } catch (SQLException ex) {
            lbl_message.setText("حدث خطأ عند الاتصال بقواعد البيانات");
            
        }
    }
    
    public void addBtn(){
    
      String result = dataBase.insertMainData(txt_companyName.getText(), txt_address.getText(), txt_telephone.getText(), txt_welcome.getText(), txt_tail.getText(), txt_logoPath.getText(), txt_faceBookLink.getText(), txt_instaLink.getText(), loginController.loginUser.getID());
      dataBase.inserActions("قام "+loginController.loginUser.getUserName() + "بتعديل بيانات الشركة");
      lbl_message.setText(result);
      txt_companyName.requestFocus();
    }
     public void choosePathOfLogo (){
     
        
        
    FileChooser fileChooser = new FileChooser();
     File file = fileChooser.showOpenDialog(container.getScene().getWindow());
  
     txt_logoPath.setText(file.getPath());
    
    }
}
