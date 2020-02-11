package com.webapp.service;

import com.webapp.model.Session;

public interface SessionService {

    int createUserSessionAndGetItsId(int userId);

    Session getSessionById(int id);

}
