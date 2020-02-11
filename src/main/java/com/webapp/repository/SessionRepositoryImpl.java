package com.webapp.repository;

import com.webapp.model.Session;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
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

    @Override
    public Session getSessionById(int id) {
        Session session = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("SELECT * FROM Sessions (id) VALUES (" + id + ")");
            rs = stmt.getResultSet();
            session = new Session(rs.getInt(1), rs.getInt(2));
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
        return session;
    }
}
