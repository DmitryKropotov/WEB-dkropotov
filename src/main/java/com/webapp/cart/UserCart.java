package com.webapp.cart;

import com.webapp.controllers.sessionModeControllers.enums.ModifyCartItemsResults;

public interface UserCart {

    String addItemToCardProducts (final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

    String displayCartContent();

    boolean removeItemFromCartById(int id);

    ModifyCartItemsResults modifyCartItem(int id, int newAmount);

    boolean checkValidityProducts();

    void clearCartProducts();

    void returnGoodsToStore();
}
