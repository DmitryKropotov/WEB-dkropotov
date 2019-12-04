package com.project.repositories;

import com.project.models.User;

import java.util.Optional;

public interface UserRepository {

    String createUser(String email, String password);

    Optional<User> selectUserByEmail(String email);
}
