package playstation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class showMoneyInSafeController  implements Initializable{

    @FXML
    private ComboBox<String> ComboSafeName;
    
    @FXML
    private TextField txt_total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            dataBase.putingColumnFromDataBaseIntoCombox("SELECT nameOfSafe from safe ", ComboSafeName);
        } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
        
    }
    
    public void showTotalOfSave(){
    
        try {
            String safeName = ComboSafeName.getSelectionModel().getSelectedItem();
            System.out.println("");
            String id  = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from safe WHERE nameOfSafe = '"+safeName+"'");
                System.out.println("the id is : "+id);
            String total = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sum(total) FROM enteroutmoney WHERE safty_id = '"+id+"'");
            txt_total.setText(total+"");
            
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
                
        
    }
    
}
