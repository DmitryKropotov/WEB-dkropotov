package com.project.services;

import com.project.models.Session;

public interface SessionService {

    int createUserSessionAndGetItsId(int userId);

    Session getSessionById(int id);

}
