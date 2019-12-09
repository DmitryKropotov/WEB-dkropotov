package com.project.controllers.sessionModeControllers;

import com.project.models.User;
import com.project.models.UserChecker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SessionModeOffControllerJsp implements SessionModeOffController {

    @RequestMapping(value = "/main")
    public String goToMainPage(@ModelAttribute ("mainPage") UserChecker user) {

        System.out.println("main " + user.getEmail() + " " + user.getPassword());

        String email = user.getEmail();
        String password = user.getPassword();
        String passwordRepeater = user.getPasswordRepeater();
        if (email == null || email.isEmpty() || password == null || password.isEmpty() ||
                (passwordRepeater !=null && !passwordRepeater.isEmpty() && !password.equals(passwordRepeater))) {
            return "main";
        } else if (passwordRepeater == null || passwordRepeater.isEmpty()) {
            //return "loginPage";
            return loginUserAndGetSessionId(new User(-200, email, password));
        } else {
            //return "registerPage";
            return registerUser(new User(-200, email, password));
        }
    }

    @Override
    @RequestMapping(value = "/registerUser")
    public String registerUser(@ModelAttribute ("user") User user) {
        System.out.println("This is registerUser method " + user);
        return "registerPage";
    }

    @Override
    @RequestMapping(value = "/loginUser")
    public String loginUserAndGetSessionId(@ModelAttribute ("user2") User user) {
        System.out.println("This is loginUserAndGetSessionId method " + user);
        return "loginPage";
    }

}
