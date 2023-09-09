
package playstation;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;


public class accounSummaryController implements Initializable {

    @FXML
    private TextField txt_account;

    @FXML
    private DatePicker date_pickerStart;

    @FXML
    private DatePicker date_pickerEnd;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       String [] allAccount = dataBase.retrivingColumnFromDataBase("SELECT name FROM son");
        TextFields.bindAutoCompletion(txt_account, allAccount);
         date_pickerStart.setValue(LocalDate.now().minus(Period.ofYears(1)));
         date_pickerEnd.setValue(LocalDate.now());
    }
    
   
    
    public void PrintReportAllMOneyOut(){
        try {
            JasperDesign jd = JRXmlLoader.load("accountSummary.jrxml");
        LocalDate startTime = date_pickerStart.getValue();
        LocalDate endTime = date_pickerEnd.getValue();
        HashMap <String,Object> para = new HashMap<>();

        String sql = "SELECT addMoney, detailsOfTransaction,dateOfTransaction ,son.name from lifeofcustomer JOIN son WHERE lifeofcustomer.customerAccountId = son.Id and transactionNumber = 556 and dateOfTransaction <= '"+endTime+"' and dateOfTransaction >=  '"+startTime+"'";
       
            System.out.println(sql);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            para.put("period", startTime.toString() +"    "+"الي"+"    "+endTime.toString());
            para.put("forWho", "لكافة الحسابات");

            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
            
        } catch (JRException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        }
   
   }
      public void PrintReportForOneAccountMOneyyOut(){
        try {
            JasperDesign jd = JRXmlLoader.load("accountSummary.jrxml");
        LocalDate startTime = date_pickerStart.getValue();
        LocalDate endTime = date_pickerEnd.getValue();
        HashMap <String,Object> para = new HashMap<>();
        para.put("period", startTime.toString() +"    "+"الي"+"    "+endTime.toString());
        para.put("forWho", txt_account.getText());
        int sonId = Integer.parseInt(dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT id from son WHERE name = '"+txt_account.getText()+"'"));
        System.out.println( "son id "+ sonId);
        String sql = "SELECT addMoney, detailsOfTransaction,dateOfTransaction ,son.name from lifeofcustomer JOIN son WHERE lifeofcustomer.customerAccountId = son.Id and son.Id = "+sonId+" and transactionNumber = 556 and dateOfTransaction <= '"+endTime+"' and dateOfTransaction >=  '"+startTime+"'";
        
            System.out.println(sql);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
            
        } catch (JRException ex) {
            dataBase.showMessageJOP(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(accounSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   }
}
