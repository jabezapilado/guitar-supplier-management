package com.guitar.dataexceptions;

public class ValidationUtils {

    public static void validateSupplierID(int supplierID) throws DataException {
        if (supplierID < 1 || supplierID > 999999) {
            throw new DataException(DataMessages.msg[0]);
        }
    }

    public static void validateSupplierNumber(String supplierNumber) throws DataException {
        if (!supplierNumber.startsWith("09")) {
            throw new DataException(DataMessages.msg[1]);
        }
        if (supplierNumber.length() != 11) {
            throw new DataException(DataMessages.msg[2]);
        }
    }

    public static void validateSupplierEmail(String email) throws DataException {
        if (!email.contains("@") || !email.endsWith(".com")) {
            throw new DataException(DataMessages.msg[3]);
        }
    }

    public static void validateProductID(int productID) throws DataException {
        String idStr = String.valueOf(productID);
        if (idStr.length() != 6) {
            throw new DataException(DataMessages.msg[4]);
        }
    }

    public static void validateProductKind(String kind) throws DataException {
        if (!kind.equals("Acoustic") && !kind.equals("Bass") && !kind.equals("Electric")) {
            throw new DataException(DataMessages.msg[5]);
        }
    }

    public static void validateProductFinish(String finish) throws DataException {
        if (!finish.equals("Matte") && !finish.equals("Glossy") && !finish.equals("Natural")) {
            throw new DataException(DataMessages.msg[6]);
        }
    }

    public static void validatePickupType(String pickupType) throws DataException {
        if (!pickupType.equals("Single Coil") && !pickupType.equals("Humbucker")) {
            throw new DataException(DataMessages.msg[7]);
        }
    }

    public static void validateBodyType(String bodyType) throws DataException {
        if (!bodyType.equals("Dreadnought") && !bodyType.equals("Cutaway") && !bodyType.equals("Parlor")) {
            throw new DataException(DataMessages.msg[8]);
        }
    }

    public static void validateFretlessAnswer(Boolean answer) throws DataException {
        String answerStr = String.valueOf(answer);
        if (!answerStr.equalsIgnoreCase("True") && !answerStr.equalsIgnoreCase("False")) {
            throw new DataException(DataMessages.msg[9]);
        }
    }
}