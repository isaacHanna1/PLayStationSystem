/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.safe;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class homeController implements Initializable {

    @FXML
    private Label lbl_companyName;
    @FXML
    private Label lbl_activeUser;
    static String companyName ="";

    @FXML
    private ComboBox<String> comboSafe;
   
    @FXML
    private MenuBar menuBar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lbl_activeUser.setText(loginController.loginUser.getUserName());
             companyName = dataBase.gettingOnvalueFromTableByAggregatefunction("select company_name from companydata");
            lbl_companyName.setText(companyName);
            int premission = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT permission from users WHERE id = ' "+ loginController.loginUser.getID()+"'"));
            System.out.println("the permission is "+ premission);
            dataBase.putingColumnFromDataBaseIntoCombox( "SELECT nameOfSafe from safe where id > 1 ORDER by id", comboSafe);
            String numberOfSafe = dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT count(nameOfSafe) from safe WHERE id> 1 ");
            // IF there is one safe 
               if(Integer.parseInt(numberOfSafe)==1){
                    comboSafe.getSelectionModel().selectFirst();            
                    comboSafe.setDisable(true);
                    safe.id = (comboSafe.getSelectionModel().getSelectedIndex())+2;             
                    menuBar.setVisible(true);
            }

            //انا خفتيهم علشان مش هيظهروا غير لما يختار الخزنة
               else{
                   menuBar.setVisible(false);
               }
          

        comboSafe.valueProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue ov, String t, String t1) {
            try{
          String safeName = comboSafe.getSelectionModel().getSelectedItem();
                System.out.println("safe name :"+safeName );
                String sql = ("SELECT id from safe WHERE nameOfSafe = '"+safeName+"'");
                System.out.println(sql);
                safe.id = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction(sql));
                 menuBar.setVisible(true);
                 comboSafe.setDisable(true);
                
            }
            catch(SQLException ex){
                dataBase.showMessageJOP(ex.getMessage());
            }
        }    

            
    });
            
           
//            btn_control.getChildren().get(0).setDisable(true);
//            btn_control.getChildren().get(1).setDisable(true);
//            //1
//           btn_control.getChildren().get(2).setDisable(true);
//           //2
//            btn_control.getChildren().get(3).setDisable(true);
//            //3
//           btn_control.getChildren().get(4).setDisable(true);
//           //4
//          btn_control.getChildren().get(5).setDisable(true);
//          //5
//            btn_control.getChildren().get(6).setDisable(true);
//            //6
//           btn_control.getChildren().get(7).setDisable(true);
//            btn_control.getChildren().get(8).setDisable(true);
//           btn_control.getChildren().get(9).setDisable(true);
//            btn_control.getChildren().get(10).setDisable(true);////
//            btn_control.getChildren().get(11).setDisable(true);
//            btn_control.getChildren().get(12).setDisable(true);
//           btn_control.getChildren().get(13).setDisable(true);
//        //7
//           btn_control.getChildren().get(14).setDisable(true);//
//           //8
//            btn_control.getChildren().get(15).setDisable(true);
//            //9
//            btn_control.getChildren().get(16).setDisable(true);
//            btn_control.getChildren().get(17).setDisable(true);
//            btn_control.getChildren().get(18).setDisable(true);
//
//            
            
        } catch (SQLException ex) {
           dataBase.showMessageJOP(ex.getMessage());
        }
        
           
    }
    public void openNewWindow(String name , String title){
    Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(name+".fxml"));
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage()+"  "+ex.getCause());
        }
  
    
    }
        public void openNewWindowFullScreen(String name , String title){
    Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(name+".fxml"));
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage());
            System.out.println(ex.getCause());
        }
  
    
    }
    public void openMainDataWindow(){
    
        openNewWindow("MainData", "الرئيسية");
    }
    
    public void openroomAdminWindow(){
    openNewWindow("roomAdmin", "الغرف");
    }
    
    public void openequimpentWindow(){
    openNewWindow("equimpent", "الادوات");
    }
    
    public void openUserWindow(){
    openNewWindow("users", "المستخدمين");
    
    }
    
    public void openSectionWindow(){
    openNewWindow("section", "الاقسام");
    
    }
    
    public void openOfferWindowWindow(){
    openNewWindow("offer", "العروض");
    
    }
    
    public void openNewDeviceWindow(){
    openNewWindow("newDevice", "جديد");
    
    }
    
    
    public void openNewFatherWindow(){
    openNewWindow("father", "الاب");
    
    }
    
    
    public void openNewSonWindow(){
    openNewWindow("son", "الابن");
    
    }
    
    
    
    public void openCafeSectionWindow(){
    openNewWindow("cafeSection", "الاقسام");
    
    }
    
    public void openCafeItemWindow(){
    openNewWindow("cafeItem", "الاصناف");
    
    }
    public void openBuyGoodsCafeWindow(){
    openNewWindow("CafeBuyGoods", "فاتورة شراء");
    
    }
    
    
      public void openSafeWindow(){
    openNewWindow("safe", "الخزينة");
    
    }
    
        
    
      public void outMoneyTo(){
    openNewWindow("outMoneyTo", "صرف نقدية");
    
    }
    
    
    
    
      public void openControlWindow(){
    openNewWindowFullScreen("control", "وحدات التحكم");
    
    }
    
       public void openRepositoryWindow(){
               openNewWindow("repository", "المخازن");

           
    }
      
         public void showingMoneyInSave(){
               openNewWindow("showMoneyInSafe", "استعلام خزائن");

           
    }
         
             public void ReceivingMoneyInSave(){
               openNewWindow("receivingMoneyFrom", "استلام نقدية ");

           
    }
                     public void StockTaking(){
               openNewWindow("Stocktaking", "جرد المخزن");
    }
                   public void openReportAboutEachDevice(){
               openNewWindow("ReporteachDeviceDetails", "تقرير ");
           
    }
    
                   public void openSupplierWindow(){
               openNewWindow("suppliers", "الموردين ");

           
    }
                   public void openPlayerAccountWindow(){
               openNewWindow("oldMoney", "حساب عميل ");

           
    }
          public void openAddcustomerWindow(){
               openNewWindow("players", "عميل جديد  ");

           
    }
                   
       public void openProfitReceiptWindow(){
               openNewWindow("proftOfReceipts", "ارباح فاتورة    ");

           
    }
            public void openAllBuyReceipt(){
               openNewWindow("allBuyReceipt", "فواتير شراء");

           
    }
            public void openBarCodeWindow(){
               openNewWindow("barCode", "طباعة الباركود");

           
    }
                   
    
          public void openBarAccountSummary(){
               openNewWindow("accountSummary", "كشف حساب المصروفات ");

           
    }
               
          public void openTableWindo(){
               openNewWindow("tables", "الطاولات");

           
    }
               public void openDetailedReportWindo(){
               openNewWindow("detailReport", "الارشيف");

           
    }
               public void openGameSectionReportWindo(){
               openNewWindow("simpleReportForEachSection", "تقرير عن صالة الالعاب");

           
    }    
    
    
       
     public void openLiveWindow(){
           
            Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("liveReceipt"+".fxml"));
                Stage stage = new Stage();
                stage.setTitle("فاتورة بيع مباشر");
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }

           
    }
     
         
}
    

