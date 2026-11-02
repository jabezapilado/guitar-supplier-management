package com.guitar.management;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.guitar.dataexceptions.DataException;
import com.guitar.dataexceptions.ValidationUtils;
import com.guitar.models.Acoustic;
import com.guitar.models.Bass;
import com.guitar.models.Electric;
import com.guitar.models.Product;

public class ProductManager implements IProductManager {
    private ProductDAO productDAO;

    public ProductManager() {
        this.productDAO = new ProductDAO();
    }

    @Override
    public void addProduct(Product product) {
        try {
            
            ValidationUtils.validateProductID(product.getProductID());
            ValidationUtils.validateProductKind(product.getProductKind());
            ValidationUtils.validateProductFinish(product.getProductFinish());

            if (product instanceof Acoustic) {
                ValidationUtils.validateBodyType(((Acoustic) product).getBodyType());
            } else if (product instanceof Electric) {
                ValidationUtils.validatePickupType(((Electric) product).getPickupType());
            } else if (product instanceof Bass) {
                ValidationUtils.validateFretlessAnswer(((Bass) product).isFretless());
            }

            productDAO.addProduct(product);
        } catch (DataException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    @Override
    public void updateProduct(Product product) {
        try {
            ValidationUtils.validateProductID(product.getProductID());
            ValidationUtils.validateProductKind(product.getProductKind());
            ValidationUtils.validateProductFinish(product.getProductFinish());

            if (product instanceof Acoustic) {
                ValidationUtils.validateBodyType(((Acoustic) product).getBodyType());
            } else if (product instanceof Electric) {
                ValidationUtils.validatePickupType(((Electric) product).getPickupType());
            } else if (product instanceof Bass) {
                ValidationUtils.validateFretlessAnswer(((Bass) product).isFretless());
            }

            productDAO.updateProduct(product);
        } catch (DataException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            Product product = productDAO.getProduct(productId);
            if (product == null) {
                System.out.println("Product not found.");
                return;
            }

            productDAO.deleteProduct(productId);
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    @Override
    public Product getProduct(int productId) {
        try {
            return productDAO.getProduct(productId);
        } catch (Exception e) {
            System.out.println("Error retrieving product: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> getProductByType(String productKind) {
        try {
            return productDAO.getProductsByType(productKind);
        } catch (SQLException e) {
            System.out.println("Error retrieving products by type: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}