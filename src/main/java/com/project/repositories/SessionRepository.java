package com.project.repositories;

public interface SessionRepository {

    int createUserSessionAndGetItsId(int userId);
}
