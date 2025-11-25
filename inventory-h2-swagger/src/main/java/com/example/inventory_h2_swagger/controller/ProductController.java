package com.example.inventory_h2_swagger.controller;

import com.example.inventory_h2_swagger.dto.ApiResponse;
import com.example.inventory_h2_swagger.model.Product;
import com.example.inventory_h2_swagger.model.ProductCategory;
import com.example.inventory_h2_swagger.service.ProductService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ✅ CREATE
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) {
        Product created = service.addProduct(product);
        return ResponseEntity.<ApiResponse<Product>>status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Product added successfully", created));
    }

    // ✅ READ (all)
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "All products fetched successfully", service.getAllProducts())
        );
    }

    // ✅ READ (by ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Product found", product));
    }

    // ✅ READ (by Category)
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> getByCategory(@PathVariable ProductCategory category) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Products by category fetched", service.getByCategory(category))
        );
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody Product updated) {
        Product updatedProduct = service.updateProduct(id, updated);
        return ResponseEntity.ok(new ApiResponse<>(200, "Product updated successfully", updatedProduct));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Long id) {
        boolean deleted = service.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Product deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404, "Product not found with ID: " + id, null));
    }
}
