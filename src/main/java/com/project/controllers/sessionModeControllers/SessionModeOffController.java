package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.models.User;
import com.project.models.UserChecker;
import org.springframework.ui.Model;

import java.util.Optional;

public interface SessionModeOffController extends MainController {

    boolean registerUser(User user, Model model);

    Optional<Integer> loginUserAndGetSessionId(UserChecker user);

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
