package com.project.controllers.sessionModeControllers;

import com.project.controllers.SessionController;
import com.project.controllers.SessionControllerImpl;
import com.project.models.ProductRequest;
import com.project.models.User;
import com.project.models.UserChecker;
import com.project.services.UserService;
import com.project.services.UserServiceImpl;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@SessionAttributes("userchecker")
public class SessionModeOffControllerJsp implements SessionModeOffController {

    private static final SessionModeOffControllerJsp SESSION_MODE_OFF_CONTROLLER_JSP = new SessionModeOffControllerJsp();

    private SessionModeOffControllerJsp() {}

    private UserService userService = new UserServiceImpl();

    private SessionController sessionController = new SessionControllerImpl();

    private SessionModeOnControllerJsp sessionModeOnController = null;

    private static int goToMainPageCall = 0;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String goToMainPage(Model model) {
        System.out.println("goToMainPage get");
        model.addAttribute("userchecker", new UserChecker());
        return "main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String goToMainPage(@Valid @ModelAttribute ("userchecker") UserChecker user, BindingResult result, Model model) {

        System.out.println("goToMainPage post " + user.getEmail() + " " + user.getPassword() + " " + user.getProductRequest() + " " + ++goToMainPageCall);
        System.out.println("Amount of errors is " + result.getErrorCount());
        if (sessionModeOnController != null) {
            String resultSessionOn = sessionModeOnController.mainInSessionModeOn(user.getProductRequest(), getAnnonymousBindingResult(), model);
            if (resultSessionOn.equals("main")) {
                sessionModeOnController = null;
                user.setProductRequest(null);
            }
            return resultSessionOn;
        }
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
            String message = loginUserAndGetSessionId(/*new User(-200, email, password)*/user, model);//think how to implement it better
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
    public String loginUserAndGetSessionId(UserChecker user, Model model) {
        System.out.println("This is loginUserAndGetSessionId method " + user);
        Optional<Integer> result = userService.loginUserAndGetSessionId(user.getEmail(), encryptPassword(user.getPassword()));
        if (!result.isPresent()) {
           return "main";
        }
        user.setProductRequest(new ProductRequest());
        this.sessionModeOnController = new SessionModeOnControllerJsp();
        String resultSessionOn = sessionModeOnController.mainInSessionModeOn(user.getProductRequest(), getAnnonymousBindingResult(), model);
        if (resultSessionOn.equals("main")) {
            sessionModeOnController = null;
            user.setProductRequest(null);
        }
        return resultSessionOn;
    }

    private BindingResult getAnnonymousBindingResult() {
        return new BindingResult() {
            @Override
            public Object getTarget() {
                return null;
            }

            @Override
            public Map<String, Object> getModel() {
                return null;
            }

            @Override
            public Object getRawFieldValue(String s) {
                return null;
            }

            @Override
            public PropertyEditor findEditor(String s, Class<?> aClass) {
                return null;
            }

            @Override
            public PropertyEditorRegistry getPropertyEditorRegistry() {
                return null;
            }

            @Override
            public void addError(ObjectError objectError) {

            }

            @Override
            public String[] resolveMessageCodes(String s) {
                return new String[0];
            }

            @Override
            public String[] resolveMessageCodes(String s, String s1) {
                return new String[0];
            }

            @Override
            public void recordSuppressedField(String s) {

            }

            @Override
            public String[] getSuppressedFields() {
                return new String[0];
            }

            @Override
            public String getObjectName() {
                return null;
            }

            @Override
            public void setNestedPath(String s) {

            }

            @Override
            public String getNestedPath() {
                return null;
            }

            @Override
            public void pushNestedPath(String s) {

            }

            @Override
            public void popNestedPath() throws IllegalStateException {

            }

            @Override
            public void reject(String s) {

            }

            @Override
            public void reject(String s, String s1) {

            }

            @Override
            public void reject(String s, Object[] objects, String s1) {

            }

            @Override
            public void rejectValue(String s, String s1) {

            }

            @Override
            public void rejectValue(String s, String s1, String s2) {

            }

            @Override
            public void rejectValue(String s, String s1, Object[] objects, String s2) {

            }

            @Override
            public void addAllErrors(Errors errors) {

            }

            @Override
            public boolean hasErrors() {
                return false;
            }

            @Override
            public int getErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getAllErrors() {
                return null;
            }

            @Override
            public boolean hasGlobalErrors() {
                return false;
            }

            @Override
            public int getGlobalErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getGlobalErrors() {
                return null;
            }

            @Override
            public ObjectError getGlobalError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors() {
                return false;
            }

            @Override
            public int getFieldErrorCount() {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors() {
                return null;
            }

            @Override
            public FieldError getFieldError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors(String s) {
                return false;
            }

            @Override
            public int getFieldErrorCount(String s) {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors(String s) {
                return null;
            }

            @Override
            public FieldError getFieldError(String s) {
                return null;
            }

            @Override
            public Object getFieldValue(String s) {
                return null;
            }

            @Override
            public Class<?> getFieldType(String s) {
                return null;
            }
        };
    }

}
