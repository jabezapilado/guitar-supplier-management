package com.guitar.management;

import com.guitar.dataexceptions.DataException;
import com.guitar.dataexceptions.ValidationUtils;
import com.guitar.models.Supply;

import java.util.Collections;
import java.util.List;

public class SupplyManager implements ISupplyManager{
    private SupplyDAO supplyDAO;

    public SupplyManager() {
        this.supplyDAO = new SupplyDAO();
    }

    @Override
    public void addSupply(Supply supply) {
        try {
            ValidationUtils.validateSupplierID(supply.getSupplierID());
            ValidationUtils.validateSupplierNumber(supply.getSupplierNumber());
            ValidationUtils.validateSupplierEmail(supply.getSupplierEmail());
            // Additional validations can be added here if needed
            supplyDAO.addSupply(supply);
        } catch (DataException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding supplier: " + e.getMessage());
        }
    }

    @Override
    public void updateSupply(Supply supply) {
        try {
            ValidationUtils.validateSupplierID(supply.getSupplierID());
            ValidationUtils.validateSupplierNumber(supply.getSupplierNumber());
            ValidationUtils.validateSupplierEmail(supply.getSupplierEmail());
            supplyDAO.updateSupply(supply);
        } catch (DataException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating supplier: " + e.getMessage());
        }
    }

    @Override
    public void deleteSupply(int supplyId) {
        try {
            Supply supply = supplyDAO.getSupply(supplyId);
            if (supply == null) {
                System.out.println("Supplier not found.");
                return;
            }

            supplyDAO.deleteSupply(supplyId);
        } catch (Exception e) {
            System.out.println("Error deleting supplier: " + e.getMessage());
        }
    }

    @Override
    public Supply getSupply(int supplyId) {
        try {
            return supplyDAO.getSupply(supplyId);
        } catch (Exception e) {
            System.out.println("Error retrieving supplier: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Supply> getAllSupplies() {
        try {
            return supplyDAO.getAllSupplies();
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            return Collections.emptyList(); // Return an empty list instead of null
        }
    }
}