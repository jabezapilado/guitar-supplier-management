package com.guitar.models;

public class Supply {
    private int supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierNumber;
    private String supplierEmail;

    public Supply(int supplierID, String supplierName, String supplierAddress, String supplierNumber, String supplierEmail) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierNumber = supplierNumber;
        this.supplierEmail = supplierEmail;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }
}