package com.guitar.management;

import com.guitar.models.Product;
import java.util.List;

public interface IProductManager {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
    Product getProduct(int productId);
    List<Product> getAllProducts();
    List<Product> getProductByType(String productKind);
}   