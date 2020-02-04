package com.databaseConnection.service;

import com.databaseConnection.models.User;
import com.databaseConnection.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean createUser(String email, String password);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    UserRepository getUserRepository();
    void setUserRepository(UserRepository userRepository);
}
