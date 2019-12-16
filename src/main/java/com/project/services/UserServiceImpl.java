package com.project.services;

import com.project.models.User;
import com.project.repositories.UserRepository;
import com.project.repositories.UserRepositoryImpl;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();
    private SessionService sessionService = new SessionServiceImpl();

    @Override
    public boolean registerUser(String email, String password) {
         return userRepository.createUser(email, password);
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(String email, String password) {
        User user = userRepository.selectUserByEmail(email).orElseGet(() -> null);
        boolean loginSucceed = user != null && validateLogin(password, user.getPassword());
        //String result = "SessionId ";
        if (loginSucceed) {
            return Optional.of(sessionService.createUserSessionAndGetItsId(user.getId()));
        } else {
            return Optional.empty();
        }
        //return result;
    }

    private boolean validateLogin(String enteredPassword, String userPassword) {
        return enteredPassword.equals(userPassword);
    }
}
