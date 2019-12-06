package com.project.controllers.sessionModeControllers;

import com.project.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class sessionModeOffControllerJsp implements SessionModeOffController{

    @Override
    @RequestMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user) {
        System.out.println("This is registerUser method " + user);
        return null;
    }

    @Override
    public String loginUserAndGetSessionId(@ModelAttribute("user2") User user) {
        System.out.println("This is loginUserAndGetSessionId method " + user);
        return null;
    }
}
