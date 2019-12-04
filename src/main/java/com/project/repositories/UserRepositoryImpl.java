package com.project.repositories;

import com.project.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private Connection conn = ConnectionSaver.getConnection();

    @Override
    public String createUser(String email, String password) {
        if (selectUserByEmail(email).isPresent()) {
            return "User with email " + email + " already exists";
        }
        Statement stmt = null;
        String result = "Status ";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Users (email, password) VALUES ('" + email + "', '" + password + "');");
            result = result + "200";//OK code
        } catch (SQLException e) {
            e.printStackTrace();
            result = result + "409";//error code
        } finally {
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
    public Optional<User> selectUserByEmail(String email) {
        List<User> users = new ArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users where email = '"  + email + "'");
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), email, rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users.isEmpty() ? Optional.empty(): Optional.ofNullable(users.get(0));
    }

}
