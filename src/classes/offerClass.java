/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;


public class offerClass {
    
    private int ID ;
    private int sectionId;
    private float mainUnit ; 
    private float aadUnit ;
    private String startDate ;
    private String endDate ;
    private String offerName ; 

    public offerClass(int ID, int sectionId, float mainUnit, float aadUnit, String startDate, String endDate, String offerName) {
     
        this.ID = ID;
        this.sectionId = sectionId;
        this.mainUnit = mainUnit;
        this.aadUnit = aadUnit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offerName = offerName;
    }
    public offerClass(int id ,String offerName , float mainUnit ,float addUnit ){
    
    this.ID = id ; 
    this.offerName= offerName;
    this.mainUnit = mainUnit;
    this.aadUnit =  addUnit ;
    }
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public float getMainUnit() {
        return mainUnit;
    }

    public void setMainUnit(float mainUnit) {
        this.mainUnit = mainUnit;
    }

    public float getAadUnit() {
        return aadUnit;
    }

    public void setAadUnit(float aadUnit) {
        this.aadUnit = aadUnit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    
    
    
    
}
