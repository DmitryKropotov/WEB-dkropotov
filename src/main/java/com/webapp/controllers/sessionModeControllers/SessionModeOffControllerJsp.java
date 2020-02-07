package com.webapp.controllers.sessionModeControllers;

import com.webapp.models.UserChecker;
import com.webapp.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller("sessionModeOffControllerJsp")
@Log
public class SessionModeOffControllerJsp implements SessionModeOffController {

    @Autowired
    private UserService userService;

    @Override
    public boolean registerUser(UserChecker user) {
        //debug logs
        log.info("MYYYYYYYYY LOG: This is registerUser method " + user);
        //debug logs
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(UserChecker user) {
        //debug logs
        log.info("MYYYYYYYYY LOG: This is loginUserAndGetSessionId method " + user);
        //debug logs
        return userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
    }
}
