package com.project.services;

public interface UserService {

    String registerUser(String email, String password);

    String loginUserAndGetSessionId(String email, String password);
}
