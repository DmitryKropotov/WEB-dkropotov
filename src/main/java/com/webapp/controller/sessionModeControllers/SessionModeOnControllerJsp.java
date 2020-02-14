package com.webapp.controller.sessionModeControllers;

import com.webapp.cart.UserCart;
import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.model.ProductForCart;
import com.webapp.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller("sessionModeOnControllerJsp")
@Primary
@Scope("prototype")
public class SessionModeOnControllerJsp implements SessionModeOnController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    UserCart userCart;

    @Override
    public String getAllProductsAsString() {
        return productsService.getAllProductsAsString();
    }

    @Override
    public List<ProductForCart> getProductsInCart() {
        return userCart.getProductCart();
    }

    @Override
    public Map<String, Integer> getTitleIdProductsAsMap() {
        return productsService.getTitleIdProductsAsMap();
    }

    @Override
    public String addItemToCartProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY) {
        return userCart.addItemToCardProducts(ASKED_ITEM_ID, ASKED_QUANTITY);
    }

    @Override
    public String displayCartContent() {
        return userCart.displayCartContent();
    }

    @Override
    public boolean removeItemFromCartById(int id) {
        return userCart.removeItemFromCartById(id);
    }

    @Override
    public ModifyCartItemsResults modifyCartItem(int id, int newAmount) {
        return userCart.modifyCartItem(id, newAmount);
    }

    @Override
    public boolean checkoutBooking() {
        boolean cartIsValid = userCart.checkValidityProducts();
        if (cartIsValid) {
            userCart.clearCartProducts();
        }
        return cartIsValid;
    }

    @Override
    public String finishSession() {
        userCart.returnGoodsToStore();
        return "main";
    }
}
