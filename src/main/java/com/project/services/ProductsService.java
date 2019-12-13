package com.project.services;

import com.project.models.Product;

import java.util.List;
import java.util.Map;

public interface ProductsService {

    List<Product> getAllProductsAsList();

    String getAllProductsAsString();

    Map<String, Integer> getTitleAmountProductsAsMap();

    Map<String, Integer> getTitleIdProductsAsMap();

    List<Product> selectProducts(Map<String, Object> conditions);

    int updateProducts(Map<String, Object> columns, Map<String, Object> conditions);

}
