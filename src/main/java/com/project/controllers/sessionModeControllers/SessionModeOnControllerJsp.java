package com.project.controllers.sessionModeControllers;

import org.springframework.stereotype.Controller;

@Controller
public class SessionModeOnControllerJsp implements SessionModeOnController {
    //@RequestMapping(value = "/sessionModeOn")
    public String mainInSessionModeOn(/*@Valid @ModelAttribute("sessionModeOn") ProductRequest product, BindingResult result*/) {
        System.out.println("mainInSessionModeOn method ");
        return "sessionModeOnMainPage";
    }

    @Override
    public String showProductsInStore() {
        return null;
    }

    @Override
    public String addItemToCardProducts(int ASKED_ITEM_ID, int ASKED_QUANTITY) {
        return null;
    }

    @Override
    public String displayCartContent() {
        return null;
    }

    @Override
    public String removeItemFromCart(int id) {
        return null;
    }

    @Override
    public String modifyCartItem(int id, int newAmount) {
        return null;
    }

    @Override
    public boolean checkoutBooking() {
        return false;
    }

    @Override
    public void finishSession() {

    }
}
