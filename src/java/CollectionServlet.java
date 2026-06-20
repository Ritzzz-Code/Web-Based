/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CollectionDAO;
import model.CollectionRequest;

@WebServlet(urlPatterns = {"/CollectionServlet"})
public class CollectionServlet extends HttpServlet {
    private CollectionDAO collectionDAO = new CollectionDAO();
    private final int simulatedUserId = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CollectionRequest> list = collectionDAO.selectRequestsByUser(simulatedUserId);
        request.setAttribute("collections", list);
        request.getRequestDispatcher("collection.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                Date date = Date.valueOf(request.getParameter("pickupDate"));
                String time = request.getParameter("timeSlot");
                String load = request.getParameter("loadSize");
                CollectionRequest req = new CollectionRequest(simulatedUserId, date, time, load);
                collectionDAO.insertRequest(req);
            } else if ("delete".equals(action)) {
                int requestId = Integer.parseInt(request.getParameter("requestId"));
                collectionDAO.deleteRequest(requestId);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        response.sendRedirect("CollectionServlet");
    }
}