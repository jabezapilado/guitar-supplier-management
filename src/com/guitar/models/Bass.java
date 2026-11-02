package com.guitar.models;

public class Bass extends Product {
    private boolean isFretless;

    public Bass(int productID, String productName, String productFinish, boolean isFretless, Supply supplier, int quantity) {
        super(productID, productName, "Bass", productFinish, supplier, quantity);
        this.isFretless = isFretless;
    }

    public boolean isFretless() {
        return isFretless;
    }

    public void setFretless(boolean isFretless) {
        this.isFretless = isFretless;
    }
}