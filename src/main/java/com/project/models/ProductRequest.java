package com.project.models;

import org.hibernate.validator.constraints.Range;

public class ProductRequest {
    private String title;
    @Range(min = 1, max = 120)
    private Integer amount;

    public ProductRequest() {}

    public ProductRequest(String title, Integer amount) {
        this.title = title;
        this.amount = amount;
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
}
