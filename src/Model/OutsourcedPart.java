/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author butte
 */
public class OutsourcedPart extends Part{
    
    private String companyName;
    
    //Cnstructor
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    //Getter
    public String getCompanyName() {
        return companyName;
    }

    //Setter
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
}
