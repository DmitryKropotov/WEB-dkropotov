package com.project.models;

import org.hibernate.validator.constraints.NotEmpty;

public class UserChecker {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordRepeater;

    private String successfulRegMessage;
    private String passwordError;
    private String wrongEmailOrPassword;
    private String userAlreadyExists;

    private ProductRequest productRequest;

    public UserChecker() {}

    public UserChecker(String email, String password) {
        this.email = email;
        this.password = password;
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
