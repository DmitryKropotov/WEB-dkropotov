package com.project.services;

import com.project.models.User;
import com.project.repositories.UserRepository;
import com.project.repositories.UserRepositoryImpl;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();
    private SessionService sessionService = new SessionServiceImpl();

    public String registerUser(String email, String password) {
         return userRepository.createUser(email, password);
    }

    public String loginUserAndGetSessionId(String email, String password) {
        User user = userRepository.selectUserByEmail(email).orElseGet(() -> {return null;});
        boolean loginSucceed = user != null && validateLogin(password, user.getPassword());
        String result = "SessionId ";
        if (loginSucceed) {
            result += sessionService.createUserSessionAndGetItsId(user.getId());
        } else {
            result += "null";
        }
        return result;
    }

    private boolean validateLogin(String enteredPassword, String userPassword) {
        return enteredPassword.equals(userPassword);
    }
}
