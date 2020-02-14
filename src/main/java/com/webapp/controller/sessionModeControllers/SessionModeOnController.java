package com.webapp.controller.sessionModeControllers;

import com.webapp.controller.MainController;
import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.model.ProductForCart;

import java.util.List;
import java.util.Map;

public interface SessionModeOnController extends MainController {

    //user operation
    String getAllProductsAsString();

    //additional operation
    List<ProductForCart> getProductsInCart();

    //additional operation
    Map<String, Integer> getTitleIdProductsAsMap();

    //user operation
    String addItemToCartProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

    //user operation
    String displayCartContent();

    //user operation
    boolean removeItemFromCartById(int id);

    //user operation
    ModifyCartItemsResults modifyCartItem(int id, int newAmount);

    //user operation
    boolean checkoutBooking();

    //additional user operation
    String finishSession();

}
