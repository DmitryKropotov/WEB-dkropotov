package com.project.controllers.sessionModeControllers;

import org.springframework.web.bind.annotation.ModelAttribute;

public class sessionModeOffControllerJsp implements SessionModeOffController {
    @Override
    public String registerUser(@ModelAttribute("register") String email, String password) {
        return null;
    }

    @Override
    public String loginUserAndGetSessionId(@ModelAttribute ("login") String email, String password) {
        return null;
    }
}
