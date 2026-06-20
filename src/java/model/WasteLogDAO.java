/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adamr
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WasteLogDAO {
    
    // Database connection details
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ecoloop_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "your_password_here"; // Update with your password

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 1. CREATE: Insert a new waste log
    public void insertWasteLog(WasteLog log) throws SQLException {
        String sql = "INSERT INTO waste_logs (user_id, material_type, weight_kg) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, log.getUserId());
            statement.setString(2, log.getMaterialType());
            statement.setDouble(3, log.getWeightKg());
            statement.executeUpdate();
        }
    }

    // 2. RETRIEVE: Get all logs for a specific user
    public List<WasteLog> selectLogsByUser(int userId) {
        List<WasteLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM waste_logs WHERE user_id = ? ORDER BY log_date DESC";
        
        try (Connection conn = getConnection(); 
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                WasteLog log = new WasteLog();
                log.setLogId(rs.getInt("log_id"));
                log.setUserId(rs.getInt("user_id"));
                log.setMaterialType(rs.getString("material_type"));
                log.setWeightKg(rs.getDouble("weight_kg"));
                log.setLogDate(rs.getTimestamp("log_date"));
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // 3. UPDATE: Edit weight or material type
    public boolean updateWasteLog(WasteLog log) throws SQLException {
        String sql = "UPDATE waste_logs SET material_type = ?, weight_kg = ? WHERE log_id = ?";
        boolean rowUpdated;
        try (Connection conn = getConnection(); 
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, log.getMaterialType());
            statement.setDouble(2, log.getWeightKg());
            statement.setInt(3, log.getLogId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // 4. DELETE: Remove a log entry
    public boolean deleteWasteLog(int logId) throws SQLException {
        String sql = "DELETE FROM waste_logs WHERE log_id = ?";
        boolean rowDeleted;
        try (Connection conn = getConnection(); 
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, logId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
