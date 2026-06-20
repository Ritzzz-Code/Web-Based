/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author adamr
 */
@WebServlet(urlPatterns = {"/RewardsServlet"})
public class RewardsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RewardsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RewardsServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // TODO: 1. Get current user's point balance from DB
        // TODO: 2. Get list of claimed vouchers
        // TODO: 3. request.setAttribute("balance", userPoints);
        
        request.getRequestDispatcher("rewards.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "createClaim":
                    String rewardId = request.getParameter("rewardId");
                    // TODO: Check if user has enough points. 
                    // If yes -> Insert into user_rewards DB & deduct points.
                    System.out.println("Claimed Reward: " + rewardId);
                    break;
                    
                case "updateGoal":
                    int newGoal = Integer.parseInt(request.getParameter("monthlyGoal"));
                    // TODO: Update user's profile with the new monthly point goal.
                    System.out.println("Updated Monthly Goal to: " + newGoal);
                    break;
                    
                case "deleteClaim":
                    String claimId = request.getParameter("claimId");
                    // TODO: Delete the voucher from user_rewards DB & refund points.
                    System.out.println("Removed/Refunded Voucher: " + claimId);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to refresh UI
        response.sendRedirect("RewardsServlet");
    }
}
