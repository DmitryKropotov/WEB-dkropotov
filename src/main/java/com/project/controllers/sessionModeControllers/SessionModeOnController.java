package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.controllers.sessionModeControllers.enums.ModifyCartItemsResults;
import com.project.models.Product;

import java.util.List;
import java.util.Map;

public interface SessionModeOnController extends MainController {

    List<Product> showProductsInStore();

    String getAllProductsAsString();

    Map<String, Integer> getTitleAmountProductsAsMap();

    Map<String, Integer> getTitleIdProductsAsMap();

    String addItemToCardProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

    String displayCartContent();

    boolean removeItemFromCart(int id);

    ModifyCartItemsResults modifyCartItem(int id, int newAmount);

    boolean checkoutBooking();

    String finishSession();

}
