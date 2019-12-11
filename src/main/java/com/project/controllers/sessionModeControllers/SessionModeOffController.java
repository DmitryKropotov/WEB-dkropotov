package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.models.User;
import org.springframework.ui.Model;

public interface SessionModeOffController extends MainController {

    boolean registerUser(User user, Model model);

    String loginUserAndGetSessionId(User user, Model model);

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

}
