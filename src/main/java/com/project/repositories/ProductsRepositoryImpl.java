package com.project.repositories;

import com.project.models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductsRepositoryImpl implements ProductsRepository {

    private static ProductsRepository productsRepository = null;

    public static ProductsRepository getProductsRepository() {
        if (productsRepository == null) {
            productsRepository = new ProductsRepositoryImpl();
        }
        return productsRepository;
    }

    private Connection conn = ConnectionSaver.getConnection();

    //initialize database
    {
        if (selectProducts(new HashMap<>()).isEmpty()) {
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

    @Override
    public List<Product> selectProducts(Map<String, Object> conditions) {
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "select * from Products";
        if (!conditions.isEmpty()) {
            sql += " where ";
            Set<String> conditionsFields = conditions.keySet();
            for (String field : conditionsFields) {
                sql += field + "=" + conditions.get(field) + " and ";
            }
            sql = sql.substring(0, sql.length() - 5);
        }
        sql += ";";
        List <Product> result = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(new Product(rs.getInt("id"), rs.getString("title"),
                        rs.getInt("available"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public int updateProducts(Map<String, Object> columns, Map<String, Object> conditions) {
        if (columns.isEmpty()) {
            return 0;
        }

        Statement stmt = null;

        String sql = "update Products set ";
        Set<String> updateFields = columns.keySet();
        for (String field : updateFields) {
            sql += field + " = " + columns.get(field) + ", ";
        }
        sql = sql.substring(0, sql.length() - 2);

        if (!conditions.isEmpty()) {
            sql += " where ";
            Set<String> conditionsFields = conditions.keySet();
            for (String field : conditionsFields) {
                sql += field + "=" + conditions.get(field) + " and ";
            }
            sql = sql.substring(0, sql.length() - 5);
        }

        sql += ";";
        int amountOfModifiedRows = 0;
        try {
            stmt = conn.createStatement();
            amountOfModifiedRows = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return amountOfModifiedRows;
    }

    @Override
    public int insertProducts(Map<String, Object> columns) {
        if (columns.isEmpty()) {
            return 0;
        }

        Statement stmt = null;

        String sql = "insert into Products (title, available, price) values (" + columns.get("title") +", " + columns.get("available")+
                ", " + columns.get("price") + ");";
        int amountOfModifiedRows = 0;
        try {
            stmt = conn.createStatement();
            amountOfModifiedRows = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return amountOfModifiedRows;
    }
}
