package com.example.inventory_master.service;

import com.example.inventory_master.exception.ResourceNotFoundException;
import com.example.inventory_master.model.Product;
import com.example.inventory_master.model.ProductCategory;
import com.example.inventory_master.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private  ProductRepository repository;

    // ✅ CREATE
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    // ✅ READ (all)
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // ✅ READ (by ID)
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with ID: " + id));
    }

    // ✅ READ (by Category)
    public List<Product> getByCategory(ProductCategory category) {
        return repository.findByCategory(category);
    }

    // ✅ UPDATE
    public Product updateProduct(Long id, Product updatedProduct) {
        // Find existing product or throw exception
        Product existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with ID: " + id));

        // Update only valid fields
        existing.setName(updatedProduct.getName());
        existing.setCategory(updatedProduct.getCategory());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());
        existing.setStatus(updatedProduct.getStatus());

        // Save updated record
        return repository.save(existing);
    }

    // ✅ DELETE
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
