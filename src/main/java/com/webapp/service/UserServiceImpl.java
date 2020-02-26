package com.webapp.service;

import com.webapp.model.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {

    @Autowired
    private CrudRepository<User, String> userRepository;
    @Autowired
    private SessionService sessionService;

    @Override
    public boolean registerUser(String email, String password) {
        log.info("MYYYYYYYYY LOG: This is registerUser method in UserServiceImpl email is " + email + " password is " + password);
        User result = userRepository.save(new User(email, password));
        log.info("MYYYYYYYYY LOG: This is registerUser method in UserServiceImpl. Result is " + result);
        return result != null;
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(String email, String password) {
        User user = userRepository.findById(email).orElseGet(() -> null);
        boolean loginSucceed = user != null && validateLogin(password, user.getPassword());
        if (loginSucceed) {
            return Optional.of(sessionService.createUserSessionAndGetItsId(user.getEmail()));
        } else {
            return Optional.empty();
        }
    }

    private boolean validateLogin(String enteredPassword, String userPassword) {
        return enteredPassword.equals(userPassword);
    }
}
