
package playstation;

import classes.detiledReport;
import classes.lifeCycleOfTransaction;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class DetailReportController implements Initializable {

    @FXML
    private Pane periodPane;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private DatePicker datePickerEnd;
    
    @FXML
    private TableView<detiledReport> table_detailedReport;
    @FXML
    private TableColumn<detiledReport, String> col_Period;
    @FXML
    private TableColumn<detiledReport, Float> col_games;
    @FXML
    private TableColumn<detiledReport, Float> col_receipt;
    @FXML
    private TableColumn<detiledReport, Float> col_total;
    @FXML
    private TableColumn<detiledReport, Float> col_expenses;
    @FXML
    private TableColumn<detiledReport, Float> col_profit;
    
      @FXML
    private TableColumn<detiledReport, Float> col_hourWork;

    
    @FXML
    private Label lbl_games;
    
    @FXML
    private Label lbl_toatalHourWork;
    @FXML
    private Label lbl_receipt;

    @FXML
    private Label lbl_total;

    @FXML
    private Label lbl_expenses;

    @FXML
    private Label lbl_profit;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_Period.setCellValueFactory(new PropertyValueFactory<detiledReport,String>("period"));
        col_games.setCellValueFactory(new PropertyValueFactory<detiledReport,Float>("gamesProfit"));   
        col_receipt.setCellValueFactory(new PropertyValueFactory<detiledReport,Float>("receiptProfit"));   
        col_total.setCellValueFactory(new PropertyValueFactory<detiledReport,Float>("totalProfit"));   
        col_expenses.setCellValueFactory(new PropertyValueFactory<detiledReport,Float>("expenses"));   
        col_profit.setCellValueFactory(new PropertyValueFactory<detiledReport,Float>("reminderProfit"));   
        col_hourWork.setCellValueFactory(new PropertyValueFactory<detiledReport,Float>("totaLHourWork"));   
      
        periodPane.setVisible(false);
//        datePickerStart.setValue(LocalDate.now().minus(Period.ofYears(1)));
//        datePickerEnd.setValue(LocalDate.now());
         
    } 
    
    
    public void dailyDetailed(){
        try{
       table_detailedReport.getItems().clear();
//خد بالك جملة دي بترجع اليوم مقسوم لتلت صفوف كل صف بيعبر عن حاجة الصفين الولولين بيعبروا عن الارباح والتالت بيعبر عن المصروفات في يوم                
            String sqlForDailyDetailed 
                  = "SELECT dateOfTransaction , typeOfTransaction , sum(addMoney) from lifeofcustomer WHERE typeOfTransaction != 5 GROUP by dateOfTransaction , typeOfTransaction ORDER by dateOfTransaction, typeOfTransaction";
                 Object[] table = dataBase.retrievingTableInArrayOfObject(sqlForDailyDetailed);
                if(!(table.length == 0)){
                    String Date = "";
                    float receiptProfit = 0 ;
                    float gamesPfrofit  = 0 ;
                    float expenses      = 0 ;
                    int numOfRow = 1;
                for (int i = 0; i < table.length; i++) {
                   String [] row         = (String[]) table[i];
                    String dateOfDay = row[0];
                    
                    
                    if (Integer.parseInt(row[1])== 1){
                    float totalOfReceipt = Float.parseFloat(row[2]);
                    receiptProfit = totalOfReceipt ;
                       
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 2){   
                        float totalOfGames =  Float.parseFloat(row[2]);
                        gamesPfrofit = totalOfGames ;
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 3){   
                    float expensesOfDay = Float.parseFloat(row[2]);
                    expenses = expensesOfDay ;
                        
                        
                        
                    }
                     if (numOfRow % 3 == 0 || numOfRow%2 == 0 ||numOfRow %1 ==0){
                      float total = receiptProfit + gamesPfrofit ;
                      float ReminderProfit  = total - expenses ;
                      detiledReport newDay = new detiledReport(dateOfDay, gamesPfrofit, receiptProfit, expenses);
                      table_detailedReport.getItems().add(newDay);
                        System.out.println("true when i "+ i);
                         Date = "";
                         receiptProfit = 0 ;
                         gamesPfrofit  = 0 ;
                         expenses      = 0 ;
                         numOfRow = 1;
                    }
                    
                    
                    
                    
                    }
                 }
           }
        
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        
        }
        
        totalOfEachCOlumn();
    }
    
    
    public void monthlyDetailed(){
        try{
       table_detailedReport.getItems().clear();
       
//خد بالك جملة دي بترجع اليوم مقسوم لتلت صفوف كل صف بيعبر عن حاجة الصفين الولولين بيعبروا عن الارباح والتالت بيعبر عن المصروفات في يوم                
            String sqlForDailyDetailed 
                  = "SELECT MONTH(dateOfTransaction) , typeOfTransaction , sum(addMoney) from lifeofcustomer WHERE typeOfTransaction != 5 GROUP by MONTH(dateOfTransaction) , typeOfTransaction ORDER by MONTH(dateOfTransaction), typeOfTransaction";
                 Object[] table = dataBase.retrievingTableInArrayOfObject(sqlForDailyDetailed);
                if(!(table.length == 0)){
                    String Date = "";
                    float receiptProfit = 0 ;
                    float gamesPfrofit  = 0 ;
                    float expenses      = 0 ;
                    int numOfRow = 1;
                for (int i = 0; i < table.length; i++) {
                   String [] row         = (String[]) table[i];
                    String dateOfDay = row[0];
                    
                    
                    if (Integer.parseInt(row[1])== 1){
                    float totalOfReceipt = Float.parseFloat(row[2]);
                    receiptProfit = totalOfReceipt ;
                       
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 2){   
                        float totalOfGames =  Float.parseFloat(row[2]);
                        gamesPfrofit = totalOfGames ;
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 3){   
                    float expensesOfDay = Float.parseFloat(row[2]);
                    expenses = expensesOfDay ;
                        
                        
                        
                    }
                     if (numOfRow % 3 == 0 || numOfRow%2 == 0 ||numOfRow %1 ==0){
                      float total = receiptProfit + gamesPfrofit ;
                      float ReminderProfit  = total - expenses ;
                      detiledReport newDay = new detiledReport(dateOfDay, gamesPfrofit, receiptProfit, expenses);
                      table_detailedReport.getItems().add(newDay);
                        System.out.println("true when i "+ i);
                         Date = "";
                         receiptProfit = 0 ;
                         gamesPfrofit  = 0 ;
                         expenses      = 0 ;
                         numOfRow = 1;
                    }
                    
                    
                    
                    
                    }
                 }
           }
        
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        
        }
        
        totalOfEachCOlumn();
    }
    public void yearDetailed(){
        try{
       table_detailedReport.getItems().clear();
       
//خد بالك جملة دي بترجع اليوم مقسوم لتلت صفوف كل صف بيعبر عن حاجة الصفين الولولين بيعبروا عن الارباح والتالت بيعبر عن المصروفات في يوم                
            String sqlForDailyDetailed 
                  = "SELECT year(dateOfTransaction) , typeOfTransaction , sum(addMoney) from lifeofcustomer WHERE typeOfTransaction != 5 GROUP by year(dateOfTransaction) , typeOfTransaction ORDER by year(dateOfTransaction), typeOfTransaction";
                 Object[] table = dataBase.retrievingTableInArrayOfObject(sqlForDailyDetailed);
                if(!(table.length == 0)){
                    String Date = "";
                    float receiptProfit = 0 ;
                    float gamesPfrofit  = 0 ;
                    float expenses      = 0 ;
                    int numOfRow = 1;
                for (int i = 0; i < table.length; i++) {
                   String [] row         = (String[]) table[i];
                    String dateOfDay = row[0];
                    
                    
                    if (Integer.parseInt(row[1])== 1){
                    float totalOfReceipt = Float.parseFloat(row[2]);
                    receiptProfit = totalOfReceipt ;
                       
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 2){   
                        float totalOfGames =  Float.parseFloat(row[2]);
                        gamesPfrofit = totalOfGames ;
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 3){   
                    float expensesOfDay = Float.parseFloat(row[2]);
                    expenses = expensesOfDay ;
                        
                        
                        
                    }
                     if (numOfRow % 3 == 0 || numOfRow%2 == 0 ||numOfRow %1 ==0){
                      float total = receiptProfit + gamesPfrofit ;
                      float ReminderProfit  = total - expenses ;
                      detiledReport newDay = new detiledReport(dateOfDay, gamesPfrofit, receiptProfit, expenses);
                      table_detailedReport.getItems().add(newDay);
                        System.out.println("true when i "+ i);
                         Date = "";
                         receiptProfit = 0 ;
                         gamesPfrofit  = 0 ;
                         expenses      = 0 ;
                         numOfRow = 1;
                    }
                    
                    
                    
                    
                    }
                 }
           }
        
        catch(SQLException ex){
        dataBase.showMessageJOP(ex.getMessage());
        
        }
        
        totalOfEachCOlumn();
    }
    
    
    public void setPaneVisible(){
    
        periodPane.setVisible(true);
    }
    
    public void detailedReportForPeriod(){
    
      try{
       
        table_detailedReport.getItems().clear();
        LocalDate start = datePickerStart.getValue();
        LocalDate end = datePickerEnd.getValue();
        
//خد بالك جملة دي بترجع اليوم مقسوم لتلت صفوف كل صف بيعبر عن حاجة الصفين الولولين بيعبروا عن الارباح والتالت بيعبر عن المصروفات في يوم                
            String sqlForDailyDetailed 
                  = "SELECT dateOfTransaction , typeOfTransaction , sum(addMoney) from lifeofcustomer WHERE typeOfTransaction != 5 and (dateOfTransaction <=  '"+end+"' and dateOfTransaction >= '"+start+"') GROUP by dateOfTransaction , typeOfTransaction ORDER by dateOfTransaction, typeOfTransaction";
                 Object[] table = dataBase.retrievingTableInArrayOfObject(sqlForDailyDetailed);
                if(!(table.length == 0)){
                    String Date = "";
                    float receiptProfit = 0 ;
                    float gamesPfrofit  = 0 ;
                    float expenses      = 0 ;
                    float totalOfHourWork = 0;
                    int numOfRow = 1;
                for (int i = 0; i < table.length; i++) {
                   String [] row         = (String[]) table[i];
                    String dateOfDay = row[0];
                    
                    if (Integer.parseInt(row[1])== 1){
                    float totalOfReceipt = Float.parseFloat(row[2]);
                    receiptProfit = totalOfReceipt ;
                       
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 2){   
                        float totalOfGames =  Float.parseFloat(row[2]);
                        gamesPfrofit = totalOfGames ;
                        ++numOfRow;
                        
                        continue;
                    }
                     if (Integer.parseInt(row[1])== 3){   
                    float expensesOfDay = Float.parseFloat(row[2]);
                    expenses = expensesOfDay ;
                        
                        
                        
                    }
                     if (numOfRow % 3 == 0 || numOfRow%2 == 0 ||numOfRow %1 ==0){
                      float total = receiptProfit + gamesPfrofit ;
                      float ReminderProfit  = total - expenses ;
                       double numberOfHourwork = Math.floor(calcTheNumberOfHourWork(dateOfDay));
                       detiledReport newDay = new detiledReport(dateOfDay, gamesPfrofit, receiptProfit, expenses, (float)numberOfHourwork);
                      table_detailedReport.getItems().add(newDay);
                        System.out.println("true when i "+ i);
                        numOfRow = 1 ;
                    }
                    
                    
                    
                    
                    }
                 }
              
                
           }
        
        catch(SQLException ex){
            dataBase.showMessageJOP(ex.getMessage());
        
        }
    totalOfEachCOlumn();
    }
    
    public void totalOfEachCOlumn(){
                    
        float receiptProfit = 0 ;
        float gamesPfrofit  = 0 ;
        float expenses      = 0 ;
        float total         = 0 ;
        float finalProfit      = 0 ;
        float totalHourWork = 0;            
        ObservableList<detiledReport> items = table_detailedReport.getItems();
        for (int i = 0; i < items.size(); i++) {
            receiptProfit = receiptProfit + items.get(i).getReceiptProfit();
            gamesPfrofit  = gamesPfrofit  + items.get(i).getGamesProfit();
            expenses      = expenses      + items.get(i).getExpenses();
            totalHourWork = totalHourWork + items.get(i).getTotaLHourWork();
        }

        lbl_receipt.setText(receiptProfit+"");
        lbl_games.setText(""+gamesPfrofit);
        total         =  receiptProfit + gamesPfrofit ;
        lbl_total.setText(total+"");
        lbl_expenses.setText(expenses+"");
        finalProfit   =  total- expenses   ;
        lbl_profit.setText(""+finalProfit);
        lbl_toatalHourWork.setText(""+totalHourWork);
        
        
    }
    
     public float calcTheNumberOfHourWork(String date){
         
         String sqlOFLoginINSuchDate = "SELECT time_format(TIMEDIFF(B.actionTime , A.actionTime), '%H:%i ') AS timedifference FROM actions A CROSS JOIN actions B WHERE A.actionDate = '"+date+"' and B.id IN (SELECT MIN(C.id) FROM actions C WHERE C.id > A.id) ORDER BY A.id ASC ";
         System.out.println(sqlOFLoginINSuchDate);
         String[] timeColumn = dataBase.retrivingColumnFromDataBase(sqlOFLoginINSuchDate); 
         float totalOftime = 0;
         System.out.println("lenght is "+timeColumn.length);
         for (int i = 0; i < timeColumn.length; i++) {
             
             System.out.println(timeColumn[i]);
//             "14:33:05.000000"
             String []time =   timeColumn[i].split(":");
             System.out.println("time  "+time);
             
             float hours =   Float.parseFloat(time[0]);
             float mintues = Float.parseFloat(time[1]);
             totalOftime = totalOftime + hours+ (mintues)/60;
         }
         
             
         return totalOftime;
    
    }
    
    
    

}
    
    
    
    
    
