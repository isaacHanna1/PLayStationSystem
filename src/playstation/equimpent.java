
package playstation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class equimpent  implements Initializable{
    @FXML
    private TextField txt_code;

    @FXML
    private TextField txt_equimpentName;
    @FXML
    private Label lbl_message;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public void addBtn(){
    
        try {
            
        String result = dataBase.insertNewEquimpent(Integer.parseInt(txt_code.getText()), txt_equimpentName.getText());
            lbl_message.setText(result);
            dataBase.inserActions(loginController.loginUser.getUserName()+"قام باضافة معدة جديدة");
                } 
        catch (NumberFormatException ex) {
             lbl_message.setText("حدثت مشكلة عند الاضافة "+ex.getMessage());
        }

    }
    
}
