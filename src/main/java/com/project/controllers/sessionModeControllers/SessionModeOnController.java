package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.controllers.sessionModeControllers.enums.ConditionsToChoose;
import com.project.controllers.sessionModeControllers.enums.ModifyCartItemsResults;
import com.project.models.Product;

import java.util.List;
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

    //additional operation
    List<Product> selectProducts(Map<String, Object> conditions, ConditionsToChoose...signs);

    //additional user operation
    String finishSession();

}
