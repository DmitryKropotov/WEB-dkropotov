package com.project.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionRepositoryImpl implements SessionRepository {

    private Connection conn = ConnectionSaver.getConnection();

    @Override
    public int createUserSessionAndGetItsId(int userId) {
        Statement stmt = null;
        ResultSet rs = null;
        int sessionId = -1;
        try {
            stmt = conn.createStatement();
            stmt.execute("INSERT INTO Sessions (UserId) VALUES (" + userId + ");");
            rs = stmt.getGeneratedKeys();
            while (rs.next()) {
                sessionId = rs.getInt("last_insert_rowid()");
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
        return sessionId;
    }
}
