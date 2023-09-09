/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

public class supplier {
    
int supplier_id ;
String supplier_name ; 
String supplier_tel ; 
String supplier_address ; 

    public supplier(int supplier_id, String supplier_name, String supplier_tel, String supplier_address) {
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.supplier_tel = supplier_tel;
        this.supplier_address = supplier_address;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_tel() {
        return supplier_tel;
    }

    public void setSupplier_tel(String supplier_tel) {
        this.supplier_tel = supplier_tel;
    }

    public String getSupplier_address() {
        return supplier_address;
    }

    public void setSupplier_address(String supplier_address) {
        this.supplier_address = supplier_address;
    }


}
