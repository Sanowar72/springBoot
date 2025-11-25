package com.example.with_status_code.service;

import com.example.with_status_code.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private int nextId = 1;

    // CREATE
    public Product addProduct(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }

    // READ all
    public List<Product> getAllProducts() {
        return products;
    }

    // READ by ID
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // UPDATE
    public boolean updateProduct(int id, Product updated) {
        for (Product p : products) {
            if (p.getId() == id) {
                p.setName(updated.getName());
                p.setCategory(updated.getCategory());
                p.setPrice(updated.getPrice());
                p.setQuantity(updated.getQuantity());
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean deleteProduct(int id) {
        return products.removeIf(p -> p.getId() == id);
    }
}
