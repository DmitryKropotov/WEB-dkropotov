package com.project.service;

import com.project.models.User;
import com.project.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean createUser(String email, String password);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    UserRepository getUserRepository();
    void setUserRepository(UserRepository userRepository);
}
