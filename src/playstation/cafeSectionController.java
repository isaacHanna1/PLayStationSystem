
package playstation;

import classes.cafeItem;
import classes.cutomerReceipt;
import classes.supplier;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
public class cafeSectionController implements Initializable{
    @FXML
    private TextField txt_SectionName;

    
    @FXML
    private TableView<cafeItem> table_section;

    @FXML
    private TableColumn<cafeItem, String> col_sectionName;
    String oldSectionName ="";
    @FXML
    private Label lbl_message;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        txt_SectionName.requestFocus();
        txt_SectionName.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){
                    if(event.getCode().toString() == "ENTER"){
                        
                        addNewCafeSection();
                                    }

                
                }
            });
        
        col_sectionName.setCellValueFactory(new PropertyValueFactory<cafeItem,String>("sectionName"));
        getAllSectionName();
        
    }
    
    public void addNewCafeSection(){
    
        if(txt_SectionName.getText().equals("")){
            lbl_message.setText("ادخل اسم القسم اولا ");
        return;
        }
        String message = dataBase.insertNewCafeSection(txt_SectionName.getText());
        lbl_message.setText(message);
        getAllSectionName();
    
    }
    
    public void getAllSectionName(){
        
        
        table_section.getItems().clear();
        try{
        Object []table =  dataBase.retrievingTableInArrayOfObject("SELECT  `cafeSectionName` FROM `cafesection` WHERE 1");
            for (int i = 0; i < table.length; i++) {
                String [] row = (String[]) table[i];
                table_section.getItems().add(new cafeItem(row[0]));
                
            }
        }
        catch(SQLException ex){
        lbl_message.setText(ex.getMessage());
        }
    
    }
    
    public void select (){
        
        
        cafeItem selectedOne = table_section.getSelectionModel().getSelectedItem();
        txt_SectionName.setText(selectedOne.getSectionName());
        oldSectionName =  selectedOne.getSectionName();
        
    
    }
    
    public void edit (){
        try{
        String newSectionName = txt_SectionName.getText();
        String sql = "UPDATE cafesection set cafeSectionName = '"+newSectionName+"' WHERE cafeSectionName = '"+oldSectionName+"'";
            System.out.println(sql);
        dataBase.excuteNonQuery(sql);
        lbl_message.setText("تم التعديل");
        getAllSectionName();
        }catch(SQLException ex){
        lbl_message.setText(ex.getMessage());
        }
    
    }
    
}
