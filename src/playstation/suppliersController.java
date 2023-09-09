
package playstation;

import classes.player;
import classes.supplier;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class suppliersController  implements Initializable {

    @FXML
    private BorderPane container;

    @FXML
    private TextField txt_supplierName;

    @FXML
    private Button edit_btn;

    @FXML
    private Label lbl_message;

    @FXML
    private TextField txt_country;

    @FXML
    private TableView<supplier> table_supplier;
    @FXML
    private TableColumn<supplier, Integer> col_id;

    @FXML
    private TableColumn<supplier, String> col_supplierName;

    @FXML
    private TableColumn<supplier, String> col_supplierTel;

    @FXML
    private TableColumn<supplier, String> col_supplierAddress;

    @FXML
    private TextField txt_tel;  
    
    int supplier_id = 0 ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
                txt_supplierName.requestFocus();
                txt_supplierName.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_country.requestFocus();
                }
            });
                txt_country.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_tel.requestFocus();
                }
            });
                
        txt_tel.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){
                    if(event.getCode().toString() == "ENTER"){
                        insertnewsupplier();
                       }

                
                }
            });
        
                

        
        
        col_id.setCellValueFactory(new PropertyValueFactory<supplier,Integer>("supplier_id"));
        col_supplierAddress.setCellValueFactory(new PropertyValueFactory<supplier,String>("supplier_address"));
        col_supplierName.setCellValueFactory(new PropertyValueFactory<supplier,String>("supplier_name"));
        col_supplierTel.setCellValueFactory(new PropertyValueFactory<supplier,String>("supplier_tel"));
        gettingAllSuppliers();
    }
    
    public void insertnewsupplier(){
    String name = txt_supplierName.getText();
    String address = txt_country.getText();
    String tel = txt_tel.getText();
    
    if(name.equals("")){
        lbl_message.setText("ادخل اسم المورد");
        txt_supplierName.requestFocus();
        return;
    }
    
    boolean result =  dataBase.insertsupplierdata(name, address, tel);
    try {
       if(result){
        
            int fatherAccount_id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id FROM father WHERE Name = '"+"الموردين"+"'"));
            dataBase.addSonAccout(fatherAccount_id, name);
            lbl_message.setText("تمت الاضافة بنجاح");
            txt_country.setText("");
            txt_supplierName.setText("");
            txt_tel.setText("");
            gettingAllSuppliers();
            txt_supplierName.requestFocus();
        }
       else{
            lbl_message.setText("لدينا هذا البيانات من قبل (الاسم او الرقم)");
            txt_supplierName.requestFocus();

       }
       
       }catch (SQLException ex) {
           dataBase.showMessageJOP(ex.getMessage());
           return;
        }
         catch (Exception ex) {
             dataBase.showMessageJOP(ex.getMessage());
             txt_supplierName.requestFocus();
        }
        
       }
       
       
    
    
    public void gettingAllSuppliers(){
    
        
        table_supplier.getItems().clear();
        try{
        Object []table =  dataBase.retrievingTableInArrayOfObject("SELECT `id`, `supliername`, `suplieraddress`, `suppliertel` FROM `supplier` WHERE 1");
            for (int i = 0; i < table.length; i++) {
                String [] row = (String[]) table[i];
                table_supplier.getItems().add(new supplier(Integer.parseInt(row[0]), row[1], row[3], row[2]));
                
            }
        }
        catch(SQLException ex){
        lbl_message.setText(ex.getMessage());
        }
    
    }
    public void selectItem (){
    
        supplier selectedOne = table_supplier.getSelectionModel().getSelectedItem();
        txt_country.setText(selectedOne.getSupplier_address());
        txt_supplierName.setText(selectedOne.getSupplier_name());
        txt_tel.setText(selectedOne.getSupplier_tel());
        supplier_id = selectedOne.getSupplier_id();
        txt_supplierName.requestFocus();
    
    }
    public void edit(){
        
        try{
        
            String newName = txt_supplierName.getText();
            String newAddress = txt_country.getText();
            String newTel = txt_tel.getText();
            String sql = "UPDATE supplier set supliername = '"+newName+"' , suplieraddress = '"+newAddress+"' , suppliertel = '"+newTel+"' " +"WHERE id = "+supplier_id;
            dataBase.excuteNonQuery(sql);
            gettingAllSuppliers();
            lbl_message.setText("تم تعديل ");
            txt_supplierName.requestFocus();
        }
        catch(SQLException ex){
            lbl_message.setText(ex.getMessage());
        }
        
    
    }
}
