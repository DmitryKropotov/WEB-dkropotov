package com.webapp.controllers.sessionModeControllers;

import com.webapp.controllers.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.services.ProductsServiceWithUserCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller("sessionModeOnControllerJsp")
@Scope("prototype")
public class SessionModeOnControllerJsp implements SessionModeOnController {

    @Autowired
    private ProductsServiceWithUserCart productsService;

    @Override
    public String getAllProductsAsString() {
        return productsService.getAllProductsAsString();
    }

    @Override
    public Map<String, Integer> getTitleIdProductsAsMap() {
        return productsService.getTitleIdProductsAsMap();
    }

    @Override
    public String addItemToCardProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY) {
        return productsService.addItemToCardProducts(ASKED_ITEM_ID, ASKED_QUANTITY);
    }

    @Override
    public String displayCartContent() {
        return productsService.displayCartContent();
    }

    @Override
    public boolean removeItemFromCartById(int id) {
        return productsService.removeItemFromCartById(id);
    }

    @Override
    public ModifyCartItemsResults modifyCartItem(int id, int newAmount) {
        return productsService.modifyCartItem(id, newAmount);
    }

    @Override
    public boolean checkoutBooking() {
        boolean cartIsValid = productsService.checkValidityProducts();
        if (cartIsValid) {
            productsService.clearCartProducts();
        }
        return cartIsValid;
    }

    @Override
    public String finishSession() {
        productsService.returnGoodsToStore();
        return "main";
    }
}
