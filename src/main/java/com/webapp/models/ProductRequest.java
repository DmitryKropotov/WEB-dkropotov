package com.webapp.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProductRequest {
    private String availableProducts;
    private Map<String, Integer> titleAmountProducts;
    private Map<String, Integer> titleIdProducts;

    //variables for logic
    private String title;
    private Integer amount;
    private boolean displayContent;
    private String itemToRemove;
    private String itemToModify;
    private Integer newAmount;
    private boolean checkoutBooking;
    private boolean logOut;

    //variables to show on page
    private String answerForGoodRespond;
    private String cartContent;
    private String removedSuccessfully;
    private String modificationResult;
    private String checkOutResult;

    public ProductRequest(String availableProducts) {
        this.availableProducts = availableProducts;
    }
}
