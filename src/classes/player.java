
package classes;


public class player {
    
    private int playerID ; 
    private String playerName ; 
    private String telPhone ;
    private float points ;

    public player(int playerID, String playerName, String telPhone, float points) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.telPhone = telPhone;
        this.points = points;
    }

    public player(String playerName){
        this.playerName = playerName;
    }
    public int getPlayerID() {
        return playerID;
    }
    

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }
    
    
    
    
    
}
