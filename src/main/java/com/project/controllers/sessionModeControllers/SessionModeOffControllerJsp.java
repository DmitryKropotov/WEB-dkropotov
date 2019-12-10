package com.project.controllers.sessionModeControllers;

import com.project.models.User;
import com.project.models.UserChecker;
import com.project.services.UserService;
import com.project.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class SessionModeOffControllerJsp implements SessionModeOffController {

    private static final SessionModeOffControllerJsp SESSION_MODE_OFF_CONSOLE = new SessionModeOffControllerJsp();

    private SessionModeOffControllerJsp() {}

    private UserService userService = new UserServiceImpl();

    @RequestMapping(value = "/main")
    public String goToMainPage(@Valid @ModelAttribute ("mainPage") UserChecker user, BindingResult result) {

        System.out.println("goToMainPage " + user.getEmail() + " " + user.getPassword());

        String email = user.getEmail();
        String password = user.getPassword();
        String passwordRepeater = user.getPasswordRepeater();
        if (email == null || email.isEmpty() || password == null || password.isEmpty() ||
                (passwordRepeater !=null && !passwordRepeater.isEmpty() && !password.equals(passwordRepeater))) {
            return "main";
        } else if (passwordRepeater == null || passwordRepeater.isEmpty()) {
            String message = loginUserAndGetSessionId(new User(-200, email, password));//think how to implement it better
            System.out.println(message);
            return message;
        } else {
            String message = registerUser(new User(-200, email, password));//think how to implement it better
            System.out.println(message);
            return message;
        }
    }

    @Override
    public String registerUser(User user) {
        System.out.println("This is registerUser method " + user);
        if (!checkEmail(user.getEmail())) {//temporary check
            return "wrongEmailPage";
        }
        String result = userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));//temporary implementation
        if (result.contains("200")) {
            return "successRegisterPage";
        }
        return "userAlreadyExists";
    }

    @Override
    @RequestMapping(value = "/loginUser")
    public String loginUserAndGetSessionId(User user) {
        System.out.println("This is loginUserAndGetSessionId method " + user);
        String result = userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
        if (result.contains("null")) {
            return "wrongEmailOrPassword";
        }
        SessionModeOnControllerJsp sessionModeOnController = new SessionModeOnControllerJsp();
        return sessionModeOnController.mainInSessionModeOn();
        //return "redirect:sessionModeOn.html";
    }

    private boolean checkEmail(String email) {
        return email.matches("[a-zA-Z0-9]+@[a-z]+.[a-z]+");
    }

}
