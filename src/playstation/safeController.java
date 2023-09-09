package playstation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class safeController implements Initializable{

    @FXML
    private TextField txt_safeName;

    @FXML
    private Label lbl_message;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
    public void addbtn(){
    
        String result = dataBase.addNewSafe(txt_safeName.getText());
        lbl_message.setText(result);
    
    }
}
