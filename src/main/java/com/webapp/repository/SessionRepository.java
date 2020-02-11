package com.webapp.repository;

import com.webapp.model.Session;

public interface SessionRepository {

    int createUserSessionAndGetItsId(int userId);

    Session getSessionById(int id);
}
