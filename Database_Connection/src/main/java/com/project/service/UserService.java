package com.project.service;

import com.project.models.User;
import com.project.repositories.UserRepository;
import com.project.repositories.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean createUser(String email, String password);
    List<User> selectAllUsers();
    Optional<User> selectUserByEmail(String email);
    UserRepository getUserRepository();
    void setUserRepository(UserRepository userRepository);
}
