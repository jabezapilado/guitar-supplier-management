package com.guitar.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseConnection {
    private static final String DB_URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/");
    private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "guitar_management_db");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASS = System.getenv().getOrDefault("DB_PASSWORD", "");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Check if the database exists
            String checkDbQuery = "SHOW DATABASES LIKE '" + DB_NAME + "'";
            ResultSet rs = stmt.executeQuery(checkDbQuery);
            if (!rs.next()) {
                // Database does not exist, create it
                String createDbQuery = "CREATE DATABASE " + DB_NAME;
                stmt.executeUpdate(createDbQuery);
                System.out.println("Database created successfully.");
            }

            // Use the database
            stmt.executeUpdate("USE " + DB_NAME);
            System.out.println("Database initialized successfully.");

            // Check if the required tables exist and have the correct columns
            String[] requiredTables = {"suppliers", "products"};
            boolean tablesCorrect = true;
            for (String table : requiredTables) {
                if (!tableExistsAndCorrect(stmt, table)) {
                    tablesCorrect = false;
                    break;
                }
            }

            // If any table is incorrect, recreate all tables
            if (!tablesCorrect) {
                System.out.println("\nCreating the Tables...");
                createTables(stmt);
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Access denied for user")) {
                System.err.println("Error: Access denied for user '" + USER + "'. Please check your username and password.");
            } else if (e.getMessage().toLowerCase().contains("communications link failure")) {
                System.err.println("Error: Unable to connect to the SQL server.\n* Check if SQL is installed and running.\n* Check if you have internet connection (for online SQL).");
                System.err.println("Error: The specified database '" + DB_NAME + "' does not exist.");
            } else if (e.getMessage().contains("No suitable driver found")) {
                System.err.println("Error: No suitable driver found for the database URL. Please check your JDBC in the 'bin' folder.");
            } else {
                e.printStackTrace();
            }
            System.out.println("\nExiting...");
            System.exit(1); // Exit the program if there is an error
        }
    }

    private static boolean tableExistsAndCorrect(Statement stmt, String tableName) throws SQLException {
        String checkTableQuery = "SHOW TABLES LIKE '" + tableName + "'";
        ResultSet rs = stmt.executeQuery(checkTableQuery);
        if (!rs.next()) {
            return false; // Table does not exist
        }
    
        // Define the expected columns for each table
        List<String> expectedColumns;
        if (tableName.equals("suppliers")) {
            expectedColumns = Arrays.asList("supplierID", "supplierName", "supplierAddress", "supplierNumber", "supplierEmail");
        } else if (tableName.equals("products")) {
            expectedColumns = Arrays.asList("productID", "productName", "productKind", "productFinish", "supplierID", "bodyType", "pickupType", "isFretless", "quantity");
        } else {
            return false; // Unknown table
        }
    
        // Check if the table has the correct columns
        String checkColumnsQuery = "DESCRIBE " + tableName;
        rs = stmt.executeQuery(checkColumnsQuery);
        List<String> actualColumns = new ArrayList<>();
        while (rs.next()) {
            String fieldName = rs.getString("Field");
            String fieldType = rs.getString("Type");
            String isNullable = rs.getString("Null");
            String key = rs.getString("Key");
            String defaultValue = rs.getString("Default");
    
            actualColumns.add(fieldName);
    
            if (tableName.equals("suppliers")) {
                if (!isValidSupplierColumn(fieldName, fieldType, isNullable, key)) {
                    return false; // Incorrect column in suppliers table
                }
            } else if (tableName.equals("products")) {
                if (!isValidProductColumn(fieldName, fieldType, isNullable, key, defaultValue)) {
                    return false; // Incorrect column in products table
                }
            }
        }
    
        // Check if all expected columns are present
        if (!actualColumns.containsAll(expectedColumns)) {
            return false; // Missing columns
        }
    
        return true; // Table exists and has correct columns
    }

    private static boolean isValidSupplierColumn(String fieldName, String fieldType, String isNullable, String key) {
        switch (fieldName) {
            case "supplierID":
                return fieldType.equals("int") && isNullable.equals("NO") && key.equals("PRI");
            case "supplierName":
                return fieldType.equals("varchar(100)");
            case "supplierAddress":
                return fieldType.equals("varchar(100)");
            case "supplierNumber":
                return fieldType.equals("varchar(11)");
            case "supplierEmail":
                return fieldType.equals("varchar(50)");
            default:
                return false;
        }
    }

    private static boolean isValidProductColumn(String fieldName, String fieldType, String isNullable, String key, String defaultValue) {
        switch (fieldName) {
            case "productID":
                return fieldType.equals("int") && isNullable.equals("NO") && key.equals("PRI");
            case "productName":
                return fieldType.equals("varchar(50)");
            case "productKind":
                return fieldType.equals("varchar(50)");
            case "productFinish":
                return fieldType.equals("varchar(50)");
            case "supplierID":
                return fieldType.equals("int") && isNullable.equals("NO");
            case "bodyType":
                return fieldType.equals("varchar(50)");
            case "pickupType":
                return fieldType.equals("varchar(50)");
            case "isFretless":
                return fieldType.equals("tinyint(1)");
            case "quantity":
                return fieldType.equals("int") && defaultValue.equals("1");
            default:
                return false;
        }
    }

    private static void createTables(Statement stmt) {
        try {
            // Drop existing tables if they exist
            stmt.executeUpdate("DROP TABLE IF EXISTS products");
            stmt.executeUpdate("DROP TABLE IF EXISTS suppliers");

            // Create suppliers table
            String createSuppliersTable = "CREATE TABLE suppliers (" +
                    "supplierID INT PRIMARY KEY NOT NULL," +
                    "supplierName VARCHAR(100)," +
                    "supplierAddress VARCHAR(100)," +
                    "supplierNumber VARCHAR(11)," +
                    "supplierEmail VARCHAR(50)" +
                    ");";

            // Create products table
            String createProductsTable = "CREATE TABLE products (" +
                    "productID INT PRIMARY KEY NOT NULL," +
                    "productName VARCHAR(50)," +
                    "productKind VARCHAR(50)," +
                    "productFinish VARCHAR(50)," +
                    "supplierID INT NOT NULL," +
                    "bodyType VARCHAR(50)," +
                    "pickupType VARCHAR(50)," +
                    "isFretless TINYINT(1)," +
                    "quantity INT DEFAULT '1'," +
                    "FOREIGN KEY (supplierID) REFERENCES suppliers(supplierID)" +
                    ");";

            stmt.executeUpdate(createSuppliersTable);
            stmt.executeUpdate(createProductsTable);

            // Insert initial data into suppliers table
            String insertSuppliersData = "INSERT INTO suppliers (supplierID, supplierName, supplierAddress, supplierNumber, supplierEmail) VALUES " +
                    "(1, 'Fender', 'Los Angeles, California, U.S.', '09287653545', 'fender@gmail.com')," +
                    "(2, 'Martin & Co.', 'Nazareth, Pennsylvania, U.S', '09876541213', 'martinco@gmail.com')," +
                    "(3, 'Gibson', 'Seattle, New York, U.S.', '09612472379', 'gibson@gmail.com');";

            // Insert initial data into products table
            String insertProductsData = "INSERT INTO products (productID, productName, productKind, productFinish, supplierID, bodyType, pickupType, isFretless, quantity) VALUES " +
                    "(100001, 'D-45', 'Acoustic', 'Glossy', 2, 'Dreadnought', NULL, NULL, 4)," +
                    "(100002, 'Stratocaster', 'Electric', 'Natural', 1, NULL, 'Single Coil', NULL, 1)," +
                    "(100003, 'Telecaster', 'Electric', 'Matte', 1, NULL, 'Single Coil', NULL, 1);";

            stmt.executeUpdate(insertSuppliersData);
            stmt.executeUpdate(insertProductsData);

            System.out.println("Database tables created and populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error in creating the Database Tables. Exiting...");
            e.printStackTrace();
            System.exit(1); // Exit the program if there is an error
        }
    }
}
