/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.WasteLog;
import model.WasteLogDAO;

@WebServlet(urlPatterns = {"/WasteLogServlet"})
public class WasteLogServlet extends HttpServlet {
    private WasteLogDAO wasteLogDAO = new WasteLogDAO();
    private final int simulatedUserId = 1; // Simulating logged-in user #1

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<WasteLog> list = wasteLogDAO.selectLogsByUser(simulatedUserId);
        request.setAttribute("wasteLogs", list);
        request.getRequestDispatcher("waste-log.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                String material = request.getParameter("materialType");
                double weight = Double.parseDouble(request.getParameter("weight"));
                WasteLog newLog = new WasteLog(simulatedUserId, material, weight);
                wasteLogDAO.insertWasteLog(newLog);
            } else if ("delete".equals(action)) {
                int logId = Integer.parseInt(request.getParameter("logId"));
                wasteLogDAO.deleteWasteLog(logId);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("WasteLogServlet");
    }
}