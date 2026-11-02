package com.guitar.main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.guitar.utils.DatabaseConnection;
import com.guitar.dataexceptions.DataException;
import com.guitar.management.IProductManager;
import com.guitar.management.ISupplyManager;
import com.guitar.management.ProductManager;
import com.guitar.management.SupplyManager;
import com.guitar.models.Acoustic;
import com.guitar.models.Bass;
import com.guitar.models.Electric;
import com.guitar.models.Product;
import com.guitar.models.Supply;

public class GuitarSupplierManagement {
    private static final ISupplyManager supplyManager = new SupplyManager();
    private static final IProductManager productManager = new ProductManager();

    public static void main(String[] args) throws DataException {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
        
        try {
            // Initialize the database
            System.out.println("Initializing the Database...");
            DatabaseConnection.initializeDatabase();
            System.out.println("");
        } catch (Exception e) {
            System.err.println("Failed to initialize the database. Exiting...");
            e.printStackTrace();
            System.exit(1);
        }   
        
        try (Scanner scanner = new Scanner(System.in)) {
            displayMainMenu(scanner);
        }
    }

    public static void displayMainMenu(Scanner scanner) throws DataException {
        while (true) {
            try {
                System.out.println("===== Guitar Supplier Management System =====");
                System.out.println("1. Manage Suppliers");
                System.out.println("2. Manage Products");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: "); int choice = scanner.nextInt(); scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        manageSuppliers(scanner);
                        break;
                    case 2:
                        manageProducts(scanner);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Try again.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.\n");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    public static void manageSuppliers(Scanner scanner) throws DataException {
        while (true) {
            try {
                System.out.println("\n===== Supplier Management =====");
                System.out.println("1. Add Supplier");
                System.out.println("2. View All Suppliers");
                System.out.println("3. Update Supplier");
                System.out.println("4. Delete Supplier");
                System.out.println("5. Go Back");
                System.out.print("Enter your choice: "); int choice = scanner.nextInt(); scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        addSupplier(scanner);
                        break;
                    case 2:
                        viewSuppliers();
                        break;
                    case 3:
                        updateSupplier(scanner);
                        break;
                    case 4:
                        deleteSupplier(scanner);
                        break;
                    case 5:
                        System.out.println("");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                scanner.next(); // Clear the invalid input
            } 
        }
    }

    public static void manageProducts(Scanner scanner) throws DataException {
        while (true) {
            try {
                System.out.println("\n===== Product Management =====");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. View Products by Supplier");
                System.out.println("4. View Products by Guitar Type");
                System.out.println("5. Update Product");
                System.out.println("6. Delete Product");
                System.out.println("7. Go Back");
                System.out.print("Enter your choice: "); int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addProduct(scanner);
                        break;
                    case 2:
                        viewProducts();
                        break;
                    case 3:
                        viewProductbySupplier(scanner);
                        break;
                    case 4:
                        viewProductsByType(scanner);
                        break;
                    case 5:
                        updateProduct(scanner); 
                        break;
                    case 6:
                        deleteProduct(scanner);
                        break;
                    case 7:
                        System.out.println("");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }   
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                scanner.next(); // Clear the invalid input
            } 
        }
    }


    private static void addSupplier(Scanner scanner) throws DataException {
        try {
            System.out.print("\nEnter Supplier ID: "); String supplierIDStr = scanner.nextLine(); 
            int supplierID;
            try {
                supplierID = Integer.parseInt(supplierIDStr);
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Supplier ID out of range. Supplier ID should only be from 1 - 999999");
                return;
            } 
            Supply existingSupply = supplyManager.getSupply(supplierID);
            if (existingSupply != null) {
                System.out.println("Supplier ID already exists.");
                return;
            }
            System.out.print("Enter Supplier Name: "); String supplierName = scanner.nextLine();
            if (!supplierName.matches(".*[a-zA-Z]+.*")) {
                System.out.println("Validation Error: Supplier Name must contain at least one letter.");
                return;
            }

            System.out.print("Enter Supplier Address: "); String supplierAddress = scanner.nextLine();
            System.out.print("Enter Supplier Number: "); String supplierNumber = scanner.nextLine();
            System.out.print("Enter Supplier Email: "); String supplierEmail = scanner.nextLine();
            Supply supply = new Supply(supplierID, supplierName, supplierAddress, supplierNumber, supplierEmail);
            supplyManager.addSupply(supply);
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Try again.");
            scanner.next(); // Clear the invalid input
        }
        
    }

    private static void viewSuppliers() {
        List<Supply> supplies = supplyManager.getAllSupplies();
        if (supplies.isEmpty()) {
            System.out.println("No suppliers found.");
            return;
        }

        boolean found = false;
        for (Supply s : supplies) {
            if (!found) {
                System.out.format("\n%75s%n\n", "LIST OF SUPPLIERS");
                System.out.format("%-20s %-20s %-50s %-20s %-20s%n", "Supplier ID", "Supplier Name", "Supplier Address", "Supplier Number", "Supplier Email");
                found = true;
            }
            System.out.format("%-20s %-20s %-50s %-20s %-20s%n", s.getSupplierID(), s.getSupplierName(), s.getSupplierAddress(), s.getSupplierNumber(), s.getSupplierEmail());
        }

        if (!found) {
            System.out.println("No suppliers found.");
        }
    }

    private static void updateSupplier (Scanner scanner) throws DataException {
        try {
            List<Supply> supplies = supplyManager.getAllSupplies();
            if (supplies.isEmpty()) {
                return;
            }
            System.out.format("\n%75s%n\n", "EXISTING SUPPLIERS");
            System.out.format("%-20s %-20s %-50s %-20s %-20s%n","Supplier ID", "Supplier Name", "Supplier Address", "Supplier Number", "Supplier Email");
            for (Supply s : supplyManager.getAllSupplies()) {
                System.out.format("%-20s %-20s %-50s %-20s %-20s%n", s.getSupplierID(), s.getSupplierName(), s.getSupplierAddress(), s.getSupplierNumber(), s.getSupplierEmail());
            }
            System.out.print("\nEnter Supplier ID to update: "); String supplierIDStr = scanner.nextLine(); 
            int supplierIDToUpdate;
            try {
                supplierIDToUpdate = Integer.parseInt(supplierIDStr);
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Supplier ID out of range. Supplier ID should only be from 1 - 999999");
                return;
            } 
            Supply existingSupply = supplyManager.getSupply(supplierIDToUpdate);
            if (existingSupply == null) {
                System.out.println("Supplier not found.");
                return;
            }
            System.out.println("\nUpdating Supplier ID: " + supplierIDToUpdate);
            System.out.print("Enter new Supplier Name: "); String newSupplierName = scanner.nextLine();
            if (!newSupplierName.matches(".*[a-zA-Z]+.*")) {
                System.out.println("Validation Error: Supplier Name must contain at least one letter.");
                return;
            }
            System.out.print("Enter new Supplier Address: "); String newSupplierAddress = scanner.nextLine();
            System.out.print("Enter new Supplier Number: "); String newSupplierNumber = scanner.nextLine();
            System.out.print("Enter new Supplier Email: "); String newSupplierEmail = scanner.nextLine();
            Supply updatedSupply = new Supply(supplierIDToUpdate, newSupplierName, newSupplierAddress, newSupplierNumber, newSupplierEmail);
            supplyManager.updateSupply(updatedSupply);   
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Try again.");
            scanner.next(); // Clear the invalid input
        }
    }

    private static void deleteSupplier(Scanner scanner) {
        try {
            List<Supply> supplies = supplyManager.getAllSupplies();
            if (supplies.isEmpty()) {
                return;
            }
            System.out.format("\n%75s%n\n", "EXISTING SUPPLIERS");
            System.out.format("%-20s %-20s %-50s %-20s %-20s%n","Supplier ID", "Supplier Name", "Supplier Address", "Supplier Number", "Supplier Email");
            for (Supply s : supplyManager.getAllSupplies()) {
                System.out.format("%-20s %-20s %-50s %-20s %-20s%n", s.getSupplierID(), s.getSupplierName(), s.getSupplierAddress(), s.getSupplierNumber(), s.getSupplierEmail());
            }
            System.out.print("\nEnter Supplier ID to delete: "); int idToDelete = scanner.nextInt();
            Supply existingSupply = supplyManager.getSupply(idToDelete);
            if (existingSupply == null) {
                System.out.println("Supplier not found.");
                return;
            }

            // Confirmation prompt with loop
            String confirmation;
            do {
                System.out.print("Are you sure you want to delete this supplier and all its products? (Y/N): ");
                confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("Y")) {
                    supplyManager.deleteSupply(idToDelete);
                    break;
                } else if (confirmation.equalsIgnoreCase("N")) {
                    System.out.println("Deletion cancelled.");
                    break;
                } else {
                    System.out.println("Invalid input. Enter a valid input (Y/N).\n");
                }
            } while (true);

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Try again.");
            scanner.next(); // Clear the invalid input
        }
    }

    private static void addProduct(Scanner scanner) throws DataException {
        try {
            System.out.println("\nProduct Type:");
            System.out.println("1 - Acoustic\n2 - Electric\n3 - Bass");
            System.out.print("Enter Product Type: "); scanner.nextLine(); String productTypeStr = scanner.nextLine();
            int productType;
            try {
                productType = Integer.parseInt(productTypeStr);
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Product Kind must only be Acoustic, Bass, or Electric");
                return;
            }

            if (productType < 1 || productType > 3) {
                System.out.println("Validation Error: Product Kind must only be Acoustic, Bass, or Electric");
                return;
            }
            System.out.print("Enter Product ID: "); String productIDStr = scanner.nextLine(); 
            int productID;
            try {
                productID = Integer.parseInt(productIDStr);
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Product ID should have 6 digits");
                return;
            }
            Product existingProduct = productManager.getProduct(productID);
            if (existingProduct != null) {
                System.out.println("Product ID already exists.");
                return;
            }
            System.out.print("Enter Product Name: "); String productName = scanner.nextLine(); 
            System.out.println("Product Finish:");
            System.out.println("Matte\nGlossy\nNatural");
            System.out.print("Enter Product Finish: "); String productFinish = scanner.nextLine();
            System.out.print("Enter Supplier ID: "); int supplierID = scanner.nextInt();
            System.out.print("Enter Quantity: "); int quantity = scanner.nextInt();
            Supply supplier = supplyManager.getAllSupplies().stream()
                .filter(s -> s.getSupplierID() == supplierID)
                .findFirst()
                .orElse(null);
            if (supplier == null) {
                System.out.println("Supplier not found.");
                return;
            }

            Product product;
            switch (productType) {
                case 1:
                    System.out.println("\nBody Type:");
                    System.out.println("Dreadnought\nCutaway\nParlor");
                    System.out.print("Enter Body Type: "); scanner.nextLine(); String bodyType = scanner.nextLine();
                    product = new Acoustic(productID, productName, productFinish, bodyType, supplier, quantity);
                    break;
                case 2:
                    System.out.println("\nPickup Type:");
                    System.out.println("Single Coil\nHumbucker");
                    System.out.print("Enter Pickup Type: "); scanner.nextLine(); String pickupType = scanner.nextLine(); 
                    product = new Electric(productID, productName, productFinish, pickupType, supplier, quantity);
                    break;
                case 3:
                    System.out.print("\nIs the Bass FRETLESS (True/False): "); scanner.nextLine(); String isFretlessStr = scanner.nextLine().trim().toLowerCase();
                    boolean isFretless;
                    if (isFretlessStr.equals("true")) {
                        isFretless = true;
                    } else if (isFretlessStr.equals("false")) {
                        isFretless = false;
                    } else {
                        System.out.println("Validation Error: Whether the bass is fretless can only be answered by True or False");
                        return;
                    }
                    product = new Bass(productID, productName, productFinish, isFretless, supplier, quantity);
                    break;
                default:
                    System.out.println("Invalid product type.");
                    return;
            }
            
            productManager.addProduct(product);
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Try again.");
            scanner.next(); // Clear the invalid input
        }
    }

    private static void viewProducts() {
        List<Product> products = productManager.getAllProducts();
        if (products.isEmpty()) {
            return;
        }
        System.out.format("\n%70s%n\n", "LIST OF ALL PRODUCTS");
        System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n","Product ID", "Product Name", "Supplier Name", "Product Type", "Product Finish", "Quantity");
        for (Product p : productManager.getAllProducts()) {
            System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", p.getProductID(), p.getProductName(), p.getSupplier().getSupplierName(), p.getProductKind(), p.getProductFinish(), p.getQuantity());
        }
    }

    private static void updateProduct(Scanner scanner) throws DataException {
        try {
            List<Product> products = productManager.getAllProducts();
            if (products.isEmpty()) {
                return;
            }
            System.out.format("\n%68s%n\n", "EXISTING PRODUCTS");
            System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n","Product ID", "Product Name", "Supplier Name", "Product Type", "Product Finish", "Quantity");
            for (Product p : productManager.getAllProducts()) {
                System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", p.getProductID(), p.getProductName(), p.getSupplier().getSupplierName(), p.getProductKind(), p.getProductFinish(), p.getQuantity());
            }

            System.out.print("\nEnter Product ID to update: "); scanner.nextLine(); String productIDStr = scanner.nextLine(); 
            int productID;
            try {
                productID = Integer.parseInt(productIDStr);
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Product ID should have 6 digits");
                return;
            } 
            
            Product existingProduct = productManager.getProduct(productID);
            if (existingProduct == null) {
                System.out.println("Product not found.");
                return;
            }
            System.out.println("\nUpdating Product ID: " + productID);
            System.out.println("Product Type:");
            System.out.println("1 - Acoustic\n2 - Electric\n3 - Bass");
            System.out.print("Enter Product Type: "); String productTypeStr = scanner.nextLine();
            int productType;
            try {
                productType = Integer.parseInt(productTypeStr);
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Product Kind must only be Acoustic, Bass, or Electric");
                return;
            }
            
            if (productType < 1 || productType > 3) {
                System.out.println("Validation Error: Product Kind must only be Acoustic, Bass, or Electric");
                return;
            }
            System.out.print("Enter new Product Name: "); String productName = scanner.nextLine();
            System.out.println("Product Finish:");
            System.out.println("Matte\nGlossy\nNatural");
            System.out.print("Enter new Product Finish: "); String productFinish = scanner.nextLine();
            Supply supplier = existingProduct.getSupplier();
            System.out.print("Enter new Quantity: "); int quantity = scanner.nextInt();

            Product updatedProduct;

            switch (productType) {
                case 1:
                    System.out.println("\nBody Type:");
                    System.out.println("Dreadnought\nCutaway\nParlor");
                    System.out.print("Enter new Body Type: "); scanner.nextLine(); String bodyType = scanner.nextLine();
                    updatedProduct = new Acoustic(productID, productName, productFinish, bodyType, supplier, quantity);
                    break;
                case 2:
                    System.out.println("\nPickup Type:");
                    System.out.println("Single Coil\nHumbucker");
                    System.out.print("Enter new Pickup Type: "); scanner.nextLine(); String pickupType = scanner.nextLine();
                    updatedProduct = new Electric(productID, productName, productFinish, pickupType, supplier, quantity);
                    break;
                case 3:
                    System.out.print("\nIs the Bass FRETLESS (True/False): "); scanner.nextLine(); String isFretlessStr = scanner.nextLine().trim().toLowerCase();
                    boolean isFretless;
                    if (isFretlessStr.equals("true")) {
                        isFretless = true;
                    } else if (isFretlessStr.equals("false")) {
                        isFretless = false;
                    } else {
                        System.out.println("Validation Error: Whether the bass is fretless can only be answered by True or False");
                        return;
                    }
                    updatedProduct = new Bass(productID, productName, productFinish, isFretless, supplier, quantity);
                    break;
                default:
                    System.out.println("Invalid product type.");
                    return;
                }
            
            productManager.updateProduct(updatedProduct);
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Try again.");
            scanner.next(); // Clear the invalid input
        }
    }

    private static void deleteProduct(Scanner scanner) {
        try {
            List<Product> products = productManager.getAllProducts();
            if (products.isEmpty()) {
                return;
            }
            System.out.format("\n%68s%n\n", "EXISTING PRODUCTS");
            System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n","Product ID", "Product Name", "Supplier Name", "Product Type", "Product Finish", "Quantity");
            for (Product p : productManager.getAllProducts()) {
                System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", p.getProductID(), p.getProductName(), p.getSupplier().getSupplierName(), p.getProductKind(), p.getProductFinish(), p.getQuantity());
            }
            System.out.print("\nEnter Product ID to delete: "); int idToDelete = scanner.nextInt();
            Product existingProduct = productManager.getProduct(idToDelete);
            if (existingProduct == null) {
                System.out.println("Product not found.");
                return;
            }

            // Confirmation prompt with loop
            String confirmation;
            do {
                System.out.print("Are you sure you want to delete this product? (Y/N): ");
                confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("Y")) {
                    productManager.deleteProduct(idToDelete);
                    break;
                } else if (confirmation.equalsIgnoreCase("N")) {
                    System.out.println("Deletion cancelled.");
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid input (Y/N).\n");
                }
            } while (true);
            
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Try again.");
            scanner.next(); // Clear the invalid input
        }
        
    }

    private static void viewProductbySupplier(Scanner scanner) {
        List<Supply> supplies = supplyManager.getAllSupplies();
        if (supplies.isEmpty()) {
            return;
        }

        System.out.format("\n%70s%n\n", "EXISTING SUPPLIERS");
        System.out.format("%-20s %-20s %-40s %-20s %-20s%n","Supplier ID", "Supplier Name", "Supplier Address", "Supplier Number", "Supplier Email");
        for (Supply s : supplyManager.getAllSupplies()) {
            System.out.format("%-20s %-20s %-40s %-20s %-20s%n", s.getSupplierID(), s.getSupplierName(), s.getSupplierAddress(), s.getSupplierNumber(), s.getSupplierEmail());
        }

        System.out.print("\nEnter Supplier ID: "); int supplierIDToView = scanner.nextInt(); scanner.nextLine(); // Consume newline
    
        
        Supply supply = supplyManager.getSupply(supplierIDToView);
        if (supply == null) {
            System.out.println("Supplier not found.");
            return;
        }
        
        boolean found = false;
        for (Product p : productManager.getAllProducts()) {
            if (p.getSupplier().getSupplierID() == supplierIDToView) {
                if (!found) {
                    System.out.format("\n%-20s %-15s %-20s%n\n", "", "", supply.getSupplierName().toUpperCase() + " PRODUCTS");
                    System.out.format("%-20s %-20s %-20s %-20s %-20s%n", "Product ID", "Product Name", "Product Type", "Product Finish", "Quantity");
                    found = true;
                }
                System.out.format("%-20s %-20s %-20s %-20s %-20s%n", p.getProductID(), p.getProductName(), p.getProductKind(), p.getProductFinish(), p.getQuantity());
            }
        }
        if (!found) {
            System.out.println("No products found for the specified supplier.");
        }
    }

    private static void viewProductsByType(Scanner scanner) {
        System.out.println("\nGuitar Types:\n1 - Acoustic\n2 - Electric\n3 - Bass");
        System.out.print("\nEnter the type of guitar to view: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        String type;
        switch (typeChoice) {
            case 1:
                type = "Acoustic";
                break;
            case 2:
                type = "Electric";
                break;
            case 3:
                type = "Bass";
                break;
            default:
                System.out.println("Validation Error: Product Kind must only be Acoustic, Bass, or Electric");
                return;
        }
    
        List<Product> products = productManager.getProductByType(type);
        if (products.isEmpty()) {
            System.out.println("No products found for the specified type.");
            return;
        }
    
        System.out.format("\n%70s%n\n", "PRODUCTS BY TYPE: " + type.toUpperCase());
        if (type.equals("Acoustic")) {
            System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", "Product ID", "Product Name", "Supplier Name", "Body Type", "Product Finish", "Quantity");
        } else if (type.equals("Electric")) {
            System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", "Product ID", "Product Name", "Supplier Name", "Pickup Type", "Product Finish", "Quantity");
        } else if (type.equals("Bass")) {
            System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", "Product ID", "Product Name", "Supplier Name", "Neck Type", "Product Finish", "Quantity");
        }
    
        for (Product p : products) {
            if (type.equals("Acoustic") && p instanceof Acoustic) {
                Acoustic acoustic = (Acoustic) p;
                System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", p.getProductID(), p.getProductName(), p.getSupplier().getSupplierName(), acoustic.getBodyType(), p.getProductFinish(), p.getQuantity());
            } else if (type.equals("Electric") && p instanceof Electric) {
                Electric electric = (Electric) p;
                System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", p.getProductID(), p.getProductName(), p.getSupplier().getSupplierName(), electric.getPickupType(), p.getProductFinish(), p.getQuantity());
            } else if (type.equals("Bass") && p instanceof Bass) {
                Bass bass = (Bass) p;
                String fretlessStr = bass.isFretless() ? "Fretless" : "Fretted";
                System.out.format("%-20s %-20s %-20s %-20s %-20s %-20s%n", p.getProductID(), p.getProductName(), p.getSupplier().getSupplierName(), fretlessStr, p.getProductFinish(), p.getQuantity());
            }
        }
    }
}