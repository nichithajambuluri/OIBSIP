package com.oasis.reservation.database;

import java.sql.*;

public final class DatabaseInitializer {
    private DatabaseInitializer() {}

    public static void initialize() throws SQLException {
        try (Connection c = DatabaseConnection.getConnection();
             Statement s = c.createStatement()) {
            s.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL UNIQUE, password TEXT NOT NULL)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS trains (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "train_number INTEGER NOT NULL UNIQUE, train_name TEXT NOT NULL, " +
                    "source TEXT NOT NULL, destination TEXT NOT NULL)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS reservations (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, pnr TEXT NOT NULL UNIQUE, " +
                    "passenger_name TEXT NOT NULL, train_number INTEGER NOT NULL, " +
                    "train_name TEXT NOT NULL, class_type TEXT NOT NULL, journey_date TEXT NOT NULL, " +
                    "source TEXT NOT NULL, destination TEXT NOT NULL, " +
                    "created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY(train_number) REFERENCES trains(train_number))");
            seedUser(c);
            seedTrains(c);
        }
    }

    private static void seedUser(Connection c) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT OR IGNORE INTO users(username,password) VALUES(?,?)")) {
            ps.setString(1, "admin");
            ps.setString(2, "admin123");
            ps.executeUpdate();
        }
    }

    private static void seedTrains(Connection c) throws SQLException {
        Object[][] trains = {
            {12001, "Deccan Queen", "Mumbai", "Pune"},
            {12002, "Shatabdi Express", "New Delhi", "Bhopal"},
            {12005, "Kalka Shatabdi", "New Delhi", "Kalka"},
            {12009, "Mumbai Central Shatabdi", "Mumbai Central", "Ahmedabad"},
            {12295, "Sanghamitra Express", "KSR Bengaluru", "Danapur"},
            {12603, "Chennai Express", "Chennai", "Coimbatore"},
            {12622, "Tamil Nadu Express", "New Delhi", "Chennai"},
            {12627, "Karnataka Express", "New Delhi", "Bengaluru"},
            {12674, "Cheran Express", "Coimbatore", "Chennai"},
            {12701, "Hussainsagar Express", "Mumbai", "Hyderabad"},
            {12702, "Hussainsagar Express", "Hyderabad", "Mumbai"},
            {12723, "Telangana Express", "Hyderabad", "New Delhi"},
            {12727, "Godavari Express", "Visakhapatnam", "Hyderabad"},
            {12728, "Godavari Express", "Hyderabad", "Visakhapatnam"},
            {12737, "Gowthami Express", "Kakinada", "Hyderabad"},
            {12738, "Gowthami Express", "Hyderabad", "Kakinada"},
            {12760, "Charminar Express", "Chennai", "Hyderabad"},
            {12805, "Janmabhoomi Express", "Visakhapatnam", "Secunderabad"},
            {12806, "Janmabhoomi Express", "Secunderabad", "Visakhapatnam"},
            {12839, "Chennai-Howrah Mail", "Chennai", "Howrah"},
            {12864, "Yeshvantpur-Howrah SF", "Yeshvantpur", "Howrah"},
            {12951, "Mumbai Rajdhani", "Mumbai", "New Delhi"},
            {12952, "Mumbai Rajdhani", "New Delhi", "Mumbai"},
            {12957, "Swarna Jayanti Rajdhani", "Mumbai Central", "New Delhi"},
            {12958, "Swarna Jayanti Rajdhani", "New Delhi", "Mumbai Central"}
        };
        String sql = "INSERT OR IGNORE INTO trains(train_number,train_name,source,destination) VALUES(?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (Object[] t : trains) {
                ps.setInt(1, (Integer)t[0]);
                ps.setString(2, (String)t[1]);
                ps.setString(3, (String)t[2]);
                ps.setString(4, (String)t[3]);
                ps.executeUpdate();
            }
        }
    }
}
