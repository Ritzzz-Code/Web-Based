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

        // Retrieve the hidden form parameter telling the controller what operation to run
        String action = request.getParameter("action");

        try {
            // CRITICAL FIX: The switch statement must wrap around your cases
            switch (action) {
                case "create":
                    String material = request.getParameter("materialType");
                    double weight = Double.parseDouble(request.getParameter("weight"));

                    // 1. Create and save the waste log entry
                    model.WasteLog newLog = new model.WasteLog(simulatedUserId, material, weight);
                    wasteLogDAO.insertWasteLog(newLog);

                    // 2. Calculate points based on material type rules
                    int pointsPerKg = 0;
                    switch (material) {
                        case "Plastic":
                            pointsPerKg = 10;
                            break;
                        case "Paper":
                            pointsPerKg = 5;
                            break;
                        case "Glass":
                            pointsPerKg = 15;
                            break;
                        case "Metal":
                            pointsPerKg = 20;
                            break;
                    }
                    int totalCalculatedPoints = (int) (weight * pointsPerKg);

                    // 3. Connect to RewardsDAO to credit the user's account balance
                    model.RewardsDAO rewardsDAO = new model.RewardsDAO();
                    rewardsDAO.addCustomerPoints(simulatedUserId, totalCalculatedPoints);

                    System.out.println("Successfully logged " + weight + "kg of " + material + ". Credited " + totalCalculatedPoints + " EcoPoints to User ID: " + simulatedUserId);
                    break;

                case "delete":
                    int logId = Integer.parseInt(request.getParameter("logId"));
                    wasteLogDAO.deleteWasteLog(logId);
                    System.out.println("Deleted log ID: " + logId);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // After completing the action, redirect back to doGet to refresh the UI view data table
        response.sendRedirect("WasteLogServlet");
    }
}
