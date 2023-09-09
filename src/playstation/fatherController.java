package playstation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class fatherController implements Initializable {
    @FXML
    private Label lbl_mesaage;
    @FXML
    private TextField txt_fatherTxt;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txt_fatherTxt.requestFocus();
        txt_fatherTxt.setOnKeyReleased((event) -> {
             if(event.getCode().toString() == "ENTER"){
                    if(event.getCode().toString() == "ENTER"){
                        
                        addFatherAccount();
                                    }

                
                }
            });
        
                }
    
    public void addFatherAccount(){
    if(txt_fatherTxt.getText().equals("")){
        lbl_mesaage.setText("ادخل اسم الحساب");
    return;
    }
        String result = dataBase.addFatherAccout(txt_fatherTxt.getText());
        
        lbl_mesaage.setText(result);
    
    
    
    }
    
    
    
    
}
