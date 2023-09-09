/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playstation;

import classes.CafeSection;
import classes.User;
import classes.cafeItem;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class dataBase {
    
    
    private static String url = ""; // to contain the url for date base
    private static String database_name = "playstation";//to contain database name 
    private static Connection connection;  // the variable that is make connection betwen database and java

    private static void setUrl() {//to intialize the url of date base 

        url = "jdbc:mysql://localhost:3306/" + database_name
                + "?useUnicode=true&characterEncoding=UTF-8";

    }

    public void setDatebaseName(String databaseName) { // to set new database 

        database_name = databaseName;

    }
    
    public  static Connection getConnection (){
    
        return  connection ;
    
    }
    public static void setConection() {//to intializ my conection for date base usring url , username , paseword

        try {

             Class.forName("com.mysql.jdbc.Driver");
            setUrl();
            connection = DriverManager.getConnection(url, "root", "");

        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, ex.getMessage());

        }  catch (ClassNotFoundException ex) {
               Logger.getLogger(dataBase.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
       public static Connection setConectionReport() {//to intializ my conection for date base usring url , username , paseword

        try {

             Class.forName("com.mysql.jdbc.Driver");
            setUrl();
            connection = DriverManager.getConnection(url, "root", "");

        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, ex.getMessage());

        }  catch (ClassNotFoundException ex) {
               Logger.getLogger(dataBase.class.getName()).log(Level.SEVERE, null, ex);
           }
        return connection;
    }
    
    
    public static void excuteNonQuery(String sqlStatment) throws SQLException {

        setConection();

        Statement state;

        state = connection.createStatement();
        state.execute(sqlStatment);
        connection.close();

    }
    
  // function for show message box by new atttribute (big font size)
    public static void showMessageJOP(String message ){
    
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(" تحذير");
        alert.setHeaderText("تحذير");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    // function return the largest value in column to make auto number by add one to the largest value 
    public static String autoNumber(String tableName, String columnName) throws SQLException {

        setConection();

        Statement statement = connection.createStatement();
        String sqlStatement = "select max(" + columnName + ")+1 "
                + "as 'auto_number' from " + tableName;
        ResultSet resultSet = statement.executeQuery(sqlStatement);
        String autoNumber = "";
        while (resultSet.next()) {
            autoNumber = resultSet.getString(1);
        }
        connection.close();
        if (autoNumber != null) {
            return autoNumber;
        } else {
            return "1";
        }

    }
     public static String autoTranscation(String tableName, String columnName) throws SQLException {

        setConection();

        Statement statement = connection.createStatement();
        String sqlStatement = "select max(" + columnName + ")+1 "
                + "as 'auto_number' from " + tableName;
        ResultSet resultSet = statement.executeQuery(sqlStatement);
        String autoNumber = "";
        while (resultSet.next()) {
            autoNumber = resultSet.getString(1);
        }
        connection.close();
        if (autoNumber != null) {
            return autoNumber;
        } else {
            return "1000";
        }

    }
     
     public static String autoTranscationForLiveReceipt(String tableName, String columnName) throws SQLException {

        setConection();

        Statement statement = connection.createStatement();
        String sqlStatement = "select min(" + columnName + ")-1 "
                + "as 'auto_number' from " + tableName;
        ResultSet resultSet = statement.executeQuery(sqlStatement);
        String autoNumber = "";
        while (resultSet.next()) {
            autoNumber = resultSet.getString(1);
        }
        connection.close();
        if (autoNumber != null) {
            return autoNumber;
        } else {
            return "-1000";
        }

    }
     public static void putingColumnFromDataBaseIntoCombox(String sql , ComboBox <String>CombName) throws SQLException {

        setConection();

        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery(sql);

        String item = "";

        while (resultSet.next()) {

            item = resultSet.getString(1);
            CombName.getItems().add(item);

        }
        connection.close();

    }
     public static Object[] retrievingTableInArrayOfObject(String Sqlstatement) throws SQLException {

        setConection();// set connection to data base 

        Statement statement = connection.createStatement();  // create statment 
        ResultSet resultSet = statement.executeQuery(Sqlstatement); // result set point to result table 

        java.sql.ResultSetMetaData rsmd = resultSet.getMetaData(); // meta data have information about result table 

        // getting the each row of table in array 
        //ResultSet resultSet2 = statement.executeQuery("select count(*) from "+tableName); // to get the number of row in column
        resultSet.last();// set the pointer to the end of table to get the number of row by getRow method 

        Object[] ArrayOfEachRow = new Object[resultSet.getRow()]; // create array of object to hold each row in table as array
        resultSet.beforeFirst();
        int indexNumberForArrayOfEachRow = 0;

        while (resultSet.next()) { // have another row ?
            String[] row = new String[rsmd.getColumnCount()];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                row[i] = resultSet.getString((i + 1));
                
            }

            ArrayOfEachRow[indexNumberForArrayOfEachRow] = row.clone();

            indexNumberForArrayOfEachRow++;
            
        }
        

        connection.close();
        
        return ArrayOfEachRow;

    }
     public static String[] retrievingRowIntableInArray(String Sqlstatement) throws SQLException{
          
          setConection();
             
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Sqlstatement);
            java.sql.ResultSetMetaData rsmd =  resultSet.getMetaData();
            
            
            String [] row = new String[rsmd.getColumnCount()];
            
          resultSet.first();
                    for (int i = 0 ; i < rsmd.getColumnCount(); i++) {
                        
                        row [i] = resultSet.getString((i+1));
                  }
            connection.close();
            return  row ;

            
            
            
        
           
      
      }
     
      public static String [] gettingColumnFromDatabaseIntoArray(String table,String columnName , String orderedBy){
      
          
            
        setConection();
        String [] column = {};
           try {
               
               PreparedStatement ps = connection.prepareStatement("select "+columnName+" FROM " + table +" ORDER by "+orderedBy);
               ResultSet resultSet = ps.executeQuery();
               resultSet.last();
                column = new String [resultSet.getRow()];
               resultSet.beforeFirst();
               int count = 0 ;
               while (resultSet.next()) {                   
                   column[count] = resultSet.getString(1);
                   count++;
               }
               connection.close();
           } catch (SQLException ex) {
               showMessageJOP(ex.getMessage());
           }
                   return  column;

          
          
      }
      
      public static String [] retrivingColumnFromDataBase(String sql){
      
          
            
        setConection();
        String [] column = {};
           try {
               
               PreparedStatement ps = connection.prepareStatement(sql);;
               ResultSet resultSet = ps.executeQuery();
               resultSet.last();
                column = new String [resultSet.getRow()];
               resultSet.beforeFirst();
               int count = 0 ;
               while (resultSet.next()) {                   
                   column[count] = resultSet.getString(1);
                   count++;
               }
               connection.close();
           } catch (SQLException ex) {
               showMessageJOP(ex.getMessage());
           }
           
           
                   return  column;

          
          
      }
      public static String [] gettingColumnFromDatabaseIntoArray(String table,String columnName ,String join,String where){
      
          
            
        setConection();
        String [] column = {};
           try {
               
               PreparedStatement ps = connection.prepareStatement("select  "+columnName+" FROM " + table +" JOIN "+join+"  where "+where);
               ResultSet resultSet = ps.executeQuery();
               resultSet.last();
                column = new String [resultSet.getRow()];
               resultSet.beforeFirst();
               int count = 0 ;
               while (resultSet.next()) {                   
                   column[count] = resultSet.getString(1);
                   count++;
               }
               connection.close();
           } catch (SQLException ex) {
               showMessageJOP(ex.getMessage());
           }
                   return  column;

          
          
      }
  public static String gettingOnvalueFromTableByAggregatefunction(String sql) throws SQLException{
    
              setConection();

        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery(sql);
        String value = "";
        while (resultSet.next()) {
            value = resultSet.getString(1);
        }
        connection.close();
        if (value != null) {
            return value;
        } else {
            return "0";
        }

    }
   

    public static User checkUserNameAndPassword(String userName , String password ) throws SQLException{
        

        
            Object [] allUsers =  retrievingTableInArrayOfObject("SELECT * FROM `users` ");
            
            for (int i = 0; i < allUsers.length; i++) {
                String [] newUser = (String[]) allUsers[i];
             
                if(newUser[1].equals(userName)&& newUser[2].equals(password)){
                
                    return  new User(Integer.parseInt(newUser[0]), newUser[1], newUser[2], Integer.parseInt(newUser[3])) ;
                    }
                
            }
        
     return new User("not found ");
    }
    
    //insert action happened in system 
    
    public static void inserActions(String details){
          setConection();
        try {
            PreparedStatement stmt=connection.prepareStatement("insert into actions values(?,?,?,?)");
            int ID = Integer.parseInt(autoNumber("actions","id"));
            
            stmt.setInt(1, ID);
            
                Date today = Calendar.getInstance().getTime();  
                DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");  
                String strDate = dateFormat.format(today).substring(0,10);  
                
                stmt.setString(2,strDate);
                String strTime = dateFormat.format(today).substring(11,19);  
                stmt.setString(3,strTime);
                stmt.setString(4, details);
                
                System.out.println(strDate + "  " +strTime);
                stmt.executeUpdate();
          connection.close();
        }catch(SQLException ex){
        
            System.out.println(ex.getMessage());
        }

    
    }
 
    
    // INSERT data into users table 
    
    public static int insertIntoUserTable(String userName , String Password , int permission){
    
        setConection();
        try {
            PreparedStatement stmt=connection.prepareStatement("insert into users values(?,?,?,?)");
          int ID = Integer.parseInt(autoNumber("users","id"));

            stmt.setInt(1, ID);
            stmt.setString(2, userName);
            stmt.setString(3, Password);
            stmt.setInt(4, permission);
            stmt.executeUpdate();
          connection.close();
                return 1 ;
        } catch (SQLException ex) {
            
            return  0 ;
        }
    
    }
    
    public static String insertMainData(String companyName , String address , String tel , String welcomMess ,String tail , String path , String face , String insta , int userId ){
    
        try {
            setConection();
            PreparedStatement stmt=connection.prepareStatement("UPDATE `companydata` SET `company_name`= (?),`address`=(?),`tel`=(?),`welcomeMessage`=(?),`tailMessage`=(?),`logoPicturePAth`=(?),`facebookLink`=(?),`instgramLink`=(?),`user_id`=(?)  WHERE id = 1");
            stmt.setString(1, companyName);
            stmt.setString(2, address);
            stmt.setString(3, tel);
            stmt.setString(4, welcomMess);
            stmt.setString(5, tail);
            stmt.setString(6, path);
            stmt.setString(7, face);
            stmt.setString(8, insta);
            stmt.setInt(9, userId);
            
            stmt.executeUpdate();
            connection.close();
            return "تمت تعديل بنجاح";
        } catch (SQLException ex) {
            return "حدث خطأ عن الاتصال والاضافة "+ex.getMessage();
        }
    
    }
  
    public static String insertDataOfRoom (  String roomNumber,String roomName , int isVIp , float vipPriceSingle , float vipPriceMulti){
        
        setConection();
        try {
            PreparedStatement stmt=connection.prepareStatement("INSERT INTO `rooms` values (?,?,?,?,?,?)");
            int ID = Integer.parseInt(autoNumber("rooms","id"));

            stmt.setInt(1, ID);
            stmt.setString(2, roomNumber);
            stmt.setString(3, roomName);
            stmt.setInt(4, isVIp);
            stmt.setFloat(5, vipPriceSingle);
            stmt.setFloat(6, vipPriceMulti);
            stmt.executeUpdate();
            connection.close();
            return "تمت الاضافة بنجاح";
        } catch (SQLException ex) {
            return "حدثت مشكلة عند الاضافة "+ ex.getMessage();
        }
    
    }
    
  public static String insertNewEquimpent(int code , String equimpentName){
        setConection();
        try {
            PreparedStatement stmt=connection.prepareStatement("INSERT INTO equimpent values(?,?,?)");
            int ID = Integer.parseInt(autoNumber("equimpent","id"));
            stmt.setInt(1, ID);
            stmt.setInt(2, code);
            stmt.setString(3,equimpentName );
            stmt.executeUpdate();
                        connection.close();

            return "تمت الاضافة بنجاح";
        } catch (SQLException ex) {
            return "حدثت مشكلة عند الاضافة "+ex.getMessage();
        }

  
  }
  public static String insertNewSection(String sectionName , float hourPrice , float multiHourPrice,float gamePrice ,String imagePath){
        try {
            setConection();
            PreparedStatement stmt=connection.prepareStatement("INSERT INTO section values(?,?,?,?,?,?)");
            int ID = Integer.parseInt(autoNumber("section","id"));
            stmt.setInt(1, ID);          
            stmt.setString(2,sectionName );
            stmt.setFloat(3, hourPrice);
            stmt.setFloat(4, multiHourPrice);
            stmt.setFloat(5, gamePrice);          
            stmt.setString(6, imagePath);          
            
            stmt.executeUpdate();
            connection.close();
                    return "تمت الاضافة بنجاح";
                    
        } catch (SQLException ex) {
            return "حدثت مشكلة عند الاضافة "+ex.getMessage();
        }

      }
  
  public static String insertNewOffer(int sectionID , float mainuUnit , float addUnit , String startDate , String endDate , String offerName){
  
      
        try {
            setConection();
            PreparedStatement stmt=connection.prepareStatement("INSERT INTO offer values(?,?,?,?,?,?,?)");
            int ID = Integer.parseInt(autoNumber("offer","id"));
            stmt.setInt(1, ID);     
            stmt.setInt(2, sectionID);
            stmt.setFloat(3, mainuUnit);
            stmt.setFloat(4, addUnit);
            stmt.setString(5, startDate);
            stmt.setString(6, endDate);
            stmt.setString(7, offerName);
            connection.close();
              stmt.executeUpdate();
                    return "تمت الاضافة بنجاح";
        } catch (SQLException ex) {
            return "حدثت مشكلة عند الاضافة "+ex.getMessage();
        }

      }
  public static String insertNewDevice(int sectionID , String deviceNumber , String deviceName , int roomID , float devicePrice , String deviceDateBuy , String note  ){
    
        try {
            setConection();
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO newdevice values(?,?,?,?,?,?,?,?)");
            PreparedStatement stmt = prepareStatement;
            int ID = Integer.parseInt(autoNumber("newdevice","id"));
            stmt.setInt(1, ID); 
            stmt.setInt(2, sectionID);
            stmt.setString(3,deviceNumber);
            stmt.setString(4, deviceName);
            stmt.setInt(5, roomID);
            stmt.setFloat(6, devicePrice);
            stmt.setString(7, deviceDateBuy);
            stmt.setString(8, note);
            connection.close();
            stmt.executeUpdate();
            
            
                    return "تمت الاضافة بنجاح";
        } catch (SQLException ex) {
            return "حدثت مشكلة عند الاضافة "+ex.getMessage();
        }

      
  }
  
  public static String addNewPlayer (String playerName ,String  tel , float points){
      
        try {
            setConection();
            PreparedStatement stmt=connection.prepareStatement("INSERT INTO players values(?,?,?,?)");
            int ID = Integer.parseInt(autoNumber("players","id"));
            stmt.setInt(1, ID);
            stmt.setString(2, playerName);
            stmt.setString(3, tel);
            stmt.setFloat(4, points);
            stmt.executeUpdate();
            connection.close();
                    return "تمت الاضافة بنجاح";
        } catch (SQLException ex) {
            return "حدثت مشكلة عند الاضافة "+ex.getMessage();
        }
            
  
  }
  
  public static String addNewSafe(String safeName){
  
      setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO safe values(?,?)");
            int ID = Integer.parseInt(autoNumber("safe","id"));
            stmt.setInt(1, ID);
            stmt.setString(2, safeName);
                  stmt.executeUpdate();
                  connection.close();
                    return "تمت الاضافة بنجاح";
        } catch (SQLException ex) {
            return "حدثت مشكلة عند الاضافة "+ex.getMessage();
        }
            
  
  }
  //هناجر جهاز جديد
  public static String addnewfollowingDeivce(int transationNumer,String startTime , String endTime , int timeType,int device_id , int isOffer , int offer_id ,int player_id , int user_id , Date dateToday){
    setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO followingdevice values(?,?,?,?,?,?,?,?,?,?,?,?)");
    int ID = Integer.parseInt(autoNumber("followingdevice","id"));
      stmt.setInt(1, ID);
      stmt.setInt(2, transationNumer);
      stmt.setString(3, startTime);
      stmt.setString(4, endTime);
      stmt.setInt(5, timeType);
      stmt.setInt(6, device_id);
      stmt.setInt(7, isOffer);
      stmt.setInt(8, offer_id);
      stmt.setInt(9, player_id);
      stmt.setInt(10, user_id);
      stmt.setInt(11, 1);
      stmt.setDate(12, (java.sql.Date) dateToday);
      //String sql = "INSERT INTO followingdevice values("+ID+","+transationNumer+",'"+startTime+"','"+endTime+"',"+timeType+","+device_id+","+isOffer+","+offer_id+","+player_id+","+user_id+","+1+",'"+dateToday+"')";
        //  System.out.println(sql);
          //excuteNonQuery(sql);
          stmt.execute();
       
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
                      showMessageJOP(ex.getMessage());

            return ex.getMessage();
      }
       
      
  }
  
  public static String insertNewCafeSection(String sectionName){
      
      setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO cafesection values(?,?)");
      int ID = Integer.parseInt(autoNumber("cafesection","id"));
      stmt.setInt(1, ID);
      stmt.setString(2, sectionName);
      stmt.executeUpdate();
      connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
          if(ex.getMessage().contains("Duplicate")){
            return "هناك قسم بهذا الاسم ";
          }else{
              return ex.getMessage();
          }
        }
  
  }
  
  public static String insertNewRepository(String repositoryName){
  
      setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO repository values(?,?)");
            int ID = Integer.parseInt(autoNumber("repository","id"));
      stmt.setInt(1, ID);
      stmt.setString(2, repositoryName);
      stmt.executeUpdate();
      connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
            return ex.getMessage();
        }
  }
  public static String insertNewCafeItem(long itemCode , int section_id, String itemName , float itemBuy , float itemPrice , int notificationNumber){
  
  setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO cafeItem values(?,?,?,?,?,?,?)");
      int ID = Integer.parseInt(autoNumber("cafeItem","id"));
      stmt.setInt(1, ID);
      stmt.setLong(2, itemCode);
      stmt.setInt(3, section_id);
      stmt.setString(4, itemName);
      stmt.setFloat(5, itemBuy);
      stmt.setFloat(6, itemPrice);
      stmt.setInt(7, notificationNumber);
      stmt.executeUpdate();

        connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
            return ex.getMessage();
        }
  
  }
  
  
    public static String saveMainBuyCafeReceipt( int transactionNumber, int receiptNumber , int user_id , java.sql.Date date,int supplierId){
    setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO cafebuymaininfo values(?,?,?,?,?,?)");
      int ID = Integer.parseInt(autoNumber("cafebuymaininfo","id"));
      stmt.setInt(1, ID);
      stmt.setInt(2,transactionNumber );
      stmt.setInt(3,   receiptNumber);
      stmt.setInt(4, user_id);
      stmt.setDate(5, date);
      stmt.setInt(6, supplierId);
      
      stmt.executeUpdate();
        connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
            return ex.getMessage();
        }
  
  }
  
  
  public static String saveDetailedDataOFBuyReceipt(int receiptNumber , long itemCode , float quantit , float buyPrice , float payprice , int repositoryID ){
        
    setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO cafebuydetails values(?,?,?,?,?,?,?)");
      int ID = Integer.parseInt(autoNumber("cafebuydetails","id"));
      stmt.setInt(1, ID);
      stmt.setInt(2, receiptNumber);
      stmt.setLong(3, itemCode);
      stmt.setFloat(4,quantit);
      stmt.setFloat(5, buyPrice);
      stmt.setFloat(6, payprice);
      stmt.setInt(7, repositoryID);
       stmt.executeUpdate();

        connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
            return ex.getMessage();
        }
}
  
    public static String saveMainCustomerCafeReceipt(int receiptNumber ,int transactionNumber,  int user_id , java.sql.Date date , int player_id){
    setConection();
        try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO customerReceiptMAinInfo values(?,?,?,?,?,?)");
      int ID = Integer.parseInt(autoNumber("customerReceiptMAinInfo","id"));
      stmt.setInt(1, ID);
      stmt.setInt(2, receiptNumber);
      stmt.setInt(3, transactionNumber);
      stmt.setInt(4, user_id);
      stmt.setDate(5, date);
      stmt.setInt(6, player_id);
      stmt.executeUpdate();
        connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
            return ex.getMessage();
        }
  
  }
  
  
  public static String saveDetailedDataOFCustomerReceipt(int receiptNumber , long itemCode , float quantit , float buyPrice , int repositoryID ){
        
    setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO customerreceiptdetailedinfo values(?,?,?,?,?,?)");
      int ID = Integer.parseInt(autoNumber("customerreceiptdetailedinfo","id"));
      stmt.setInt(1, ID);
      stmt.setInt(2, receiptNumber);
      stmt.setLong(3, itemCode);
      stmt.setFloat(4,quantit);
      stmt.setFloat(5, buyPrice);
      stmt.setInt(6, repositoryID);
       stmt.executeUpdate();

        connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
            return ex.getMessage();
        }
}
  
  public static void deleteItemFromBill( int id  ,int billId  ){
   setConection();
           try {
   String sql = "delete FROM `customerreceiptdetailedinfo` WHERE id = ? and receiptNumber = ?";
   System.out.println(sql);
   PreparedStatement pst= connection.prepareStatement(sql);
   pst.setLong(1, id);
   pst.setInt(2, billId);
   pst.execute();
        } catch (SQLException ex) {
               showMessageJOP(ex.getMessage());
        }
   
  }
  public static void deleteBill(int billId ){
   setConection();
           try {
   String sql = "delete FROM `customerreceiptmaininfo` where  receiptNumber = ? ;";
   System.out.println(sql);
   PreparedStatement pst= connection.prepareStatement(sql);
   pst.setInt(1, billId);
   pst.execute();
        } catch (SQLException ex) {
               showMessageJOP(ex.getMessage());
        }
   
  }
  
  public static String enterAndOutMoney(int transactionNumber , float deviceMoneyOrCafeMoney , int safty_id , int userId ,java.sql.Date date, int account_id , String details , int id_lifeCustomerId){
      
      setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("insert into enteroutmoney VALUES (?,?,?,?,?,?,?,?,?)");
      int ID = Integer.parseInt(autoNumber("enteroutmoney","id"));
      stmt.setInt(1, ID);
      stmt.setInt(2, transactionNumber);
      stmt.setFloat(3, deviceMoneyOrCafeMoney);
      stmt.setInt(4, safty_id);
      stmt.setInt(5, userId);
      stmt.setDate(6, date);
      stmt.setInt(7, account_id);
      stmt.setString(8, details);
      stmt.setInt(9, id_lifeCustomerId);
      stmt.executeUpdate(); 
      
     connection.close();
       return "تمت الاضافة بنجاح";
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
            return ex.getMessage();
        }
      
  }
  
  public  static int customerLife (int transcactionNumber , int type_of_transaction , float addMOney , float minsMoney , String details ,int  customer_account_ID , java.sql.Date date){
  
  setConection();
  int ID = 0;
      try{
      PreparedStatement stmt = connection.prepareStatement("insert into lifeofcustomer VALUES (?,?,?,?,?,?,?,?)");
      ID = Integer.parseInt(autoNumber("lifeofcustomer","id"));
      stmt.setInt(1, ID);
      stmt.setInt(2, transcactionNumber);
      
      
      //type of transction 
      //1   ==>  فاتورة بيع  من مخزن
      //2   ==>     فاتورة ايجار من بلايستيشن 
      //3   ==>      سداد فلوس 
      //4   ==>                فاتورة مرتجع
      //  5  ==> فاتورة شراء 
      
      stmt.setInt(3, type_of_transaction);
      stmt.setFloat(4, addMOney);
      stmt.setFloat(5, minsMoney);
      stmt.setString(6, details);
      stmt.setInt(7, customer_account_ID);
      stmt.setDate(8, date);
      stmt.executeUpdate(); 
      
     connection.close();
       
      } catch (SQLException ex) {
            
          showMessageJOP(ex.getMessage() );
        }
      //بص يا سيحا انا عامل انه يرجع 
      //id 
      //علشان لما يسجل انه في جدول دورة تفاصيل الفلوس يعني خد مننين وحط فيم
      //لما يدخل انه حط فلوس وسدد يرجع 
      //id
      // and use this id in enterAndOutMoney table
  return ID;
  }
  
  
  //enter father account 
  public static String addFatherAccout (String fatherAccountName){
  
  setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO father values(?,?)");
      int ID = Integer.parseInt(autoNumber("father","ID"));
      stmt.setInt(1, ID);
      stmt.setString(2, fatherAccountName);
      stmt.execute();
      
      }
      catch(SQLException ex){
      return ex.getMessage();
      }
      return "تمت الاضافة ";
  }
  
    public static String addSonAccout (int fatherAccountId , String sonAccountName){
  
  setConection();
      try{
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO son values(?,?,?)");
      int ID = Integer.parseInt(autoNumber("son","Id"));
      stmt.setInt(1, ID);
      stmt.setInt(2, fatherAccountId);
      stmt.setString(3, sonAccountName);
      stmt.execute();
      
      }
      catch(SQLException ex){
      return ex.getMessage();
      }
      return "تمت الاضافة ";
  }
    
    //اضافة البيانات في الجدول بتاع الفاتورة 
    
    public static void insertDataToReciptTable (int transactionNumber , String shopName , String playerName , String casherName , float hours , float minuts , String SectionName , float toatalOfDEvice , float totalOfStock , float paied , String startTime , String endTime , String mult , float hourPrice,String roomName, int deviceID, int period){
        
        setConection();
        try{
        PreparedStatement stm = connection.prepareStatement("INSERT INTO `receiptInformation` values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        int ID = Integer.parseInt(autoNumber("receiptInformation","id"));
        stm.setInt(1, ID);
        
        stm.setInt(2, transactionNumber);
        stm.setString(3, shopName);
        stm.setString(4,playerName);
        stm.setString(5, casherName);
        stm.setFloat(6, hours);
        stm.setFloat(7, minuts);
        stm.setString(8, SectionName);
        stm.setFloat(9, toatalOfDEvice);
        stm.setFloat(10, totalOfStock);
        stm.setFloat(11, paied);
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String todayStr = sdf.format(today);
        java.util.Date dateStr = sdf.parse(todayStr);
        java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
        stm.setDate(12, (java.sql.Date) dateDB);
        stm.setString(13, startTime);
        stm.setString(14, endTime);
        stm.setString(15, mult);
        stm.setFloat(16, hourPrice);
        stm.setString(17, roomName);
        stm.setInt(18, deviceID);
        stm.setInt(19, period);
        stm.execute();
        }
        catch(SQLException ex ){
            showMessageJOP(ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(dataBase.class.getName()).log(Level.SEVERE, null, ex);
            showMessageJOP(ex.getMessage());
        }
    
    }
    
    public static boolean insertsupplierdata(String name , String address , String tel ){
        
        setConection();
        try{
        PreparedStatement stmt = connection.prepareStatement("insert into supplier VALUES (?,?,?,?)");
        int ID = Integer.parseInt(autoNumber("supplier","id"));
        stmt.setInt(1, ID);
        stmt.setString(2,name);
        stmt.setString(3,address);
        stmt.setString(4,tel);
        boolean result = stmt.execute();
        return true;
        
        
        
        
        
        
        }
        catch(SQLException ex){
            
                return false;
            
        }
    
    
    }
    
    public static boolean insertNewTable(String number){
    setConection();
        try{
        PreparedStatement stmt = connection.prepareStatement("insert into tables VALUES (?,?,?,?)");
        int ID = Integer.parseInt(autoNumber("tables","id"));
        stmt.setInt(1, ID);
        stmt.setString(2,number);
        stmt.setInt(3,0);
        stmt.setInt(4,47);
        boolean result = stmt.execute();
        return  true;
        }
        catch(SQLException ex){
            
        Alert alert = new  Alert(Alert.AlertType.WARNING);

        alert.setTitle(" تحذير");
        alert.setHeaderText("تحذير");
        alert.setContentText("خطأ في رقم الطاولة");
            System.out.println(ex.getMessage());
        alert.showAndWait();
            return  false;

        }
    

}
  // add new features 15/08/2023
  // cafe section 
    public static List<CafeSection> gettingAllSection(){
    
        List<CafeSection> allSection = new ArrayList<>();
        try{
            setConection();
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM `cafesection` ");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = Integer.parseInt(rs.getString("id"));
                String section_name = rs.getString("cafeSectionName");
                CafeSection cafeSection = new CafeSection(id, section_name);
                allSection.add(cafeSection);
            }
        }catch(SQLException ex){
            showMessageJOP(ex.getMessage());
        }
        return allSection;
    } 
    
    
    public static List<cafeItem> gettingallItemsInSuchCafeSection(int sectionId ){
    List<cafeItem> allItemInSection = new ArrayList<>();
        try{
            setConection();
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM `cafeitem` join cafesection WHERE secction_id =  ? and (cafeitem.secction_id = cafesection.id) ");
            pst.setInt(1, sectionId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int itemId = rs.getInt(1);
                long itemCode = rs.getLong(2);
                String itemName = rs.getString(4);
                float itemBuy = rs.getFloat(5);
                float itemPrice = rs.getFloat(6);
                float notificationNumber = rs.getFloat(7);
                String sectionName = rs.getString(9);
                cafeItem item = new cafeItem(itemId, itemCode, itemName, sectionName, itemBuy, itemPrice, sectionId);
                allItemInSection.add(item);
            }

        }catch(SQLException ex){
            showMessageJOP(ex.getMessage());
        }
        return allItemInSection;
    }
  // end cafe session 
  
}
  
    

