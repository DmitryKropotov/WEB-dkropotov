package com.project.controllers.sessionModeControllers;

import com.project.models.UserChecker;
import com.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller("sessionModeOffControllerConsole")
public class SessionModeOffControllerConsole implements SessionModeOffController {

    @Autowired
    private UserService userService;

    @Override
    public boolean registerUser(UserChecker user) {
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(UserChecker user) {
        return userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
    }
}
