package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;

public interface SessionModeOnController extends MainController {

    String showProductsInStore();

    String addItemToCardProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

    String displayCartContent();

    String removeItemFromCart(int id);

    String modifyCartItem(int id, int newAmount);

    boolean checkoutBooking();

    String finishSession();

}
