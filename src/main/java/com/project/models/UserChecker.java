package com.project.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class UserChecker {
    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String password;
    private String passwordRepeater;

    private String success;
    private String passwordsNotMatch;
    private String wrongEmailOrPassword;
    private String userAlreadyExists;

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getPasswordsNotMatch() {
        return passwordsNotMatch;
    }

    public void setPasswordsNotMatch(String passwordsNotMatch) {
        this.passwordsNotMatch = passwordsNotMatch;
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
}
