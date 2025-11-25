package com.example.inventory_h2_swagger.repository;

import com.example.inventory_h2_swagger.model.Product;
import com.example.inventory_h2_swagger.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(ProductCategory category);
}