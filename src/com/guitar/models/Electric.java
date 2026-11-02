package com.guitar.models;

public class Electric extends Product {
    private String pickupType;

    public Electric(int productID, String productName, String productFinish, String pickupType, Supply supplier, int quantity) {
        super(productID, productName, "Electric", productFinish, supplier, quantity);
        this.pickupType = pickupType;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }
}