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
import model.ClaimedVoucher;
import model.RewardsDAO;

@WebServlet(urlPatterns = {"/RewardsServlet"})
public class RewardsServlet extends HttpServlet {
    private RewardsDAO rewardsDAO = new RewardsDAO();
    private final int simulatedUserId = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int balance = rewardsDAO.getUserPoints(simulatedUserId);
        int goal = rewardsDAO.getUserGoal(simulatedUserId);
        List<ClaimedVoucher> list = rewardsDAO.selectVouchersByUser(simulatedUserId);

        request.setAttribute("balance", balance);
        request.setAttribute("goal", goal);
        request.setAttribute("vouchers", list);
        request.getRequestDispatcher("rewards.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("createClaim".equals(action)) {
                String rewardId = request.getParameter("rewardId");
                String rewardName = "RM10 Grocery Voucher";
                int points = 500;
                if ("R02".equals(rewardId)) {
                    rewardName = "Free Reusable Coffee Cup";
                    points = 1200;
                }
                boolean success = rewardsDAO.claimVoucher(simulatedUserId, rewardName, points);
                if(!success) {
                    request.getSession().setAttribute("errorMsg", "Insufficient EcoPoints!");
                }
            } else if ("updateGoal".equals(action)) {
                int newGoal = Integer.parseInt(request.getParameter("monthlyGoal"));
                rewardsDAO.updateGoal(simulatedUserId, newGoal);
            } else if ("deleteClaim".equals(action)) {
                int claimId = Integer.parseInt(request.getParameter("claimId"));
                int pointsRefunded = Integer.parseInt(request.getParameter("pointsSpent"));
                rewardsDAO.removeVoucher(claimId, simulatedUserId, pointsRefunded);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("RewardsServlet");
    }
}