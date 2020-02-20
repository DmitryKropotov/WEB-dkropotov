package com.webapp.repository;

import com.webapp.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findProducts(Map<String, Object> columns);

    int updateProducts(Map<String, Object> columns, Map<String, Object> conditions);
}