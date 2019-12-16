package com.project.controllers;

import com.project.models.Session;
import com.project.services.SessionService;
import com.project.services.SessionServiceImpl;

public class SessionControllerImpl implements SessionController {
    private SessionService sessionService = new SessionServiceImpl();


    @Override
    public int createUserSessionAndGetItsId(int userId) {
        return sessionService.createUserSessionAndGetItsId(userId);
    }

    @Override
    public Session getSessionById(int id) {
        return sessionService.getSessionById(id);
    }
}
