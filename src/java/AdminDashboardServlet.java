/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CollectionRequest;

@WebServlet(urlPatterns = {"/AdminDashboardServlet"})
public class AdminDashboardServlet extends HttpServlet {
    
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ecoloop_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "your_password_here";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CollectionRequest> allRequests = new ArrayList<>();
        // Query ALL entries regardless of individual user constraints
        String sql = "SELECT * FROM collection_requests ORDER BY status DESC, pickup_date ASC";
        
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CollectionRequest req = new CollectionRequest();
                req.setRequestId(rs.getInt("request_id"));
                req.setUserId(rs.getInt("user_id"));
                req.setPickupDate(rs.getDate("pickup_date"));
                req.setTimeSlot(rs.getString("time_slot"));
                req.setLoadSize(rs.getString("load_size"));
                req.setStatus(rs.getString("status"));
                allRequests.add(req);
            }
        } catch (SQLException e) { e.printStackTrace(); }

        request.setAttribute("allRequests", allRequests);
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String sql = "UPDATE collection_requests SET status = 'Completed' WHERE request_id = ?";
        
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        
        response.sendRedirect("AdminDashboardServlet");
    }
}
