package com.example.inventory_master.repository;

import com.example.inventory_master.model.Product;
import com.example.inventory_master.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(ProductCategory category);
}
