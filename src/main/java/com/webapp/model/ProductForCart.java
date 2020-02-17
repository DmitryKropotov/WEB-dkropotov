package com.webapp.model;

import lombok.*;

@Data
@AllArgsConstructor
public class ProductForCart {
    private int id;
    private String title;
    private int quantityInCart;
    private double priceForOneItem;
    private double fullPrice;

    public ProductForCart(int id, String title, int quantityInCart, double priceForOneItem) {
        this.id = id;
        this.title = title;
        this.quantityInCart = quantityInCart;
        this.priceForOneItem = priceForOneItem;
        this.fullPrice = quantityInCart*priceForOneItem;
    }

    public void setQuantityInCart(int quantityInCart) {
        this.fullPrice = quantityInCart * priceForOneItem;
        this.quantityInCart = quantityInCart;
    }

    public void setPriceForOneItem(double priceForOneItem) {
        this.fullPrice = quantityInCart * priceForOneItem;
        this.priceForOneItem = priceForOneItem;
    }
}
