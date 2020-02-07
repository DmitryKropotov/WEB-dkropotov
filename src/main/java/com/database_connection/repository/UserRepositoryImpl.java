package com.database_connection.repository;

import com.database_connection.model.User;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
@Log
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    public UserRepositoryImpl() {}

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean createUser(String email, String password) {
        Session session = getSession();
        Transaction txn = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("INSERT INTO User (email, password) VALUES (:email, :password);");
        query.setParameter("email", email);
        query.setParameter("password", password);
        Optional<Integer> executeResult = Optional.empty();
        try {
            executeResult = Optional.of(query.executeUpdate());
        } catch (PersistenceException e) {
            log.warning("" + e);
            return false;
        }
        txn.commit();
        return executeResult.isPresent();
    }

    @Override
    public List<User> findAll() {
        Query query = getSession().createQuery("from User", User.class);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = getSession();
        Optional<User> user = Optional.of(session.load(User.class, email));
        if(user.isPresent()) {
            System.out.println("User is present");
        }
        try {
            log.info("User is " + user);//Artem, we need to discuss it
        } catch (HibernateException e) {
             log.warning("hibernateException from load method " + e);
            System.out.println("In catch before return");
            return Optional.empty();
        }

        System.out.println("After catch");
        return user;
    }
}
