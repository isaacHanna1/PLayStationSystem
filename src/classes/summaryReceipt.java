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
public class summaryReceipt {
 
    private int serialNumber ; 
    private int transactionNumber ; 
    private String startTime;
    private String endTime ;
    private String singleOrMulti;
    private float hourPrice;
    private float hourCount ;
    private float minutesCount; 
    private float devicePrice ;
    private float orderPrice ;
    private float total ;
    private float payed;
    private String room;
    private String playerName;

    public summaryReceipt(int serialNumber, int transactionNumber, String startTime, String endTime, String singleOrMulti, float hourPrice, float hourCount, float minutesCount, float devicePrice, float orderPrice, float total, float payed, String room, String playerName) {
        this.serialNumber = serialNumber;
        this.transactionNumber = transactionNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.singleOrMulti = singleOrMulti;
        this.hourPrice = hourPrice;
        this.hourCount = hourCount;
        this.minutesCount = minutesCount;
        this.devicePrice = devicePrice;
        this.orderPrice = orderPrice;
        this.total = total;
        this.payed = payed;
        this.room = room;
        this.playerName = playerName;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSingleOrMulti() {
        return singleOrMulti;
    }

    public void setSingleOrMulti(String singleOrMulti) {
        this.singleOrMulti = singleOrMulti;
    }

    public float getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(float hourPrice) {
        this.hourPrice = hourPrice;
    }

    public float getHourCount() {
        return hourCount;
    }

    public void setHourCount(float hourCount) {
        this.hourCount = hourCount;
    }

    public float getMinutesCount() {
        return minutesCount;
    }

    public void setMinutesCount(float minutesCount) {
        this.minutesCount = minutesCount;
    }

    public float getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(float devicePrice) {
        this.devicePrice = devicePrice;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getPayed() {
        return payed;
    }

    public void setPayed(float payed) {
        this.payed = payed;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
            
    
    
}
