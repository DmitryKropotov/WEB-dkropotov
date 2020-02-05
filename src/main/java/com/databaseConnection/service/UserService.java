package com.databaseConnection.service;

import com.databaseConnection.model.User;
import com.databaseConnection.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean createUser(String email, String password);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    UserRepository getUserRepository();
}
