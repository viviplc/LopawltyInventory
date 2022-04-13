package com.example.lopawltyinventory;

//Class to model the product entity
public class Product {

    //Product fields definitions, specified with private accessor, getter and setter methods will be
    // used to access and set these
    private int id;
    private String name;
    private int quantityInStock;
    private double price;
    private String category;
    private String description;
    private String address;
    private String postalCode;

    //empty args constructor
    public Product(){

    }

    //constructor with parameters to set the Product fields
    public Product(int id, String name, int quantityInStock, double price, String category, String description, String address, String postalCode) {
        this.id = id;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.category = category;
        this.description = description;
        this.address = address;
        this.postalCode = postalCode;
    }

    //public getter function for id field
    public int getId() {
        return id;
    }

    //public setter function for id field
    public void setId(int id) {
        this.id = id;
    }

    //public getter function for name field
    public String getName() {
        return name;
    }

    //public setter function for name field
    public void setName(String name) {
        this.name = name;
    }

    //public getter function for quantityInStock field
    public int getQuantityInStock() {
        return quantityInStock;
    }

    //public setter function for quantityInStock field
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    //public getter function for price field
    public double getPrice() {
        return price;
    }

    //public setter function for price field
    public void setPrice(double price) {
        this.price = price;
    }

    //public getter function for category field
    public String getCategory() {
        return category;
    }

    //public setter function for category field
    public void setCategory(String category) {
        this.category = category;
    }

    //public getter function for description field
    public String getDescription() {
        return description;
    }

    //public setter function for description field
    public void setDescription(String description) {
        this.description = description;
    }

    //public getter function for address field
    public String getAddress() {
        return address;
    }

    //public setter function for address field
    public void setAddress(String address) {
        this.address = address;
    }

    //public getter function for postalCode field
    public String getPostalCode() {
        return postalCode;
    }

    //public setter function for postalCode field
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
