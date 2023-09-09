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
public class detiledReport {
    
    String period ; 
    float gamesProfit ;
    float receiptProfit ; 
    float totalProfit ; 
    float expenses;
    float reminderProfit ;
    float totaLHourWork ;
    
    public detiledReport(String period, float gamesProfit, float receiptProfit, float expenses) {
        this.period = period;
        this.gamesProfit = gamesProfit;
        this.receiptProfit = receiptProfit;
        this.totalProfit = gamesProfit+receiptProfit;
        this.expenses = expenses;
        this.reminderProfit = totalProfit - expenses;
    }
   public detiledReport(String period, float gamesProfit, float receiptProfit, float expenses , float totalHourWork) {
        this.period = period;
        this.gamesProfit = gamesProfit;
        this.receiptProfit = receiptProfit;
        this.totalProfit = gamesProfit+receiptProfit;
        this.expenses = expenses;
        this.reminderProfit = totalProfit - expenses;
        this.totaLHourWork = totalHourWork;
    }
    public float getTotaLHourWork() {
        return totaLHourWork;
    }

    public void setTotaLHourWork(float totaLHourWork) {
        this.totaLHourWork = totaLHourWork;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getGamesProfit() {
        return gamesProfit;
    }

    public void setGamesProfit(float gamesProfit) {
        this.gamesProfit = gamesProfit;
    }

    public float getReceiptProfit() {
        return receiptProfit;
    }

    public void setReceiptProfit(float receiptProfit) {
        this.receiptProfit = receiptProfit;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }

    public float getExpenses() {
        return expenses;
    }

    public void setExpenses(float expenses) {
        this.expenses = expenses;
    }

    public float getReminderProfit() {
        return reminderProfit;
    }

    public void setReminderProfit(float reminderProfit) {
        this.reminderProfit = reminderProfit;
    }
    
    
    
}
