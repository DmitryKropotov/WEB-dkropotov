package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;
import com.project.models.User;

public interface SessionModeOffController extends MainController {

    String registerUser(User user);

    String loginUserAndGetSessionId(User user);

    //boolean sessionOffControl();

}
