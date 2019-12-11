package com.project.services;

public interface UserService {

    boolean registerUser(String email, String password);

    String loginUserAndGetSessionId(String email, String password);
}
