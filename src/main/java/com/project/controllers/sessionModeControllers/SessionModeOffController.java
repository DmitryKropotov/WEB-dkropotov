package com.project.controllers.sessionModeControllers;

import com.project.controllers.MainController;

public interface SessionModeOffController extends MainController {

    String registerUser(String email, String password);

    String loginUserAndGetSessionId(String email, String password);

    //boolean sessionOffControl();

}
