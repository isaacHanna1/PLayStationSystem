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
public class newDevice {

private int id ; 
private int section_id ; 
private String  deviceNumber ;
private String deviceName ; 
private int room_id ; 
private float devicePrice ; 
private String buyDate;
private String note ;

    public newDevice(int id, int section_id, String deviceNumber, String deviceName, int room_id, float devicePrice, String buyDate, String note) {
        this.id = id;
        this.section_id = section_id;
        this.deviceNumber = deviceNumber;
        this.deviceName = deviceName;
        this.room_id = room_id;
        this.devicePrice = devicePrice;
        this.buyDate = buyDate;
        this.note = note;
    }
    public newDevice(int id ,int section_id, String deviceNumber, String deviceName, int room_id ){
    
        this.id = id;
        this.section_id = section_id;
        this.deviceNumber = deviceNumber;
        this.deviceName = deviceName;
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public float getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(float devicePrice) {
        this.devicePrice = devicePrice;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



}
