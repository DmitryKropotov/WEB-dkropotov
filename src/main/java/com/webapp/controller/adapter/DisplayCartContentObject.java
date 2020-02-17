package com.webapp.controller.adapter;

import com.webapp.model.ProductForCart;
import lombok.Getter;

import java.util.List;

@Getter
public class DisplayCartContentObject {
    private List<ProductForCart> productForCartList;
    private double priceForAllProducts;

    public DisplayCartContentObject(List<ProductForCart> productForCartList) {
        double fullPrice = 0;
        for (ProductForCart productForCart : productForCartList) {
            fullPrice += productForCart.getFullPrice();
        }
        this.priceForAllProducts = fullPrice;
        this.productForCartList = productForCartList;
    }
}
