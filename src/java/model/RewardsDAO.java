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

public class RewardsDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ecoloop_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

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

    // Add this method inside model/RewardsDAO.java
    public void addCustomerPoints(int userId, int pointsToAdd) throws SQLException {
        String sql = "UPDATE users SET total_points = total_points + ? WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, pointsToAdd);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    public int getUserPoints(int userId) {
        String sql = "SELECT total_points FROM users WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_points");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getUserGoal(int userId) {
        String sql = "SELECT monthly_goal FROM users WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("monthly_goal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 2000;
    }

    public void updateGoal(int userId, int newGoal) throws SQLException {
        String sql = "UPDATE users SET monthly_goal = ? WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newGoal);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public boolean claimVoucher(int userId, String rewardName, int pointsRequired) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Transaction-safe

            // Verify points
            int currentPoints = getUserPoints(userId);
            if (currentPoints < pointsRequired) {
                return false;
            }

            // Deduct points
            String updatePointsSql = "UPDATE users SET total_points = total_points - ? WHERE user_id = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(updatePointsSql)) {
                ps1.setInt(1, pointsRequired);
                ps1.setInt(2, userId);
                ps1.executeUpdate();
            }

            // Insert Voucher
            String code = "ECO-" + (int) (Math.random() * 9000 + 1000);
            String insertVoucherSql = "INSERT INTO claimed_vouchers (user_id, voucher_code, reward_name, points_spent) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps2 = conn.prepareStatement(insertVoucherSql)) {
                ps2.setInt(1, userId);
                ps2.setString(2, code);
                ps2.setString(3, rewardName);
                ps2.setInt(4, pointsRequired);
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public List<ClaimedVoucher> selectVouchersByUser(int userId) {
        List<ClaimedVoucher> list = new ArrayList<>();
        String sql = "SELECT * FROM claimed_vouchers WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClaimedVoucher v = new ClaimedVoucher();
                v.setClaimId(rs.getInt("claim_id"));
                v.setUserId(rs.getInt("user_id"));
                v.setVoucherCode(rs.getString("voucher_code"));
                v.setRewardName(rs.getString("reward_name"));
                v.setPointsSpent(rs.getInt("points_spent"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void removeVoucher(int claimId, int userId, int pointsRefunded) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String deleteSql = "DELETE FROM claimed_vouchers WHERE claim_id = ? AND user_id = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(deleteSql)) {
                ps1.setInt(1, claimId);
                ps1.setInt(2, userId);
                ps1.executeUpdate();
            }

            String refundSql = "UPDATE users SET total_points = total_points + ? WHERE user_id = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(refundSql)) {
                ps2.setInt(1, pointsRefunded);
                ps2.setInt(2, userId);
                ps2.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
