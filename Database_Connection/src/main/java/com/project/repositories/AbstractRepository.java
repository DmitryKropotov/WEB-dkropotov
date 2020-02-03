package com.project.repositories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Repository
public abstract class AbstractRepository<T> {

    private final Class persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    public AbstractRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        Type type = this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        System.out.println("sessionFactory in constructor with argument is " + sessionFactory);
    }

    public AbstractRepository() {
        Type type = this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        System.out.println("sessionFactory in no args constructor is " + sessionFactory);
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected Session getSession() {
        System.out.println("sessionFactory in getSession() is " + sessionFactory);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
