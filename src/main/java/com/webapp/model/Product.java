package com.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private int id;
    private String title;
    private int available;
    private double price;

    @Override
    public String toString() {
        return "id = " + id + ", title = " + title + ", available = " + available + ", price = " + price;
    }
}
