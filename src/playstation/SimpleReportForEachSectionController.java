/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class SimpleReportForEachSectionController implements Initializable {

    @FXML
    private DatePicker startDate_picker;
    @FXML
    private DatePicker endDate_picker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      startDate_picker.setValue(LocalDate.now());
      endDate_picker.setValue(LocalDate.now());
    }    
    
    public void  showReportOfGameSection() throws SQLException{
    
            
          JasperDesign jd;
          LocalDate startTime = startDate_picker.getValue();
          LocalDate endTime = endDate_picker.getValue();
        try {
            jd = JRXmlLoader.load("gameSectionReport.jrxml");
            String sql ="SELECT sectionName ,sum(totalOfDevice) ,TRUNCATE( ( (sum(totalHour)+(SUM(totalMinutes))/60 ) ) , 2 )from receiptinformation WHERE date<= '"+endTime+"' and date >= '"+startTime+"' GROUP by sectionName ";
            System.out.println(sql);
            String sqlRow = "SELECT TRUNCATE(( sum(totalHour) + ((sum(totalMinutes))/60)  ),2) , SUM(totalOfDevice) from receiptinformation WHERE date <= '"+endTime+"' and date >= '"+startTime+"' ";
            System.out.println(sqlRow);
            String[] row = dataBase.retrievingRowIntableInArray(sqlRow);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            HashMap <String,Object> para = new HashMap<>();
            para.put("para_startDate", startTime);
            para.put("para_endDate", endTime);
            para.put("total",row[1]);
            para.put("totalHours",row[0]);
            
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
            
        } catch (JRException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
            
            
    }
    
    public void  showReportOfGameDevice() throws SQLException{
    
            
          JasperDesign jd;
          LocalDate startTime = startDate_picker.getValue();
          LocalDate endTime = endDate_picker.getValue();
        try {
            jd = JRXmlLoader.load("reportEachDeviceGame.jrxml");
            String sql ="SELECT newdevice.deviceName ,sum(totalOfDevice) ,TRUNCATE( ( (sum(totalHour)+(SUM(totalMinutes))/60 ) ) , 2 )from receiptinformation RIGHT JOIN newdevice ON date <= '2022-10-17' and date >= '2022-10-17' and receiptinformation.deviceID = newdevice.id GROUP by newdevice.deviceName ORDER by sum(totalOfDevice) DESC ";
            System.out.println(sql);
//            String sqlRow = "SELECT TRUNCATE(( sum(totalHour) + ((sum(totalMinutes))/60)  ),2) , SUM(totalOfDevice) from receiptinformation WHERE date <= '"+endTime+"' and date >= '"+startTime+"' ";
//            System.out.println(sqlRow);
//            String[] row = dataBase.retrievingRowIntableInArray(sqlRow);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            HashMap <String,Object> para = new HashMap<>();
            para.put("para_startDate", startTime);
            para.put("para_endDate", endTime);
//            para.put("total",row[1]);
//            para.put("totalHours",row[0]);
            
            JasperReport js = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(js,para,dataBase.setConectionReport());
            JasperViewer.viewReport(jp,false);
            
        } catch (JRException ex) {
                dataBase.showMessageJOP(ex.getMessage());
        }
            
            
    }
}
