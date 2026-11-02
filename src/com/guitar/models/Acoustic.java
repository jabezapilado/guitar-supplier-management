package com.guitar.models;

public class Acoustic extends Product {
    private String bodyType;

    public Acoustic(int productID, String productName, String productFinish, String bodyType, Supply supplier, int quantity) {
        super(productID, productName, "Acoustic", productFinish, supplier, quantity);
        this.bodyType = bodyType;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }
}