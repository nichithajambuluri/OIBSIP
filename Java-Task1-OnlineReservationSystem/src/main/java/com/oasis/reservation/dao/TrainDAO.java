package com.oasis.reservation.dao;

import com.oasis.reservation.database.DatabaseConnection;
import com.oasis.reservation.model.Train;
import java.sql.*;

public class TrainDAO {
    public Train findByNumber(int trainNumber) throws SQLException {
        String sql = "SELECT id,train_number,train_name,source,destination FROM trains WHERE train_number=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, trainNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Train(rs.getInt("id"), rs.getInt("train_number"),
                        rs.getString("train_name"), rs.getString("source"), rs.getString("destination"));
            }
        }
    }
    public java.util.List<Train> findAll() throws SQLException {
        java.util.List<Train> result = new java.util.ArrayList<>();
        String sql = "SELECT id,train_number,train_name,source,destination FROM trains ORDER BY train_number";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new Train(rs.getInt("id"), rs.getInt("train_number"),
                        rs.getString("train_name"), rs.getString("source"), rs.getString("destination")));
            }
        }
        return result;
    }

}
