package classes;

import java.sql.Date;

public class lifeCycleOfTransaction {
    
    int transactionNumber ; 
    int transactionType ;
    String transactionDate ;
    String transactionDetails ;
    float addMoney ;
    float minsMoney ;
    float finalPriceAfterTrancation ;

    public lifeCycleOfTransaction(int transactionNumber, int transactionType, String transactionDate, String transactionDetails, float addMOney,float minsMoney, float finalPriceAfterTrancation) {
        this.transactionNumber = transactionNumber;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionDetails = transactionDetails;
        this.addMoney = addMOney;
        this.minsMoney = minsMoney;
        this.finalPriceAfterTrancation = finalPriceAfterTrancation;
    }

    public lifeCycleOfTransaction(int transactionNumber, String transactionDate, String transactionDetails, float addMoney) {
        this.transactionNumber = transactionNumber;
        this.transactionDate = transactionDate;
        this.transactionDetails = transactionDetails;
        this.addMoney = addMoney;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public float getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(float addMoney) {
        this.addMoney = addMoney;
    }

    public float getMinsMoney() {
        return minsMoney;
    }

    public void setMinsMoney(float minsMoney) {
        this.minsMoney = minsMoney;
    }

    public float getFinalPriceAfterTrancation() {
        return finalPriceAfterTrancation;
    }

    public void setFinalPriceAfterTrancation(float finalPriceAfterTrancation) {
        this.finalPriceAfterTrancation = finalPriceAfterTrancation;
    }
    
    
   
    
}
