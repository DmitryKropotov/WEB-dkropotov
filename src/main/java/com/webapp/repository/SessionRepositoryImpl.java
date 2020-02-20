package com.webapp.repository;

import com.webapp.model.Product;
import com.webapp.model.Session;
import lombok.extern.java.Log;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
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
public class SessionRepositoryImpl implements CrudRepository<Session, Integer> {

    private SessionFactory sessionFactory;

    org.hibernate.Session session;

    @Autowired
    public SessionRepositoryImpl(SessionFactory sessionFactory) {
        log.info("MYYYYY LOG: constructor in SessionRepositoryImpl");
        this.sessionFactory = sessionFactory;
        org.hibernate.Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        this.session = session;
    }

    @Override
    public <S extends Session> S save(S userSession) {
        Transaction txn = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("INSERT INTO Session (userId) VALUES (:userId);");
        query.setParameter("userId", userSession.getUserEmail());
        int amountOfSavedSessions = 0;
        try {
            amountOfSavedSessions = query.executeUpdate();
        } catch (PersistenceException e) {
            log.warning("" + e);
        }
        log.info("MYYYYYY LOG: amount of saved Sessions are " + amountOfSavedSessions);
        txn.commit();
        return amountOfSavedSessions == 0 ? userSession: null;
    }

    @Override
    public <S extends Session> Iterable<S> saveAll(Iterable<S> userSessions) {
        NativeQuery query = session.createSQLQuery("INSERT INTO Product (id, userId) VALUES (:id, :userId);");
        List<S> updatedproducts = new ArrayList<S>();
        userSessions.forEach(userSession -> {
            Transaction txn = session.beginTransaction();
            query.setParameter("id", userSession.getId());
            query.setParameter("userId", userSession.getUserEmail());
            int amountOfUpdatedUserSessions = 0;
            try {
                amountOfUpdatedUserSessions = query.executeUpdate();
            } catch (PersistenceException e) {
                log.warning("" + e + " updatedSessions are " + amountOfUpdatedUserSessions);
            }
            if (amountOfUpdatedUserSessions != 0) {
                updatedproducts.add(userSession);
            }
            txn.commit();
        });
        return updatedproducts;
    }

    @Override
    public Optional<Session> findById(Integer id) {
        Session userSession = session.get(Session.class, id);
        if (userSession == null) {
            log.info("MYYYYY LOG: Product is not present");
            return Optional.empty();
        }
        log.info("Product is present");
        return Optional.of(userSession);
    }

    @Override
    public Iterable<Session> findAllById(Iterable<Integer> ids) {
        List<Session> updatedProducts = new ArrayList<>();
        ids.forEach(id -> {
            Session userSession = session.get(Session.class, id);
            if (userSession != null) {
                updatedProducts.add(userSession);
                log.info("MYYYYY LOG: Session is present");
            }
        });
        return updatedProducts;
    }

    @Override
    public List<Session> findAll() {
        Query query = session.createQuery("from Session", Product.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(Integer id) {
        Session userSession = session.get(Session.class, id);
        return userSession != null;
    }

    @Override
    public long count() {
        Query query = session.createQuery("from Session", Session.class);
        return query.getResultList().size();
    }

    @Override
    public void deleteById(Integer id) {
        session.remove(findById(id));
    }

    @Override
    public void delete(Session userSession) {
        session.remove(userSession);
    }

    @Override
    public void deleteAll(Iterable<? extends Session> userSessions) {
        userSessions.forEach(userSession -> {
            this.session.remove(userSession);
        });
    }

    @Override
    public void deleteAll() {
        List<Session> userSessions = findAll();
        deleteAll(userSessions);
    }
}
