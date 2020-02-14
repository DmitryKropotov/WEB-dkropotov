package com.webapp.cart;

import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.model.ProductForCart;

import java.util.List;

public interface UserCart {

    List<ProductForCart> getProductCart();

    String addItemToCardProducts (final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

    String displayCartContent();

    boolean removeItemFromCartById(int id);

    ModifyCartItemsResults modifyCartItem(int id, int newAmount);

    boolean checkValidityProducts();

    void clearCartProducts();

    void returnGoodsToStore();
}
