package com.webapp.service;

import com.webapp.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> getAllProductsAsList();

    String getAllProductsAsString();

    Map<String, Integer> getTitleAmountProductsAsMap();

    Map<String, Integer> getTitleIdProductsAsMap();

    List<Product> findProducts(Map<String, Object> columns);

    void updateProducts(List<Product> products);

}
