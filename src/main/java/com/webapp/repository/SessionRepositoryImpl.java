package com.webapp.repository;

import com.webapp.model.Product;
import com.webapp.model.Session;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        boolean saved = true;
        try {
            Transaction txn = session.beginTransaction();
            session.save(userSession);
            txn.commit();
        } catch (PersistenceException e) {
            log.warning("MYYYYY LOG: " + e);
            saved = false;
        }
        return saved ? userSession: null;
    }

    @Override
    public <S extends Session> Iterable<S> saveAll(Iterable<S> userSessions) {
        List<S> savedSessions = new ArrayList<S>();
        userSessions.forEach(userSession -> {
            boolean saved = true;
            try {
                Transaction txn = session.beginTransaction();
                session.save(userSession);
                txn.commit();
            } catch (PersistenceException e) {
                log.warning("MYYYYY LOG: " + e);
                saved = false;
            }
            if (saved) {
                savedSessions.add(userSession);
            }
        });
        return savedSessions;
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
