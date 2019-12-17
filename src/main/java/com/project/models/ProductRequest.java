package com.project.models;

import org.hibernate.validator.constraints.Range;

import java.util.Map;

public class ProductRequest {
    private String availableProducts;
    private Map<String, Integer> titleAmountProducts;
    private Map<String, Integer> titleIdProducts;

    //variables for logic
    private String title;
    @Range(min = 1)
    private Integer amount;
    private boolean displayContent;
    private String itemToRemove;
    private String itemToModify;
    @Range(min = 1)
    private Integer newAmount;
    private boolean checkoutBooking;
    private boolean logOut;

    //variables to show on page
    private String answerForGoodRespond;
    private String cartContent;
    private String removedSuccessfully;
    private String modificationResult;
    private String checkOutResult;

    public ProductRequest() {}

    public ProductRequest(String availableProducts, Map<String, Integer> titleAmountProducts, Map<String, Integer> titleIdProducts) {
        this.availableProducts = availableProducts;
        this.titleAmountProducts = titleAmountProducts;
        this.titleIdProducts = titleIdProducts;
    }

    public String getAvailableProducts() { return availableProducts; }

    public void setAvailableProducts(String availableProducts) { this.availableProducts = availableProducts; }

    public Map<String, Integer> getTitleAmountProducts() { return titleAmountProducts; }

    public void setTitleAmountProducts(Map<String, Integer> titleAmountProducts) { this.titleAmountProducts = titleAmountProducts; }

    public Map<String, Integer> getTitleIdProducts() { return titleIdProducts; }

    public void setTitleIdProducts(Map<String, Integer> titleIdProducts) { this.titleIdProducts = titleIdProducts; }


    public boolean isDisplayContent() {
        return displayContent;
    }

    public void setDisplayContent(boolean displayContent) {
        this.displayContent = displayContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getItemToRemove() {
        return itemToRemove;
    }

    public void setItemToRemove(String itemToRemove) {
        this.itemToRemove = itemToRemove;
    }

    public String getAnswerForGoodRespond() {
        return answerForGoodRespond;
    }

    public void setAnswerForGoodRespond(String answerForGoodRespond) {
        this.answerForGoodRespond = answerForGoodRespond;
    }

    public String getCartContent() {
        return cartContent;
    }

    public void setCartContent(String cartContent) {
        this.cartContent = cartContent;
    }

    public String getRemovedSuccessfully() {
        return removedSuccessfully;
    }

    public void setRemovedSuccessfully(String removedSuccessfully) {
        this.removedSuccessfully = removedSuccessfully;
    }

    public String getModificationResult() {
        return modificationResult;
    }

    public void setModificationResult(String modificationResult) {
        this.modificationResult = modificationResult;
    }

    public String getCheckOutResult() {
        return checkOutResult;
    }

    public void setCheckOutResult(String checkOutResult) {
        this.checkOutResult = checkOutResult;
    }

    public String getItemToModify() {
        return itemToModify;
    }

    public void setItemToModify(String itemToModify) {
        this.itemToModify = itemToModify;
    }

    public Integer getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(Integer newAmount) {
        this.newAmount = newAmount;
    }

    public boolean isCheckoutBooking() {
        return checkoutBooking;
    }

    public void setCheckoutBooking(boolean checkoutBooking) {
        this.checkoutBooking = checkoutBooking;
    }

    public boolean isLogOut() {
        return logOut;
    }

    public void setLogOut(boolean logOut) {
        this.logOut = logOut;
    }
}
