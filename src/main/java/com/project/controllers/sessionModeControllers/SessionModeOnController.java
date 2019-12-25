package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.controllers.sessionModeControllers.enums.ModifyCartItemsResults;

import java.util.Map;

public interface SessionModeOnController extends MainController {

    //user operation
    String getAllProductsAsString();

    //additional operation, think what to do with it
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
