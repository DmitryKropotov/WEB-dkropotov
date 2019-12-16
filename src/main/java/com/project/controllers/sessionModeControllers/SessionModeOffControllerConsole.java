package com.project.controllers.sessionModeControllers;

import com.project.models.User;
import com.project.services.UserService;
import com.project.services.UserServiceImpl;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Map;

public class SessionModeOffControllerConsole implements SessionModeOffController {

    private static final SessionModeOffController SESSION_MODE_OFF_CONSOLE = new SessionModeOffControllerConsole();

    private SessionModeOffControllerConsole() {}

    private UserService userService = new UserServiceImpl();

    //private ProductsService productsService = new ProductsServiceImpl();

    public static SessionModeOffController getInstance() {
        return SESSION_MODE_OFF_CONSOLE;
    }

    @Override
    public boolean registerUser(User user, Model model) {
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));
    }

    public boolean registerUser(User user) {
        return this.registerUser(user, new Model() {
            @Override
            public Model addAttribute(String s, Object o) {
                return null;
            }

            @Override
            public Model addAttribute(Object o) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> collection) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public boolean containsAttribute(String s) {
                return false;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        });
    }

    @Override
    public String loginUserAndGetSessionId(User user, Model model) {
        return userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword())).toString();
    }

    public String loginUserAndGetSessionId(User user) {
        return this.loginUserAndGetSessionId(user, new Model() {
            @Override
            public Model addAttribute(String s, Object o) {
                return null;
            }

            @Override
            public Model addAttribute(Object o) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> collection) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public boolean containsAttribute(String s) {
                return false;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        });
    }
}
