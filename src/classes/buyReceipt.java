/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Date;

/**
 *
 * @author Seha
 */
public class buyReceipt {
    
    private int id ;
    private int receiptNumber ; 
    private long code ; 
    private String itemName ;
    private float quantity ; 
    private float buyPrice;
    private float payPrice ; 
    private float total ;
    private int repository_id ;
    private int transactionNumber ;
    private String date ;
    private String details ;
    private float totalOfAllRecipt ;
    public buyReceipt( long code, String itemName,float quantity, float buyPrice, float payPrice, float total,int repsitory_id) {
            
        this.code = code;
        this.itemName = itemName;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.payPrice = payPrice;
        this.total = quantity * buyPrice ;
        this.repository_id = repsitory_id ;
    }

    public buyReceipt(int transactionNumber, String date, String details, float totalOfAllRecipt) {
        this.transactionNumber = transactionNumber;
        this.date = date;
        this.details = details;
        this.totalOfAllRecipt = totalOfAllRecipt;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getTotalOfAllRecipt() {
        return totalOfAllRecipt;
    }

    public void setTotalOfAllRecipt(float totalOfAllRecipt) {
        this.totalOfAllRecipt = totalOfAllRecipt;
    }
    

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public long getCode() {
        return code;
    }
    public void setCode(long code) {
        this.code = code;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(float payPrice) {
        this.payPrice = payPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getRepository_id() {
        return repository_id;
    }

    public void setRepository_id(int repository_id) {
        this.repository_id = repository_id;
    }
    
    
}
