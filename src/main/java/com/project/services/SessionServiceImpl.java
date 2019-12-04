package com.project.services;

import com.project.repositories.SessionRepository;
import com.project.repositories.SessionRepositoryImpl;

public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository = new SessionRepositoryImpl();

    @Override
    public int createUserSessionAndGetItsId(int userId) {
        return sessionRepository.createUserSessionAndGetItsId(userId);
    }
}
