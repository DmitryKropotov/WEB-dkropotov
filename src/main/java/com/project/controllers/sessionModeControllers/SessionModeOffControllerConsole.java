package com.project.controllers.sessionModeControllers;

import com.project.services.UserService;
import com.project.services.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class SessionModeOffControllerConsole implements SessionModeOffController {

    private static final SessionModeOffController SESSION_MODE_OFF_CONSOLE = new SessionModeOffControllerConsole();

    private SessionModeOffControllerConsole() {}

    private UserService userService = new UserServiceImpl();

    //private ProductsService productsService = new ProductsServiceImpl();

    private List<String> allowedFormats = new ArrayList();

    {
        allowedFormats.add("register user [a-zA-Z0-9]+@[a-z]+.[a-z]+ [a-zA-Z0-9]+");
        allowedFormats.add("login user [a-zA-Z0-9]+@[a-z]+.[a-z]+ [a-zA-Z0-9]+");
    }

    public static SessionModeOffController getInstance() {
        return SESSION_MODE_OFF_CONSOLE;
    }

    @Override
    public String registerUser(String email, String password) {
        return userService.registerUser(email, encryptPassword(password));
    }

    @Override
    public String loginUserAndGetSessionId(String email, String password) {
        return userService.loginUserAndGetSessionId(email, encryptPassword(password));
    }

    /*@Override
    public boolean sessionOffControl() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please print command in format 'register user [email] [password]'," +
                "'login user [email] [password]' or 'exit' to exit application. " +
                "Password can contain lower and upper cases latin letters and numbers");

        String inputString = scanner.nextLine();
        if (inputString.equals("exit")) {
            ConnectionSaver.closeConnection();
            System.exit(0);
        }
        int commandNumber = -1;
        for (int i = 0; i < allowedFormats.size(); i++) {
            if (inputString.matches(allowedFormats.get(i))) {
                commandNumber = i;
                break;
            }
        }
        boolean sessionModeOn = false;
        List<String> strings = Arrays.asList(inputString.split(" "));
        String respond;
        switch(commandNumber) {
            case 0:
                respond = userService.registerUser(strings.get(2), encryptPassword(strings.get(3)));
                break;
            case 1:
                respond = userService.loginUserAndGetSessionId(strings.get(2), encryptPassword(strings.get(3)));
                if (!respond.contains("null")) {
                    sessionModeOn = true;
                }
                break;
            default:
                respond = "Wrong format";
                break;
        }
        System.out.println(respond);
        return sessionModeOn;
    }*/

    private String encryptPassword(String password) {
        if (password.isEmpty()) {
            return "";
        }
        char[] pass = password.toCharArray();
        String result = "";
        for (int i = 0; i < pass.length; i++) {
            result += pass[i] + "rgh";
        }
        return result;
    }
}
