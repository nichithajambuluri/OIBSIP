package com.oasis.reservation.dao;

import com.oasis.reservation.database.DatabaseConnection;
import com.oasis.reservation.model.User;
import java.sql.*;

public class UserDAO {
    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT id,username FROM users WHERE username=? AND password=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new User(rs.getInt("id"), rs.getString("username")) : null;
            }
        }
    }
}
