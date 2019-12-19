package com.project.controllers.sessionModeControllers;

import com.project.models.UserChecker;
import com.project.services.UserService;
import com.project.services.UserServiceImpl;

import java.util.Optional;

public class SessionModeOffControllerConsole implements SessionModeOffController {

    private static final SessionModeOffController SESSION_MODE_OFF_CONSOLE = new SessionModeOffControllerConsole();

    private SessionModeOffControllerConsole() {}

    private UserService userService = new UserServiceImpl();

    public static SessionModeOffController getInstance() {
        return SESSION_MODE_OFF_CONSOLE;
    }

    @Override
    public boolean registerUser(UserChecker user) {
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(UserChecker user) {
        return userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
    }
}
