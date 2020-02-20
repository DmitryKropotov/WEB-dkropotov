package com.webapp.service;

import com.webapp.model.Product;
import com.webapp.repository.ProductRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
@Log
public class ProductServiceImpl implements ProductService {

    private List<Product> cartProducts = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProductsAsList() {
        return (List<Product>)productRepository.findAll();
    }

    @Override
    public String getAllProductsAsString() {
        Iterable<Product> products = productRepository.findAll();
        String result = "";
        for (Product product : products) {
            result = result + product.toString() + " ";
        }
        return result;
    }

    @Override
    public Map<String, Integer> getTitleAmountProductsAsMap(){
        Iterable<Product> products = productRepository.findAll();
        Map<String, Integer> result = new HashMap<>();
        for (Product product : products) {
            result.put(product.getTitle(), product.getAvailable());
        }
        return result;
    }

    @Override
    public Map<String, Integer> getTitleIdProductsAsMap() {
        Iterable<Product> products = productRepository.findAll();
        Map<String, Integer> result = new HashMap<>();
        for (Product product : products) {
            result.put(product.getTitle(), product.getId());
        }
        return result;
    }

    @Override
    public List<Product> findProducts(Map<String, Object> columns) {
        return productRepository.findProducts(columns);
    }

    @Override
    public int updateProducts(Map<String, Object> columns, Map<String, Object> conditions) {
        return productRepository.updateProducts(columns, conditions);
    }

}
