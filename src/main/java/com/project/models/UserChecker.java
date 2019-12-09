package com.project.models;

public class UserChecker {
    private String email;
    private String password;
    private String passwordRepeater;

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
}
