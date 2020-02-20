package com.webapp.service;

import com.webapp.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private CrudRepository<Session, Integer> sessionRepository;

    @Override
    public int createUserSessionAndGetItsId(String userEmail) {
        return sessionRepository.save(new Session(userEmail)).getId();
    }

    @Override
    public Optional<Session> getSessionById(int id) {
        return sessionRepository.findById(id);
    }
}
