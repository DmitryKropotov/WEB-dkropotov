package com.webapp.model;

import lombok.*;

@Data
@AllArgsConstructor
public class ProductForCart {
    private int id;
    private String title;
    private int quantityInCart;
    private double price;
}
