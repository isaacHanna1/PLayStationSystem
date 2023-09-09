/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.textfield.TextFields;
import javafx.scene.control.TreeView;

/**
 *
 * @author Seha
 */
public class sonController implements Initializable {

    @FXML
    private TextField txt_fatherAccount;

    @FXML
    private TextField txt_sonAccount;
    
        @FXML
    private TreeView<String> treeViewAccounts;
    @FXML
    private Label lbl_mesage;
    String [] allFatherAccount ={};
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        txt_fatherAccount.requestFocus();
        txt_fatherAccount.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_sonAccount.requestFocus();
                }
            });
        
        txt_sonAccount.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){
                    if(event.getCode().toString() == "ENTER"){
                        
                        addSonAccount();
                                    }

                
                }
            });
        
        allFatherAccount = dataBase.gettingColumnFromDatabaseIntoArray("father", "Name", "ID");
        TextFields.bindAutoCompletion(txt_fatherAccount, allFatherAccount);
        ShowTree();
    }
    
    
    public void addSonAccount(){
       String FatherAccountName = txt_fatherAccount.getText();
       try{
       int id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id FROM father WHERE Name = '"+FatherAccountName+"'"));
       String sonAccountName  = txt_sonAccount.getText();
       String result = dataBase.addSonAccout(id, sonAccountName);
       if(sonAccountName.equals("") || FatherAccountName.equals("")){
           lbl_mesage.setText("ادخل البيانات ");
           return;
       }
       lbl_mesage.setText(result);
       ShowTree();
        
       }catch(SQLException ex){
       dataBase.showMessageJOP(ex.getMessage());
       }
    
    
    }
    
    public void ShowTree(){
    TreeItem <String> root = new TreeItem<>("شجرة الحسابات");
        treeViewAccounts.setRoot(root);
        for (int i = 0; i < allFatherAccount.length; i++) {
        TreeItem <String> newItem = new TreeItem<>(allFatherAccount[i]);    
        
        try{
            root.getChildren().add(newItem);
            int id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id FROM father WHERE Name = '"+allFatherAccount[i]+"'"));
            String []allSon = dataBase.retrivingColumnFromDataBase("SELECT  name from son WHERE father_id = '"+id+"'");
            System.out.println("the length of father "+allFatherAccount[i]+"     "+allSon.length);
          for (int j = 0; j < allSon.length; j++) {
                TreeItem <String> sonItem = new TreeItem<>(allSon[j]);    
                newItem.getChildren().add(sonItem);
            }
            
        }catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        }
        }
        
    
    }
    
   
    
    
    
}
