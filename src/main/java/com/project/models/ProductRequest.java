package com.project.models;

import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

public class ProductRequest {
    List<Product> products = new ArrayList<>();
    private String title;
    @Range(min = 1, max = 120)
    private Integer amount;

    private boolean logOut = false;

    public ProductRequest() {}

    public ProductRequest(String title, Integer amount) {
        this.title = title;
        this.amount = amount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> product) {
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isLogOut() {
        return logOut;
    }

    public void setLogOut(boolean logOut) {
        this.logOut = logOut;
    }
}
