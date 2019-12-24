package com.project.services;

import com.project.models.Product;
import com.project.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ProductsService")
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Product> getAllProductsAsList() {
        return productsRepository.selectProducts(new HashMap<>());
    }

    @Override
    public String getAllProductsAsString() {
        List<Product> products = productsRepository.selectProducts(new HashMap<>());
        String result = "";
        for (Product product : products) {
            result = result + product.toString() + " ";
        }
        return result;
    }

    @Override
    public Map<String, Integer> getTitleAmountProductsAsMap(){
        List<Product> products = productsRepository.selectProducts(new HashMap<>());
        Map<String, Integer> result = new HashMap<>();
        for (Product product : products) {
            result.put(product.getTitle(), product.getAvailable());
        }
        return result;
    }

    @Override
    public Map<String, Integer> getTitleIdProductsAsMap() {
        List<Product> products = productsRepository.selectProducts(new HashMap<>());
        Map<String, Integer> result = new HashMap<>();
        for (Product product : products) {
            result.put(product.getTitle(), product.getId());
        }
        return result;
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
