package com.guitar.dataexceptions;

public class DataMessages {
    public static final String[] msg = {
        "Supplier ID out of range. Supplier ID should only be from 1 - 999999", //0
        "Supplier Number must start with '09'", //1
        "Supplier Number must be 11 digits", //2
        "Supplier Email must contain an '@' symbol and end with '.com'", //3
        "Product ID should have 6 digits", //4
        "Product Kind must only be Acoustic, Bass, or Electric", //5
        "Product Finish can only be Matte, Glossy, or Natural", //6
        "Pickup Type can only be a Single Coil or Humbucker", //7
        "Body Type can only be Dreadnought, Cutaway, or Parlor", //8
        "Whether the bass is fretless can only be answered by True or False", //9
    };
}