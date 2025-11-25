package com.example.inventory_h2_swagger.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products") // you can name it "product" too
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private double price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    // ✅ Default Constructor
    public Product() {
    }

    // ✅ All-Args Constructor
    public Product(Long id, String name, ProductCategory category, double price, int quantity, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    // ✅ Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    // ✅ Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    // ✅ Optional — for logging and debugging
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
