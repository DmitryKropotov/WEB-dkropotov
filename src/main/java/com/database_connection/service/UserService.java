package com.database_connection.service;

import com.database_connection.model.User;
import com.database_connection.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean createUser(String email, String password);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    UserRepository getUserRepository();
}
