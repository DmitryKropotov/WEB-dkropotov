package com.database_connection.repository;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Repository
@Log
public abstract class AbstractRepository<T> {

    private final Class persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    public AbstractRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        Type type = this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        log.info("MYYYYY LOG: sessionFactory in constructor with argument is " + sessionFactory);
    }

    public AbstractRepository() {
        Type type = this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        log.info("MYYYYY LOG: sessionFactory in no args constructor is " + sessionFactory);
    }

    protected Session getSession() {
        log.info("MYYYYY LOG: sessionFactory in getSession() is " + sessionFactory);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
