package com.project.repositories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Repository
//@PropertySource(value = { "classpath:app.properties" })
public abstract class AbstractDAO<T> {

    private final Class persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    public AbstractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        Type type = this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        System.out.println("sessionFactory in constructor with argument is " + sessionFactory);
    }

    //@SuppressWarnings("unchecked")
    public AbstractDAO() {
        Type type = this.getClass().getGenericSuperclass();
        //Type[] types = ((ParameterizedType) type).getActualTypeArguments();
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
