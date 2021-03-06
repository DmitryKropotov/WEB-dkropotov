package com.project.models;

public class Product {
    private int id;
    private String title;
    private int available;
    private double price;

    public Product(int id, String title, int available, double price) {
        this.id = id;
        this.title = title;
        this.available = available;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "id = " + id + ", title = " + title + ", available = " + available + ", price = " + price;
    }
}
