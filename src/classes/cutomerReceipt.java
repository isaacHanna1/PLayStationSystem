/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Objects;

/**
 *
 * @author Seha
 */
public class cutomerReceipt {
    
        
    private int id ;
    private int receiptNumber ; 
    private long code ; 
    private String itemName ;
    private float quantity ; 
    private float buyPrice;
    private float payPrice ; 
    private float total ;
    private int repository_id ;
    private String savedInDataBase ;
        
    public cutomerReceipt(int id  ,long code, String itemName,float quantity, float payPrice, float total,int repsitory_id,String savedInDataBase) {
        this.id = id ;
        this.code = code;
        this.itemName = itemName;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.payPrice = payPrice;
        this.total = quantity * payPrice ;
        this.repository_id = repsitory_id ;
        this.savedInDataBase = savedInDataBase;
    }

    public cutomerReceipt(long code, String itemName,float payPrice ,float quantity) {
        this.code = code;
        this.itemName = itemName;
        this.payPrice = payPrice;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSavedInDataBase() {
        return savedInDataBase;
    }

    public void setSavedInDataBase(String savedInDataBase) {
        this.savedInDataBase = savedInDataBase;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.receiptNumber;
        hash = 89 * hash + (int) (this.code ^ (this.code >>> 32));
        hash = 89 * hash + Objects.hashCode(this.itemName);
        hash = 89 * hash + Float.floatToIntBits(this.quantity);
        hash = 89 * hash + Float.floatToIntBits(this.buyPrice);
        hash = 89 * hash + Float.floatToIntBits(this.payPrice);
        hash = 89 * hash + Float.floatToIntBits(this.total);
        hash = 89 * hash + this.repository_id;
        hash = 89 * hash + Objects.hashCode(this.savedInDataBase);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final cutomerReceipt other = (cutomerReceipt) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.receiptNumber != other.receiptNumber) {
            return false;
        }
        if (this.code != other.code) {
            return false;
        }
        if (Float.floatToIntBits(this.quantity) != Float.floatToIntBits(other.quantity)) {
            return false;
        }
        if (Float.floatToIntBits(this.buyPrice) != Float.floatToIntBits(other.buyPrice)) {
            return false;
        }
        if (Float.floatToIntBits(this.payPrice) != Float.floatToIntBits(other.payPrice)) {
            return false;
        }
        if (Float.floatToIntBits(this.total) != Float.floatToIntBits(other.total)) {
            return false;
        }
        if (this.repository_id != other.repository_id) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.savedInDataBase, other.savedInDataBase)) {
            return false;
        }
        return true;
    }

    
}
