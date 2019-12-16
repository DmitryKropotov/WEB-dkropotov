package com.project.controllers;

import com.project.models.Session;

public interface SessionController {
    int createUserSessionAndGetItsId(int userId);

    Session getSessionById(int id);
}
