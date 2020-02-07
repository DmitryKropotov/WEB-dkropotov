package com.webapp.repositories;

import com.webapp.models.User;

import java.util.Optional;

public interface UserRepository {

    boolean createUser(String email, String password);

    Optional<User> selectUserByEmail(String email);
}
