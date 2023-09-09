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
public class cafeItem {
    
    int itemid ;
    long itemCode ;
    String itemName ; 
    int itemSection ;
    String sectionName ;
    int sectionId;
    float itembuy;
    float itemSell;
    int notifaction ;
    
    public cafeItem(long itemCode, String itemName, int itemSection, String sectionName, float itembuy, float itemSell) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemSection = itemSection;
        this.sectionName = sectionName;
        this.itembuy = itembuy;
        this.itemSell = itemSell;
    }

    public cafeItem(int itemid, long itemCode, String itemName, String sectionName, float itembuy, float itemSell, int notifaction) {
        this.itemid = itemid;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.sectionName = sectionName;
        this.itembuy = itembuy;
        this.itemSell = itemSell;
        this.notifaction = notifaction;
    }


    public cafeItem(String sectionName) {
        this.sectionName = sectionName;
    }
 public cafeItem(int sectionId , String sectionName) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
    }

    public long getItemCode() {
        return itemCode;
    }

    public void setItemCode(long itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemSection() {
        return itemSection;
    }

    public void setItemSection(int itemSection) {
        this.itemSection = itemSection;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public float getItembuy() {
        return itembuy;
    }

    public void setItembuy(float itembuy) {
        this.itembuy = itembuy;
    }

    public float getItemSell() {
        return itemSell;
    }

    public void setItemSell(float itemSell) {
        this.itemSell = itemSell;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getNotifaction() {
        return notifaction;
    }

    public void setNotifaction(int notifaction) {
        this.notifaction = notifaction;
    }
    
}
