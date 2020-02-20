package com.webapp.repository;

import com.webapp.model.Product;
import lombok.extern.java.Log;
import org.hibernate.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
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
        Transaction txn = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("INSERT INTO Product (id, title, available, price) VALUES (:id, :title, :available, :price);");
        query.setParameter("id", product.getId());
        query.setParameter("title", product.getTitle());
        query.setParameter("available", product.getAvailable());
        query.setParameter("price", product.getPrice());
        int amountOfUpdatedProducts = 0;
        try {
            amountOfUpdatedProducts = query.executeUpdate();
        } catch (PersistenceException e) {
            log.warning("MYYYYY LOG: " + e);
        }
        log.info("MYYYYY LOG: amount of updatedProducts are " + amountOfUpdatedProducts);
        txn.commit();
        return amountOfUpdatedProducts == 0 ? product: null;
    }

    @Override
    public <S extends Product> Iterable<S> saveAll(Iterable<S> products) {
        NativeQuery query = session.createSQLQuery("INSERT INTO Product (id, title, available, price) VALUES (:id, :title, :available, :price);");
        List<S> updatedproducts = new ArrayList<S>();
        products.forEach(product -> {
            Transaction txn = session.beginTransaction();
            query.setParameter("id", product.getId());
            query.setParameter("title", product.getTitle());
            query.setParameter("available", product.getAvailable());
            query.setParameter("price", product.getPrice());
            int amountOfUpdatedProducts = 0;
            try {
                amountOfUpdatedProducts = query.executeUpdate();
            } catch (PersistenceException e) {
                log.warning("MYYYYY LOG: " + e + " updatedProducts are " + amountOfUpdatedProducts);
            }
            if (amountOfUpdatedProducts != 0) {
                updatedproducts.add(product);
            }
            txn.commit();
        });
        return updatedproducts;
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
        if (columns.size() == 0) {
            return findAll();
        }
        String sql = "select * from Products";
        sql += " where ";
        String[] conditionsFields = (String[]) columns.keySet().toArray();
        for (int i = 0; i < conditionsFields.length; i++) {
            String field = conditionsFields[i];
            sql += field + "=" + columns.get(field);
            if (i != conditionsFields.length - 1) {
                sql += " and ";
            }
        }
        sql += ";";
        Query query = session.createQuery(sql, Product.class);
        return query.getResultList();
    }

    @Override
    public int updateProducts(Map<String, Object> columns, Map<String, Object> conditions) {
        if (columns.isEmpty()) {
            return 0;
        }

        String sql = "UPDATE Product (";
        String[] updateFields = (String[])columns.keySet().toArray();
        for (int i = 0; i < updateFields.length; i++) {
            sql += updateFields[i];
            if (i < updateFields.length - 1) {
                sql += ", ";
            }
        }
        sql += ") VALUES (";
        for (int i = 0; i < updateFields.length; i++) {
            Object value = columns.get(updateFields[i]);
            sql += ":" + value;
            if (i < updateFields.length - 1) {
                sql += ", ";
            }
        }
        sql += ")";

        if (!conditions.isEmpty()) {
            sql += " where (";
            String[] fieldsForSearching = (String[])conditions.keySet().toArray();
            for (int i = 0; i < fieldsForSearching.length; i++) {
                sql += ", " + fieldsForSearching[i];
                if (i < fieldsForSearching.length - 1) {
                    sql += ", ";
                }
            }
            sql += ") VALUES (";
            for (int i = 0; i < fieldsForSearching.length; i++) {
                Object value = conditions.get(fieldsForSearching[i]);
                sql += ":" + value;
                if (i < updateFields.length - 1) {
                    sql += ", ";
                }
            }
        }

        sql += ");";

        int amountOfModifiedRows = 0;
        NativeQuery query = session.createSQLQuery(sql);
        try {
            amountOfModifiedRows = query.executeUpdate();
        } catch (PersistenceException e) {
            log.warning("MYYYYY LOG: " + e + " updatedProducts are " + amountOfModifiedRows);
        }
        return amountOfModifiedRows;
    }
}
