package com.webapp.service;

import com.webapp.model.Session;

import java.util.Optional;

public interface SessionService {

    int createUserSessionAndGetItsId(String userEmail);

    Optional<Session> getSessionById(int id);

}
