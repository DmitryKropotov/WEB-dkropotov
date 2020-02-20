package com.webapp.controller.user_functional;

import com.webapp.controller.adapter.DisplayCartContentObject;
import com.webapp.model.Product;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserFunctionalController {

    ResponseEntity registerUser(String email, String password, String repeatingPassword);

    String loginUserAndGetSessionId(String email, String password);

    List<Product> getAllProductsFromStore();

    ResponseEntity<String> addItemToCartProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY);

    DisplayCartContentObject displayCartContent();

    boolean removeItemFromCartById(int id);

    ResponseEntity modifyCartItem(int id, int newAmount);

    boolean checkoutBooking();

    boolean finishSession(HttpServletRequest request, HttpServletResponse response);
}
