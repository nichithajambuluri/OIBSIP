package com.oasis.reservation.dao;

import com.oasis.reservation.database.DatabaseConnection;
import com.oasis.reservation.model.Reservation;
import java.sql.*;

public class ReservationDAO {
    public void insert(Reservation r) throws SQLException {
        String sql = "INSERT INTO reservations(pnr,passenger_name,train_number,train_name,class_type,journey_date,source,destination) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,r.pnr()); ps.setString(2,r.passengerName()); ps.setInt(3,r.trainNumber());
            ps.setString(4,r.trainName()); ps.setString(5,r.classType()); ps.setString(6,r.journeyDate());
            ps.setString(7,r.source()); ps.setString(8,r.destination()); ps.executeUpdate();
        }
    }

    public Reservation findByPnr(String pnr) throws SQLException {
        String sql = "SELECT id,pnr,passenger_name,train_number,train_name,class_type,journey_date,source,destination FROM reservations WHERE pnr=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,pnr);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return map(rs);
            }
        }
    }

    public java.util.List<Reservation> findAll() throws SQLException {
        String sql = "SELECT id,pnr,passenger_name,train_number,train_name,class_type,journey_date,source,destination " +
                "FROM reservations ORDER BY id DESC";
        java.util.List<Reservation> results = new java.util.ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) results.add(map(rs));
        }
        return results;
    }

    public boolean deleteByPnr(String pnr) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM reservations WHERE pnr=?")) {
            ps.setString(1,pnr);
            return ps.executeUpdate() == 1;
        }
    }

    private Reservation map(ResultSet rs) throws SQLException {
        return new Reservation(rs.getInt("id"),rs.getString("pnr"),rs.getString("passenger_name"),
                rs.getInt("train_number"),rs.getString("train_name"),rs.getString("class_type"),
                rs.getString("journey_date"),rs.getString("source"),rs.getString("destination"));
    }
}
