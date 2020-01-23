package com.project.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseInitializer {

    private static Connection conn = ConnectionSaver.getConnection();

    private ProductsRepository productsRepository;

    public DatabaseInitializer(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    //initialize database
    public void initializeDatabase() {
        if (productsRepository.selectProducts(new HashMap<>()).isEmpty()) {
            List<Map<String, Object>> productList = new ArrayList<>();
            Map<String, Object> product1 = new HashMap<>(), product2 = new HashMap<>(),
                    product3 = new HashMap<>(), product4 = new HashMap<>();
            product1.put("title", "shoes");
            product1.put("available", 10);
            product1.put("price", 20);
            productList.add(product1);
            product2.put("title", "jacket");
            product2.put("available", 6);
            product2.put("price", 50);
            productList.add(product2);
            product3.put("title", "trousers");
            product3.put("available", 100);
            product3.put("price", 60);
            productList.add(product3);
            product4.put("title", "celt");
            product4.put("available", 70);
            product4.put("price", 30);
            productList.add(product4);


            productList.forEach(product -> {
                String sql = "insert into Products (title, available, price) values ('" + product.get("title") + "', " + product.get("available") +
                        ", " + product.get("price") + ");";
                Statement stmt = null;
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
