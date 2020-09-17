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
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    //Add part
    public static void addPart(Part part) {
        allParts.add(part);
    }

    //Add product
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    //Lookup Part by ID
    public static Part lookupPart(int partID) {
        Part searchedPart = null;
        for (Part part : getAllParts()) {
            if (part.getId() == partID) {
                searchedPart =  part;
                break;

            }
        }
        return searchedPart;

    }

    //Lookup Product by ID
    public static Product lookupProduct(int productID) {
        Product searchedProduct = null;
        for (Product product : getAllProducts()) {
            if (product.getId() == productID) {
                searchedProduct =  product;
                break;

            }
        }
        return searchedProduct;
    }
    
    //Lookup part by name
    public static ObservableList<Part> lookupPart(String name) {
        if (!getFilteredParts().isEmpty()) {
            getFilteredParts().clear();
        }
        for (Part part : getAllParts()) {
            if (part.getName().contains(name)) {
                getFilteredParts().add(part);
            }
        }
        if (getFilteredParts().isEmpty()) {
            getFilteredParts().add(lookupPart(Integer.valueOf(name)));
        }
        
        if (getFilteredParts().isEmpty()) {
            return getAllParts();
            
        }
        
        return getFilteredParts();
    }
    
    //Lookup product by name
    public static ObservableList<Product> lookupProduct(String name) {
        if (!getFilteredProducts().isEmpty()) {
            getFilteredProducts().clear();
        }
        for (Product product : getAllProducts()) {
            if (product.getName().contains(name)) {
                getFilteredProducts().add(product);
            }
        }
        if (getFilteredProducts().isEmpty()) {
            getFilteredProducts().add(lookupProduct(Integer.valueOf(name)));
        }
        
        if (getFilteredProducts().isEmpty()) {
            return getAllProducts();
            
        }
        
        return getFilteredProducts();    
    }
    //Update part
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    //Update product
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    
    //Delete Part
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Inventory.getAllParts()) {
            if (part == selectedPart) {
                return getAllParts().remove(part);
            }
        }
        return false;
    }
    
    //Delete product
    public static boolean deleteProduct(Product selectedProduct) {
    for (Product product : Inventory.getAllProducts()) {
            if (product == selectedProduct) {
                return getAllProducts().remove(product);
            }
        }
        return false;
    }
    
    //Get parts list
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    //get parts search result list
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }
    
    //get products list
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    //get products search result list
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }

}
