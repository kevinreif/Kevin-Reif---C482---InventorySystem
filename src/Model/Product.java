/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author butte
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
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

    //Add parts to product
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    //Delete parts from product
    public boolean deleteAssociatePart(Part part) {
        return false;
    }

    //Part list getter
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    //Product validation
    public static String validation(String name, double price, int inv, int min, int max, String productError, ObservableList<Part> tempList) {

        double sumOfParts = 0;

        for (Part part : tempList) {
            sumOfParts += part.getPrice();
        }
        if (name.isEmpty()) {
            productError = ("Please give the product a valid name");
        } else if (price <= 0) {
            productError = ("Price must be greater than 0.");
        } else if (inv < 0) {
            productError = ("Inventory must be greater than 0.");
        } else if (min > max) {
            productError = ("Inventory minimum cannot be greater than the inventory maximum.");
        } else if (inv > max || inv < min) {
            productError = ("Inventory must be between the minimum and maximum values.");
        } else if (tempList.isEmpty()) {
            productError = ("Please select at least one part for the product.");
        } else if (price < sumOfParts) {
            productError = ("Price of the product cannot be less than the cost of the parts.");
        }
        return productError;
    }
}
