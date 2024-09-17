package com.webapp.model;

public class UserChecker {
//    @NonNull
    private String email;
//    @NonNull
    private String password;
//    @NonNull
    private String passwordRepeater;

    private String successfulRegMessage;
    private String passwordError;
    private String wrongEmailOrPassword;
    private String userAlreadyExists;

    private ProductRequest productRequest;

    public UserChecker(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserChecker(String email, String password, String passwordRepeater) {
        this.email = email;
        this.password = password;
        this.passwordRepeater = passwordRepeater;
    }

    public UserChecker(String email, String password, String passwordRepeater, String successfulRegMessage, String passwordError, String wrongEmailOrPassword, String userAlreadyExists, ProductRequest productRequest) {
        this.email = email;
        this.password = password;
        this.passwordRepeater = passwordRepeater;
        this.successfulRegMessage = successfulRegMessage;
        this.passwordError = passwordError;
        this.wrongEmailOrPassword = wrongEmailOrPassword;
        this.userAlreadyExists = userAlreadyExists;
        this.productRequest = productRequest;
    }

    public UserChecker() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeater() {
        return passwordRepeater;
    }

    public void setPasswordRepeater(String passwordRepeater) {
        this.passwordRepeater = passwordRepeater;
    }

    public String getSuccessfulRegMessage() {
        return successfulRegMessage;
    }

    public void setSuccessfulRegMessage(String successfulRegMessage) {
        this.successfulRegMessage = successfulRegMessage;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getWrongEmailOrPassword() {
        return wrongEmailOrPassword;
    }

    public void setWrongEmailOrPassword(String wrongEmailOrPassword) {
        this.wrongEmailOrPassword = wrongEmailOrPassword;
    }

    public String getUserAlreadyExists() {
        return userAlreadyExists;
    }

    public void setUserAlreadyExists(String userAlreadyExists) {
        this.userAlreadyExists = userAlreadyExists;
    }

    public ProductRequest getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }
}
