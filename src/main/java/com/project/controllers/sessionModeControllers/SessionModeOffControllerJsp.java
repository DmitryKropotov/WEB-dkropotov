package com.project.controllers.sessionModeControllers;

import com.project.controllers.SessionController;
import com.project.controllers.SessionControllerImpl;
import com.project.models.User;
import com.project.models.UserChecker;
import com.project.services.UserService;
import com.project.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes("userchecker")
public class SessionModeOffControllerJsp implements SessionModeOffController {

    private static final SessionModeOffControllerJsp SESSION_MODE_OFF_CONTROLLER_JSP = new SessionModeOffControllerJsp();

    private SessionModeOffControllerJsp() {}

    private UserService userService = new UserServiceImpl();

    private SessionController sessionController = new SessionControllerImpl();

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String goToMainPage(Model model) {
        System.out.println("goToMainPage get");
        model.addAttribute("userchecker", new UserChecker());
        return "main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String goToMainPage(@Valid @ModelAttribute ("userchecker") UserChecker user, BindingResult result, Model model) {

        System.out.println("goToMainPage post" + user.getEmail() + " " + user.getPassword());
        System.out.println("Amount of errors is " + result.getErrorCount());
        List<ObjectError> errors = result.getAllErrors();
        errors.forEach(error -> System.out.println(error + " "));

        user.setPasswordsNotMatch("");
        user.setWrongEmailOrPassword("");
        user.setSuccess("");
        user.setUserAlreadyExists("");

        String email = user.getEmail();
        String password = user.getPassword();
        String passwordRepeater = user.getPasswordRepeater();
        if (result.hasErrors()) {
            user.setPasswordRepeater(null);
            return "main";
        } else if (passwordRepeater != null && !passwordRepeater.isEmpty() && !password.equals(passwordRepeater)) {
            user.setPasswordsNotMatch("Passwords don't match");
            user.setPasswordRepeater(null);
            return "main";
        } else if (passwordRepeater != null && !passwordRepeater.isEmpty()) {
            boolean registered = registerUser(new User(-200, email, password), model);//think how to implement it better
            System.out.println(registered);
            if (registered) {
                user.setSuccess("User is registered successfully");
            } else {
                user.setUserAlreadyExists("User with such email already exists");
            }
            user.setPasswordRepeater(null);
            return "main";
        } else {
            String message = loginUserAndGetSessionId(new User(-200, email, password), model);//think how to implement it better
            System.out.println(message);
            if (message.equals("main")) {
                user.setWrongEmailOrPassword("Wrong email or password");
            }
            user.setPasswordRepeater(null);
            return message;
        }
    }

    @Override
    public boolean registerUser(User user, Model model) {
        System.out.println("This is registerUser method " + user);
        return userService.registerUser(user.getEmail(), encryptPassword(user.getPassword()));//temporary implementation
    }

    @Override
    @RequestMapping(value = "/loginUser")
    public String loginUserAndGetSessionId(User user, Model model) {
        System.out.println("This is loginUserAndGetSessionId method " + user);
        Optional<Integer> result = userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
        if (!result.isPresent()) {
           return "main";
        }
        //SessionModeOnControllerJsp sessionModeOnController = new SessionModeOnControllerJsp();
        //Session session = sessionController.getSessionById(result.get());
        //SessionModeOnController sessionModeOnControllerProxy = (SessionModeOnController) SecurityProxy.newInstance(new SessionModeOnControllerJsp());
        //sessionModeOnControllerProxy.in
        return "redirect:sessionModeOn.html";
    }

}
