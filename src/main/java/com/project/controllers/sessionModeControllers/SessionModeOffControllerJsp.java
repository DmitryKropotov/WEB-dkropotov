package com.project.controllers.sessionModeControllers;

import com.project.models.UserChecker;
import com.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.Optional;

@Controller("sessionModeOffControllerJsp")
public class SessionModeOffControllerJsp implements SessionModeOffController {

    @Autowired
    private UserService userService;

    @Override
    public boolean registerUser(UserChecker user) {
        //debug logs
        System.out.println("This is registerUser method " + user);
        //debug logs
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(UserChecker user) {
        //debug logs
        System.out.println("This is loginUserAndGetSessionId method " + user);
        //debug logs
        return userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
    }
}
