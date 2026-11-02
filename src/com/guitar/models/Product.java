package com.guitar.models;

public class Product {
    private int productID;
    private String productName;
    private String productKind;
    private String productFinish;
    private Supply supplier;
    private int quantity;

    public Product(int productID, String productName, String productKind, String productFinish, Supply supplier, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.productKind = productKind;
        this.productFinish = productFinish;
        this.supplier = supplier;
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKind() {
        return productKind;
    }

    public String getProductFinish() {
        return productFinish;
    }

    public void setProductFinish(String productFinish) {
        this.productFinish = productFinish;
    }

    public Supply getSupplier() {
        return supplier;
    }
    
    public void setSupplier(Supply supplier) {
        this.supplier = supplier;
    }

}