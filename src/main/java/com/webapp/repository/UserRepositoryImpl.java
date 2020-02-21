package com.webapp.repository;

import com.webapp.model.User;
import lombok.extern.java.Log;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Log
public class UserRepositoryImpl implements CrudRepository<User, String> {

    private SessionFactory sessionFactory;

    Session session;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        log.info("MYYYYY LOG: constructor in UserRepositoryImpl");
        this.sessionFactory = sessionFactory;
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        this.session = session;
    }

    @Override
    public <S extends User> S save(S user) {
        session.save(user);
        return user;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> users) {
        List<S> savedUsers = new ArrayList<S>();
        users.forEach(user -> {
            session.save(user);
            savedUsers.add(user);
        });
        return savedUsers;
    }

    @Override
    public Optional<User> findById(String id) {
        User user = session.get(User.class, id);
        if (user == null) {
            log.info("MYYYYY LOG: User is not present");
            return Optional.empty();
        }
        log.info("MYYYYY LOG: User is present");
        return Optional.of(user);
    }

    @Override
    public Iterable<User> findAllById(Iterable<String> ids) {
        List<User> updatedUsers = new ArrayList<>();
        ids.forEach(id -> {
            User user = session.get(User.class, id);
            if (user != null) {
                updatedUsers.add(user);
                log.info("MYYYYY LOG: User is present");
            }
        });
        return updatedUsers;
    }

    @Override
    public List<User> findAll() {
        Query query = session.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(String id) {
        User user = session.get(User.class, id);
        return user != null;
    }

    @Override
    public long count() {
        Query query = session.createQuery("from User", User.class);
        return query.getResultList().size();
    }

    @Override
    public void deleteById(String id) {
        session.remove(findById(id));
    }

    @Override
    public void delete(User user) {
        session.remove(user);
    }

    @Override
    public void deleteAll(Iterable<? extends User> users) {
        users.forEach(user -> {
            session.remove(user);
        });
    }

    @Override
    public void deleteAll() {
        List<User> users = findAll();
        deleteAll(users);
    }

}
