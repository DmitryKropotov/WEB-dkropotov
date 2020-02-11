package com.webapp.repository;

import com.webapp.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
public class ProductsRepositoryImpl implements ProductsRepository {

    private Connection conn = ConnectionSaver.getConnection();

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
