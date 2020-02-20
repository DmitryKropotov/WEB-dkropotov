package com.webapp.repository;

import com.webapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseInitializer {

    @Autowired
    private ProductRepository productRepository;

    public DatabaseInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //initialize database
    @SuppressWarnings("unchecked")
    public void initializeDatabase() {
        List<Product> products = (List)productRepository.findAll();
        if (products.isEmpty()) {
            List<Map<String, Object>> productList = new ArrayList<>();
            productRepository.save(new Product(1,"shoes", 10, 20));
            productRepository.save(new Product(2,"jacket", 6, 50));
            productRepository.save(new Product(3,"trousers", 100, 60));
            productRepository.save(new Product(4,"celt", 70, 30));
        }
    }
}
