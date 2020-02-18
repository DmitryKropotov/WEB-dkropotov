package com.webapp.model;

import lombok.*;

@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String title;
    private int available;
    private double price;
}