package com.webapp.controllers.sessionModeControllers;

import com.webapp.controllers.MainController;
import com.webapp.model.UserChecker;

import java.util.Optional;

public interface SessionModeOffController extends MainController {

    boolean registerUser(UserChecker user);

    Optional<Integer> loginUserAndGetSessionId(UserChecker user);

    default String encryptPassword(String password) {
        if (password.isEmpty()) {
            return "";
        }
        char[] pass = password.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : pass) {
            result.append(c).append("rgh");
        }
        return result.toString();
    }

}
