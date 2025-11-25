package com.example.with_status_code.controller;

import com.example.with_status_code.dto.ApiResponse;
import com.example.with_status_code.model.Product;
import com.example.with_status_code.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) {
        Product created = service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Product added successfully", created));
    }

    // READ all
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "All products fetched successfully", products)
        );
    }

    // READ by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getProductById(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Product not found with ID: " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Product fetched successfully", product));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable int id, @RequestBody Product updated) {
        Product existing = service.getProductById(id); // fetch actual product from storage
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Product not found with ID: " + id, null));
        }

        // Update fields of the existing product
        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        existing.setQuantity(updated.getQuantity());

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Product updated successfully", existing)
        );
    }



    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        boolean deleted = service.deleteProduct(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
