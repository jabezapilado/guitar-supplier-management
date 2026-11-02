package com.guitar.management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.guitar.models.Acoustic;
import com.guitar.models.Bass;
import com.guitar.models.Electric;
import com.guitar.models.Product;
import com.guitar.models.Supply;
import com.guitar.utils.DatabaseConnection;

public class ProductDAO {

    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (productID, productName, productKind, productFinish, supplierID, bodyType, pickupType, isFretless, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, product.getProductID());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getProductKind());
            statement.setString(4, product.getProductFinish());
            statement.setInt(5, product.getSupplier().getSupplierID());

            if (product instanceof Acoustic) {
                statement.setString(6, ((Acoustic) product).getBodyType());
                statement.setNull(7, Types.VARCHAR);
                statement.setNull(8, Types.BOOLEAN);
            } else if (product instanceof Electric) {
                statement.setNull(6, Types.VARCHAR);
                statement.setString(7, ((Electric) product).getPickupType());
                statement.setNull(8, Types.BOOLEAN);
            } else if (product instanceof Bass) {
                statement.setNull(6, Types.VARCHAR);
                statement.setNull(7, Types.VARCHAR);
                statement.setBoolean(8, ((Bass) product).isFretless());
            } else {
                statement.setNull(6, Types.VARCHAR);
                statement.setNull(7, Types.VARCHAR);
                statement.setNull(8, Types.BOOLEAN);
            }
            statement.setInt(9, product.getQuantity());
            statement.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET productName = ?, productKind = ?, productFinish = ?, supplierID = ?, bodyType = ?, pickupType = ?, isFretless = ?, quantity = ? WHERE productID = ?";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductKind());
            statement.setString(3, product.getProductFinish());
            statement.setInt(4, product.getSupplier().getSupplierID());
    
            if (product instanceof Acoustic) {
                statement.setString(5, ((Acoustic) product).getBodyType());
                statement.setNull(6, Types.VARCHAR);
                statement.setNull(7, Types.BOOLEAN);
            } else if (product instanceof Electric) {
                statement.setNull(5, Types.VARCHAR);
                statement.setString(6, ((Electric) product).getPickupType());
                statement.setNull(7, Types.BOOLEAN);
            } else if (product instanceof Bass) {
                statement.setNull(5, Types.VARCHAR);
                statement.setNull(6, Types.VARCHAR);
                statement.setBoolean(7, ((Bass) product).isFretless());
            } else {
                statement.setNull(5, Types.VARCHAR);
                statement.setNull(6, Types.VARCHAR);
                statement.setNull(7, Types.BOOLEAN);
            }
    
            statement.setInt(8, product.getQuantity());
        statement.setInt(9, product.getProductID());
        statement.executeUpdate();
        System.out.println("Product updated successfully.");
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
    }

    public void deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM products WHERE productID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
            System.out.println("Product deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
    }

    public Product getProduct(int productId) throws SQLException {
        String query = "SELECT * FROM products WHERE productID = ?";
        Product product = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String productName = resultSet.getString("productName");
                String productKind = resultSet.getString("productKind");
                String productFinish = resultSet.getString("productFinish");
                int supplierID = resultSet.getInt("supplierID");
                Supply supplier = getSupplier(supplierID);
                int quantity = resultSet.getInt("quantity");
                switch (productKind) {
                    case "Acoustic":
                        String bodyType = resultSet.getString("bodyType");
                        product = new Acoustic(productId, productName, productFinish, bodyType, supplier, quantity);
                        break;
                    case "Electric":
                        String pickupType = resultSet.getString("pickupType");
                        product = new Electric(productId, productName, productFinish, pickupType, supplier, quantity);
                        break;
                    case "Bass":
                        boolean isFretless = resultSet.getBoolean("isFretless");
                        product = new Bass(productId, productName, productFinish, isFretless, supplier, quantity);
                        break;
                    default:
                        product = new Product(productId, productName, productKind, productFinish, supplier, quantity);
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
        return product;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                String productKind = resultSet.getString("productKind");
                String productFinish = resultSet.getString("productFinish");
                int supplierID = resultSet.getInt("supplierID");
                Supply supplier = getSupplier(supplierID);
                int quantity = resultSet.getInt("quantity");

                Product product;
                switch (productKind) {
                    case "Acoustic":
                        String bodyType = resultSet.getString("bodyType");
                        product = new Acoustic(productID, productName, productFinish, bodyType, supplier, quantity);
                        break;
                    case "Electric":
                        String pickupType = resultSet.getString("pickupType");
                        product = new Electric(productID, productName, productFinish, pickupType, supplier, quantity);
                        break;
                    case "Bass":
                        boolean isFretless = resultSet.getBoolean("isFretless");
                        product = new Bass(productID, productName, productFinish, isFretless, supplier, quantity);
                        break;
                    default:
                        product = new Product(productID, productName, productKind, productFinish, supplier, quantity);
                        break;
                }
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
        return productList;
    }

    public List<Product> getProductsByType(String productKind) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE productKind = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productKind);
            try (ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    String productFinish = rs.getString("productFinish");
                    int supplierID = rs.getInt("supplierID");
                    int quantity = rs.getInt("quantity");
                    Supply supplier = getSupplier(supplierID);

                    Product product;
                    switch (productKind) {
                        case "Acoustic":
                            String bodyType = rs.getString("bodyType");
                            product = new Acoustic(productID, productName, productFinish, bodyType, supplier, quantity);
                            break;
                        case "Electric":
                            String pickupType = rs.getString("pickupType");
                            product = new Electric(productID, productName, productFinish, pickupType, supplier, quantity);
                            break;
                        case "Bass":
                            boolean isFretless = rs.getBoolean("isFretless");
                            product = new Bass(productID, productName, productFinish, isFretless, supplier, quantity);
                            break;
                        default:
                            product = new Product(productID, productName, productKind, productFinish, supplier, quantity);
                            break;
                    }
                    products.add(product);
                }
            }
        }
        return products;
    }

    private Supply getSupplier(int supplierID) throws SQLException {
        String query = "SELECT * FROM suppliers WHERE supplierID = ?";
        Supply supplier = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, supplierID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String supplierName = resultSet.getString("supplierName");
                String supplierAddress = resultSet.getString("supplierAddress");
                String supplierNumber = resultSet.getString("supplierNumber");
                String supplierEmail = resultSet.getString("supplierEmail");

                supplier = new Supply(supplierID, supplierName, supplierAddress, supplierNumber, supplierEmail);
            }
        } catch (SQLException e) {
            System.err.println("Network error: Unable to connect to the database.");
        }
        return supplier;
    }
}