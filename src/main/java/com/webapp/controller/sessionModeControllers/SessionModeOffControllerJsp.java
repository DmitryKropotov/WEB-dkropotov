package com.webapp.controller.sessionModeControllers;

import com.webapp.model.UserChecker;
import com.webapp.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("sessionModeOffControllerJsp")
@Primary
@Log
public class SessionModeOffControllerJsp implements SessionModeOffController {

    @Autowired
    private UserService userService;

    @Override
    public boolean registerUser(UserChecker user) {
        //debug logs
        log.info("MYYYYYYYYY LOG: This is registerUser method in SessionModeOffControllerJsp" + user);
        //debug logs
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));
    }

    @Override
    public Optional<Integer> loginUserAndGetSessionId(UserChecker user) {
        //debug logs
        log.info("MYYYYYYYYY LOG: This is loginUserAndGetSessionId method in SessionModeOffControllerJsp" + user);
        //debug logs
        return userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
    }
}
