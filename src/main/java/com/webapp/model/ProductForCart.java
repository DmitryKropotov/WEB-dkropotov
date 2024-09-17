package com.webapp.model;


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

    public ProductForCart(int id, String title, int quantityInCart, double priceForOneItem, double fullPrice) {
        this.id = id;
        this.title = title;
        this.quantityInCart = quantityInCart;
        this.priceForOneItem = priceForOneItem;
        this.fullPrice = fullPrice;
    }

    public ProductForCart() {
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

    public int getQuantityInCart() {
        return quantityInCart;
    }

    public double getPriceForOneItem() {
        return priceForOneItem;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }
}
