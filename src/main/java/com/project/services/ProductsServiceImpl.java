package com.project.services;

import com.project.models.Product;
import com.project.repositories.ProductsRepository;
import com.project.repositories.ProductsRepositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsServiceImpl implements ProductsService {

    private ProductsRepository productsRepository = ProductsRepositoryImpl.getProductsRepository();

    @Override
    public List<Product> selectAllProducts() {
        return productsRepository.selectProducts(new HashMap<>());
    }

    @Override
    public List<Product> selectProducts(Map<String, Object> conditions) {
        return productsRepository.selectProducts(conditions);
    }

    @Override
    public int updateProducts(Map<String, Object> columns, Map<String, Object> conditions) {
        return productsRepository.updateProducts(columns, conditions);
    }
}
