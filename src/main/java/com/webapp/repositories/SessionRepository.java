package com.webapp.repositories;

import com.webapp.models.Session;

public interface SessionRepository {

    int createUserSessionAndGetItsId(int userId);

    Session getSessionById(int id);
}
