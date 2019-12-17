package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.models.User;
import com.project.models.UserChecker;
import org.springframework.ui.Model;

public interface SessionModeOffController extends MainController {

    boolean registerUser(User user, Model model);

    //string, but not int is returned because we need to redirect to another page
    String loginUserAndGetSessionId(UserChecker user, Model model);

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
