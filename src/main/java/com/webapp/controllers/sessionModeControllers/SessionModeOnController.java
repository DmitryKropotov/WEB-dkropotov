package com.webapp.controllers.sessionModeControllers;

import com.webapp.controllers.MainController;
import com.webapp.controllers.sessionModeControllers.enums.ModifyCartItemsResults;

import java.util.Map;

public interface SessionModeOnController extends MainController {

    //user operation
    String getAllProductsAsString();

    //additional operation
    Map<String, Integer> getTitleIdProductsAsMap();

    //user operation
    String addItemToCardProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

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
