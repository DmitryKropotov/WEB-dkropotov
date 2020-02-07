package com.webapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
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

    public UserChecker(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
