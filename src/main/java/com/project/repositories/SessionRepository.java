package com.project.repositories;

import com.project.models.Session;

public interface SessionRepository {

    int createUserSessionAndGetItsId(int userId);

    Session getSessionById(int id);
}
