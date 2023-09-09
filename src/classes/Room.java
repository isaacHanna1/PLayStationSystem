package classes;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import playstation.dataBase;

public class Room {
    
    private int id ; 
    private String roomNumber ; 
    private String roomName ; 
    private int isVip ;
    private float vipHourPrice ;
    private float vipPriceMulti ;
    private int numberOfDevices;
    public Room(String roomNumber, String roomName ,int isVip, float vipHourPrice, float vipPriceMulti) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.isVip = isVip;
        this.vipHourPrice = vipHourPrice;
        this.vipPriceMulti = vipPriceMulti;
    }

    public Room(String roomNumber, String roomName, int isVip, int numberOfDevices) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.isVip = isVip;
        this.numberOfDevices = numberOfDevices;
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public float getVipHourPrice() {
        return vipHourPrice;
    }

    public void setVipHourPrice(float vipHourPrice) {
        this.vipHourPrice = vipHourPrice;
    }

    public float getVipPriceMulti() {
        return vipPriceMulti;
    }

    public void setVipPriceMulti(float vipPriceMulti) {
        this.vipPriceMulti = vipPriceMulti;
    }

    public int getNumberOfDevices() {
        try {
            String roomID =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT Id FROM rooms WHERE roomName = '"+getRoomName()+"'");
            String numberOfDevice =  dataBase.gettingOnvalueFromTableByAggregatefunction("SELECT COUNT(id) from newdevice WHERE room_id = '"+roomID+"'");   
            return Integer.parseInt(numberOfDevice);
        } catch (SQLException ex) {
           dataBase.showMessageJOP(ex.getMessage());
           return 0;
        }
    }

    public void setNumberOfDevices(int numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }
    
    
    
    
}
