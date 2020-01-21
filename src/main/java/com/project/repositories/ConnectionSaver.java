package com.project.repositories;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class ConnectionSaver {

    private static Connection conn = null;

    static Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        try {
            // db parameters
            String url = "jdbc:sqlite:shop.db";
            DriverManager.registerDriver(new JDBC());
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTableInDb("Users", Arrays.asList("id INTEGER NOT NULL", "email", "password", "PRIMARY KEY(\"id\")"));
        createTableInDb("Products", Arrays.asList("id INTEGER NOT NULL", "title", "available Integer", "price Numeric", "PRIMARY KEY(\"id\")"));
        createTableInDb("Sessions", Arrays.asList("id INTEGER NOT NULL", "UserId INTEGER NOT NULL", "PRIMARY KEY(\"id\")"));

        return conn;
    }

    private static void createTableInDb(String tableName, List<String> columns) {
        Statement statement  = null;
        String sql = "create table if not exists " + tableName + " (";
        for (int i = 0; i < columns.size(); i++) {
            sql = sql + columns.get(i);
            if (i < columns.size() - 1) {
                sql = sql + ", ";
            }
        }
        sql = sql + ");";

        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
