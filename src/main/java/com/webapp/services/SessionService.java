package com.webapp.services;

import com.webapp.models.Session;

public interface SessionService {

    int createUserSessionAndGetItsId(int userId);

    Session getSessionById(int id);

}
