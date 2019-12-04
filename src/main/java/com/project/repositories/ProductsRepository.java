package com.project.repositories;

import com.project.models.Product;

import java.util.List;
import java.util.Map;

public interface ProductsRepository {

    List<Product> selectProducts(Map<String, Object> conditions);

    int updateProducts(Map<String, Object> columns, Map<String, Object> conditions);

    int insertProducts(Map<String, Object> columns);
}
