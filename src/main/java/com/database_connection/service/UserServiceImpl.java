package com.database_connection.service;

import com.database_connection.model.User;
import com.database_connection.repository.UserRepositoryImpl;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Override
    public UserRepositoryImpl getUserRepository() {
        return userRepository;
    }

    @Override
    public boolean saveUser(String email, String password) {
        log.info("MYYYYY LOG: saveUser method in service");
        return userRepository.save(new User(email, password)) != null;
    }

    @Override
    public List<User> findAll() {
        log.info("MYYYYY LOG: findAll method in service");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        log.info("MYYYYY LOG: findById method in service");
        return userRepository.findById(id);
    }
}
