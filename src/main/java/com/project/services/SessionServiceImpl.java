package com.project.services;

import com.project.models.Session;
import com.project.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public int createUserSessionAndGetItsId(int userId) {
        return sessionRepository.createUserSessionAndGetItsId(userId);
    }

    @Override
    public Session getSessionById(int id) {
        return sessionRepository.getSessionById(id);
    }
}
