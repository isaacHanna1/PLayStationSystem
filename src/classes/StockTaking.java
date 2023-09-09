    package classes;

public class StockTaking {
    
    private long itemCode ;
    private String itemName ;
    private float itemEnterNumber ;
    private float itemOutNumber ;
    private float remaningNumber ;
    private float buyPrice ;
    private float total;
    private int sectionId ;

    public StockTaking(long itemCode, String itemName, float itemEnterNumber, float itemOutNumber , float buyPrice,int sectionId) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemEnterNumber = itemEnterNumber;
        this.itemOutNumber = itemOutNumber;
        remaningNumber = itemEnterNumber - itemOutNumber ;
        this.sectionId = sectionId;
        this.buyPrice = buyPrice;
        this.total = remaningNumber*buyPrice;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
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

    public float getItemEnterNumber() {
        return itemEnterNumber;
    }

    public void setItemEnterNumber(float itemEnterNumber) {
        this.itemEnterNumber = itemEnterNumber;
    }

    public float getItemOutNumber() {
        return itemOutNumber;
    }

    public void setItemOutNumber(float itemOutNumber) {
        this.itemOutNumber = itemOutNumber;
    }

    public float getRemaningNumber() {
        return remaningNumber;
    }

    public void setRemaningNumber(float remaningNumber) {
        this.remaningNumber = remaningNumber;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    
            
            
}
