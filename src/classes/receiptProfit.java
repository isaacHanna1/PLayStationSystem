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
public class receiptProfit {


    String  receiptDate;
    String  receiptDetails;
    float receiptPrice ;
    float receiptCost;
    float receiptProft ;
    int receiptTransaction ;

    public receiptProfit(String receiptDate, String receiptDetails, float receiptPrice, float receiptCost, float receiptProft, int receiptTransaction) {
        this.receiptDate = receiptDate;
        this.receiptDetails = receiptDetails;
        this.receiptPrice = receiptPrice;
        this.receiptCost = receiptCost;
        this.receiptProft = receiptProft;
        this.receiptTransaction = receiptTransaction;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptDetails() {
        return receiptDetails;
    }

    public void setReceiptDetails(String receiptDetails) {
        this.receiptDetails = receiptDetails;
    }

    public float getReceiptPrice() {
        return receiptPrice;
    }

    public void setReceiptPrice(float receiptPrice) {
        this.receiptPrice = receiptPrice;
    }

    public float getReceiptCost() {
        return receiptCost;
    }

    public void setReceiptCost(float receiptCost) {
        this.receiptCost = receiptCost;
    }

    public float getReceiptProft() {
        return receiptProft;
    }

    public void setReceiptProft(float receiptProft) {
        this.receiptProft = receiptProft;
    }

    public int getReceiptTransaction() {
        return receiptTransaction;
    }

    public void setReceiptTransaction(int receiptTransaction) {
        this.receiptTransaction = receiptTransaction;
    }
    
    
}
