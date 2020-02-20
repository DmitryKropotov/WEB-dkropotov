package com.webapp.service;

import com.webapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CrudRepository<User, String> userRepository;
    @Autowired
    private SessionService sessionService;

    @Override
    public boolean registerUser(String email, String password) {
         return userRepository.save(new User(email, password)) == null;
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
