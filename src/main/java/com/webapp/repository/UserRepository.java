package com.webapp.repository;

import com.webapp.model.User;

import java.util.Optional;

public interface UserRepository {

    boolean createUser(String email, String password);

    Optional<User> selectUserByEmail(String email);
}
