
package classes;

public class User {
    
   private int  ID ;
    private String UserName ; 
    private String password ; 
    private int permission ;

    public User(int ID, String UserName, String password, int permission) {
        this.ID = ID;
        this.UserName = UserName;
        this.password = password;
        this.permission = permission;
    }

    public User(String name ) {
        this.UserName = "not found";
    }
    public User(int id ,String name ) {
        this.ID = id ;
        this.UserName = name;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

      
    
   public int checkLogin(String userName , String password){
   
       if (this.UserName.equals(userName)&&this.password.equals(password)){
           return 1 ;
       }
       else{
           return 0 ;
       }
   
   }
 
    
    
}
