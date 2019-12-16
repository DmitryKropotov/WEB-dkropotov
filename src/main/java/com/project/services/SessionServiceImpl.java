package com.project.services;

import com.project.models.Session;
import com.project.repositories.SessionRepository;
import com.project.repositories.SessionRepositoryImpl;

public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository = new SessionRepositoryImpl();

    @Override
    public int createUserSessionAndGetItsId(int userId) {
        return sessionRepository.createUserSessionAndGetItsId(userId);
    }

    @Override
    public Session getSessionById(int id) {
        return sessionRepository.getSessionById(id);
    }
}
