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
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ecoloop_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = ""; // Update with your password

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        // Handle Logout operations safely
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Clear out all session data attributes
            }
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userParam = request.getParameter("username");
        String passParam = request.getParameter("password");
        
        String sql = "SELECT user_id, username, role FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userParam);
            ps.setString(2, passParam);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Login Success: Initialize HTTP Session Container state attributes
                HttpSession session = request.getSession();
                session.setAttribute("userId", rs.getInt("user_id"));
                session.setAttribute("username", rs.getString("username"));
                session.setAttribute("userRole", rs.getString("role"));
                
                // Route user to the Profile welcoming page dashboard context loop
                response.sendRedirect("ProfileServlet");
            } else {
                // Failure state: Return invalid matching validation tokens
                request.getSession().setAttribute("loginError", "Invalid username or password match configuration parameters.");
                response.sendRedirect("login.jsp");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
