package com.webapp.services;

import com.webapp.models.User;
import com.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionService sessionService;

    @Override
    public boolean registerUser(String email, String password) {
         return userRepository.createUser(email, password);
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(String email, String password) {
        User user = userRepository.selectUserByEmail(email).orElseGet(() -> null);
        boolean loginSucceed = user != null && validateLogin(password, user.getPassword());
        if (loginSucceed) {
            return Optional.of(sessionService.createUserSessionAndGetItsId(user.getId()));
        } else {
            return Optional.empty();
        }
    }

    private boolean validateLogin(String enteredPassword, String userPassword) {
        return enteredPassword.equals(userPassword);
    }
}
