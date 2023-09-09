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
public class CafeSection {
    
    private int id;
    private String sectionName ; 

    public CafeSection(int id, String sectionName) {
        this.id = id;
        this.sectionName = sectionName;
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

    @Override
    public String toString() {
        return "CafeSection{" + "id=" + id + ", sectionName=" + sectionName + '}';
    }
    
}
