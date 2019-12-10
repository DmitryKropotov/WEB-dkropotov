package com.project.controllers.sessionModeControllers;

import com.project.models.User;
import com.project.services.UserService;
import com.project.services.UserServiceImpl;

public class SessionModeOffControllerConsole implements SessionModeOffController {

    private static final SessionModeOffController SESSION_MODE_OFF_CONSOLE = new SessionModeOffControllerConsole();

    private SessionModeOffControllerConsole() {}

    private UserService userService = new UserServiceImpl();

    //private ProductsService productsService = new ProductsServiceImpl();

    public static SessionModeOffController getInstance() {
        return SESSION_MODE_OFF_CONSOLE;
    }

    @Override
    public String registerUser(User user) {
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));
    }

    @Override
    public String loginUserAndGetSessionId(User user) {
        return userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
    }
}
