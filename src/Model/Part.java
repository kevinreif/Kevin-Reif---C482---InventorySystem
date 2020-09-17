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
public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Constructor
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    //Part Validation
    public static String validation(String name, double price, int inv, int min, int max, String partError) {
        if (name.isEmpty()) {
            partError = ("Please give the part a valid name");
        } else if (price <= 0) {
            partError = ("Price must be greater than 0.");
        } else if (inv < 0) {
            partError = ("Inventory must be greater than 0.");
        } else if (min > max) {
            partError = ("Inventory minimum cannot be greater than the inventory maximum.");
        } else if (inv > max || inv < min) {
            partError = ("Inventory must be between the minimum and maximum values.");
        }
        return partError;
    }
}
