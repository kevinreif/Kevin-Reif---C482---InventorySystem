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
public class InhousePart extends Part{
    
    private int machineID;
    
    //Constructor
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    //Setter
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    //Getter
    public int getMachineID() {
        return machineID;
    }
    
}
