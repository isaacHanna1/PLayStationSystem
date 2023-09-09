/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Seha
 */
public class safe {
    
   public static int id ;
    private String safeName ;
    private String details ;
    private float money ; 
    private String date ;
    private int id_edit ;

    public safe(int id, String safeName) {
        this.id = id;
        this.safeName = safeName;
    }
    
    
    public safe(int edit_id, String date,String details , float money) {
        this.id_edit = edit_id;
        this.date = date ;
        this.details = details;
        this.money = money ;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_edit() {
        return id_edit;
    }

    public void setId_edit(int id_edit) {
        this.id_edit = id_edit;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        safe.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
    
    

    public String getSafeName() {
        return safeName;
    }

    public void setSafeName(String safeName) {
        this.safeName = safeName;
    }
    
    public void printInfo(){
        
        System.out.println("id "+id_edit +"   "+details+"  "+date+"   "+ money);
        
    
    }
    
}
