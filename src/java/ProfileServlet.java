/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; // Added Import

@WebServlet(urlPatterns = {"/ProfileServlet"})
public class ProfileServlet extends HttpServlet {
    
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ecoloop_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = ""; // Update with your MySQL password

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. AUTHENTICATION GUARD: Verify active login session context
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp"); // Intercept guests
            return; // Terminate execution
        }
        
        // Extract the dynamic user ID token from session attributes
        int currentUserId = (Integer) session.getAttribute("userId");
        
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            // Retrieve complete user profile details
            String sql = "SELECT username, email, phone_number, address, total_points, role FROM users WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, currentUserId); // CHANGED: Replaced simulatedUserId with currentUserId
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    // FIXED LAYER BINDING: Package attributes directly into a User model bean for profile.jsp
                    model.User user = new model.User();
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setAddress(rs.getString("address"));
                    user.setTotalPoints(rs.getInt("total_points"));
                    user.setRole(rs.getString("role"));
                    
                    // Bind the container object expected by your form fields
                    request.setAttribute("userObj", user);
                    
                    // Keep loose attributes intact for global shared header summaries
                    request.setAttribute("username", user.getUsername());
                    request.setAttribute("balance", user.getTotalPoints());
                    request.setAttribute("role", user.getRole());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Forward safely to your profile.jsp view template
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 2. AUTHENTICATION GUARD: Protect database modifications from session timeouts
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int currentUserId = (Integer) session.getAttribute("userId");
        
        // Process Profile Updates (Form Submissions)
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        String sql = "UPDATE users SET email = ?, phone_number = ?, address = ? WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setInt(4, currentUserId); // CHANGED: Replaced simulatedUserId with currentUserId
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Redirect back to refresh updated information seamlessly
        response.sendRedirect("ProfileServlet");
    }
}