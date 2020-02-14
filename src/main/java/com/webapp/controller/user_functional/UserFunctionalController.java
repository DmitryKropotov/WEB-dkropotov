package com.webapp.controller.user_functional;

import com.webapp.controller.adapter.ModifyCartReturnedObject;
import com.webapp.model.Product;
import com.webapp.model.ProductForCart;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserFunctionalController {

    ResponseEntity<Boolean> registerUser(String email, String password, String repeatingPassword);

    String loginUserAndGetSessionId(String email, String password);

    List<Product> getAllProductsFromStore();

    String addItemToCartProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

    List<ProductForCart> displayCartContent();

    boolean removeItemFromCartById(int id);

    ModifyCartReturnedObject modifyCartItem(int id, int newAmount);

    boolean checkoutBooking();

    boolean finishSession(HttpServletRequest request, HttpServletResponse response);
}
