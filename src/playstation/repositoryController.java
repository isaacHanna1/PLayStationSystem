
package playstation;

import classes.buyReceipt;
import classes.repository;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
public class repositoryController implements Initializable{

    
    @FXML
    private TextField txt_repositoryName;

    @FXML
    private Label lbl_message;

      @FXML
    private TableView<repository> repository_table;

    @FXML
    private TableColumn<repository, Integer> col_id;

    @FXML
    private TableColumn<repository, String> col_repository_name;
    
    int selectItemID = 0;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        setAllRepository();
        txt_repositoryName.requestFocus();
        
         col_id.setCellValueFactory(new PropertyValueFactory<repository,Integer>("repositoryID"));
         col_repository_name.setCellValueFactory(new PropertyValueFactory<repository,String>("repositoryName"));
           
        txt_repositoryName.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){
                    if(event.getCode().toString() == "ENTER"){
                        
                        add_btn();
                                    }

                
                }
            });
        
    }
    public void add_btn(){
    
        if(txt_repositoryName.getText().equals("")){
          lbl_message.setText("ادخل اسم المخزن اولا ");
          
        return;
        }
        String result = dataBase.insertNewRepository(txt_repositoryName.getText());
        setAllRepository();
        lbl_message.setText(result);
    }
    
    public void setAllRepository(){
        try{
            Object[] table = dataBase.retrievingTableInArrayOfObject("SELECT * FROM `repository`");
            for (int i = 0; i < table.length; i++) {
              
                    String [] row =(String[]) table[i];
                    int id = Integer.parseInt(row[0]);
                    String repositoryName = row[1];
                    repository repository = new repository(id, repositoryName);
                    repository_table.getItems().add(repository);
            }
            
            
        
        }
        catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
    }
    
    public void selectItem(){
        
        txt_repositoryName.setText(repository_table.getSelectionModel().getSelectedItem().getRepositoryName());
        selectItemID =  repository_table.getSelectionModel().getSelectedItem().getRepositoryID();
    
    }
    
    public void editRepository(){
        
        try{
            repository_table.getItems().clear();
         dataBase.excuteNonQuery("UPDATE `repository` SET `repositoryName`= '"+txt_repositoryName.getText()+" 'WHERE id = "+selectItemID);
         setAllRepository();
        }
        catch(SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
   
    }
}
