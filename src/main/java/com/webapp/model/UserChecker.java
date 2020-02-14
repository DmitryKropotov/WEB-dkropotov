package com.webapp.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserChecker {
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
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
