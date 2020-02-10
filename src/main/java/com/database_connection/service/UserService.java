package com.database_connection.service;

import com.database_connection.model.User;
import com.database_connection.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean saveUser(String email, String password);
    List<User> findAll();
    Optional<User> findById(String id);
    UserRepositoryImpl getUserRepository();
}
