package com.example.inventory_h2_swagger.service;

import com.example.inventory_h2_swagger.exception.ResourceNotFoundException;
import com.example.inventory_h2_swagger.model.*;
import com.example.inventory_h2_swagger.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public ProductRepository repository;



    // CREATE
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    // READ (all)
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // READ (by ID)
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }


    // READ (by category)
    public List<Product> getByCategory(ProductCategory category) {
        return repository.findByCategory(category);
    }

    // UPDATE
    public Product updateProduct(Long id, Product updated) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        existing.setQuantity(updated.getQuantity());
        existing.setStatus(updated.getStatus());

        return repository.save(existing);
    }

    // DELETE
    public boolean deleteProduct(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
