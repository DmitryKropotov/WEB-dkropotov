package com.database_connection.service;

import com.database_connection.model.User;
import com.database_connection.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public boolean createUser(String email, String password) {
        log.info("createUser method in service");
        return userRepository.createUser(email, password);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
