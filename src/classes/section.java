
package classes;


public class section {
    
    private int id ; 
    private String sectionName ;
    private float sectionHourPrice ;
    private float sectionMultiHourPrice ;
    private float sectionGamePrice ;
    private String imagePath ;
    public section(int id, String sectionName, float sectionHourPrice, float sectionMultiHourPrice, float sectionGamePrice , String imagePath) {
        this.id = id;
        this.sectionName = sectionName;
        this.sectionHourPrice = sectionHourPrice;
        this.sectionMultiHourPrice = sectionMultiHourPrice;
        this.sectionGamePrice = sectionGamePrice;
        this.imagePath =  imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public float getSectionHourPrice() {
        return sectionHourPrice;
    }

    public void setSectionHourPrice(float sectionHourPrice) {
        this.sectionHourPrice = sectionHourPrice;
    }

    public float getSectionMultiHourPrice() {
        return sectionMultiHourPrice;
    }

    public void setSectionMultiHourPrice(float sectionMultiHourPrice) {
        this.sectionMultiHourPrice = sectionMultiHourPrice;
    }

    public float getSectionGamePrice() {
        return sectionGamePrice;
    }

    public void setSectionGamePrice(float sectionGamePrice) {
        this.sectionGamePrice = sectionGamePrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    
}
