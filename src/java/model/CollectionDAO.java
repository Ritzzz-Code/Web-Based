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

public class CollectionDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ecoloop_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = ""; // Update with your password

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

    public void insertRequest(CollectionRequest request) throws SQLException {
        String sql = "INSERT INTO collection_requests (user_id, pickup_date, time_slot, load_size) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, request.getUserId());
            ps.setDate(2, request.getPickupDate());
            ps.setString(3, request.getTimeSlot());
            ps.setString(4, request.getLoadSize());
            ps.executeUpdate();
        }
    }

    public List<CollectionRequest> selectRequestsByUser(int userId) {
        List<CollectionRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM collection_requests WHERE user_id = ? ORDER BY pickup_date ASC";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectionRequest req = new CollectionRequest();
                req.setRequestId(rs.getInt("request_id"));
                req.setUserId(rs.getInt("user_id"));
                req.setPickupDate(rs.getDate("pickup_date"));
                req.setTimeSlot(rs.getString("time_slot"));
                req.setLoadSize(rs.getString("load_size"));
                req.setStatus(rs.getString("status"));
                list.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteRequest(int requestId) throws SQLException {
        String sql = "DELETE FROM collection_requests WHERE request_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        }
    }
}
