package com.webapp.repository;

import com.webapp.model.Product;
import lombok.extern.java.Log;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository("ProductRepositoryImpl")
@Log
public class ProductRepositoryImpl implements ProductRepository {

    private SessionFactory sessionFactory;

    Session session;

    @Autowired
    public ProductRepositoryImpl(SessionFactory sessionFactory) {
        log.info("MYYYYY LOG: constructor in ProductRepositoryImpl");
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
    public <S extends Product> S save(S product) {
        session.save(product);
        return  product;
    }

    @Override
    public <S extends Product> Iterable<S> saveAll(Iterable<S> products) {
        List<S> savedProducts = new ArrayList<S>();
        products.forEach(product -> {
            session.save(product);
            savedProducts.add(product);
        });
        return savedProducts;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        Product product = session.get(Product.class, id);
        if (product == null) {
            log.info("MYYYYY LOG: Product is not present");
            return Optional.empty();
        }
        log.info("MYYYYY LOG: Product is present");
        return Optional.of(product);
    }

    @Override
    public Iterable<Product> findAllById(Iterable<Integer> ids) {
        List<Product> updatedProducts = new ArrayList<>();
        ids.forEach(id -> {
            Product product = session.get(Product.class, id);
            if (product != null) {
                updatedProducts.add(product);
                log.info("MYYYYY LOG: Product is present");
            }
        });
        return updatedProducts;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> findAll() {
        Query query = session.createQuery("from Product", Product.class);
        return query.getResultList();
    }

    @Override
    public boolean existsById(Integer id) {
        Product product = session.get(Product.class, id);
        return product != null;
    }

    @Override
    public long count() {
        Query query = session.createQuery("from Product", Product.class);
        return query.getResultList().size();
    }

    @Override
    public void deleteById(Integer id) {
        session.remove(findById(id));
    }

    @Override
    public void delete(Product product) {
        session.remove(product);
    }

    @Override
    public void deleteAll(Iterable<? extends Product> products) {
        products.forEach(product -> {
            session.remove(product);
        });
    }

    @Override
    public void deleteAll() {
        List<Product> products = findAll();
        deleteAll(products);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> findProducts(Map<String, Object> columns) {
        log.info("MYYYYY LOG: findProducts method in class ProductRepositoryImpl");
        if (columns.size() == 0) {
            return findAll();
        }
        String sql = "from Product";
        sql += " where ";
        Object[] conditionsFieldsAsObjects = columns.keySet().toArray();
        String[] conditionsFields = new String[columns.size()];
        for (int i = 0; i < conditionsFieldsAsObjects.length; i++) {
            conditionsFields[i] = (String) conditionsFieldsAsObjects[i];
        }
        for (int i = 0; i < conditionsFields.length; i++) {
            String field = conditionsFields[i];
            sql += field + "=" + columns.get(field);
            if (i != conditionsFields.length - 1) {
                sql += " and ";
            }
        }
        Query query = session.createQuery(sql, Product.class);
        return query.getResultList();
    }

    @Override
    public void updateProducts(List<Product> productsToUpdate) {
        log.info("MYYYYY LOG: productsToUpdate in ProductRepositoryImpl");
        productsToUpdate.forEach(product -> {
            log.info("MYYYYY LOG: " + product + " is going to be updated");
            session.update(product);
            log.info("MYYYYY LOG: " + product + " has been updated");
        });
    }
}
