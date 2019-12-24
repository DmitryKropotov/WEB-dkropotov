package com.project.repositories;

import com.project.models.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private Connection conn = ConnectionSaver.getConnection();

    @Override
    public boolean createUser(String email, String password) {
        if (selectUserByEmail(email).isPresent()) {
            return false;
        }
        Statement stmt = null;
        boolean result;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Users (email, password) VALUES ('" + email + "', '" + password + "');");
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
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
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Users where email = '"  + email + "'");
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), email, rs.getString("password")));
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
        return users.isEmpty() ? Optional.empty(): Optional.ofNullable(users.get(0));
    }

}
