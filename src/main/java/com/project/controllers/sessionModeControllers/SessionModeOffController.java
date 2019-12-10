package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.models.User;

public interface SessionModeOffController extends MainController {

    String registerUser(User user);

    String loginUserAndGetSessionId(User user);

    default String encryptPassword(String password) {
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

    //boolean sessionOffControl();

}
