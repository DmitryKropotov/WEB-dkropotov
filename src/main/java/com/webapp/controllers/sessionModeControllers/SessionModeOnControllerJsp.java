package com.webapp.controllers.sessionModeControllers;

import com.webapp.controllers.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.service.ProductsServiceWithUserCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String addItemToCardProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY) {
        return productsService.addItemToCardProducts(ASKED_ITEM_ID, ASKED_QUANTITY);
    }

    @Override
    @RequestMapping(value = "/displayCartContent", method = RequestMethod.GET)
    public String displayCartContent() {
        return productsService.displayCartContent();
    }

    @Override
    @RequestMapping(value = "/removeItem", method = RequestMethod.GET)
    public boolean removeItemFromCartById(int id) {
        return productsService.removeItemFromCartById(id);
    }

    @Override
    @RequestMapping(value = "/modifyItem", method = RequestMethod.GET)
    public ModifyCartItemsResults modifyCartItem(int id, int newAmount) {
        return productsService.modifyCartItem(id, newAmount);
    }

    @Override
    @RequestMapping(value = "/checkoutBooking", method = RequestMethod.GET)
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
