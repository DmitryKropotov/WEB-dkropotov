package com.webapp.service;

import java.util.Optional;

public interface UserService {

    boolean registerUser(String email, String password);

    Optional<Integer> loginUserAndGetSessionId(String email, String password);
}
