/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.offerClass;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author Seha
 */
public class offerController implements Initializable{

    @FXML
    private TextField txt_sectionID;

    @FXML
    private TextField txt_mainUnit;

    @FXML
    private TextField txt_addUnit;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TableView<offerClass> offerTable;
    @FXML
    private TableColumn<offerClass, Integer> col_id;
    @FXML
    private TableColumn<offerClass, Integer> col_Section;

    @FXML
    private TableColumn<offerClass,Float> col_mainItem;

    @FXML
    private TableColumn<offerClass, Float> col_addItem;

    @FXML
    private TableColumn<offerClass, String> col_startDate;

    @FXML
    private TableColumn<offerClass, String> col_endDate;

    
    @FXML
    private TableColumn<offerClass, String> col_offerName;

    @FXML
    private TextField txt_offerName;
    
    @FXML
    private Label lbl_message;
    
    String [] allSections ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       allSections = dataBase.gettingColumnFromDatabaseIntoArray("section", "sectionName", "id");
      TextFields.bindAutoCompletion(txt_sectionID, allSections);
      col_id.setCellValueFactory(new PropertyValueFactory<offerClass,Integer>("ID"));
      col_Section.setCellValueFactory(new PropertyValueFactory<offerClass,Integer>("sectionId"));
      col_mainItem.setCellValueFactory(new PropertyValueFactory<offerClass,Float>("mainUnit"));
      col_addItem.setCellValueFactory(new PropertyValueFactory<offerClass,Float>("aadUnit"));
      col_startDate.setCellValueFactory(new PropertyValueFactory<offerClass,String>("startDate"));
      col_endDate.setCellValueFactory(new PropertyValueFactory<offerClass,String>("endDate"));
      col_offerName.setCellValueFactory(new PropertyValueFactory<offerClass,String>("offerName"));
      
        gettingAllOffer();
    }
    
    
    public void addBtn(){
    
         try{
         String offerName = txt_offerName.getText();
         String firstDate = startDate.getValue().toString();
         String lastDAte = endDate.getValue().toString();
         
         
         
         int sectionId = findID(txt_sectionID.getText(), allSections);
         float mainUnit = Float.parseFloat(txt_mainUnit.getText());
         float addUnit = Float.parseFloat(txt_addUnit.getText());
            
         if(offerName.equals("")){
            lbl_message.setText("ادخل اسم العرض");
            return;
         }
         
         int id = 1;
         
         if(offerTable.getItems().size() != 0){
         
        ObservableList<offerClass> items = offerTable.getItems();
        offerClass getlast = items.get(items.size()-1);
             System.out.println(getlast.getOfferName());
        id = getlast.getID()+1;
        
         }
        String result = dataBase.insertNewOffer(sectionId,mainUnit,addUnit, firstDate, lastDAte, offerName);
          if(result.contains("Duplicate")){
         
         lbl_message.setText("هناك عرض بنفس البيانات ");
         return;
         }
        offerClass newOffer = new offerClass(id, sectionId, mainUnit, addUnit, firstDate, lastDAte, offerName);
        offerTable.getItems().add(newOffer);
         lbl_message.setText(result);
         dataBase.inserActions(loginController.loginUser.getUserName()+"قام باضافة عرض جديد");
    }
         catch(NullPointerException ex){
         lbl_message.setText("ادخل البيانات بشكل صحيح");
         }
         catch(NumberFormatException ex){
         lbl_message.setText("ادخل البيانات بشكل صحيح");
         }
              
    }
    
     public void gettingAllOffer(){

            try {

                ObservableList<offerClass> allOffer = FXCollections.observableArrayList();

                String sql = "SELECT * FROM `offer` ORDER by id";
                Object[] table = dataBase.retrievingTableInArrayOfObject(sql);


                    for (int i = 0; i < table.length; i++) {
                     String [] row  = (String[]) table[i];
                     int id   = Integer.parseInt(row[0]);
                     int sectionId = Integer.parseInt(row[1]);
                     float mainUnit = Float.parseFloat(row[2]);
                     float addUnit = Float.parseFloat(row[3]);
                     String startDate = row[4];
                     String endDate   = row[5];
                     String offerName = row[6];

                     offerClass newOne = new offerClass(id, sectionId, mainUnit, addUnit, startDate, endDate, offerName);

                        allOffer.add(newOne);

                    }
                    offerTable.setItems(allOffer);

            } catch (SQLException ex) {
                dataBase.showMessageJOP(ex.getMessage());
            } 
       
       
   }
   
          public int findID(String data , String [] array ){
    
        int i=0 ;
         
        for (; i < array.length; i++) {
         
            if(data.equals(array[i])){
         
            break; 
            }
          
        }
    return i+1 ;
    }
          
    public void getSelectedOfferToTxtBox(){
        
        offerClass selectedItem  = offerTable.getSelectionModel().getSelectedItem();
        txt_offerName.setText(selectedItem.getOfferName());
        txt_addUnit.setText(selectedItem.getAadUnit()+"");
        txt_mainUnit.setText(selectedItem.getMainUnit()+"");
        try{
            System.out.println("section ID : "+selectedItem.getSectionId());
       txt_sectionID.setText(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT sectionName from section WHERE id = '"+selectedItem.getSectionId()+"'"));
        }catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
        }
    }

        public void editButton(){
            try{
            offerClass selectedItem  = offerTable.getSelectionModel().getSelectedItem();
            String sql = "DELETE FROM `offer` WHERE id = '"+selectedItem.getID()+"'";
            String firstDate = startDate.getValue().toString();
            String lastDAte = endDate.getValue().toString();
            int sectionId = findID(txt_sectionID.getText(), allSections);
            float mainUnit = Float.parseFloat(txt_mainUnit.getText());
            float addUnit = Float.parseFloat(txt_addUnit.getText());
            String offerName = txt_offerName.getText();
            if(offerName.equals("")){
             lbl_message.setText("ادخل اسم العرض");
             return;
         }
         
            dataBase.excuteNonQuery(sql);
            String result = dataBase.insertNewOffer(sectionId,mainUnit,addUnit, firstDate, lastDAte, offerName);
            gettingAllOffer();
            lbl_message.setText("تم التعديل ");
            }catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
            }
            
         catch(NullPointerException ex){
         lbl_message.setText("ادخل البيانات بشكل صحيح");
         }
         catch(NumberFormatException ex){
         lbl_message.setText("ادخل البيانات بشكل صحيح");
         }
              
        }
    
}
