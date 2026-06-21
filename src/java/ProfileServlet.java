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

@WebServlet(urlPatterns = {"/ProfileServlet"})
public class ProfileServlet extends HttpServlet {
    
    private final String jdbcURL = "jdbc:mysql://localhost:3306/ecoloop_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "your_password_here"; // Update with your MySQL password
    private final int simulatedUserId = 1; // Simulated active user

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            // Retrieve complete user profile details
            String sql = "SELECT username, email, phone_number, address, total_points, role FROM users WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, simulatedUserId);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    // Inject parameters explicitly into request scope attributes
                    request.setAttribute("username", rs.getString("username"));
                    request.setAttribute("email", rs.getString("email"));
                    request.setAttribute("phone", rs.getString("phone_number"));
                    request.setAttribute("address", rs.getString("address"));
                    request.setAttribute("balance", rs.getInt("total_points"));
                    request.setAttribute("role", rs.getString("role"));
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
            ps.setInt(4, simulatedUserId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Redirect back to refresh updated information seamlessly
        response.sendRedirect("ProfileServlet");
    }
}
