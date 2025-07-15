package com.example.thunghiemcopilot;

public class Product {
    private long id;
    private String name;
    private String title;
    private String description;
    private String status;
    private double price;

    public Product(long id, String name, String title, String description, String status, double price) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.status = status;
        this.price = price;
    }

    public Product(String name, String title, String description, String status, double price) {
        this(-1, name, title, description, status, price);
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}


