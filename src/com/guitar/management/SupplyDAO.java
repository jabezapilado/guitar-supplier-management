package com.guitar.management;

import com.guitar.models.Supply;
import com.guitar.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplyDAO {

    public void addSupply(Supply supply) throws SQLException {
        String query = "INSERT INTO suppliers (supplierID, supplierName, supplierAddress, supplierNumber, supplierEmail) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, supply.getSupplierID());
            statement.setString(2, supply.getSupplierName());
            statement.setString(3, supply.getSupplierAddress());
            statement.setString(4, supply.getSupplierNumber());
            statement.setString(5, supply.getSupplierEmail());

            statement.executeUpdate();
            System.out.println("Supplier added successfully.");
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
    }

    public void updateSupply(Supply supply) throws SQLException {
        String query = "UPDATE suppliers SET supplierName = ?, supplierAddress = ?, supplierNumber = ?, supplierEmail = ? WHERE supplierID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, supply.getSupplierName());
            statement.setString(2, supply.getSupplierAddress());
            statement.setString(3, supply.getSupplierNumber());
            statement.setString(4, supply.getSupplierEmail());
            statement.setInt(5, supply.getSupplierID());

            statement.executeUpdate();
            System.out.println("Supplier updated successfully.");
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
    }

    public void deleteSupply(int supplierID) throws SQLException {
        String query1 = "DELETE FROM products WHERE supplierID = ?";
        String query2 = "DELETE FROM suppliers WHERE supplierID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement1 = connection.prepareStatement(query1);
             PreparedStatement statement2 = connection.prepareStatement(query2)) {

            // Delete all products of the supplier
            statement1.setInt(1, supplierID);
            statement1.executeUpdate();

            // Delete the supplier
            statement2.setInt(1, supplierID);
            statement2.executeUpdate();
            System.out.println("Supplier deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
    }

    public Supply getSupply(int supplierID) throws SQLException {
        String query = "SELECT * FROM suppliers WHERE supplierID = ?";
        Supply supply = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, supplierID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String supplierName = resultSet.getString("supplierName");
                String supplierAddress = resultSet.getString("supplierAddress");
                String supplierNumber = resultSet.getString("supplierNumber");
                String supplierEmail = resultSet.getString("supplierEmail");

                supply = new Supply(supplierID, supplierName, supplierAddress, supplierNumber, supplierEmail);
            }
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
        return supply;
    }

    public List<Supply> getAllSupplies() throws SQLException {
        List<Supply> supplyList = new ArrayList<>();
        String query = "SELECT * FROM suppliers";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int supplierID = resultSet.getInt("supplierID");
                String supplierName = resultSet.getString("supplierName");
                String supplierAddress = resultSet.getString("supplierAddress");
                String supplierNumber = resultSet.getString("supplierNumber");
                String supplierEmail = resultSet.getString("supplierEmail");

                Supply supply = new Supply(supplierID, supplierName, supplierAddress, supplierNumber, supplierEmail);
                supplyList.add(supply);
            }
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
        return supplyList;
    }
}