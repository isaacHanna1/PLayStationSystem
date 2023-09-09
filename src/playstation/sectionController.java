package playstation;

import classes.offerClass;
import classes.section;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.controlsfx.control.textfield.TextFields;

public class sectionController implements Initializable{
  @FXML
    private BorderPane container;
    @FXML
    private TextField txt_sectionName;

    @FXML
    private TextField txt_sectionHourPrice;

    @FXML
    private TextField txt_sectionGamePrice;

    @FXML
    private TextField txt_sectionMultiHourPrice;
    @FXML
    private Label lbl_message;
    @FXML
    private TextField txt_sectionImagePath;

    String [] allSection = {};
    int sectionID ; 
    @FXML
    private TableView<section> table_section;

    @FXML
    private TableColumn<section,String> col_sectionName;

    @FXML
    private TableColumn<section,Float> col_sectionSinglePrice;

    @FXML
    private TableColumn<section,Float> col_sectionMultiPrice;

    @FXML
    private TableColumn<section, Float> col_sectionGamePrice;

    @FXML
    private TableColumn<section, String> col_sectionImgPath;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txt_sectionName.requestFocus();
        txt_sectionName.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_sectionHourPrice.requestFocus();
                }
            });
        txt_sectionHourPrice.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_sectionMultiHourPrice.requestFocus();
                }
            });
        txt_sectionMultiHourPrice.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_sectionGamePrice.requestFocus();
                }
            });
        txt_sectionGamePrice.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){

                txt_sectionImagePath.requestFocus();
                }
            });
        
        txt_sectionImagePath.setOnKeyReleased((event) -> {
                if(event.getCode().toString() == "ENTER"){
                if(event.getCode().toString() == "ENTER"){
                    addBtn();
                }
                }
            });
        
        
        
           allSection = dataBase.gettingColumnFromDatabaseIntoArray("section", "sectionName","Id");
            TextFields.bindAutoCompletion(txt_sectionName, allSection);
            
           col_sectionName.setCellValueFactory(new PropertyValueFactory<section,String>("sectionName"));
           col_sectionSinglePrice.setCellValueFactory(new PropertyValueFactory<section,Float>("sectionHourPrice"));
           col_sectionMultiPrice.setCellValueFactory(new PropertyValueFactory<section,Float>("sectionMultiHourPrice"));
           col_sectionGamePrice.setCellValueFactory(new PropertyValueFactory<section,Float>("sectionGamePrice"));
           col_sectionImgPath.setCellValueFactory(new PropertyValueFactory<section,String>("imagePath"));
           
            gettingAllSection();
    }

    public void addBtn(){

        
        
        String sectionName = txt_sectionName.getText();
        
        if(sectionName.equals("")){
            lbl_message.setText("ادخل اسم القسم ");
            return;
        }
        try{
        float sectionHourPrice = Float.parseFloat(txt_sectionHourPrice.getText());
        float multiHourPrice = Float.parseFloat(txt_sectionMultiHourPrice.getText());
        float sectionGamePrice = Float.parseFloat(txt_sectionGamePrice.getText());
        String sectionImagePath = txt_sectionImagePath.getText();
        
          }
        catch(NumberFormatException ex){
            lbl_message.setText("ادخل الاسعار بشكل صحيح");
        }
        try{
        String result = dataBase.insertNewSection(txt_sectionName.getText(), Float.parseFloat(txt_sectionHourPrice.getText()), Float.parseFloat(txt_sectionMultiHourPrice.getText()),Float.parseFloat(txt_sectionGamePrice.getText()),txt_sectionImagePath.getText());
        if(result.contains("Duplicate")){
            
        lbl_message.setText("هذا القسم مسجل من قبل");    
        txt_sectionName.requestFocus();
        }
        else{
        lbl_message.setText(result);
        txt_sectionName.requestFocus();
        }
        dataBase.inserActions(loginController.loginUser.getUserName()+"قام باضافة قسم جديد");
        txt_sectionName.requestFocus();        
        }
        catch(NumberFormatException ex){
        lbl_message.setText("ادخل الاسعار بشكل صحيح");
        txt_sectionName.requestFocus();
        }
      table_section.getItems().clear();
        gettingAllSection();

    }
    
    public void btnBrowse(){
    
              
    FileChooser fileChooser = new FileChooser();
     File file = fileChooser.showOpenDialog(container.getScene().getWindow());
  
     txt_sectionImagePath.setText(file.getPath());
        System.out.println(file.getPath());
    }
    
    public void searchButton(){
        
        String sectionName = txt_sectionName.getText();
      try {
          String []sectionData = dataBase.retrievingRowIntableInArray("SELECT * FROM `section` WHERE sectionName = '"+sectionName+"'" );
          sectionID = Integer.parseInt(sectionData[0]);
          txt_sectionName.setText(sectionData[1]);
          txt_sectionHourPrice.setText(sectionData[2]);
          txt_sectionMultiHourPrice.setText(sectionData[3]);
          txt_sectionGamePrice.setText(sectionData[4]);
          txt_sectionImagePath.setText(sectionData[5]);
          
      } catch (SQLException ex) {
          lbl_message.setText(ex.getMessage());
      }

    
    }
    
    public void editButton(){
    
        String sectionName = txt_sectionName.getText();
        
        if(sectionName.equals("")){
            lbl_message.setText("ادخل اسم القسم ");
            return;
        }
        try{
        float sectionHourPrice = Float.parseFloat(txt_sectionHourPrice.getText());
        float multiHourPrice = Float.parseFloat(txt_sectionMultiHourPrice.getText());
        float sectionGamePrice = Float.parseFloat(txt_sectionGamePrice.getText());
        String sectionImagePath = txt_sectionImagePath.getText();
        sectionImagePath = sectionImagePath.replace("\\","\\\\");
        System.out.println("section path "+ sectionImagePath);
        String sqlEdit = "UPDATE section set sectionName = '"+sectionName+"' , sectionHourPrice = "+sectionHourPrice+", multiHourPrice = "+multiHourPrice+", " +
        "SectionGamePrice ="+sectionGamePrice+" , imagePath = '"+sectionImagePath+"' where id = "+sectionID;
            System.out.println(sqlEdit);
          dataBase.excuteNonQuery(sqlEdit);
          lbl_message.setText("تم التعديل");
        }
        catch(NumberFormatException ex){
            lbl_message.setText("ادخل الاسعار بشكل صحيح");
        }
        catch(SQLException ex){
        
        dataBase.showMessageJOP(ex.getMessage());
        System.out.println(ex.getMessage());
        }
        
      table_section.getItems().clear();
        gettingAllSection();
    }


        public void gettingAllSection(){
            
        try {
           
            ObservableList<section> allSection = FXCollections.observableArrayList();
           
            String sql = "SELECT * FROM `section` ORDER by id";
            Object[] table = dataBase.retrievingTableInArrayOfObject(sql);
           
           
                for (int i = 0; i < table.length; i++) {
                 String [] row  = (String[]) table[i];
                 int id   = Integer.parseInt(row[0]);
                 String sectionName = row[1];
                 float sectionSingleHour = Float.parseFloat(row[2]);
                 float sectionSingleMulti = Float.parseFloat(row[3]);
                 float sectionGame = Float.parseFloat(row[4]);
                 String imagePath   = row[5];
                 section newOne = new section(id, sectionName, sectionSingleHour, sectionSingleMulti, sectionGame, imagePath);
                    
                    allSection.add(newOne);
                 
                }
                table_section.setItems(allSection);
                
        } catch (SQLException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        } 
            
            
        }
        
        public void onMouseClickInTable(){

            section newOne = table_section.getSelectionModel().getSelectedItem();
            txt_sectionName.setText(newOne.getSectionName());
            searchButton();
        }
        
        
}
