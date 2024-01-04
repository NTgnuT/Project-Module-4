package com.ra.model.entity;

import org.springframework.web.multipart.MultipartFile;

public class Product {
    private int productId;
    private String productName;
    private String image;
    private Category category;
    private String description;
    private double price;
    private int stock;
    private boolean status;

    public Product() {
    }

    public Product(int productId, String productName, String image, Category category, String description, double price, int stock, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.category = category;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
