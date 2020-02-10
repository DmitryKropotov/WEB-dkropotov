package com.database_connection.repository;

import com.database_connection.model.User;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
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
        Transaction txn = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("INSERT INTO User (email, password) VALUES (:email, :password);");
        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());
        int amountOfUpdatedUsers = 0;
        try {
            amountOfUpdatedUsers = query.executeUpdate();
        } catch (PersistenceException e) {
            log.warning("" + e);
        }
        log.info("amount of updatedUsers are " + amountOfUpdatedUsers);
        txn.commit();
        return amountOfUpdatedUsers == 0 ? user: null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> users) {
        NativeQuery query = session.createSQLQuery("INSERT INTO User (email, password) VALUES (:email, :password);");
        List<S> updatedUsers = new ArrayList<S>();
        users.forEach(user -> {
            Transaction txn = session.beginTransaction();
            query.setParameter("email", user.getEmail());
            query.setParameter("password", user.getPassword());
            int amountOfUpdatedUsers = 0;
            try {
                amountOfUpdatedUsers = query.executeUpdate();
            } catch (PersistenceException e) {
                log.warning("" + e + " updatedUsers are " + amountOfUpdatedUsers);
            }
            if (amountOfUpdatedUsers != 0) {
                updatedUsers.add(user);
            }
            txn.commit();
        });
        return updatedUsers;
    }

    @Override
    public Optional<User> findById(String id) {
        User user = session.load(User.class, id);
        if (user == null) {
            log.info("MYYYYY LOG: User is not present");
            return Optional.empty();
        }
        log.info("User is present");
        return Optional.of(user);
    }

    @Override
    public Iterable<User> findAllById(Iterable<String> ids) {
        List<User> updatedUsers = new ArrayList<>();
        ids.forEach(id -> {
           User user = session.load(User.class, id);
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
        User user = session.load(User.class, id);
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
